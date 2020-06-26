package promoter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lexicalAnalyzer.*;
import parseTree.ParseNode;
import parseTree.nodeTypes.ArrayNode;
import parseTree.nodeTypes.CastNode;
import parseTree.nodeTypes.OperatorNode;
import semanticAnalyzer.signatures.*;
import semanticAnalyzer.types.*;
import tokens.LextantToken;

public class Promoter {
	LinkedHashMap<ParseNode, List<TypeLiteral>> promotions;

	public Promoter() {
		promotions = new LinkedHashMap<ParseNode, List<TypeLiteral>>();
	}

	public void addPromotion(ParseNode node, List<TypeLiteral> castTypes) {
		promotions.put(node, castTypes);
	}
	public void removePromotion(ParseNode node) {
		promotions.remove(node);
	}

	//todo: TESTING!!!!!
	public boolean promotable(OperatorNode node) {
		Lextant operator = operatorFor(node);
		
		List<Type> childTypes = new ArrayList<Type>();
		node.getChildren().forEach((child) -> childTypes.add(child.getType()));
		FunctionSignature signature = FunctionSignatures.signature(operator, childTypes);
		
		// Check for cast to integer
		for (int i = 0; i < childTypes.size(); i++) {
		    if (childTypes.get(i) == PrimitiveType.CHARACTER) {
		    	childTypes.set(i, PrimitiveType.INTEGER);

				signature = FunctionSignatures.signature(operator, childTypes);
				if (signature.isNull()) {
					childTypes.set(i, PrimitiveType.CHARACTER);
				} else {
					addPromotion(node.child(i), Arrays.asList(TypeLiteral.INTEGER));
				}
		    }
		}
		
		if (signature.accepts(childTypes)) {
			node.setSignature(signature);
			node.setType(signature.resultType());
			return true;
		}
		
		// Check for cast to float
		for (int i = 0; i < childTypes.size(); i++) {
		    if (childTypes.get(i) == PrimitiveType.CHARACTER) {
		    	childTypes.set(i, PrimitiveType.FLOATING);

				signature = FunctionSignatures.signature(operator, childTypes);
				if (signature.isNull()) {
					childTypes.set(i, PrimitiveType.CHARACTER);
				} else {
					addPromotion(node.child(i), Arrays.asList(TypeLiteral.INTEGER, TypeLiteral.FLOATING));
				}
		    }
		    
		    if (childTypes.get(i) == PrimitiveType.INTEGER) {
		    	childTypes.set(i, PrimitiveType.FLOATING);

				signature = FunctionSignatures.signature(operator, childTypes);
				if (signature.isNull()) {
					childTypes.set(i, PrimitiveType.INTEGER);
				} else {
					addPromotion(node.child(i), Arrays.asList(TypeLiteral.FLOATING));
				}
		    }
		}
		
		if (signature.accepts(childTypes)) {
			node.setSignature(signature);
			node.setType(signature.resultType());
			return true;
		}
		
		// Check for cast to rational
		for (int i = 0; i < childTypes.size(); i++) {
		    if (childTypes.get(i) == PrimitiveType.CHARACTER) {
		    	childTypes.set(i, PrimitiveType.RATIONAL);

				signature = FunctionSignatures.signature(operator, childTypes);
				if (signature.isNull()) {
					childTypes.set(i, PrimitiveType.CHARACTER);
				} else {
					addPromotion(node.child(i), Arrays.asList(TypeLiteral.INTEGER, TypeLiteral.RATIONAL));
				}
		    }
		    
		    if (childTypes.get(i) == PrimitiveType.INTEGER) {
		    	childTypes.set(i, PrimitiveType.RATIONAL);

				signature = FunctionSignatures.signature(operator, childTypes);
				if (signature.isNull()) {
					childTypes.set(i, PrimitiveType.INTEGER);
				} else {
					addPromotion(node.child(i), Arrays.asList(TypeLiteral.RATIONAL));
				}
		    }
		}
		
		if (signature.accepts(childTypes)) {
			node.setSignature(signature);
			node.setType(signature.resultType());
			return true;
		}
		
		return false;
	}
	public boolean promotable(ArrayNode node) {
		List<Type> childTypes = new ArrayList<Type>();
		List<Type> castTypes = new ArrayList<Type>();
		
		node.getChildren().forEach((child) -> childTypes.add(child.getType()));
		node.getChildren().forEach((child) -> castTypes.add(child.getType()));
		
		int numType = 0;
		int totalNum = node.nChildren();
		
		// Check for cast to integer
		for (int i = 0; i < childTypes.size(); i++) {			
		    if (childTypes.get(i) == PrimitiveType.CHARACTER) {
		    	castTypes.set(i, PrimitiveType.INTEGER);
				addPromotion(node.child(i), Arrays.asList(TypeLiteral.INTEGER));
		    }
		}

		numType = Collections.frequency(castTypes, PrimitiveType.INTEGER);
		if (numType == totalNum) {
			node.setSubtype(PrimitiveType.INTEGER);
			return true;
		} else {
			promotions.clear();
			castTypes.clear();
			node.getChildren().forEach((child) -> castTypes.add(child.getType()));
		}
		
		// Check for cast to float
		for (int i = 0; i < childTypes.size(); i++) {
		    if (childTypes.get(i) == PrimitiveType.CHARACTER) {
		    	castTypes.set(i, PrimitiveType.FLOATING);
		    	addPromotion(node.child(i), Arrays.asList(TypeLiteral.INTEGER, TypeLiteral.FLOATING));
		    }
		    
		    if (childTypes.get(i) == PrimitiveType.INTEGER) {
		    	castTypes.set(i, PrimitiveType.FLOATING);
				addPromotion(node.child(i), Arrays.asList(TypeLiteral.FLOATING));	//todo: fix whatever warning it's giving me.
		    }
		}

		numType = Collections.frequency(castTypes, PrimitiveType.FLOATING);
		if (numType == totalNum) {
			node.setSubtype(PrimitiveType.FLOATING);
			return true;
		} else {
			promotions.clear();	
			castTypes.clear();
			node.getChildren().forEach((child) -> castTypes.add(child.getType()));
		}
		
		//this was awful programming.
		for (int i = 0; i < childTypes.size(); i++) {
		    if (childTypes.get(i) == PrimitiveType.CHARACTER) {
		    	castTypes.set(i, PrimitiveType.RATIONAL);
		    	addPromotion(node.child(i), Arrays.asList(TypeLiteral.INTEGER, TypeLiteral.RATIONAL));
		    }
		    
		    if (childTypes.get(i) == PrimitiveType.INTEGER) {
		    	castTypes.set(i, PrimitiveType.RATIONAL);
				addPromotion(node.child(i), Arrays.asList(TypeLiteral.RATIONAL));
		    }
		}

		numType = Collections.frequency(castTypes, PrimitiveType.RATIONAL);
		if (numType == totalNum) {
			node.setSubtype(PrimitiveType.RATIONAL);
			return true;
		} else {
			promotions.clear();
			castTypes.clear();
			node.getChildren().forEach((child) -> castTypes.add(child.getType()));
		}
		// :(((
		return false;
	}
	private Lextant operatorFor(ParseNode node) {
		LextantToken token = (LextantToken) node.getToken();
		return token.getLextant();
	}
	
	public void promote() {
		// todo: test this some more.
		for (Map.Entry<ParseNode, List<TypeLiteral>> entry : promotions.entrySet()) { 
			ParseNode node = entry.getKey();
			List<TypeLiteral> casts = entry.getValue();
			
			for (TypeLiteral typeLiteral : casts) {
				ParseNode parentNode = node.getParent();

				List<Type> childTypes = Arrays.asList(node.getType(), typeLiteral);
				FunctionSignature signature = FunctionSignatures.signature(Punctuator.CAST_MID, childTypes);
				CastNode cast = CastNode.withChildren(Punctuator.CAST_MID.prototype(), node, typeLiteral);
				cast.setSignature(signature);
				cast.setType(signature.resultType());
				
				parentNode.replaceChild(node, cast);
				node = cast;
			}
		}
	}
}
