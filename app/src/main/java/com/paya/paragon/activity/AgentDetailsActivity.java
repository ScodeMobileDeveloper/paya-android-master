package com.paya.paragon.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.paya.paragon.R;
import com.paya.paragon.api.agentList.AgentList;
import com.paya.paragon.api.contactAgent.ContactAgentApi;
import com.paya.paragon.api.contactAgent.ContactAgentResponse;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.fragments.agentDetails.FragmentAgentProject;
import com.paya.paragon.fragments.agentDetails.FragmentAgentProperty;
import com.paya.paragon.fragments.agentDetails.FragmentOverView;
import com.paya.paragon.utilities.CountryCode.CountryCodePicker;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.ExtendedFragment;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"RedundantCast", "HardCodedStringLiteral"})
public class AgentDetailsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private AgentList agentList;
    private TextView contactButton;
    public Dialog alertDialog;
    private DialogProgress progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_details);
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.rl_agent_details_parent_layout));
        Intent intent = getIntent();
        agentList = (AgentList) intent.getSerializableExtra("agentDetail");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTextSize(18);
        mTitle.setText(agentList.getUserCompanyName());

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        contactButton = findViewById(R.id.contactButton);
        contactButton.setText(R.string.contact_agent);
        progress = new DialogProgress(AgentDetailsActivity.this);

        tabLayout.setupWithViewPager(viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        setFragmentOverView();

        viewPager.setAdapter(adapter);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            View view = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tab_with_badge, null, false);
            ((TextView) view.findViewById(R.id.textView1)).setText(adapter.getPageTitle(i));
            if (adapter.getBadge(i) > 0) {
                view.findViewById(R.id.textView2).setVisibility(View.VISIBLE);
            } else {
                view.findViewById(R.id.textView2).setVisibility(View.GONE);
            }
            ((TextView) view.findViewById(R.id.textView2)).setText(adapter.getBadge(i) + "");
            tab.setCustomView(view);
        }

        TabLayout.Tab tab = tabLayout.getTabAt(0);
        assert tab != null;
        View view = tab.getCustomView();
        assert view != null;
        TextView tabText = view.findViewById(R.id.textView1);
        tabText.setTextColor(getResources().getColor(R.color.white));

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertContactOwner();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView tabText = view.findViewById(R.id.textView1);
                tabText.setTextColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView tabText = view.findViewById(R.id.textView1);
                tabText.setTextColor(getResources().getColor(R.color.tab_not_selected));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    //TODO API Calls
    private void postContactOwner(final Dialog alertDialog, String strName, String strEmail,
                                  String strPhone, String strCountryCode,
                                  String strMessage) {
        progress.show();
        ApiLinks.getClient().create(ContactAgentApi.class).post(
                        strName, strEmail,
                        strPhone, agentList.getUserID(),
                        "Agency",
                        strCountryCode, strMessage.trim(),
                        agentList.getUserUrlKey())
                .enqueue(new Callback<ContactAgentResponse>() {
                    @Override
                    public void onResponse(Call<ContactAgentResponse> call, Response<ContactAgentResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getResponse();
                            if (response.body().getResponse().equalsIgnoreCase("Success")) {
                                alertDialog.dismiss();
                                alertSuccess(getResources().getString(R.string.thank_you_for_contact),
                                        getResources().getString(R.string.contact_owner_success_message));
                                //Toast.makeText(ActivityPropertyDetails.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                alertDialog.dismiss();
                                Toast.makeText(AgentDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                            progress.dismiss();
                        } else {
                            alertDialog.dismiss();
                            Toast.makeText(AgentDetailsActivity.this, getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ContactAgentResponse> call, Throwable t) {
                        alertDialog.dismiss();
                        Toast.makeText(AgentDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                        alertDialog.dismiss();
                    }
                });

    }

    //Fragment setups
    private FragmentOverView fragmentOverView;
    private FragmentAgentProject fragmentAgentProject;
    private FragmentAgentProperty fragmentAgentProperty;
    private FragmentAgentProperty fragmentAgentPropertyRent;

    public void setFragmentOverView() {
        fragmentOverView = new FragmentOverView();
        fragmentOverView.setData(agentList);
        fragmentAgentProject = new FragmentAgentProject();
        fragmentAgentProject.setData(agentList);

        fragmentAgentProperty = new FragmentAgentProperty();
        fragmentAgentPropertyRent = new FragmentAgentProperty();

        adapter.addFragment(fragmentOverView, "OVERVIEW ", 0);
        if (!agentList.getProjectCount().equals("0"))
            adapter.addFragment(fragmentAgentProject, "PROJECTS (" + agentList.getProjectCount() + ")", 0);

        if (!agentList.getSellCount().equals("0")) {
            fragmentAgentProperty.setData(agentList, "Sell");
            adapter.addFragment(fragmentAgentProperty, "PROPERTY FOR SALE (" + agentList.getSellCount() + ")", 0);

        }
        if (!agentList.getRentCount().equals("0")) {
            fragmentAgentPropertyRent.setData(agentList, "Rent");
            adapter.addFragment(fragmentAgentPropertyRent, "PROPERTY FOR RENT (" + agentList.getRentCount() + ")", 0);

        }
       /* if (!agentList.getProjectCount().equals("0"))
            adapter.addFragment(new FragmentOverView(),"PROPERTY FOR RENT  ("+agentList.getRentCount()+")",0);*/
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<ExtendedFragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private final List<Integer> mFragmentBadges = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(ExtendedFragment fragment, String title, int badage) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
            mFragmentBadges.add(badage);
            notifyDataSetChanged();
        }

        public void resetFragment(ExtendedFragment fragment, String title, int badage, int position) {
            mFragmentList.set(position, fragment);
            mFragmentTitleList.set(position, title);
            mFragmentBadges.set(position, badage);
            notifyDataSetChanged();
        }

        public int getBadge(int position) {
            return mFragmentBadges.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    //TODO Main functions
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


    //TODO Alert
    private void alertContactOwner() {
        alertDialog = new Dialog(AgentDetailsActivity.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_contact_agent_popup, null);
        ImageView close = alert_layout.findViewById(R.id.close_contact_owner);
        final EditText fullName = alert_layout.findViewById(R.id.editText_user_full_name_contact_owner);
        final EditText email = alert_layout.findViewById(R.id.editText_user_email_address_contact_owner);
        final EditText phone = alert_layout.findViewById(R.id.editText_phone_contact_owner);
        final CountryCodePicker codePicker = alert_layout.findViewById(R.id.country_code_contact_owner);
        final EditText message = alert_layout.findViewById(R.id.editText_user_message_contact_owner);
        final TextView title = alert_layout.findViewById(R.id.title);
        title.setText("Contact Agent");
       /* final CheckBox cbMortgage = alert_layout.findViewById(R.id.checkBox_info_mortgage_contact_owner);
        final CheckBox cbTerms = alert_layout.findViewById(R.id.checkBox_terms_conditions_contact_owner);*/
        TextView submit = alert_layout.findViewById(R.id.button_submit_contact_owner);
        codePicker.setCountryForPhoneCode(91);
        if (SessionManager.getLoginStatus(AgentDetailsActivity.this)) {
            fullName.setText(SessionManager.getFullName(AgentDetailsActivity.this));
            email.setText(SessionManager.getEmail(AgentDetailsActivity.this));
            phone.setText(SessionManager.getPhone(AgentDetailsActivity.this));
            codePicker.setDefaultCountryUsingNameCode(SessionManager.getCountryCode(AgentDetailsActivity.this).toUpperCase());
            message.clearFocus();
            message.requestFocus();
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strMessage;
                String strCountryCode = codePicker.getSelectedCountryCode().toLowerCase();
                String strName;
                String strEmail;
                String strPhone;


                if (fullName.getText().toString().trim().equals("")) {
                    fullName.clearFocus();
                    fullName.requestFocus();
                    Toast.makeText(AgentDetailsActivity.this, getString(R.string.please_enter_name), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    strName = fullName.getText().toString();
                    if (!Utils.isValidName(strName)) {
                        Toast.makeText(AgentDetailsActivity.this, getString(R.string.please_enter_valid_name),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (email.getText().toString().equals("")) {
                    email.clearFocus();
                    email.requestFocus();
                    Toast.makeText(AgentDetailsActivity.this, getString(R.string.please_enter_email_address), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    strEmail = email.getText().toString();
                    if (TextUtils.isEmpty(strEmail) || !Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                        Toast.makeText(AgentDetailsActivity.this, getString(R.string.please_enter_valid_email_address), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (phone.getText().toString().equals("")) {
                    phone.clearFocus();
                    phone.requestFocus();
                    Toast.makeText(AgentDetailsActivity.this, getString(R.string.please_enter_phone_number), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    strPhone = phone.getText().toString();
                    if (!Utils.isValidMobile(strPhone)) {
                        Toast.makeText(AgentDetailsActivity.this,
                                getString(R.string.valid_mobile_number), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (message.getText().toString().trim().equals("")) {
                    message.clearFocus();
                    message.requestFocus();
                    Toast.makeText(AgentDetailsActivity.this, getString(R.string.please_enter_enquiry), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    strMessage = message.getText().toString();
                }

                if (!Utils.NoInternetConnection(AgentDetailsActivity.this)) {
                    postContactOwner(alertDialog, strName, strEmail, strPhone, strCountryCode, strMessage);
                } else {
                    Toast.makeText(AgentDetailsActivity.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void alertSuccess(String successTitle, String successMessage) {
        alertDialog = new Dialog(AgentDetailsActivity.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_success_popup, null);
        ImageView close = alert_layout.findViewById(R.id.close_success_dialog);
        TextView title = alert_layout.findViewById(R.id.title_alert_success_popup);
        TextView message = alert_layout.findViewById(R.id.message_alert_success_popup);
        title.setText(successTitle);
        message.setText(successMessage);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }
}
