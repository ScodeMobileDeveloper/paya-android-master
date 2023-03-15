package com.paya.paragon.fragments.agentDetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.paya.paragon.R;
import com.paya.paragon.activity.login.ActivityLoginEmail;
import com.paya.paragon.api.AgentDetail.AgentDetail;
import com.paya.paragon.api.AgentDetail.AgentDetailResponse;
import com.paya.paragon.api.AgentDetail.AgentFollowResponse;
import com.paya.paragon.api.AgentDetail.AgentsDetailApi;
import com.paya.paragon.api.AgentDetail.AgentsFollowApi;
import com.paya.paragon.api.agentList.AgentList;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.ExtendedFragment;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"RedundantCast", "HardCodedStringLiteral", "ConstantConditions"})
public class FragmentOverView extends ExtendedFragment {
    public TextView userCompanyName, username, userAboutMe, userSkills,
            userAddress1, userAddress2, dealsIn, userRegNumber, userCompanyUrlKey,
            userYearOfInception, agentFollow;
    private LinearLayout userCompanyUrlLay;
    public ImageView userProfilePicture, userCompanyLogo;
    private AgentList agentList;
    private AgentDetail agentDetail;
    private DialogProgress progress;
    private LinearLayout userAboutMeLay, userSkillsLay;
    private View mask;

    private String imagePath = "", companyImageURL = "";
    private boolean isAgentFollow = false;

    @Override
    public View onCreateFragmentView(LayoutInflater inflater, @Nullable ViewGroup container,
                                     @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_agent_overview, null, false);
        view = declarations(view);

