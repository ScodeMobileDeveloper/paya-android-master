package com.paya.paragon.activity.postRequirements;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.paya.paragon.R;
import com.paya.paragon.model.RequirementModel;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

@SuppressWarnings({"RedundantCast", "HardCodedStringLiteral"})
public class ActivityRequirementPurpose extends AppCompatActivity {

    TextView buy, rent;
    private static RequirementModel requirementModel;
    private Boolean isRent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_requirement_type);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow_black);

        declarations();

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requirementModel.setPurpose("Sell");
                isRent = false;
                nextActivity();
            }
        });

        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requirementModel.setPurpose("Rent");
                isRent = true;
                nextActivity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.rl_requirement_type_parent_layout));
    }

    private void nextActivity() {
        SessionManager.setPostRequirement(ActivityRequirementPurpose.this, requirementModel);
        startActivity(new Intent(ActivityRequirementPurpose.this, ActivityRequirementPropertyType.class).
                putExtra("propertyPurpose", isRent));
    }

    private void declarations() {
        requirementModel = new RequirementModel();
        requirementModel.setRequirementAction("post");
        SessionManager.setPostRequirement(ActivityRequirementPurpose.this, requirementModel);
        buy = findViewById(R.id.text_buy_post_requirement_type);
        rent = findViewById(R.id.text_rent_post_requirement_type);
    }

    //TODO Main Functions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
