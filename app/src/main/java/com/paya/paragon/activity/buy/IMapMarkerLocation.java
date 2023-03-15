package com.paya.paragon.activity.buy;

import com.paya.paragon.api.BuyProjects.BuyProjectsModel;
import com.paya.paragon.api.buy_properties.BuyPropertiesModel;

import java.util.List;

public interface IMapMarkerLocation {
    void setPropertyListForMap(List<BuyPropertiesModel> propertyLists);
    void setProjectListForMap(List<BuyProjectsModel> projectLists);

    void onPropertyTabSelected();
}
