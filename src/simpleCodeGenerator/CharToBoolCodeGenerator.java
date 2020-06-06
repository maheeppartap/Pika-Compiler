package simpleCodeGenerator;
import asmCodeGenerator.codeStorage.ASMCodeFragment;
import asmCodeGenerator.codeStorage.ASMOpcode;
import asmCodeGenerator.runtime.RunTime;
import parseTree.ParseNode;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

import static asmCodeGenerator.codeStorage.ASMOpcode.*;

public class CharToBoolCodeGenerator extends simpleCodeGenerator{
    public ASMCodeFragment generate(ParseNode node){
        ASMCodeFragment fragment = new ASMCodeFragment(ASMCodeFragment.CodeType.GENERATES_VALUE);
        return fragment;
    }
}
