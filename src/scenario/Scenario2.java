package scenario;

import model.*;
import algorithm.*;
import java.util.*;

public class Scenario2 {

    public static void run() {
        System.out.println("\n========== Scenario 2: Multi-floor Hospital ==========\n");

        Building hospital = new Building("Hospital - Scenario 2", 3);
        System.out.println("Building: " + hospital.getName());

        System.out.println("\n--- Room Configuration ---");

        // Floor 1: High Priority
        Room icu = new Room("ICU", 100, 3, 0, "ICU");
        Room surgery = new Room("Surgery", 80, 3, 0, "Surgery");
        Room emergency = new Room("Emergency", 60, 2, 0, "Emergency");

        hospital.addRoom(0, icu);
        hospital.addRoom(0, surgery);
        hospital.addRoom(0, emergency);

        System.out.println("  Floor 1 (High Priority):");
        System.out.println("    ICU: Area=100m², Check time=" + String.format("%.2f", icu.getCheckTime()) + " minutes");
        System.out.println("    Surgery: Area=80m², Check time=" + String.format("%.2f", surgery.getCheckTime()) + " minutes");
        System.out.println("    Emergency: Area=60m², Check time=" + String.format("%.2f", emergency.getCheckTime()) + " minutes");

        // Floor 2: General Wards
        System.out.println("  Floor 2 (General Wards):");
        for (int i = 1; i <= 20; i++) {
            Room ward = new Room("Ward_" + i, 20, 2, 1, "Ward");
            hospital.addRoom(1, ward);
        }
        System.out.println("    20 wards, each with an area of 20m²");

        // Floor 3: Offices and Storage Rooms
        Room office = new Room("Office", 40, 1, 2, "Office");
        Room storage = new Room("Storage", 50, 1, 2, "Storage");

        hospital.addRoom(2, office);
        hospital.addRoom(2, storage);

        System.out.println("  Floor 3 (Low Priority):");
        System.out.println("    Office: Area=40m², Check time=" + String.format("%.2f", office.getCheckTime()) + " minutes");
        System.out.println("    Storage: Area=50m², Check time=" + String.format("%.2f", storage.getCheckTime()) + " minutes");

        // Set distances
        System.out.println("\n--- Distance Matrix Setup ---");
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

        System.out.println("  Distance setup complete");

        // Create personnel
        System.out.println("\n--- Personnel Configuration ---");
        Responder[] responders = new Responder[4];
        for (int i = 0; i < 4; i++) {
            responders[i] = new Responder("Personnel " + (i + 1), 1.2);
        }
        System.out.println("  4 emergency personnel, speed=1.2 m/s");

        // Allocate rooms (priority-based)
        System.out.println("\n--- Room Allocation ---");
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

        System.out.println("  Personnel 1: Checks Floor 1 (High Priority)");
        System.out.println("  Personnel 2-3: Each checks 10 wards on Floor 2");
        System.out.println("  Personnel 4: Checks Floor 3");

        // Calculate results
        System.out.println("\n--- Time Calculation ---");
        EvacuationModel model = new EvacuationModel(hospital, responders);
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
            responders[i] = new Responder("Personnel " + (i + 1), 1.2);
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