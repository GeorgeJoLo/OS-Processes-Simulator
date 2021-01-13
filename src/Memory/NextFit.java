package Memory;

import Process.Process;
import java.util.ArrayList;

public class NextFit extends MemoryAllocationAlgorithm {

    private int startSearch = 0;
    
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

        //Calculate last byte address
        int lastByte = 0;
        for (int block : availableBlockSizes)
            lastByte += block; //ATTENTION! last byte is out of bounds

        //Iterate all bytes from startSearch until the end and try to locate the process
        for (int i = startSearch; i < lastByte; i++) {
            if (emptySpaceAhead(i, currentlyUsedMemorySlots) >= p.getMemoryRequirements()) {
                fit = true;
                address = i;
                startSearch = i + p.getMemoryRequirements();
                currentlyUsedMemorySlots.add(placeProcess(i, p.getMemoryRequirements()));
                break;
            }
        }

        //Iterate all bytes from start until startSearch and try to locate the process
        if (!fit)
            for (int i = 0; i < startSearch; i++) {
                if (emptySpaceAhead(i, currentlyUsedMemorySlots) >= p.getMemoryRequirements()) {
                    fit = true;
                    address = i;
                    startSearch = i + p.getMemoryRequirements();
                    currentlyUsedMemorySlots.add(placeProcess(i, p.getMemoryRequirements()));
                    break;
                }
            }


        return address;
    }

}
