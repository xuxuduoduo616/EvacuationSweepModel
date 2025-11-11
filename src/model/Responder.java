package model;

import java.util.*;

public class Responder {
    private String id;
    private double speed;
    private List<Room> path;
    private double totalTime;
    private double checkTime;
    private double moveTime;
    private Room startRoom;
    private Room endRoom;

    public Responder(String id, double speed) {
        this.id = id;
        this.speed = speed;
        this.path = new ArrayList<>();
        this.totalTime = 0;
        this.checkTime = 0;
        this.moveTime = 0;
    }

    public void addRoomToPath(Room room) {
        path.add(room);
    }

    public void setStartRoom(Room room) {
        this.startRoom = room;
    }

    public void setEndRoom(Room room) {
        this.endRoom = room;
    }

    public double calculateTotalTime(Building building) {
        if (path.isEmpty()) {
            this.totalTime = 0;
            return 0;
        }

        this.checkTime = 0;
        for (Room room : path) {
            this.checkTime += room.getCheckTime();
        }

        this.moveTime = 0;

        if (startRoom != null && !path.isEmpty()) {
            double dist = building.getDistance(startRoom.getId(), path.get(0).getId());
            if (dist != Double.MAX_VALUE) {
                this.moveTime += dist / speed / 60.0;
            }
        }

        for (int i = 0; i < path.size() - 1; i++) {
            double dist = building.getDistance(path.get(i).getId(),
                    path.get(i + 1).getId());
            if (dist != Double.MAX_VALUE) {
                this.moveTime += dist / speed / 60.0;
            }
        }

        if (endRoom != null && !path.isEmpty()) {
            double dist = building.getDistance(path.get(path.size() - 1).getId(),
                    endRoom.getId());
            if (dist != Double.MAX_VALUE) {
                this.moveTime += dist / speed / 60.0;
            }
        }

        this.totalTime = this.checkTime + this.moveTime;
        return this.totalTime;
    }

    public boolean isPathValid() {
        return !path.isEmpty();
    }

    public List<String> getPathIds() {
        List<String> ids = new ArrayList<>();
        for (Room room : path) {
            ids.add(room.getId());
        }
        return ids;
    }

    public void clearPath() {
        path.clear();
        totalTime = 0;
        checkTime = 0;
        moveTime = 0;
    }

    public String getId() { return id; }
    public double getSpeed() { return speed; }
    public List<Room> getPath() { return new ArrayList<>(path); }
    public double getTotalTime() { return totalTime; }
    public double getCheckTime() { return checkTime; }
    public double getMoveTime() { return moveTime; }
    public int getPathLength() { return path.size(); }

    @Override
    public String toString() {
        return String.format("Responder{id='%s', speed=%.1f, pathLength=%d, totalTime=%.2f min}",
                id, speed, path.size(), totalTime);
    }
}