import java.util.ArrayList;
import java.util.Arrays;
import static java.lang.Math.abs;
import static java.util.Collections.max;
import static java.util.Collections.min;

public class DiskScheduler {

    public static void main(String[] args) {

        ArrayList<Integer> sectorRequests = new ArrayList<>(
                Arrays.asList(188, 37, 147, 104, 129, 63, 134, 11, 100, 19)
        );

        int start = 157;

        ArrayList<Integer> path = CLOOK(sectorRequests, start, true);

        int count = 1;
        for (int point : path) {
            System.out.println(count + ") " + point);
            count++;
        }

        System.out.println("=========");
        System.out.println("Total distance: " + pathDistance(path));
    }

    private static ArrayList<Integer> FCFS(ArrayList<Integer> requests, int start) {
        ArrayList<Integer> visited = new ArrayList<>();
        visited.add(start);

        visited.addAll(requests);

        return visited;
    }

    private static ArrayList<Integer> SSTF(ArrayList<Integer> requests, int start) {
        ArrayList<Integer> visited = new ArrayList<>();
        visited.add(start);

        int prev = start;
        int sz = requests.size();
        for (int i = 0; i < sz; i++) {

            ArrayList<Integer> temp = new ArrayList<>();
            for (int x : requests)
                temp.add(abs(x-prev));

            int minDist = min(temp);
            prev = requests.get(temp.indexOf(minDist));
            visited.add(prev);

            requests.remove(temp.indexOf(minDist));
        }

        return visited;
    }

    private static ArrayList<Integer> SCAN(ArrayList<Integer> requests, int start, int size, boolean up) {
        ArrayList<Integer> visited = new ArrayList<>();
        visited.add(start);

        int pointer = start;

        while (pointer != (up?size:-1)) {
            if (requests.contains(pointer) && !visited.contains(pointer))
                visited.add(pointer);

            if (up) pointer++;
            else pointer--;
        }

        if (requests.size() + 1 == visited.size())
            return visited;

        if (!visited.contains(up?size-1:0))
            visited.add(up?size-1:0);

        while (pointer != (up?-1:size)) {
            if (requests.contains(pointer) && !visited.contains(pointer)) {
                visited.add(pointer);
            }

            if (up) pointer--;
            else pointer++;
        }

        return visited;
    }

    private static ArrayList<Integer> CSCAN(ArrayList<Integer> requests, int start, int size, boolean up) {
        ArrayList<Integer> visited = new ArrayList<>();
        visited.add(start);

        int point = start;

        while (point != (up?size:-1)) {
            if (requests.contains(point) && !visited.contains(point))
                visited.add(point);

            if (up) point++;
            else point--;
        }

        if (requests.size() + 1 == visited.size())
            return visited;

        if (!visited.contains(up?size-1:0))
            visited.add(up?size-1:0);
        point = up?0:size-1;
        if (!visited.contains(point))
            visited.add(point);

        while (point != (up?size:-1)) {
            if (requests.contains(point) && !visited.contains(point))
                visited.add(point);

            if (up) point++;
            else point--;
        }

        return visited;
    }

    private static ArrayList<Integer> CLOOK(ArrayList<Integer> requests, int start, boolean up) {
        ArrayList<Integer> visited = new ArrayList<>();
        visited.add(start);

        int point = start;

        while (point != (up?max(requests)+1:min(requests)-1)) {
            if (requests.contains(point) && !visited.contains(point))
                visited.add(point);

            if (up) point++;
            else point--;
        }

        if (requests.size() + 1 == visited.size())
            return visited;

        point = up?min(requests):max(requests);

        while (point != (up?max(requests)+1:min(requests)-1)) {
            if (requests.contains(point) && !visited.contains(point))
                visited.add(point);

            if (up) point++;
            else point--;
        }

        return visited;
    }

    private static int pathDistance(ArrayList<Integer> points) {
        int distance = 0;

        int prev = points.get(0);
        for (int point : points) {
            distance += abs(point - prev);
            prev = point;
        }

        return distance;
    }
}
