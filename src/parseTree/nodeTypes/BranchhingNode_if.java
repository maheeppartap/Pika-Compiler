package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import tokens.LextantToken;
import tokens.Token;

public class BranchhingNode_if extends ParseNode {
	
	public BranchhingNode_if(Token token) {
		super(token);
		assert(token.isLextant(Keyword.IF));
	}
	
	public BranchhingNode_if(ParseNode node) {
		super(node);
		initChildren();
	}
	
	public BranchhingNode_if(Token token, ParseNode condition, ParseNode blockStatement) {
		super(token);
		assert(token.isLextant(Keyword.IF));
		
		this.appendChild(condition);
		this.appendChild(blockStatement);
	}
	
	

	public Lextant getDeclarationType() {
		return lextantToken().getLextant();
	}
	public LextantToken lextantToken() {
		return (LextantToken)token;
	}	
	
	

	public static BranchhingNode_if withElse(Token token, ParseNode condition, ParseNode blockStatement, ParseNode elseStatement) {
		BranchhingNode_if node = new BranchhingNode_if(token, condition, blockStatement);
		node.appendChild(elseStatement);
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
