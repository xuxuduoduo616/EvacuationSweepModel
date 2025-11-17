import java.util.Map;

import scenario.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║   Emergency Evacuation Sweep Optimization Model        ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        // Prompt to start running
        System.out.println("\nStarting all scenarios...\n");

        // Run Scenario 1
        Scenario1.run();

        // Run Scenario 2
        Scenario2.run();

        // Run Scenario 3
        Scenario3.run();

        // Summarize results
        System.out.println("\n\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║                    Final Results Summary               ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");

        Map<String, Object> result1 = Scenario1.getResults();
        Map<String, Object> result2 = Scenario2.getResults();
        Map<String, Object> result3 = Scenario3.getResults();

        System.out.println("Scenario 1: Basic Office Building");
        System.out.println("  Building: " + result1.get("building"));
        System.out.println("  Floors: " + result1.get("floors"));
        System.out.println("  Rooms: " + result1.get("rooms"));
        System.out.println("  Responders: " + result1.get("responders"));
        System.out.println("  Total time: " + String.format("%.2f", result1.get("total_time")) + " minutes");

        System.out.println("\nScenario 2: Multi-floor Hospital");
        System.out.println("  Building: " + result2.get("building"));
        System.out.println("  Floors: " + result2.get("floors"));
        System.out.println("  Rooms: " + result2.get("rooms"));
        System.out.println("  Responders: " + result2.get("responders"));
        System.out.println("  Total time: " + String.format("%.2f", result2.get("total_time")) + " minutes");

        System.out.println("\nScenario 3: Industrial Warehouse");
        System.out.println("  Building: " + result3.get("building"));
        System.out.println("  Floors: " + result3.get("floors"));
        System.out.println("  Rooms: " + result3.get("rooms"));
        System.out.println("  Responders: " + result3.get("responders"));
        System.out.println("  Total time: " + String.format("%.2f", result3.get("total_time")) + " minutes");

        System.out.println("\n✓ All scenarios completed!");
    }
}