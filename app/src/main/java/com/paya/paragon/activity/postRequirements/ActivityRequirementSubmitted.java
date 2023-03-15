package com.paya.paragon.activity.postRequirements;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.paya.paragon.R;
import com.paya.paragon.PropertyProjectListActivity;
import com.paya.paragon.activity.dashboard.ActivityMyProperties;
import com.paya.paragon.activity.dashboard.ActivityPostedRequirements;
import com.paya.paragon.model.RequirementModel;
import com.paya.paragon.utilities.SessionManager;

@SuppressWarnings({"RedundantCast", "HardCodedStringLiteral"})
public class ActivityRequirementSubmitted extends AppCompatActivity {

    TextView backToHome;
    private static RequirementModel requirementModel;
    String payment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_requirement_submitted);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Intent intent = getIntent();
        payment = intent.getStringExtra("payment");
        backToHome = findViewById(R.id.text_back_to_home_requirement_submit);
        if (payment != null) {
            ((TextView) findViewById(R.id.thank_message_post_req)).setText(R.string.plan_upgraded_successfully);
            backToHome.setText(R.string.back);
        } else backToHome.setText(getString(R.string.back_to_home));
        requirementModel = SessionManager.getPostRequirement(ActivityRequirementSubmitted.this);

        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
        if (payment != null) {
            Intent intentLogin = new Intent(ActivityRequirementSubmitted.this, ActivityMyProperties.class);
            startActivity(intentLogin);
            finish();
        } else if (requirementModel.getRequirementAction().equalsIgnoreCase("edit")) {
            SessionManager.setPostRequirement(ActivityRequirementSubmitted.this, new RequirementModel());
            startActivity(new Intent(ActivityRequirementSubmitted.this, ActivityPostedRequirements.class));
            finish();
        } else {
            SessionManager.setPostRequirement(ActivityRequirementSubmitted.this, new RequirementModel());
            Intent intent = new Intent(ActivityRequirementSubmitted.this, PropertyProjectListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("searchPropertyPurpose", "Sell");
            startActivity(intent);
            finish();
        }
    }
}
