package scenario;

import model.*;
import algorithm.*;
import java.util.*;


public class Scenario3 {

    public static void run() {
        System.out.println("\n========== Scenario 3: Industrial Warehouse ==========\n");

        Building warehouse = new Building("Warehouse - Scenario 3", 1);
        System.out.println("Building: " + warehouse.getName());

        System.out.println("\n--- Room Configuration ---");
        
        Room[] warehouses = new Room[8];
        int[] areas = {150, 160, 170, 180, 190, 200, 210, 220};  

        for (int i = 0; i < 8; i++) {
            warehouses[i] = new Room("Warehouse_" + (i + 1), areas[i], 2, 0, "Warehouse");
            warehouse.addRoom(0, warehouses[i]);
            System.out.println("  Warehouse_" + (i + 1) + ": Area=" + warehouses[i].getArea() +
                    "m², Check time=" + String.format("%.2f", warehouses[i].getCheckTime()) + " minutes");
        }

        System.out.println("\n--- Distance Matrix Setup ---");
        
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 8; j++) {
                double dist = 15 + Math.abs(i - j) * 5;  
                warehouse.setDistance("Warehouse_" + (i + 1), "Warehouse_" + (j + 1), dist);
            }
        }
        System.out.println("  Distance setup complete");

        System.out.println("\n--- Personnel Configuration ---");
        
        Responder[] responders = new Responder[8];
        for (int i = 0; i < 8; i++) {
            responders[i] = new Responder("Personnel " + (i + 1), 1.5);
        }
        System.out.println("  8 emergency personnel, speed=1.5 m/s");

        System.out.println("\n--- Room Allocation ---");
        

        
        responders[0].setStartRoom(warehouses[0]);
        responders[0].setEndRoom(warehouses[0]);
        responders[0].addRoomToPath(warehouses[0]);

        responders[1].setStartRoom(warehouses[1]);
        responders[1].setEndRoom(warehouses[1]);
        responders[1].addRoomToPath(warehouses[1]);

        // Personnel 3-4: Each checks 2 warehouses
        responders[2].setStartRoom(warehouses[2]);
        responders[2].setEndRoom(warehouses[2]);
        responders[2].addRoomToPath(warehouses[2]);
        responders[2].addRoomToPath(warehouses[3]);

        responders[3].setStartRoom(warehouses[4]);
        responders[3].setEndRoom(warehouses[4]);
        responders[3].addRoomToPath(warehouses[4]);
        responders[3].addRoomToPath(warehouses[5]);

        // Personnel 5-6: Each checks 1 warehouse
        responders[4].setStartRoom(warehouses[6]);
        responders[4].setEndRoom(warehouses[6]);
        responders[4].addRoomToPath(warehouses[6]);

        responders[5].setStartRoom(warehouses[7]);
        responders[5].setEndRoom(warehouses[7]);
        responders[5].addRoomToPath(warehouses[7]);

        // Personnel 7-8: Redundant checks (backup)
        responders[6].setStartRoom(warehouses[0]);
        responders[6].setEndRoom(warehouses[0]);
        responders[6].addRoomToPath(warehouses[0]);

        responders[7].setStartRoom(warehouses[1]);
        responders[7].setEndRoom(warehouses[1]);
        responders[7].addRoomToPath(warehouses[1]);

        System.out.println("  Personnel 1: Warehouse_1");
        System.out.println("  Personnel 2: Warehouse_2");
        System.out.println("  Personnel 3: Warehouse_3, Warehouse_4");
        System.out.println("  Personnel 4: Warehouse_5, Warehouse_6");
        System.out.println("  Personnel 5: Warehouse_7");
        System.out.println("  Personnel 6: Warehouse_8");
        System.out.println("  Personnel 7-8: Redundant checks");

        System.out.println("\n--- Time Calculation ---");
        EvacuationModel model = new EvacuationModel(warehouse, responders);
        double totalTime = model.calculateTotalTime();

        for (Responder responder : responders) {
            System.out.println("  " + responder.getId() + ": " +
                    String.format("%.2f", responder.getTotalTime()) + " minutes");
        }

        System.out.println("\n  ★ Total time: " + String.format("%.2f", totalTime) + " minutes ★");

        System.out.println("\n--- Solution Validation ---");
        boolean valid = model.validateSolution();
        if (valid) {
            System.out.println("  ✓ Solution is valid");
        } else {
            System.out.println("  ✗ Solution is invalid");
        }

        System.out.println(model.generateReport());
    }

    public static Map<String, Object> getResults() {
        Map<String, Object> results = new HashMap<>();

        Building warehouse = new Building("Warehouse - Scenario 3", 1);

        // 改进的参数
        int[] areas = {150, 160, 170, 180, 190, 200, 210, 220};
        Room[] warehouses = new Room[8];
        for (int i = 0; i < 8; i++) {
            warehouses[i] = new Room("Warehouse_" + (i + 1), areas[i], 2, 0, "Warehouse");
            warehouse.addRoom(0, warehouses[i]);
        }

        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 8; j++) {
                double dist = 15 + Math.abs(i - j) * 5;
                warehouse.setDistance("Warehouse_" + (i + 1), "Warehouse_" + (j + 1), dist);
            }
        }

        Responder[] responders = new Responder[8];
        for (int i = 0; i < 8; i++) {
            responders[i] = new Responder("Personnel " + (i + 1), 1.5);
        }

        // 改进的分配方式
        responders[0].setStartRoom(warehouses[0]);
        responders[0].setEndRoom(warehouses[0]);
        responders[0].addRoomToPath(warehouses[0]);

        responders[1].setStartRoom(warehouses[1]);
        responders[1].setEndRoom(warehouses[1]);
        responders[1].addRoomToPath(warehouses[1]);

        responders[2].setStartRoom(warehouses[2]);
        responders[2].setEndRoom(warehouses[2]);
        responders[2].addRoomToPath(warehouses[2]);
        responders[2].addRoomToPath(warehouses[3]);

        responders[3].setStartRoom(warehouses[4]);
        responders[3].setEndRoom(warehouses[4]);
        responders[3].addRoomToPath(warehouses[4]);
        responders[3].addRoomToPath(warehouses[5]);

        responders[4].setStartRoom(warehouses[6]);
        responders[4].setEndRoom(warehouses[6]);
        responders[4].addRoomToPath(warehouses[6]);

        responders[5].setStartRoom(warehouses[7]);
        responders[5].setEndRoom(warehouses[7]);
        responders[5].addRoomToPath(warehouses[7]);

        responders[6].setStartRoom(warehouses[0]);
        responders[6].setEndRoom(warehouses[0]);
        responders[6].addRoomToPath(warehouses[0]);

        responders[7].setStartRoom(warehouses[1]);
        responders[7].setEndRoom(warehouses[1]);
        responders[7].addRoomToPath(warehouses[1]);

        for (Responder responder : responders) {
            responder.calculateTotalTime(warehouse);
        }

        double maxTime = 0;
        for (Responder responder : responders) {
            if (responder.getTotalTime() > maxTime) {
                maxTime = responder.getTotalTime();
            }
        }

        results.put("scenario", "Scenario 3");
        results.put("building", "Warehouse");
        results.put("floors", 1);
        results.put("rooms", 8);
        results.put("responders", 8);
        results.put("total_time", maxTime);

        return results;
    }
}