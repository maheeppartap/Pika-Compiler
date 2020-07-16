package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import tokens.LextantToken;
import tokens.Token;

public class If_else_statements_node extends ParseNode {
	
	public If_else_statements_node(Token token) {
		super(token);
		assert(token.isLextant(Keyword.IF));
	}
	
	public If_else_statements_node(ParseNode node) {
		super(node);
		initChildren();
	}
	
	public If_else_statements_node(Token token, ParseNode condition, ParseNode blockStatement) {
		super(token);
		assert(token.isLextant(Keyword.IF));
		
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
	
	
	////////////////////////////////////////////////////////////
	// convenience factory
	
	public static If_else_statements_node withElse(Token token, ParseNode condition, ParseNode blockStatement, ParseNode elseStatement) {
		If_else_statements_node node = new If_else_statements_node(token, condition, blockStatement);
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
