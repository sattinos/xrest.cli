package org.malsati.models;

import java.util.ArrayList;
import java.util.List;

public class EntityInfo {
    private String packageName;
    private String name;
    private List<FieldInfo> fields = new ArrayList<>();

    public EntityInfo(String name) {
        this.name = name;
    }

    public EntityInfo(String packageName, String name) {
        this.packageName = packageName;
        this.name = name;
    }

    public List<FieldInfo> getFields() {
        return fields;
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
