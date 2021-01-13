package Memory;

import Process.Process;
import java.util.ArrayList;

public class WorstFit extends MemoryAllocationAlgorithm {
    
    public WorstFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        boolean fit = false;
        int address = -1;
        /* TODO: you need to add some code here
         * Hint: this should return the memory address where the process was
         * loaded into if the process fits. In case the process doesn't fit, it
         * should return -1. */

        int tempAddress = 0;
        int maxRemainingSpace = 0;


        //Iterate all bytes from startSearch until the end and try to locate the process
        while(tempAddress < calculateBlockEnd( availableBlockSizes.length - 1  ) ) {

            int availableSpace = emptySpaceAhead(tempAddress, currentlyUsedMemorySlots);

            //if there is enough space for the process
            if (availableSpace >= p.getMemoryRequirements()) {
                fit = true;

                //if there is a bigger block that the process can fit
                if(maxRemainingSpace < availableSpace){
                    address = tempAddress;
                    tempAddress += availableSpace;
                    maxRemainingSpace = availableSpace;
                }
                else{
                    tempAddress += availableSpace;
                }
            }
            else{
                tempAddress += 1;
            }
        }

        //if the process can fit, we create tha calculated slot
        if(fit){
            currentlyUsedMemorySlots.add( placeProcess( address , p.getMemoryRequirements() ) );
        }

        return address;
    }

}
