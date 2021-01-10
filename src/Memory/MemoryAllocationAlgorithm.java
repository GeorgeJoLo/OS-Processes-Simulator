package Memory;

import java.util.ArrayList;
import Process.Process;

public abstract class MemoryAllocationAlgorithm {

    protected final int[] availableBlockSizes;

    public MemoryAllocationAlgorithm(int[] availableBlockSizes) {
        this.availableBlockSizes = availableBlockSizes;
    }

    public abstract int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots);

    /**
     * Calculates the free space after the pointer and before the end of the block
     * or a used Memory Slot
     * @param currentlyUsedMemorySlots the used memory
     * @param pointer the start point
     * @return the empty bytes
     */
    protected int emptySpaceAhead(int pointer, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        if (!isFree(pointer, currentlyUsedMemorySlots)) return 0;

        //Find the end of the block the pointer is
        int rightBound = calculateBlockEnd(allocateBlock(pointer)) + 1;

        //Find the closest, to the pointer, MemorySlot inside the block
        for (MemorySlot x : currentlyUsedMemorySlots)
            if (x.getStart() < rightBound && x.getStart() > pointer)
                rightBound = x.getStart();

        return rightBound - pointer;
    }

    protected MemorySlot placeProcess( int start , int size ){
        int blockToPlaceProcess = allocateBlock(start);
        int blockStart = calculateBlockStart( blockToPlaceProcess);
        int blockEnd   = calculateBlockEnd( blockToPlaceProcess);
        return new MemorySlot(start, start + size - 1, blockStart , blockEnd);
    }



    private int allocateBlock(int start){
        int blockId = 0;
        int currentSize = 0;
        for(int blockSize: availableBlockSizes){
            currentSize += blockSize;
            if(start < currentSize){
                return blockId;
            }
            blockId += 1;
        }
        return -1;
    }

    private int calculateBlockStart(int blockId){
        int start = 0;
        for(int i = 0; i< blockId;i++){
            start += availableBlockSizes[i];
        }
        return start;
    }

    private int calculateBlockEnd(int blockId){
        int end = 0;
        for(int i = 0; i < blockId; i++){
            end += availableBlockSizes[i];
        }
        end += availableBlockSizes[blockId] - 1;
        return end;
    }

    private boolean isFree(int slot, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        for (MemorySlot x : currentlyUsedMemorySlots)
            if (x.getStart() <= slot && x.getEnd() >= slot)
                return false;
        return true;
    }
}
