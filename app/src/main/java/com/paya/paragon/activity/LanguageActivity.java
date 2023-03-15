package com.paya.paragon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.paya.paragon.PropertyProjectListActivity;
import com.paya.paragon.R;
import com.paya.paragon.api.langaugeList.LanguageList;
import com.paya.paragon.base.BaseViewModelActivity;
import com.paya.paragon.databinding.ActivityLanguageBinding;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;
import com.paya.paragon.viewmodel.LanguageActivityViewModel;

public class LanguageActivity extends BaseViewModelActivity<LanguageActivityViewModel> implements LanguageActivityViewModel.LanguageActivityViewModelCallback {

    private ActivityLanguageBinding binding;
    private LanguageActivityViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_language);
        Utils.changeLayoutOrientationDynamically(this, binding.getRoot());
        onCreateViewModel();
    }

    @Override
    public LanguageActivityViewModel onCreateViewModel() {
        viewModel = ViewModelProviders.of(this).get(LanguageActivityViewModel.class);
        viewModel.init(this, this);
        return viewModel;
    }

    private void initiate() {
        binding.trEnglish.setOnClickListener(languageSelectionListener(viewModel.getLanguageFromList(Utils.LAG_ENGLISH_ID)));
        binding.trArabic.setOnClickListener(languageSelectionListener(viewModel.getLanguageFromList(Utils.LAG_ARABIC_ID)));
        binding.trKurdish.setOnClickListener(languageSelectionListener(viewModel.getLanguageFromList(Utils.LAG_KURDISH_ID)));
    }

    private View.OnClickListener languageSelectionListener(final LanguageList language) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.toggleLanguage(LanguageActivity.this, language);
            }
        };
    }

    @Override
    public void initiateLanguageData() {
        boolean isEnglishAvailable = false, isArabicAvailable = false, isKurdishAvailable = false;
        for (LanguageList language : viewModel.languageLists) {
            if (Utils.LAG_ENGLISH.equalsIgnoreCase(language.getLanguageName())) {
                isEnglishAvailable = true;
            }
            if (Utils.LAG_ARABIC.equalsIgnoreCase(language.getLanguageName())) {
                isArabicAvailable = true;
            }
            if (Utils.LAG_KURDISH.equalsIgnoreCase(language.getLanguageName())) {
                isKurdishAvailable = true;
            }
        }
        binding.trEnglish.setVisibility(isEnglishAvailable ? View.VISIBLE : View.GONE);
        binding.trArabic.setVisibility(isArabicAvailable ? View.VISIBLE : View.GONE);
        binding.trKurdish.setVisibility(isKurdishAvailable ? View.VISIBLE : View.GONE);
        initiate();
    }

    @Override
    public void languageUpdateSuccessfully() {
        SessionManager.setFirstTimeLaunch(this, false);
        Intent intent;
        if (SessionManager.getLocationId(this) != null && !SessionManager.getLocationId(this).isEmpty()) {
            intent = new Intent(this, PropertyProjectListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            GlobalValues.clearBuyGlobalValues();
            intent.putExtra("searchPropertyPurpose", "Sell");
        } else {
            intent = new Intent(this, CitySelectionActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
