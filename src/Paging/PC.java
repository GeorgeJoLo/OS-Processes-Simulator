package Paging;

import java.util.ArrayList;
import java.util.Arrays;

public class PC {
    public static void main(String[] args) {
        ArrayList<Integer> pageRequests = new ArrayList<>(
                Arrays.asList(4, 3, 1, 5, 1, 2, 3, 6, 7, 4, 2, 5, 6, 1, 3, 4)
        );
        int memorySize = 4;

        ReplacementPolicy algorithm = new LRU(pageRequests);
        MMU mmu = new MMU(algorithm, pageRequests, memorySize);
        mmu.handleRequests();
    }
}
