package com.paya.paragon.api.listLocProject;

import java.util.List;

public class SearchDataLocProj {
    private String name;
    private List<RegionDataChild> child;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RegionDataChild> getChild() {
        return child;
    }

    public void setChild(List<RegionDataChild> child) {
        this.child = child;
    }
}
