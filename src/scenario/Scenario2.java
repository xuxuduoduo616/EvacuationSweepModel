package scenario;

import model.*;
import algorithm.*;
import java.util.*;

public class Scenario2 {

    public static void run() {
        System.out.println("\n========== 场景2：多层医院 ==========\n");

        Building hospital = new Building("Hospital - Scenario 2", 3);
        System.out.println("建筑: " + hospital.getName());

        System.out.println("\n--- 房间配置 ---");

        // 第1层：高优先级
        Room icu = new Room("ICU", 100, 3, 0, "ICU");
        Room surgery = new Room("Surgery", 80, 3, 0, "Surgery");
        Room emergency = new Room("Emergency", 60, 2, 0, "Emergency");

        hospital.addRoom(0, icu);
        hospital.addRoom(0, surgery);
        hospital.addRoom(0, emergency);

        System.out.println("  第1层（高优先级）:");
        System.out.println("    ICU: 面积=100m², 检查时间=" + String.format("%.2f", icu.getCheckTime()) + "分钟");
        System.out.println("    Surgery: 面积=80m², 检查时间=" + String.format("%.2f", surgery.getCheckTime()) + "分钟");
        System.out.println("    Emergency: 面积=60m², 检查时间=" + String.format("%.2f", emergency.getCheckTime()) + "分钟");

        // 第2层：普通病房
        System.out.println("  第2层（普通病房）:");
        for (int i = 1; i <= 20; i++) {
            Room ward = new Room("Ward_" + i, 20, 2, 1, "Ward");
            hospital.addRoom(1, ward);
        }
        System.out.println("    20间病房，每间面积=20m²");

        // 第3层：办公室和存储室
        Room office = new Room("Office", 40, 1, 2, "Office");
        Room storage = new Room("Storage", 50, 1, 2, "Storage");

        hospital.addRoom(2, office);
        hospital.addRoom(2, storage);

        System.out.println("  第3层（低优先级）:");
        System.out.println("    Office: 面积=40m², 检查时间=" + String.format("%.2f", office.getCheckTime()) + "分钟");
        System.out.println("    Storage: 面积=50m², 检查时间=" + String.format("%.2f", storage.getCheckTime()) + "分钟");

        // 设置距离
        System.out.println("\n--- 距离矩阵设置 ---");
        hospital.setDistance("ICU", "Surgery", 15);
        hospital.setDistance("Surgery", "Emergency", 15);

        List<Room> ward2Rooms = hospital.getRoomsOnFloor(1);
        for (int i = 0; i < ward2Rooms.size() - 1; i++) {
            hospital.setDistance(ward2Rooms.get(i).getId(),
                    ward2Rooms.get(i + 1).getId(), 5);
        }

        hospital.setDistance("Office", "Storage", 10);
        hospital.setDistance("Emergency", "Ward_1", 30);
        hospital.setDistance("Ward_20", "Office", 40);

        System.out.println("  距离设置完成");

        // 创建人员
        System.out.println("\n--- 人员配置 ---");
        Responder[] responders = new Responder[4];
        for (int i = 0; i < 4; i++) {
            responders[i] = new Responder("人员" + (i + 1), 1.2);
        }
        System.out.println("  4名应急人员，速度=1.2 m/s");

        // 分配房间（优先级导向）
        System.out.println("\n--- 房间分配 ---");
        responders[0].setStartRoom(icu);
        responders[0].setEndRoom(icu);
        responders[0].addRoomToPath(icu);
        responders[0].addRoomToPath(surgery);
        responders[0].addRoomToPath(emergency);

        responders[1].setStartRoom(ward2Rooms.get(0));
        responders[1].setEndRoom(ward2Rooms.get(0));
        for (int i = 0; i < 10; i++) {
            responders[1].addRoomToPath(ward2Rooms.get(i));
        }

        responders[2].setStartRoom(ward2Rooms.get(10));
        responders[2].setEndRoom(ward2Rooms.get(10));
        for (int i = 10; i < 20; i++) {
            responders[2].addRoomToPath(ward2Rooms.get(i));
        }

        responders[3].setStartRoom(office);
        responders[3].setEndRoom(office);
        responders[3].addRoomToPath(office);
        responders[3].addRoomToPath(storage);

        System.out.println("  人员1: 检查第1层（高优先级）");
        System.out.println("  人员2-3: 分别检查第2层的10间病房");
        System.out.println("  人员4: 检查第3层");

        // 计算结果
        System.out.println("\n--- 耗时计算 ---");
        EvacuationModel model = new EvacuationModel(hospital, responders);
        double totalTime = model.calculateTotalTime();

        for (Responder responder : responders) {
            System.out.println("  " + responder.getId() + ": " +
                    String.format("%.2f", responder.getTotalTime()) + " 分钟");
        }

        System.out.println("\n  ★ 总耗时: " + String.format("%.2f", totalTime) + " 分钟 ★");

        System.out.println("\n--- 解验证 ---");
        boolean valid = model.validateSolution();
        if (valid) {
            System.out.println("  ✓ 解有效");
        } else {
            System.out.println("  ✗ 解无效");
        }

        System.out.println(model.generateReport());
    }

