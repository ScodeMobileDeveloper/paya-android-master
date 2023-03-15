package com.paya.paragon.utilities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.PropertyProjectListActivity;
import com.paya.paragon.activity.buy.LocationsActivity;
import com.paya.paragon.activity.dashboard.ActivityUpgradePayment;
import com.paya.paragon.activity.details.ActivityProjectDetails;
import com.paya.paragon.activity.details.ActivityPropertyDetails;
import com.paya.paragon.activity.postProperty.ActivityPostPropertyPreview;

import java.util.List;

@SuppressWarnings("RedundantCast")
public class ListDialogBox extends Dialog {
    Context context;
    private RecyclerView rv_list;
    private TextView tv_head;
    private List<String> list;
    private String title;
    private String from;

    public ListDialogBox(Context context, List<String> list, String title, String from) {
        super(context);
        this.context = context;
        this.list = list;
        this.title = title;
        this.from = from;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Utils.changeLayoutOrientationDynamically(getContext(), findViewById(R.id.rv_list));
        setContentView(R.layout.dialog_list_style);
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        tv_head = (TextView) findViewById(R.id.tv_head);
        tv_head.setText(title);
        DialogListNewAdapter adapter = new DialogListNewAdapter(list, context, from);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        rv_list.setLayoutManager(mLayoutManager);
        rv_list.setAdapter(adapter);
        rv_list.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        switch (from) {
                            case "payment":
                                ActivityUpgradePayment.selectedPosition = position;
                                break;
                            case "location":
                                LocationsActivity.selectedPosition = position;
                                break;
                            case "sort":
                                PropertyProjectListActivity.selectedPosition = position;

                                break;
                            case "property":
                                ActivityPropertyDetails.selectedPosition = position;
                                break;
                            case "project":
                                ActivityProjectDetails.selectedPosition = position;
                                break;
                            case "propertyPreview":
                                ActivityPostPropertyPreview.selectedPosition = position;
                                break;
                        }
                        ListDialogBox.this.dismiss();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }
}
