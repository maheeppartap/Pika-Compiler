package simpleCodeGenerator;

import parseTree.ParseNode;
import asmCodeGenerator.codeStorage.ASMCodeFragment;

public abstract class simpleCodeGenerator {
    public abstract ASMCodeFragment generate(ParseNode node);
}


