package model;

public class Room {
    private String id;
    private double area;
    private double checkTime;
    private int priority;
    private double x, y;
    private int floor;
    private String type;

    public Room(String id, double area, int priority) {
        this.id = id;
        this.area = area;
        this.priority = priority;
        this.checkTime = calculateCheckTime(area);
        this.floor = 0;
        this.type = "Standard";
    }

    public Room(String id, double area, int priority, int floor, String type) {
        this.id = id;
        this.area = area;
        this.priority = priority;
        this.floor = floor;
        this.type = type;
        this.checkTime = calculateCheckTime(area);
    }

    private double calculateCheckTime(double area) {
        double baseTime = area * 0.05;
        double priorityFactor = 1.0 + (priority - 1) * 0.2;
        return baseTime * priorityFactor;
    }

    public void setCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(Room other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) +
                Math.pow(this.y - other.y, 2));
    }

    public String getId() { return id; }
    public double getArea() { return area; }
    public double getCheckTime() { return checkTime; }
    public int getPriority() { return priority; }
    public double getX() { return x; }
    public double getY() { return y; }
    public int getFloor() { return floor; }
    public String getType() { return type; }

    public void setCheckTime(double checkTime) { this.checkTime = checkTime; }

    @Override
    public String toString() {
        return String.format("Room{id='%s', area=%.1f, priority=%d, checkTime=%.2f}",
                id, area, priority, checkTime);
    }
}