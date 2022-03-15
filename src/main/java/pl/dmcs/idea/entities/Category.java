package pl.dmcs.idea.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum Category {

    BED("category.bed"),
    SOFA("category.sofa"),
    TABLE("category.table"),
    CHAIR("category.chair");

    private static final Map<String, Category> BY_LABEL = new HashMap<>();

    static {
        for (Category category : values()) {
            BY_LABEL.put(category.label, category);
        }
    }

    public final String label;

    Category(String label) {
        this.label = label;
    }

    public static Set<String> labels() {
        return BY_LABEL.keySet();
    }

    public static Category valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}
