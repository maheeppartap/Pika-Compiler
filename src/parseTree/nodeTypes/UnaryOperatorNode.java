package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import lexicalAnalyzer.Lextant;
import lexicalAnalyzer.Punctuator;
import tokens.LextantToken;
import tokens.Token;
import semanticAnalyzer.signatures.FunctionSignature;

public class UnaryOperatorNode extends OperatorNode {
	protected FunctionSignature signature;

	public UnaryOperatorNode(Token token) {
		super(token);
		assert(token instanceof LextantToken);
	}

	public UnaryOperatorNode(ParseNode node) {
		super(node);
	}
	
	
	////////////////////////////////////////////////////////////
	// attributes
	
	public Lextant getOperator() {
		return lextantToken().getLextant();
	}
	public LextantToken lextantToken() {
		return (LextantToken)token;
	}

	public void setSignature(FunctionSignature signature) {
		this.signature = signature;
	}
	public FunctionSignature getSignature() {
		return signature;
	}

	
	////////////////////////////////////////////////////////////
	// convenience factory
	
	public static UnaryOperatorNode withChild(Token token, ParseNode child) {
		UnaryOperatorNode node = new UnaryOperatorNode(token);
		node.appendChild(child);
		return node;
	}
	
	//////////////////////////////////////////////////////////////////////////////
	//delegates
	
	public boolean isComparator() {
		Lextant operator = getOperator();
		return (operator == Punctuator.LESSTHANEQUAL || operator == Punctuator.LESS ||
				operator == Punctuator.EQUAL || operator == Punctuator.NOT_EQUAL ||
				operator == Punctuator.GREATER || operator == Punctuator.GREATERTHANEQUAL);
	}
	
	public boolean isBooleanOperator() {
		Lextant operator = getOperator();
		return (operator == Punctuator.AND || operator == Punctuator.OR);
	}
	
	
	///////////////////////////////////////////////////////////
	// boilerplate for visitors
			
	public void accept(ParseNodeVisitor visitor) {
		visitor.visitEnter(this);
		visitChildren(visitor);
		visitor.visitLeave(this);
	}
}
