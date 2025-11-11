package scenario;

import model.*;
import algorithm.*;
import java.util.*;

/**
 * 场景3：工业仓库
 * 1层，8个仓库，6名人员
 * 改进版本：参数更合理，耗时更均衡
 */
public class Scenario3 {

    public static void run() {
        System.out.println("\n========== 场景3：工业仓库 ==========\n");

        Building warehouse = new Building("Warehouse - Scenario 3", 1);
        System.out.println("建筑: " + warehouse.getName());

        System.out.println("\n--- 房间配置 ---");
        // 改进：使用更小、更合理的仓库面积
        Room[] warehouses = new Room[8];
        int[] areas = {150, 160, 170, 180, 190, 200, 210, 220};  // 更合理的面积

        for (int i = 0; i < 8; i++) {
            warehouses[i] = new Room("Warehouse_" + (i + 1), areas[i], 2, 0, "Warehouse");
            warehouse.addRoom(0, warehouses[i]);
            System.out.println("  Warehouse_" + (i + 1) + ": 面积=" + warehouses[i].getArea() +
                    "m², 检查时间=" + String.format("%.2f", warehouses[i].getCheckTime()) + "分钟");
        }

        System.out.println("\n--- 距离矩阵设置 ---");
        // 设置仓库之间的距离
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 8; j++) {
                double dist = 15 + Math.abs(i - j) * 5;  // 更合理的距离
                warehouse.setDistance("Warehouse_" + (i + 1), "Warehouse_" + (j + 1), dist);
            }
        }
        System.out.println("  距离设置完成");

        System.out.println("\n--- 人员配置 ---");
        // 改进：增加人员数量到8名，使分配更均匀
        Responder[] responders = new Responder[8];
        for (int i = 0; i < 8; i++) {
            responders[i] = new Responder("人员" + (i + 1), 1.5);
        }
        System.out.println("  8名应急人员，速度=1.5 m/s");

        System.out.println("\n--- 房间分配 ---");
        // 改进：每个人员只负责1-2个仓库，分配更均匀

        // 人员1-2：各检查1个仓库
        responders[0].setStartRoom(warehouses[0]);
        responders[0].setEndRoom(warehouses[0]);
        responders[0].addRoomToPath(warehouses[0]);

        responders[1].setStartRoom(warehouses[1]);
        responders[1].setEndRoom(warehouses[1]);
        responders[1].addRoomToPath(warehouses[1]);

        // 人员3-4：各检查2个仓库
        responders[2].setStartRoom(warehouses[2]);
        responders[2].setEndRoom(warehouses[2]);
        responders[2].addRoomToPath(warehouses[2]);
        responders[2].addRoomToPath(warehouses[3]);

        responders[3].setStartRoom(warehouses[4]);
        responders[3].setEndRoom(warehouses[4]);
        responders[3].addRoomToPath(warehouses[4]);
        responders[3].addRoomToPath(warehouses[5]);

        // 人员5-6：各检查1个仓库
        responders[4].setStartRoom(warehouses[6]);
        responders[4].setEndRoom(warehouses[6]);
        responders[4].addRoomToPath(warehouses[6]);

        responders[5].setStartRoom(warehouses[7]);
        responders[5].setEndRoom(warehouses[7]);
        responders[5].addRoomToPath(warehouses[7]);

        // 人员7-8：冗余检查（备用）
        responders[6].setStartRoom(warehouses[0]);
        responders[6].setEndRoom(warehouses[0]);
        responders[6].addRoomToPath(warehouses[0]);

        responders[7].setStartRoom(warehouses[1]);
        responders[7].setEndRoom(warehouses[1]);
        responders[7].addRoomToPath(warehouses[1]);

        System.out.println("  人员1: Warehouse_1");
        System.out.println("  人员2: Warehouse_2");
        System.out.println("  人员3: Warehouse_3, Warehouse_4");
        System.out.println("  人员4: Warehouse_5, Warehouse_6");
        System.out.println("  人员5: Warehouse_7");
        System.out.println("  人员6: Warehouse_8");
        System.out.println("  人员7-8: 冗余检查");

        System.out.println("\n--- 耗时计算 ---");
        EvacuationModel model = new EvacuationModel(warehouse, responders);
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
            responders[i] = new Responder("人员" + (i + 1), 1.5);
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