    public static Map<String, Object> getResults() {
        Map<String, Object> results = new HashMap<>();

        Building hospital = new Building("Hospital - Scenario 2", 3);

        Room icu = new Room("ICU", 100, 3, 0, "ICU");
        Room surgery = new Room("Surgery", 80, 3, 0, "Surgery");
        Room emergency = new Room("Emergency", 60, 2, 0, "Emergency");

        hospital.addRoom(0, icu);
        hospital.addRoom(0, surgery);
        hospital.addRoom(0, emergency);

        for (int i = 1; i <= 20; i++) {
            Room ward = new Room("Ward_" + i, 20, 2, 1, "Ward");
            hospital.addRoom(1, ward);
        }

        Room office = new Room("Office", 40, 1, 2, "Office");
        Room storage = new Room("Storage", 50, 1, 2, "Storage");

        hospital.addRoom(2, office);
        hospital.addRoom(2, storage);

        hospital.setDistance("ICU", "Surgery", 15);
        hospital.setDistance("Surgery", "Emergency", 15);

        List<Room> ward2Rooms = hospital.getRoomsOnFloor(1);
        for (int i = 0; i < ward2Rooms.size() - 1; i++) {
            hospital.setDistance(ward2Rooms.get(i).getId(),
                    ward2Rooms.get(i + 1).getId(), 5);
        }

        hospital.setDistance("Office", "Storage", 10);
        hospital.setDistance("Emergency", "Ward_1", 30);
        hospital.setDistance("Ward_20", "Office", 40);

        Responder[] responders = new Responder[4];
        for (int i = 0; i < 4; i++) {
            responders[i] = new Responder("人员" + (i + 1), 1.2);
        }

        responders[0].setStartRoom(icu);
        responders[0].setEndRoom(icu);
        responders[0].addRoomToPath(icu);
        responders[0].addRoomToPath(surgery);
        responders[0].addRoomToPath(emergency);

        responders[1].setStartRoom(ward2Rooms.get(0));
        responders[1].setEndRoom(ward2Rooms.get(0));
        for (int i = 0; i < 10; i++) {
            responders[1].addRoomToPath(ward2Rooms.get(i));
        }

        responders[2].setStartRoom(ward2Rooms.get(10));
        responders[2].setEndRoom(ward2Rooms.get(10));
        for (int i = 10; i < 20; i++) {
            responders[2].addRoomToPath(ward2Rooms.get(i));
        }

        responders[3].setStartRoom(office);
        responders[3].setEndRoom(office);
        responders[3].addRoomToPath(office);
        responders[3].addRoomToPath(storage);

        for (Responder responder : responders) {
            responder.calculateTotalTime(hospital);
        }

        double maxTime = 0;
        for (Responder responder : responders) {
            if (responder.getTotalTime() > maxTime) {
                maxTime = responder.getTotalTime();
            }
        }

        results.put("scenario", "Scenario 2");
        results.put("building", "Hospital");
        results.put("floors", 3);
        results.put("rooms", 25);
        results.put("responders", 4);
        results.put("total_time", maxTime);

        return results;
    }
}