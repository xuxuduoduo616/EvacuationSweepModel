package algorithm;

import model.Room;
import java.util.*;

public class PathOptimizer {

    public static List<Room> greedyOptimization(
            Room startRoom,
            List<Room> unvisitedRooms,
            Map<String, Map<String, Double>> distanceMatrix) {

        List<Room> path = new ArrayList<>();
        Room current = startRoom;
        path.add(current);

        List<Room> remaining = new ArrayList<>(unvisitedRooms);

        while (!remaining.isEmpty()) {
            Room nearest = findNearestRoom(current, remaining, distanceMatrix);

            if (nearest == null) {
                System.err.println("警告：无法找到下一个房间");
                break;
            }

            path.add(nearest);
            remaining.remove(nearest);
            current = nearest;
        }

        return path;
    }

    private static Room findNearestRoom(
            Room current,
            List<Room> candidates,
            Map<String, Map<String, Double>> distanceMatrix) {

        Room nearest = null;
        double minDist = Double.MAX_VALUE;

        for (Room room : candidates) {
            double dist = getDistance(current.getId(), room.getId(), distanceMatrix);
            if (dist < minDist) {
                minDist = dist;
                nearest = room;
            }
        }

        return nearest;
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

    public static double calculatePathDistance(
            List<Room> path,
            Map<String, Map<String, Double>> distanceMatrix) {

        double totalDist = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            totalDist += getDistance(path.get(i).getId(),
                    path.get(i + 1).getId(),
                    distanceMatrix);
        }
        return totalDist;
    }

    public static double calculateCheckTime(List<Room> path) {
        double totalTime = 0;
        for (Room room : path) {
            totalTime += room.getCheckTime();
        }
        return totalTime;
    }
}