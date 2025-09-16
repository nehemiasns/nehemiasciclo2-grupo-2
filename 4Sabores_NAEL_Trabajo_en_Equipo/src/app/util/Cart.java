package app.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private static Cart instance;
    private Map<String, Integer> items = new LinkedHashMap<>();

    private Cart() {}

    public static Cart getInstance() {
        if (instance == null) instance = new Cart();
        return instance;
    }

    public void addItem(String name) {
        items.put(name, items.getOrDefault(name, 0) + 1);
    }

    public void removeItem(String name) {
        items.remove(name);
    }

    public void clear() {
        items.clear();
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
