package model;

import java.util.*;

public class Building {
    private String name;
    private int floors;
    private List<List<Room>> rooms;
    private Map<String, Map<String, Double>> distanceMatrix;

    public Building(String name, int floors) {
        this.name = name;
        this.floors = floors;
        this.rooms = new ArrayList<>();
        this.distanceMatrix = new HashMap<>();

        for (int i = 0; i < floors; i++) {
            rooms.add(new ArrayList<>());
        }
    }

    public void addRoom(int floor, Room room) {
        if (floor < 0 || floor >= floors) {
            throw new IllegalArgumentException("楼层号超出范围");
        }
        rooms.get(floor).add(room);

        if (!distanceMatrix.containsKey(room.getId())) {
            distanceMatrix.put(room.getId(), new HashMap<>());
        }
    }

    public void setDistance(String roomId1, String roomId2, double distance) {
        if (!distanceMatrix.containsKey(roomId1)) {
            distanceMatrix.put(roomId1, new HashMap<>());
        }
        if (!distanceMatrix.containsKey(roomId2)) {
            distanceMatrix.put(roomId2, new HashMap<>());
        }

        distanceMatrix.get(roomId1).put(roomId2, distance);
        distanceMatrix.get(roomId2).put(roomId1, distance);
    }

    public double getDistance(String roomId1, String roomId2) {
        if (roomId1.equals(roomId2)) {
            return 0;
        }
        if (!distanceMatrix.containsKey(roomId1) ||
                !distanceMatrix.get(roomId1).containsKey(roomId2)) {
            return Double.MAX_VALUE;
        }
        return distanceMatrix.get(roomId1).get(roomId2);
    }

    public List<Room> getRoomsOnFloor(int floor) {
        if (floor < 0 || floor >= floors) {
            throw new IllegalArgumentException("楼层号超出范围");
        }
        return rooms.get(floor);
    }

    public List<Room> getAllRooms() {
        List<Room> allRooms = new ArrayList<>();
        for (List<Room> floorRooms : rooms) {
            allRooms.addAll(floorRooms);
        }
        return allRooms;
    }

    public Room getRoomById(String roomId) {
        for (Room room : getAllRooms()) {
            if (room.getId().equals(roomId)) {
                return room;
            }
        }
        return null;
    }

    public String getName() { return name; }
    public int getFloors() { return floors; }
    public int getTotalRooms() { return getAllRooms().size(); }
    public Map<String, Map<String, Double>> getDistanceMatrix() { return distanceMatrix; }
}