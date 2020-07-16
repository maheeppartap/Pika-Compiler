package symbolTable;


public interface MemoryAllocator {
	public String getBaseAddress();
	public MemoryLocation allocate(int sizeInBytes);
	public void saveState();
	public void restoreState();
	public int getMaxAllocatedSize();
}
