package com.android.fbeyer.lootredo.dummy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    static {
        // Add 3 sample items.
        addItem(new DummyItem("1", "Prom Dress", 300.00, new Date()));
        addItem(new DummyItem("2", "Cancun", 750.34, new Date()));
        addItem(new DummyItem("3", "Duder", 40.13, new Date()));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String id;
        public String name;
        public Double cost;
        public Date date;

        public DummyItem(String id, String name, Double cost, Date date) {
            this.id = id;
            this.name = name;
            this.cost = cost;
            this.date = date;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
