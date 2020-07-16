package symbolTable;

import asmCodeGenerator.codeStorage.ASMCodeFragment;
import static asmCodeGenerator.codeStorage.ASMOpcode.*;

public enum EnterMemoryMethods {
	NULL_ACCESS () {
		@Override
		public void generateAddress(ASMCodeFragment code, String baseAddress, int offset, String comment) {
			code.add(PushI, 0, comment);
		}
	},		
	
	// base is the label of the start of the memory block.
	DIRECT_ACCESS_BASE() {
		@Override
		protected void generateBaseAddress(ASMCodeFragment code, String baseAddress) {
			code.add(PushD, baseAddress);
		}
	},
	
	INDIRECT_ACCESS_BASE() {
		@Override
		protected void generateBaseAddress(ASMCodeFragment code, String baseAddress) {
			code.add(PushD, baseAddress);
			code.add(LoadI);
		}
	};	
	

	public void generateAddress(ASMCodeFragment code, String baseAddress, int offset, String comment) {
		generateBaseAddress(code, baseAddress);
		addOffsetASM(code, offset, comment);
	}
	private void addOffsetASM(ASMCodeFragment code, int offset, String comment) {
		code.add(PushI, offset);
		code.add(Add, "", comment);
	}		
	protected void generateBaseAddress(ASMCodeFragment code, String baseAddress) {}

}
