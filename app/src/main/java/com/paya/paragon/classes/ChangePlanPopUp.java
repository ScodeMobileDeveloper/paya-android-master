package com.paya.paragon.classes;

import android.app.Activity;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.paya.paragon.R;
import com.paya.paragon.adapter.AdapterPlanChange;
import com.paya.paragon.api.myProperties.ActivePlanList;
import com.paya.paragon.utilities.ControllerPopupWindow;

import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
public class ChangePlanPopUp extends ControllerPopupWindow{

    private RecyclerView recyclerPlans;
    private TextView submit;
    private ArrayList<ActivePlanList> planList;
    private AdapterPlanChange adapterPlanChange;
    private ImageView close;
    private Activity activity;
    private NestedScrollView nestedScrollView;

    public ChangePlanPopUp(Activity activity, int layoutRes, ArrayList<ActivePlanList> planList) {
        super(activity, layoutRes);
        this.planList = planList;
        this.activity = activity;
    }

    @Override
    public void onCreate(@Nullable View view) {
        recyclerPlans = (RecyclerView) findViewById(R.id.recycler_change_plan_list);
        submit = (TextView) findViewById(R.id.submit_change_plan);
        close = (ImageView) findViewById(R.id.close_alert);

        recyclerPlans.setLayoutManager(new LinearLayoutManager(activity));
        adapterPlanChange = new AdapterPlanChange(activity, planList);
        recyclerPlans.setAdapter(adapterPlanChange);
        recyclerPlans.setFocusable(false);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nested_scroll_change_plan);
        nestedScrollView.fullScroll(View.FOCUS_UP);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public boolean onShow() {
        return true;
    }

    @Override
    public boolean onDismiss() {
        return false;
    }
}
