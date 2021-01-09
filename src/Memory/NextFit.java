package Memory;

import java.util.ArrayList;
import Process.Process;

public class NextFit extends MemoryAllocationAlgorithm {
    
    public NextFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        boolean fit = false;
        int address = -1;
        /* TODO: you need to add some code here
         * Hint: this should return the memory address where the process was
         * loaded into if the process fits. In case the process doesn't fit, it
         * should return -1. */
        for (int x : availableBlockSizes)
            break;


        return address;
    }

}
