package src.entities;

import src.entities.TranspositionWrapper;

import java.util.HashMap;
import java.util.Map;

public class TranspositionTable {
    private static Map<String, TranspositionWrapper> TABLE_MAX = new HashMap<String, TranspositionWrapper>();
    private static Map<String, TranspositionWrapper> TABLE_MIN = new HashMap<String, TranspositionWrapper>();

    public static void addEntryMax(String key, TranspositionWrapper wrapper) {
        TABLE_MAX.put(key, wrapper);
    }

    public static boolean entryExistsMax(String key) {
        return TABLE_MAX.containsKey(key);
    }

    public static TranspositionWrapper getItemMax(String key) {
        return TABLE_MAX.get(key);
    }

    public static void addEntryMin(String key, TranspositionWrapper wrapper) {
        TABLE_MIN.put(key, wrapper);
    }

    public static boolean entryExistsMin(String key) {
        return TABLE_MIN.containsKey(key);
    }

    public static TranspositionWrapper getItemMin(String key) {
        return TABLE_MIN.get(key);
    }
}
