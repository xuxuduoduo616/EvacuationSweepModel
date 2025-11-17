package model;

import java.util.*;

public class EvacuationModel {
    private Building building;
    private Responder[] responders;
    private double totalTime;
    private boolean isOptimized;
    private List<String> optimizationLog;

    public EvacuationModel(Building building, Responder[] responders) {
        this.building = building;
        this.responders = responders;
        this.totalTime = 0;
        this.isOptimized = false;
        this.optimizationLog = new ArrayList<>();
    }

    public void optimize() {
        addLog("Starting optimization");
        addLog("Building: " + building.getName());
        addLog("Number of rooms: " + building.getTotalRooms());
        addLog("Number of responders: " + responders.length);
    }

    public boolean validateSolution() {
        addLog("Verify the feasibility of solution");

        Set<String> checkedRooms = new HashSet<>();
        for (Responder responder : responders) {
            for (Room room : responder.getPath()) {
                checkedRooms.add(room.getId());
            }
        }

        Set<String> allRooms = new HashSet<>();
        for (Room room : building.getAllRooms()) {
            allRooms.add(room.getId());
        }

        if (!checkedRooms.equals(allRooms)) {
            addLog("✗ Error: Not all rooms have been checked");
            return false;
        }
        addLog("✓ All rooms have been checked");

        for (Responder responder : responders) {
            List<Room> path = responder.getPath();
            if (path.size() > 1) {
                for (int i = 0; i < path.size() - 1; i++) {
                    double dist = building.getDistance(path.get(i).getId(),
                            path.get(i + 1).getId());
                    if (dist == Double.MAX_VALUE) {
                        addLog("✗ Error: The path is disconnected " + path.get(i).getId() +
                                " -> " + path.get(i + 1).getId());
                        return false;
                    }
                }
            }
        }
        addLog("✓ Path connected");
        addLog("✓ Solution feasible");
        return true;
    }

    public double calculateTotalTime() {
        double maxTime = 0;
        for (Responder responder : responders) {
            double time = responder.calculateTotalTime(building);
            if (time > maxTime) {
                maxTime = time;
            }
        }
        this.totalTime = maxTime;
        return maxTime;
    }

    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("\n========== Optimization Report ==========\n");
        report.append("Building: ").append(building.getName()).append("\n");
        report.append("Number of rooms: ").append(building.getTotalRooms()).append("\n");
        report.append("Number of responders: ").append(responders.length).append("\n");
        report.append("Total time consumed: ").append(String.format("%.2f", totalTime)).append(" minutes\n");
        report.append("\n--- Personnel Details ---\n");

        for (Responder responder : responders) {
            report.append("\n").append(responder.getId()).append(":\n");
            report.append("  Checked rooms: ").append(responder.getPathIds()).append("\n");
            report.append("  Check time: ").append(String.format("%.2f", responder.getCheckTime()))
                    .append(" minutes\n");
            report.append("  Move time: ").append(String.format("%.2f", responder.getMoveTime()))
                    .append(" minutes\n");
            report.append("  Total time: ").append(String.format("%.2f", responder.getTotalTime()))
                    .append(" minutes\n");
        }

        report.append("\n--- Optimization Log ---\n");
        for (String log : optimizationLog) {
            report.append(log).append("\n");
        }

        return report.toString();
    }

    private void addLog(String message) {
        optimizationLog.add(message);
    }

    public Building getBuilding() { return building; }
    public Responder[] getResponders() { return responders; }
    public double getTotalTime() { return totalTime; }
    public boolean isOptimized() { return isOptimized; }
    public List<String> getOptimizationLog() { return new ArrayList<>(optimizationLog); }
}