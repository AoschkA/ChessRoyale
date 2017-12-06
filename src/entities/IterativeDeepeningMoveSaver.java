package src.entities;

import java.util.HashMap;
import java.util.Map;

public class IterativeDeepeningMoveSaver {
    private static Map<Integer, String> MOVESTACK = new HashMap<Integer ,String>();

    public static boolean entryExists(int key) {
        return MOVESTACK.containsKey(key);
    }

    public static String getItem(int key) {
        return MOVESTACK.get(key);
    }

    public static void addEntry(int key, String move) {
        MOVESTACK.put(key, move);
    }

    public static void reset() {
        MOVESTACK = new HashMap<Integer, String>();
    }

    public static void print() {
        System.out.println(MOVESTACK);
    }
}
