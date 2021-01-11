import Memory.*;
import Scheduler.*;
import Process.Process;


public class PC {

    public static void main(String[] args) {
        /* TODO: You may change this method to perform any tests you like */
        
        final Process[] processes = {
                // Process.Process parameters are: arrivalTime, burstTime, memoryRequirements (kB)
                new Process(0, 5, 10),
                new Process(2, 2, 40),
                new Process(3, 1, 25),
                new Process(4, 3, 30)

//                new Process(0, 6, 0),
//                new Process(1, 2, 0),
//                new Process(2, 1, 0),
//                new Process(5, 3, 0),
//                new Process(7, 4, 0),

//                new Process(1, 8, 0),
//                new Process(2, 4, 0),
//                new Process(3, 9, 0),
//                new Process(4, 5, 0)

        };
        final int[] availableBlockSizes = {15, 40, 10, 20}; // sizes in kB
        MemoryAllocationAlgorithm algorithm = new FirstFit(availableBlockSizes);
        MMU mmu = new MMU(availableBlockSizes, algorithm);
        Scheduler scheduler = new RoundRobin(2);
        CPU cpu = new CPU(scheduler, mmu, processes);
        cpu.run();
    }

}
