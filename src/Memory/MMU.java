package Memory;

import java.util.ArrayList;
import Process.Process;
import Process.ProcessState;

public class MMU {

    private final int[] availableBlockSizes;
    private MemoryAllocationAlgorithm algorithm;
    private ArrayList<MemorySlot> currentlyUsedMemorySlots;
    private ArrayList<ProcessSlot> processSlots;

    static class ProcessSlot {
        private Process process;
        private MemorySlot slot;

        ProcessSlot(Process process, MemorySlot slot) {
            this.process = process;
            this.slot = slot;
        }

        MemorySlot getSlot() { return slot; }

        ProcessState getProcessState() { return process.getPCB().getState(); }
    }
    
    public MMU(int[] availableBlockSizes, MemoryAllocationAlgorithm algorithm) {
        this.availableBlockSizes = availableBlockSizes;
        this.algorithm = algorithm;
        currentlyUsedMemorySlots = new ArrayList<>();
        processSlots = new ArrayList<>();
    }

    public boolean loadProcessIntoRAM(Process p) {
        freeMemory();
        boolean fit = false;
        /* TODO: you need to add some code here
         * Hint: this should return true if the process was able to fit into memory
         * and false if not
         * Also, this need to check for toDelete Processes*/
        if (algorithm.fitProcess(p, currentlyUsedMemorySlots) != -1) {
            fit = true;
            //processSlots.add(new ProcessSlot())
        }
        
        return fit;
    }

    /**
     * Checks all the stored processes
     * if a process has been terminated, frees the allocated slot
     */
    private void freeMemory() {
        for (ProcessSlot ps : processSlots)
            if (ps.getProcessState() == ProcessState.TERMINATED) {
                /* TODO: you need to add some code here
                 * Hint: This should free the memory allocated by ps*/
            }
    }
}
