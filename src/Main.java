import java.util.Map;

import scenario.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║   紧急疏散扫清优化模型 - Emergency Evacuation Sweeps    ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        System.out.println("\n开始运行所有场景...\n");

        // 运行场景1
        Scenario1.run();

        Scenario2.run();

        // 运行场景3
        Scenario3.run();

        // 汇总结果
        System.out.println("\n\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║                    最终结果汇总                        ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");

        Map<String, Object> result1 = Scenario1.getResults();
        Map<String, Object> result2 = Scenario2.getResults();
        Map<String, Object> result3 = Scenario3.getResults();

        System.out.println("场景1：基础办公楼");
        System.out.println("  建筑: " + result1.get("building"));
        System.out.println("  楼层: " + result1.get("floors"));
        System.out.println("  房间: " + result1.get("rooms"));
        System.out.println("  人员: " + result1.get("responders"));
        System.out.println("  总耗时: " + String.format("%.2f", result1.get("total_time")) + " 分钟");

        System.out.println("\n场景2：多层医院");
        System.out.println("  建筑: " + result2.get("building"));
        System.out.println("  楼层: " + result2.get("floors"));
        System.out.println("  房间: " + result2.get("rooms"));
        System.out.println("  人员: " + result2.get("responders"));
        System.out.println("  总耗时: " + String.format("%.2f", result2.get("total_time")) + " 分钟");

        System.out.println("\n场景3：工业仓库");
        System.out.println("  建筑: " + result3.get("building"));
        System.out.println("  楼层: " + result3.get("floors"));
        System.out.println("  房间: " + result3.get("rooms"));
        System.out.println("  人员: " + result3.get("responders"));
        System.out.println("  总耗时: " + String.format("%.2f", result3.get("total_time")) + " 分钟");

        System.out.println("\n✓ 所有场景运行完成！");
    }
}