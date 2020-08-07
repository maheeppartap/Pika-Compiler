package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import lexicalAnalyzer.Lextant;
import tokens.LextantToken;
import tokens.Token;

public class ControlNode extends ParseNode {

	public ControlNode(Token token) {
		super(token);
		assert(token instanceof LextantToken);
	}

	public ControlNode(ParseNode node) {
		super(node);
	}
	
	

	public Lextant getOperator() {
		return lextantToken().getLextant();
	}
	public LextantToken lextantToken() {
		return (LextantToken)token;
	}

	

	public static ControlNode withChild(Token token) {
		ControlNode node = new ControlNode(token);
		return node;
	}
	

	public void accept(ParseNodeVisitor visitor) {
		visitor.visitEnter(this);
		visitChildren(visitor);
		visitor.visitLeave(this);
	}
}
