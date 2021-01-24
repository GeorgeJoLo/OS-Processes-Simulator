import CPU.CPU;
import Memory.*;
import Scheduler.*;
import Process.Process;

import java.util.ArrayList;


public class PC {

    public static void main(String[] args) {
        /* TODO: You may change this method to perform any tests you like */
        
        final Process[] processes = {
                // Process.Process parameters are: arrivalTime, burstTime, memoryRequirements (kB)
//                new Process(1, 1, 10),
//                new Process(3, 2, 40),
//                new Process(4, 1, 5),
//                new Process(5, 3, 20)

                new Process(11, 3 , 0),
                new Process(0, 7 , 0),
                new Process(10, 1 , 0),
                new Process(3, 6 , 0),
                new Process(7, 5 , 0)
        };

        final int[] availableBlockSizes = {15, 45, 5, 10, 20}; // sizes in kB

        MemoryAllocationAlgorithm algorithm = new FirstFit(availableBlockSizes);

        MMU mmu = new MMU(availableBlockSizes, algorithm);

        Scheduler scheduler = new RoundRobin(2);

        CPU cpu = new CPU(scheduler, mmu, processes);
        cpu.run();
    }

}
