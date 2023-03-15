package com.paya.paragon.fragments.builderDetails;

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
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.paya.paragon.R;
import com.paya.paragon.api.builderDetails.BuilderDetail;
import com.paya.paragon.api.builderDetails.BuildersDetailApi;
import com.paya.paragon.api.builderDetails.BuildersDetailResponse;
import com.paya.paragon.api.buildersList.BuildersList;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.utilities.DialogProgress;
import com.paya.paragon.utilities.ExtendedFragment;
import com.paya.paragon.utilities.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"RedundantCast", "HardCodedStringLiteral", "ConstantConditions"})
public class FragmentBuilderOverView extends ExtendedFragment {
    public TextView userCompanyName, userAboutMe, userSkills,
            userAddress1, userCompanyUrl,
            userYearOfInception;
    public ImageView userCompanyLogo;
    private BuildersList buildersList;
    private BuilderDetail builderDetail;
    private DialogProgress progress;
    private LinearLayout userAboutMeLay, userSkillsLay;
    private String imagePath = "";

    @Override
    public View onCreateFragmentView(LayoutInflater inflater, @Nullable ViewGroup container,
                                     @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_builder_overview,
                null, false);
        view = declarations(view);

        getAgentList();
        return view;
    }

    public void getAgentList() {
        progress.show();
        ApiLinks.getClient().create(BuildersDetailApi.class).post(buildersList.getUserUrlKey())
                .enqueue(new Callback<BuildersDetailResponse>() {
                    @Override
                    public void onResponse(Call<BuildersDetailResponse> call,
                                           Response<BuildersDetailResponse> response) {
                        String message = response.body().getMessage();
                        if (response.body().getResponse().equalsIgnoreCase("Success")) {
                            imagePath = response.body().getData().getImageURL();
                            builderDetail = response.body().getData().getBuilderDetail();
                            setBuilderData(builderDetail);
                        } else {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        }
                        progress.dismiss();
                    }

                    @Override
                    public void onFailure(Call<BuildersDetailResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                });


    }


    private void setBuilderData(final BuilderDetail builderDetail) {
        userCompanyName.setText(builderDetail.getUserCompanyName());

        String userAddressString = builderDetail.getUserAddress1()
                + builderDetail.getUserAddress2()
                + builderDetail.getCityName() + ", "
                + builderDetail.getStateName() + ", "
                + builderDetail.getCountryName() + "-"
                + builderDetail.getUserZip();
        userAddress1.setText(userAddressString);
        if (!builderDetail.getUserAboutMe().equals(""))
            userAboutMeLay.setVisibility(View.VISIBLE);

        userAboutMe.setText(builderDetail.getUserAboutMe());
        if (!builderDetail.getUserSkills().equals(""))
            userSkillsLay.setVisibility(View.VISIBLE);

        userSkills.setText(builderDetail.getUserSkills());

        String year = "Year of Inception: " + builderDetail.getUserYearOfInception();
        int c1 = builderDetail.getUserYearOfInception().length();
        int c2 = year.length();
        Spannable strYear = new SpannableString(year);
        strYear.setSpan(new ForegroundColorSpan(Color.BLACK),
                c2 - c1, c2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        userYearOfInception.setText(strYear);

        if (builderDetail.getUserCompanyUrlKey() != null & !builderDetail.getUserCompanyUrlKey().equals("")) {
            String url = "Company Url: " + builderDetail.getUserCompanyUrlKey();
            int l1 = builderDetail.getUserCompanyUrlKey().length();
            int l2 = url.length();
            Spannable strUrl = new SpannableString(url);
            ClickableSpan click = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Uri uri = Uri.parse(builderDetail.getUserCompanyUrlKey());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(true);
                    ds.setColor(Color.BLACK);
                    userCompanyUrl.setHighlightColor(Color.TRANSPARENT);
                }
            };
            strUrl.setSpan(click, l2 - l1, l2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            userCompanyUrl.setVisibility(View.VISIBLE);
            userCompanyUrl.setText(strUrl);
            userCompanyUrl.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            userCompanyUrl.setVisibility(View.GONE);
        }

        String companyLogo = imagePath + builderDetail.getUserCompanyLogo();
        Utils.loadUrlImage(userCompanyLogo, companyLogo, R.drawable.no_logo, false);
    }

    private View declarations(View view) {
        progress = new DialogProgress(getActivity());
        userCompanyName = (TextView) view.findViewById(R.id.userCompanyName);
        userYearOfInception = (TextView) view.findViewById(R.id.userYearOfInception);
        userCompanyLogo = (ImageView) view.findViewById(R.id.userCompanyLogo);
        userAddress1 = (TextView) view.findViewById(R.id.userAddress1);
        userCompanyUrl = (TextView) view.findViewById(R.id.userCompanyUrlKey);

        userAboutMeLay = (LinearLayout) view.findViewById(R.id.userAboutMeLay);
        userSkillsLay = (LinearLayout) view.findViewById(R.id.userSkillsLay);
        userAboutMe = (TextView) view.findViewById(R.id.userAboutMe);
        userSkills = (TextView) view.findViewById(R.id.userSkills);


        return view;
    }

    public void setData(BuildersList data) {
        this.buildersList = data;
    }

}