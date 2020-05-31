package simpleCodeGenerator;

import asmCodeGenerator.codeStorage.ASMCodeFragment;
import asmCodeGenerator.codeStorage.ASMOpcode;
import asmCodeGenerator.runtime.RunTime;
import parseTree.ParseNode;
import simpleCodeGenerator.simpleCodeGenerator;

import static asmCodeGenerator.codeStorage.ASMOpcode.*;

public class FloatingDivideCodeGenerator extends simpleCodeGenerator {
    public ASMCodeFragment generate(ParseNode node){
        ASMCodeFragment fragment = new ASMCodeFragment(ASMCodeFragment.CodeType.GENERATES_VALUE);
        fragment.add(Duplicate);
        fragment.add(JumpFZero, RunTime.FLOATING_DIVIDE_BY_ZERO_RUNTIME_ERROR);
        fragment.add(FDivide);
        return fragment;
    }
}
