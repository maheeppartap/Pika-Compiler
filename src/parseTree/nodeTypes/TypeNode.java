package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import lexicalAnalyzer.Lextant;
import semanticAnalyzer.signatures.FunctionSignature;
import semanticAnalyzer.types.Type;
import tokens.LextantToken;
import tokens.Token;

public class TypeNode extends ParseNode {

    public TypeNode(Token token) {
        super(token);
        assert(token instanceof LextantToken);
    }

    public TypeNode(ParseNode node) {
        super(node);
    }



    ////////////////////////////////////////////////////////////
    // attributes
    private FunctionSignature signature = FunctionSignature.nullInstance();
    public final FunctionSignature getSignature(){
        return signature;
    }
    public final void setSignature(FunctionSignature signature){
        this.signature = signature;
    }

    public Lextant getOperator() {
        return lextantToken().getLextant();
    }
    public LextantToken lextantToken() {
        return (LextantToken)token;
    }


    ////////////////////////////////////////////////////////////
    // convenience factory

    public static TypeNode withChildren(Token token, ParseNode left, ParseNode right) {
        TypeNode node = new TypeNode(token);
        node.appendChild(left);
        node.appendChild(right);
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
