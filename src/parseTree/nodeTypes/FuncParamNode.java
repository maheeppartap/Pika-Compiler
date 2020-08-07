package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import semanticAnalyzer.types.*;
import tokens.Token;

public class FuncParamNode extends ParseNode {

	public FuncParamNode(Token token) {
		super(token);
	}

	public FuncParamNode(ParseNode node) {
		super(node);
	}

	
	////////////////////////////////////////////////////////////
	// convenience factory
	
	public static FuncParamNode withChildren(Token token, Type type, ParseNode identifier) {
		FuncParamNode node = new FuncParamNode(token);
		node.setType(type);
		node.appendChild(identifier);
		return node;
	}
	
	public static FuncParamNode withChildren(Token token, Type type, ParseNode identifier, ParseNode child) {
		FuncParamNode node = new FuncParamNode(token);
		node.setType(type);
		node.appendChild(identifier);
		node.appendChild(child);
		return node;
	}
	
	
	///////////////////////////////////////////////////////////
	// boilerplate for visitors
			
	public void accept(ParseNodeVisitor visitor) {
		visitor.visitEnter(this);
		visitChildren(visitor);
		visitor.visitLeave(this);
	}
}
