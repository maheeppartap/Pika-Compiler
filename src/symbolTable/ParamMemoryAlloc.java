package symbolTable;

import java.util.ArrayList;
import java.util.List;

public class ParamMemoryAlloc implements MemoryAllocator {
	EnterMemoryMethods accessor;
	final int startingOffset;
	int currentOffset;
	int minOffset;
	String baseAddress;
	List<Integer> bookmarks;
	List<MemoryLocation> memoryLocations;
	
	public ParamMemoryAlloc(EnterMemoryMethods accessor, String baseAddress, int startingOffset) {
		this.accessor = accessor;
		this.baseAddress = baseAddress;
		this.startingOffset = startingOffset;
		this.currentOffset = startingOffset;
		this.minOffset = startingOffset;
		this.bookmarks = new ArrayList<Integer>();
		this.memoryLocations = new ArrayList<MemoryLocation>();
	}
	public ParamMemoryAlloc(EnterMemoryMethods accessor, String baseAddress) {
		this(accessor, baseAddress, 0);
	}

	@Override
	public MemoryLocation allocate(int sizeInBytes) {
		currentOffset -= sizeInBytes;
		updateMin();
		
		MemoryLocation mem = new MemoryLocation(accessor, baseAddress, currentOffset);
		memoryLocations.add(mem);
		
		return mem;
	}
	private void updateMin() {
		if(minOffset > currentOffset) {
			minOffset = currentOffset;
		}
	}

	@Override
	public String getBaseAddress() {
		return baseAddress;
	}

	@Override
	public int getMaxAllocatedSize() {
		return startingOffset - minOffset;
	}
	
	@Override
	public void saveState() {
		bookmarks.add(currentOffset);
	}
	@Override
	public void restoreState() {
		assert bookmarks.size() > 0;
		int bookmarkIndex = bookmarks.size()-1;
		currentOffset = (int) bookmarks.remove(bookmarkIndex);
		
		// Update assigned memory locations
		if (bookmarkIndex == 0) {
			int maxAllocatedSize = getMaxAllocatedSize();
			for (MemoryLocation mem : memoryLocations) {
				mem.modifyOffset(maxAllocatedSize);
			}
		}
	}
}