        getAgentDetail();
        return view;
    }


    //TODO API Calls
    public void getAgentDetail() {
        progress.show();
        mask.setVisibility(View.VISIBLE);
        String userId = SessionManager.getAccessToken(getActivity());
        ApiLinks.getClient().create(AgentsDetailApi.class).post(agentList.getUserUrlKey(), userId)
                .enqueue(new Callback<AgentDetailResponse>() {
                    @Override
                    public void onResponse(Call<AgentDetailResponse> call, Response<AgentDetailResponse> response) {
                        String message = response.body().getMessage();
                        if (response.body().getResponse().equalsIgnoreCase("Success")) {
                            imagePath = response.body().getData().getImageURL();
                            companyImageURL = response.body().getData().getCompanyImageURL();

                            agentDetail = response.body().getData().getAgentDetail();
                            setAgentData(agentDetail);
                            mask.setVisibility(View.GONE);
                        } else {

                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            mask.setVisibility(View.VISIBLE);
                        }
                        progress.dismiss();
                    }

                    @Override
                    public void onFailure(Call<AgentDetailResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                        mask.setVisibility(View.VISIBLE);
                    }
                });


    }

    public void agentFollow() {
        progress.show();
        ApiLinks.getClient().create(AgentsFollowApi.class).post(SessionManager.getAccessToken(getActivity()), agentList.getUserID())
                .enqueue(new Callback<AgentFollowResponse>() {
                    @Override
                    public void onResponse(Call<AgentFollowResponse> call, Response<AgentFollowResponse> response) {
                        String message = response.body().getMessage();
                        if (response.body().getResponse().equalsIgnoreCase("Success")) {
                            isAgentFollow = !isAgentFollow;
                            agentFollowToggle();
                        }
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }

                    @Override
                    public void onFailure(Call<AgentFollowResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                });
    }


    //TODO Set Data
    private View declarations(View view) {
        progress = new DialogProgress(getActivity());
        userCompanyName = (TextView) view.findViewById(R.id.userCompanyName);
        userAboutMeLay = (LinearLayout) view.findViewById(R.id.userAboutMeLay);
        userSkillsLay = (LinearLayout) view.findViewById(R.id.userSkillsLay);
        username = (TextView) view.findViewById(R.id.username);
        userAboutMe = (TextView) view.findViewById(R.id.userAboutMe);
        userSkills = (TextView) view.findViewById(R.id.userSkills);
        userAddress1 = (TextView) view.findViewById(R.id.userAddress1);
        userAddress2 = (TextView) view.findViewById(R.id.userAddress2);

        userRegNumber = (TextView) view.findViewById(R.id.userRegNumber);
        userCompanyUrlKey = (TextView) view.findViewById(R.id.userCompanyUrlKey);
        userCompanyUrlLay = (LinearLayout) view.findViewById(R.id.userCompanyUrlLay);
        agentFollow = (TextView) view.findViewById(R.id.agentFollow);
        mask = (View) view.findViewById(R.id.mask);

        dealsIn = (TextView) view.findViewById(R.id.dealsIn);
        userYearOfInception = (TextView) view.findViewById(R.id.userYearOfInception);
        userProfilePicture = (ImageView) view.findViewById(R.id.userProfilePicture);
        userCompanyLogo = (ImageView) view.findViewById(R.id.userCompanyLogo);
        return view;
    }

    private void agentFollowToggle() {
        if (isAgentFollow) {
            agentFollow.setText(R.string.unfollow);
            agentFollow.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_unfollow_user, 0, 0, 0);
        } else {
            agentFollow.setText(R.string.follow);
            agentFollow.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_follow_user, 0, 0, 0);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setAgentData(final AgentDetail agentDetail) {
        userCompanyName.setText(agentDetail.getUserCompanyName());
        username.setText(agentDetail.getUsername() + " " + agentDetail.getUserLastName());
        if (!agentDetail.getUserAboutMe().equals(""))
            userAboutMeLay.setVisibility(View.VISIBLE);

        userAboutMe.setText(agentDetail.getUserAboutMe());
        if (!agentDetail.getUserSkills().equals(""))
            userSkillsLay.setVisibility(View.VISIBLE);

        userSkills.setText(agentDetail.getUserSkills());

        String userAddress1String = agentDetail.getUserAddress1() + ", " + agentDetail.getUserAddress2();

        String userAddress2String = agentDetail.getStateName() + ", " + agentDetail.getCityName()
                + ", " + agentDetail.getCountryName() + "  " + agentDetail.getUserZip();
        userAddress1.setText(userAddress1String);
        userAddress2.setText(userAddress2String);
        if (agentDetail.getUserYearOfInception() != null & !agentDetail.getUserYearOfInception().equals("")) {
            userYearOfInception.setText("Year of Inception: " + agentDetail.getUserYearOfInception());
            userYearOfInception.setVisibility(View.VISIBLE);
        } else userYearOfInception.setVisibility(View.GONE);
        if (agentDetail.getUserRegNumber() != null & !agentDetail.getUserRegNumber().equals("")) {
            userRegNumber.setText("Company Register Number: " + agentDetail.getUserRegNumber());
            userRegNumber.setVisibility(View.VISIBLE);
        } else userRegNumber.setVisibility(View.GONE);


        String agentFollowT = agentDetail.getPropertyFollowCount();
        if (agentFollowT.equalsIgnoreCase("1")) {
            isAgentFollow = true;
        } else {
            isAgentFollow = false;
        }
        agentFollowToggle();

        if (agentDetail.getUserCompanyUrlKey() != null & !agentDetail.getUserCompanyUrlKey().equals("")) {
            String url = agentDetail.getUserCompanyUrlKey();
            int l1 = agentDetail.getUserCompanyUrlKey().length();
            //int l2 = url.length();
            Spannable strUrl = new SpannableString(url);
            ClickableSpan click = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Uri uri = Uri.parse(agentDetail.getUserCompanyUrlKey());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(true);
                    ds.setColor(Color.BLACK);
                    userCompanyUrlKey.setHighlightColor(Color.TRANSPARENT);
                }
            };
            strUrl.setSpan(click, 0, l1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            userCompanyUrlLay.setVisibility(View.VISIBLE);
            userCompanyUrlKey.setText(strUrl);
            userCompanyUrlKey.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            userCompanyUrlLay.setVisibility(View.GONE);
        }

        dealsIn.setText(agentDetail.getDealsin());
        String image = imagePath + agentDetail.getUserProfilePicture();
        Utils.loadUrlImage(userProfilePicture, image, R.drawable.no_image_user, true);
        String companyLogo = companyImageURL + agentDetail.getUserCompanyLogo();
        Utils.loadUrlImage(userCompanyLogo, companyLogo, R.drawable.no_logo, false);
        agentFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SessionManager.getAccessToken(getActivity()).equals("")) {
                    Intent intentLogin = new Intent(getActivity(), ActivityLoginEmail.class);
                    startActivity(intentLogin);
                } else agentFollow();
            }
        });

    }

    public void setData(AgentList data) {
        this.agentList = data;
    }

}