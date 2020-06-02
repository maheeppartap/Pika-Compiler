package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import lexicalAnalyzer.Lextant;
import tokens.LextantToken;
import tokens.Token;

public class AssignmentStatementNode extends ParseNode {

    public AssignmentStatementNode(Token token) {
        super(token);

    }

    public AssignmentStatementNode(ParseNode node) {
        super(node);
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

    public static AssignmentStatementNode withChildren(Token token, ParseNode declaredName, ParseNode initializer) {
        AssignmentStatementNode node = new AssignmentStatementNode(token);
        node.appendChild(declaredName);
        node.appendChild(initializer);
        return node;
    }


    public void setME(ParseNode d){
        setType(d.getType());
    }
    ///////////////////////////////////////////////////////////
    // boilerplate for visitors

    public void accept(ParseNodeVisitor visitor) {
        visitor.visitEnter(this);
        visitChildren(visitor);
        visitor.visitLeave(this);
    }
}
