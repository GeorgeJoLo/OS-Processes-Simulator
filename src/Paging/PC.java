package Paging;

import java.util.ArrayList;
import java.util.Arrays;

public class PC {
    public static void main(String[] args) {
        ArrayList<Integer> pageRequests = new ArrayList<>(
                Arrays.asList(4, 7, 5, 4, 6, 5, 3, 6, 1, 7, 4, 1, 4, 6, 1, 5, 3, 6, 4, 5)
        );
        int memorySize = 3;

        ReplacementPolicy algorithm = new Optimal(pageRequests);
        MMU mmu = new MMU(algorithm, pageRequests, memorySize);
        mmu.handleRequests();
    }
}
