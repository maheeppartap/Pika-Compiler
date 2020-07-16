package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import tokens.LextantToken;
import tokens.Token;

public class While_loop_Node extends ParseNode {
	String loopLabel;
	String bodyLabel;
	String joinLabel;

	// unused
	public While_loop_Node(Token token) {
		super(token);
		assert(token.isLextant(Keyword.WHILE));
	}

	// unused
	public While_loop_Node(ParseNode node) {
		super(node);
		initChildren();
	}
	
	public While_loop_Node(Token token, ParseNode condition, ParseNode blockStatement) {
		super(token);
		assert(token.isLextant(Keyword.WHILE));
		
		this.appendChild(condition);
		this.appendChild(blockStatement);
	}
	
	
	////////////////////////////////////////////////////////////
	// attributes
	
	public Lextant getDeclarationType() {
		return lextantToken().getLextant();
	}
	public LextantToken lextantToken() {
		return (LextantToken)token;
	}
	
	public void setLabels(String loop, String body, String join) {
		this.loopLabel = loop;
		this.bodyLabel = body;
		this.joinLabel = join;
	}
	public String getLoopLabel() {
		return this.loopLabel;
	}
	public String getBodyLabel() {
		return this.bodyLabel;
	}
	public String getJoinLabel() {
		return this.joinLabel;
	}
	
	
	///////////////////////////////////////////////////////////
	// boilerplate for visitors
			
	public void accept(ParseNodeVisitor visitor) {
		visitor.visitEnter(this);
		visitChildren(visitor);
		visitor.visitLeave(this);
	}
}
