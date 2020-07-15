package asmCodeGenerator.codeGenerator;

import asmCodeGenerator.codeStorage.*;
import semanticAnalyzer.types.*;

public class OpcodeForLoadSCG implements SimpleCodeGenerator {
	public Type type;
	
	public OpcodeForLoadSCG(Type t) {
		type = t;
	}
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		if(type == PrimitiveType.INTEGER || type == TypeLiteral.INTEGER) {
			chunk.add(ASMOpcode.LoadI);
		}
		else if(type == PrimitiveType.FLOATING || type == TypeLiteral.FLOATING) {
			chunk.add(ASMOpcode.LoadF);
		}
		else if(type == PrimitiveType.RATIONAL || type == TypeLiteral.RATIONAL) {
			RationalMemToStackSCG scg = new RationalMemToStackSCG();
			chunk.append(scg.generate());
		}
		else if(type == PrimitiveType.BOOLEAN || type == TypeLiteral.BOOLEAN) {
			chunk.add(ASMOpcode.LoadC);
		}
		else if(type == PrimitiveType.CHARACTER || type == TypeLiteral.CHARACTER) {
			chunk.add(ASMOpcode.LoadC);
		}
		else if(type == PrimitiveType.STRING || type == TypeLiteral.STRING) {
			chunk.add(ASMOpcode.LoadI);
		}
		else if(type == PrimitiveType.NULL_RET || type == TypeLiteral.VOID) {
			
		}
		else if(type instanceof ArrayType) {
			chunk.add(ASMOpcode.LoadI);
		}
		else if(type instanceof LambdaType) {
			chunk.add(ASMOpcode.LoadI);
		}
		else {
			assert false: "Type " + type + " unimplemented in opcodeForLoad()";
		}
		
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}

}
