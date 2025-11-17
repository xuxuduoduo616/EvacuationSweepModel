package scenario;

import model.*;
import algorithm.*;
import java.util.*;

public class Scenario1 {

    public static void run() {
        System.out.println("\n========== Scenario 1: Basic Office Building ==========\n");

        Building office = new Building("Office Building - Scenario 1", 1);
        System.out.println("Building: " + office.getName());

        System.out.println("\n--- Room Configuration ---");
        Room[] rooms = new Room[6];
        rooms[0] = new Room("L1", 30, 1, 0, "Office");
        rooms[1] = new Room("L2", 30, 1, 0, "Office");
        rooms[2] = new Room("L3", 30, 1, 0, "Office");
        rooms[3] = new Room("R1", 30, 1, 0, "Office");
        rooms[4] = new Room("R2", 30, 1, 0, "Office");
        rooms[5] = new Room("R3", 30, 1, 0, "Office");

        for (Room room : rooms) {
            office.addRoom(0, room);
            System.out.println("  " + room.getId() + ": Area=" + room.getArea() +
                    "m², Check time=" + String.format("%.2f", room.getCheckTime()) + " minutes");
        }

        System.out.println("\n--- Distance Matrix ---");
        office.setDistance("L1", "L2", 10);
        office.setDistance("L2", "L3", 10);
        office.setDistance("R1", "R2", 10);
        office.setDistance("R2", "R3", 10);
        office.setDistance("L3", "R1", 20);
        office.setDistance("L3", "R3", 40);
        office.setDistance("L1", "R1", 40);

        System.out.println("  L1-L2: 10m, L2-L3: 10m");
        System.out.println("  R1-R2: 10m, R2-R3: 10m");
        System.out.println("  L3-R1: 20m, L3-R3: 40m");

        System.out.println("\n--- Personnel Configuration ---");
        Responder responder1 = new Responder("Firefighter 1", 1.5);
        Responder responder2 = new Responder("Firefighter 2", 1.5);

        responder1.setStartRoom(rooms[0]);
        responder1.setEndRoom(rooms[0]);

        responder2.setStartRoom(rooms[5]);
        responder2.setEndRoom(rooms[5]);

        System.out.println("  Firefighter 1: Speed=1.5 m/s, Start=L1, End=L1");
        System.out.println("  Firefighter 2: Speed=1.5 m/s, Start=R3, End=R3");

        System.out.println("\n--- Room Allocation ---");
        GreedyAlgorithm.allocateRoomsByPartition(
                new Responder[]{responder1, responder2},
                Arrays.asList(rooms),
                office
        );

        System.out.println("  Firefighter 1 checks: " + responder1.getPathIds());
        System.out.println("  Firefighter 2 checks: " + responder2.getPathIds());

        Responder[] responders = {responder1, responder2};
        EvacuationModel model = new EvacuationModel(office, responders);

        System.out.println("\n--- Time Calculation ---");
        double time1 = responder1.calculateTotalTime(office);
        double time2 = responder2.calculateTotalTime(office);
        double totalTime = model.calculateTotalTime();

        System.out.println("  Firefighter 1:");
        System.out.println("    Check time: " + String.format("%.2f", responder1.getCheckTime()) + " minutes");
        System.out.println("    Move time: " + String.format("%.2f", responder1.getMoveTime()) + " minutes");
        System.out.println("    Total time: " + String.format("%.2f", time1) + " minutes");

        System.out.println("  Firefighter 2:");
        System.out.println("    Check time: " + String.format("%.2f", responder2.getCheckTime()) + " minutes");
        System.out.println("    Move time: " + String.format("%.2f", responder2.getMoveTime()) + " minutes");
        System.out.println("    Total time: " + String.format("%.2f", time2) + " minutes");

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

        Building office = new Building("Office Building - Scenario 1", 1);
        Room[] rooms = new Room[6];
        rooms[0] = new Room("L1", 30, 1, 0, "Office");
        rooms[1] = new Room("L2", 30, 1, 0, "Office");
        rooms[2] = new Room("L3", 30, 1, 0, "Office");
        rooms[3] = new Room("R1", 30, 1, 0, "Office");
        rooms[4] = new Room("R2", 30, 1, 0, "Office");
        rooms[5] = new Room("R3", 30, 1, 0, "Office");

        for (Room room : rooms) {
            office.addRoom(0, room);
        }

        office.setDistance("L1", "L2", 10);
        office.setDistance("L2", "L3", 10);
        office.setDistance("R1", "R2", 10);
        office.setDistance("R2", "R3", 10);
        office.setDistance("L3", "R1", 20);
        office.setDistance("L3", "R3", 40);
        office.setDistance("L1", "R1", 40);

        Responder responder1 = new Responder("Firefighter 1", 1.5);
        Responder responder2 = new Responder("Firefighter 2", 1.5);

        responder1.setStartRoom(rooms[0]);
        responder1.setEndRoom(rooms[0]);
        responder2.setStartRoom(rooms[5]);
        responder2.setEndRoom(rooms[5]);

        GreedyAlgorithm.allocateRoomsByPartition(
                new Responder[]{responder1, responder2},
                Arrays.asList(rooms),
                office
        );

        responder1.calculateTotalTime(office);
        responder2.calculateTotalTime(office);

        results.put("scenario", "Scenario 1");
        results.put("building", "Office Building");
        results.put("floors", 1);
        results.put("rooms", 6);
        results.put("responders", 2);
        results.put("responder1_time", responder1.getTotalTime());
        results.put("responder2_time", responder2.getTotalTime());
        results.put("total_time", Math.max(responder1.getTotalTime(), responder2.getTotalTime()));

        return results;
    }
}