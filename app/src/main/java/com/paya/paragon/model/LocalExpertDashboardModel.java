package com.paya.paragon.model;


public class LocalExpertDashboardModel {
    private String categoryName;
    private int categoryIcon;

    public LocalExpertDashboardModel(String name, int icon){
        this.categoryName = name;
        this.categoryIcon = icon;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(int categoryIcon) {
        this.categoryIcon = categoryIcon;
    }
}
