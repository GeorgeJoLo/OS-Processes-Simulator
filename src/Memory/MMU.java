package Memory;

import Process.Process;
import Process.ProcessState;

import java.util.ArrayList;

public class MMU {

    private final int[] availableBlockSizes;
    private MemoryAllocationAlgorithm algorithm;
    private ArrayList<MemorySlot> currentlyUsedMemorySlots;
    private ArrayList<ProcessSlot> processSlots = new ArrayList<>();   // here we store the slots that are
                                                                       // occupied by every process

    /**
     * A ProcessSlot is the MemorySlot (slot) that stores
     * a specific Process (process)
     */
    static class ProcessSlot {
        private Process process;
        private MemorySlot slot;

        ProcessSlot(Process process, MemorySlot slot) {
            this.process = process;
            this.slot = slot;
        }

        /**
         * @return the state of the process stored in slot
         */
        ProcessState getProcessState() { return process.getPCB().getState(); }
    }

    public MMU(int[] availableBlockSizes, MemoryAllocationAlgorithm algorithm) {
        this.availableBlockSizes = availableBlockSizes;
        this.algorithm = algorithm;
        this.currentlyUsedMemorySlots = new ArrayList<>();
    }

    /**
     * Tries to fit the Process p to the given RAM (availableBlocks)
     * @param p a Process need to be stored into RAM
     * @return true if p fits into RAM
     */
    public boolean loadProcessIntoRAM(Process p) {
        boolean fit = false;
        /* TODO: you need to add some code here
         * Hint: this should return true if the process was able to fit into memory
         * and false if not
         * Also, this need to check for toDelete Processes*/

        // Delete all terminated processes
        freeMemory();

        // Check if process can fit in RAM if it's empty
        if (p.getMemoryRequirements() > max()) {
            p.getPCB().setState(ProcessState.TERMINATED, -1);
            return false;
        }

        // Try to fit the process p into RAM
        if (algorithm.fitProcess(p, currentlyUsedMemorySlots) != -1) {
            fit = true;
            processSlots.add(new ProcessSlot(p, currentlyUsedMemorySlots.get(currentlyUsedMemorySlots.size() - 1)));
        }

        /*
        // Auxiliary print
        if (fit) {
            System.out.print("In Memory Process ID: " + p.getPCB().getPid() + " | ");
            printSlots();
        } else {
            System.out.println("Process ID: " + p.getPCB().getPid() + " could not fit.");
        }
         */

        return fit;
    }

    /**
     * Checks all the stored processes
     * if a process has been terminated, frees the allocated slot
     */
    private void freeMemory() {
        for (int i = 0; i < processSlots.size(); i++)
            if (processSlots.get(i).getProcessState() == ProcessState.TERMINATED) {
                /* TODO: you need to add some code here
                 * Hint: This should free the memory allocated by ps*/
                currentlyUsedMemorySlots.remove(processSlots.get(i).slot);
                processSlots.remove(i);
                i--;
            }
    }

    /**
     * @return the size of the max available block
     */
    int max () {
        int returnMax = availableBlockSizes[0];
        for (int memoryBlockSize : availableBlockSizes) {
            if (memoryBlockSize > returnMax) {
                returnMax = memoryBlockSize;
            }
        }

        return returnMax;
    }

    /**
     * Auxiliary print
     */
    void printSlots() {
        System.out.print("Used Slots: ");
        if (currentlyUsedMemorySlots.isEmpty()) System.out.println("None");
        else {
            for (MemorySlot x : currentlyUsedMemorySlots) {
                System.out.print(x.getStart() + "-" + x.getEnd() + ", ");
            }
            System.out.println();
        }
    }
}
