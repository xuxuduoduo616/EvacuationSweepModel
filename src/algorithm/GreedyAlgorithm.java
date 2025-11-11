package algorithm;

import model.*;
import java.util.*;

public class GreedyAlgorithm {

    public static void allocateRoomsGreedy(
            Responder[] responders,
            List<Room> rooms,
            Building building) {

        List<Room> unallocated = new ArrayList<>(rooms);

        for (int i = 0; i < responders.length && i < unallocated.size(); i++) {
            Room startRoom = unallocated.get(0);
            responders[i].setStartRoom(startRoom);
            responders[i].addRoomToPath(startRoom);
            unallocated.remove(0);
        }

        int responderIndex = 0;
        while (!unallocated.isEmpty()) {
            Responder current = responders[responderIndex % responders.length];
            Room lastRoom = current.getPath().get(current.getPath().size() - 1);

            Room nearest = findNearestUnallocatedRoom(lastRoom, unallocated, building);

            if (nearest != null) {
                current.addRoomToPath(nearest);
                unallocated.remove(nearest);
            }

            responderIndex++;
        }
    }

    private static Room findNearestUnallocatedRoom(
            Room current,
            List<Room> unallocated,
            Building building) {

        Room nearest = null;
        double minDist = Double.MAX_VALUE;

        for (Room room : unallocated) {
            double dist = building.getDistance(current.getId(), room.getId());
            if (dist < minDist && dist != Double.MAX_VALUE) {
                minDist = dist;
                nearest = room;
            }
        }

        return nearest;
    }

    public static void allocateRoomsByPartition(
            Responder[] responders,
            List<Room> rooms,
            Building building) {

        List<Room> leftRooms = new ArrayList<>();
        List<Room> rightRooms = new ArrayList<>();

        for (Room room : rooms) {
            if (room.getId().startsWith("L")) {
                leftRooms.add(room);
            } else if (room.getId().startsWith("R")) {
                rightRooms.add(room);
            }
        }

        if (responders.length >= 2) {
            for (Room room : leftRooms) {
                responders[0].addRoomToPath(room);
            }

            for (Room room : rightRooms) {
                responders[1].addRoomToPath(room);
            }
        }
    }
}