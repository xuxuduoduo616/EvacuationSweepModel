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
        addLog("开始优化扫清策略...");
        addLog("建筑: " + building.getName());
        addLog("房间数: " + building.getTotalRooms());
        addLog("人员数: " + responders.length);
    }

    public boolean validateSolution() {
        addLog("验证解的可行性...");

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
            addLog("✗ 错误：不是所有房间都被检查");
            return false;
        }
        addLog("✓ 所有房间都被检查");

        for (Responder responder : responders) {
            List<Room> path = responder.getPath();
            if (path.size() > 1) {
                for (int i = 0; i < path.size() - 1; i++) {
                    double dist = building.getDistance(path.get(i).getId(),
                            path.get(i + 1).getId());
                    if (dist == Double.MAX_VALUE) {
                        addLog("✗ 错误：路径不连通 " + path.get(i).getId() +
                                " -> " + path.get(i + 1).getId());
                        return false;
                    }
                }
            }
        }
        addLog("✓ 路径连通");
        addLog("✓ 解可行");
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
        report.append("\n========== 优化报告 ==========\n");
        report.append("建筑: ").append(building.getName()).append("\n");
        report.append("房间总数: ").append(building.getTotalRooms()).append("\n");
        report.append("人员总数: ").append(responders.length).append("\n");
        report.append("总耗时: ").append(String.format("%.2f", totalTime)).append(" 分钟\n");
        report.append("\n--- 人员详情 ---\n");

        for (Responder responder : responders) {
            report.append("\n").append(responder.getId()).append(":\n");
            report.append("  检查房间: ").append(responder.getPathIds()).append("\n");
            report.append("  检查时间: ").append(String.format("%.2f", responder.getCheckTime()))
                    .append(" 分钟\n");
            report.append("  移动时间: ").append(String.format("%.2f", responder.getMoveTime()))
                    .append(" 分钟\n");
            report.append("  总耗时: ").append(String.format("%.2f", responder.getTotalTime()))
                    .append(" 分钟\n");
        }

        report.append("\n--- 优化日志 ---\n");
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