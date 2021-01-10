package Memory;

import java.util.ArrayList;

import Process.Process;
import Process.ProcessState;

public class MMU {

    private final int[] availableBlockSizes;
    private MemoryAllocationAlgorithm algorithm;
    private ArrayList<MemorySlot> currentlyUsedMemorySlots;
    private ArrayList<ProcessSlot> processSlots = new ArrayList<>();

    static class ProcessSlot {
        private Process process;
        private MemorySlot slot;

        ProcessSlot(Process process, MemorySlot slot) {
            this.process = process;
            this.slot = slot;
        }

        ProcessState getProcessState() { return process.getPCB().getState(); }
    }

    public MMU(int[] availableBlockSizes, MemoryAllocationAlgorithm algorithm) {
        this.availableBlockSizes = availableBlockSizes;
        this.algorithm = algorithm;
        currentlyUsedMemorySlots = new ArrayList<>();
    }

    public boolean loadProcessIntoRAM(Process p) {
        freeMemory();

        if (p.getMemoryRequirements() > max()) {
            p.getPCB().setState(ProcessState.TERMINATED, -1);
            return false;
        }

        boolean fit = false;
        /* TODO: you need to add some code here
         * Hint: this should return true if the process was able to fit into memory
         * and false if not
         * Also, this need to check for toDelete Processes*/
        if (algorithm.fitProcess(p, currentlyUsedMemorySlots) != -1) {
            fit = true;
            processSlots.add(new ProcessSlot(p, currentlyUsedMemorySlots.get(currentlyUsedMemorySlots.size() - 1)));
        }

        System.out.print("Process ID: " + p.getPCB().getPid() + " | ");
        printSlots();
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

    public static void main(String[] args) {
        int[] blocks = {20, 5, 10};
        //MemoryAllocationAlgorithm algorithm = new FirstFit(blocks);
        //MemoryAllocationAlgorithm algorithm = new NextFit(blocks);
        //MemoryAllocationAlgorithm algorithm = new BestFit(blocks);
        MemoryAllocationAlgorithm algorithm = new WorstFit(blocks);
        MMU mmu = new MMU(blocks, algorithm);

        Process a = new Process(0, 1, 3);
        Process b = new Process(1, 1, 8);
        Process c = new Process(1, 1, 10);
        Process d = new Process(1, 1, 10);
        Process e = new Process(1, 1, 2);

        mmu.loadProcessIntoRAM(a);
        mmu.printSlots();
        mmu.loadProcessIntoRAM(b);
        mmu.printSlots();
        mmu.loadProcessIntoRAM(c);
        mmu.printSlots();
        mmu.loadProcessIntoRAM(d);
        mmu.printSlots();
        a.getPCB().setState(ProcessState.TERMINATED, 5);
        mmu.loadProcessIntoRAM(e);
        mmu.printSlots();

    }

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

    int max () {
        int returnMax = availableBlockSizes[0];
        for (int memoryBlockSize : availableBlockSizes) {
            if (memoryBlockSize > returnMax) {
                returnMax = memoryBlockSize;
            }
        }

        return returnMax;
    }
}
