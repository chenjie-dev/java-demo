package com.chenjie.collection_and_map.List;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HashMapIteratorDemo {

    public static void main(String[] args) {
        // 1. 创建一个HashMap并添加数据
        HashMap<String, Integer> studentScores = new HashMap<>();
        studentScores.put("张三", 85);
        studentScores.put("李四", 92);
        studentScores.put("王五", 78);
        studentScores.put("赵六", 95);
        studentScores.put("钱七", 88);

        System.out.println("原始HashMap内容: " + studentScores);

        // 2. 使用迭代器遍历HashMap的键
        System.out.println("\n=== 遍历键 ===");
        Set<String> keys = studentScores.keySet();
        Iterator<String> keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            System.out.println("键: " + key);

            // 可以在遍历时安全地移除元素
            if (key.equals("王五")) {
                keyIterator.remove();
            }
        }
        System.out.println("移除'王五'后的HashMap: " + studentScores);

        // 3. 使用迭代器遍历HashMap的值
        System.out.println("\n=== 遍历值 ===");
        Iterator<Integer> valueIterator = studentScores.values().iterator();
        while (valueIterator.hasNext()) {
            Integer value = valueIterator.next();
            System.out.println("值: " + value);
        }

        // 4. 使用迭代器遍历HashMap的键值对
        System.out.println("\n=== 遍历键值对 ===");
        Set<Map.Entry<String, Integer>> entries = studentScores.entrySet();
        Iterator<Map.Entry<String, Integer>> entryIterator = entries.iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, Integer> entry = entryIterator.next();
            System.out.println("键: " + entry.getKey() + ", 值: " + entry.getValue());

            // 可以在遍历时修改值
            if (entry.getKey().equals("李四")) {
                entry.setValue(100); // 修改李四的分数为100
            }
        }
        System.out.println("修改'李四'分数后的HashMap: " + studentScores);

        // 5. 使用forEach方法遍历（Java 8+）
        System.out.println("\n=== 使用forEach遍历 ===");
        studentScores.forEach((key, value) -> {
            System.out.println("学生: " + key + ", 分数: " + value);
        });
    }
}