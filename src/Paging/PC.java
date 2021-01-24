package Paging;

import java.util.ArrayList;
import java.util.Arrays;

public class PC {
    public static void main(String[] args) {
        ArrayList<Integer> pageRequests = new ArrayList<>(
                Arrays.asList(1, 2, 3, 4, 2, 1, 5, 6, 2, 1, 2, 3, 7, 6, 3, 2, 1, 2, 3, 6)
        );
        int memorySize = 4;

        ReplacementPolicy algorithm = new LRU(pageRequests);
        MMU mmu = new MMU(algorithm, pageRequests, memorySize);
        mmu.handleRequests();
    }
}
