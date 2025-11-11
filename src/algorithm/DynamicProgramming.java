package algorithm;

import model.Room;
import java.util.*;

public class DynamicProgramming {

    public static List<Room> solveTSP(
            List<Room> rooms,
            Map<String, Map<String, Double>> distanceMatrix) {

        int n = rooms.size();
        if (n == 0) return new ArrayList<>();
        if (n == 1) return new ArrayList<>(rooms);

        double[][] dp = new double[1 << n][n];
        int[][] parent = new int[1 << n][n];

        for (int i = 0; i < (1 << n); i++) {
            Arrays.fill(dp[i], Double.MAX_VALUE);
            Arrays.fill(parent[i], -1);
        }

        dp[1][0] = 0;

        for (int mask = 1; mask < (1 << n); mask++) {
            for (int u = 0; u < n; u++) {
                if ((mask & (1 << u)) == 0) continue;
                if (dp[mask][u] == Double.MAX_VALUE) continue;

                for (int v = 0; v < n; v++) {
                    if ((mask & (1 << v)) != 0) continue;

                    double dist = getDistance(rooms.get(u).getId(),
                            rooms.get(v).getId(),
                            distanceMatrix);

                    int newMask = mask | (1 << v);
                    if (dp[mask][u] + dist < dp[newMask][v]) {
                        dp[newMask][v] = dp[mask][u] + dist;
                        parent[newMask][v] = u;
                    }
                }
            }
        }

        int fullMask = (1 << n) - 1;
        double minDist = Double.MAX_VALUE;
        int lastNode = -1;

        for (int i = 0; i < n; i++) {
            if (dp[fullMask][i] < minDist) {
                minDist = dp[fullMask][i];
                lastNode = i;
            }
        }

        List<Room> path = new ArrayList<>();
        int mask = fullMask;
        int current = lastNode;

        while (current != -1) {
            path.add(0, rooms.get(current));
            int prev = parent[mask][current];
            if (prev != -1) {
                mask ^= (1 << current);
            }
            current = prev;
        }

        return path;
    }

    private static double getDistance(
            String roomId1,
            String roomId2,
            Map<String, Map<String, Double>> distanceMatrix) {

        if (roomId1.equals(roomId2)) {
            return 0;
        }

        if (!distanceMatrix.containsKey(roomId1)) {
            return Double.MAX_VALUE;
        }

        Double dist = distanceMatrix.get(roomId1).get(roomId2);
        return dist != null ? dist : Double.MAX_VALUE;
    }
}