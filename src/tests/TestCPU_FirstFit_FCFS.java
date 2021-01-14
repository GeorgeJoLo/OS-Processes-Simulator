package tests;

import Memory.FirstFit;
import Memory.MMU;
import Memory.MemoryAllocationAlgorithm;
import Scheduler.Scheduler;
import Scheduler.FCFS;
import CPU.CPU;
import Process.Process;
import org.junit.*;
import org.junit.Assert;



public class TestCPU_FirstFit_FCFS {

    Process[] processes;
    int[] availableBlockSizes;

    @Before
    public void init() {
        processes = new Process[] {
                new Process(0, 5, 10),
                new Process(3, 2, 40),
                new Process(4, 1, 35),
                new Process(4, 3, 5),
                new Process(6, 1, 15)
        };

       availableBlockSizes = new int[] {15, 45, 5, 10, 20};
    }

    @Test
    public void testCPU_FirstFit_FCFS() {
        MemoryAllocationAlgorithm algorithm = new FirstFit(availableBlockSizes);
        MMU mmu = new MMU(availableBlockSizes, algorithm);
        Scheduler scheduler = new FCFS();
        CPU cpu = new CPU(scheduler, mmu, processes);
        cpu.run();

        Assert.assertEquals(processes[0].getResponseTime(), 0d, 0.01);
        Assert.assertEquals(processes[0].getWaitingTime(), 0d, 0.01);
        Assert.assertEquals(processes[0].getTurnAroundTime(), 5d, 0.01);

        Assert.assertEquals(processes[1].getResponseTime(), 2d, 0.01);
        Assert.assertEquals(processes[1].getWaitingTime(), 2d, 0.01);
        Assert.assertEquals(processes[1].getTurnAroundTime(), 4d, 0.01);

        Assert.assertEquals(processes[2].getResponseTime(), 7d, 0.01);
        Assert.assertEquals(processes[2].getWaitingTime(), 4d, 0.01);
        Assert.assertEquals(processes[2].getTurnAroundTime(), 8d, 0.01);

        Assert.assertEquals(processes[3].getResponseTime(), 3d, 0.01);
        Assert.assertEquals(processes[3].getWaitingTime(), 3d, 0.01);
        Assert.assertEquals(processes[3].getTurnAroundTime(), 6d, 0.01);

        Assert.assertEquals(processes[4].getResponseTime(), 4d, 0.01);
        Assert.assertEquals(processes[4].getWaitingTime(), 4d, 0.01);
        Assert.assertEquals(processes[4].getTurnAroundTime(), 5d, 0.01);
    }
}
