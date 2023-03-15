package com.paya.paragon.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.paya.paragon.PayaAppClass;
import com.paya.paragon.R;
import com.paya.paragon.api.langaugeList.LanguageList;
import com.paya.paragon.api.langaugeList.LanguageListApi;
import com.paya.paragon.api.langaugeList.LanguageListResponse;
import com.paya.paragon.base.BaseViewModel;
import com.paya.paragon.base.commonClass.ApiLinks;
import com.paya.paragon.utilities.SessionManager;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class
LanguageActivityViewModel extends BaseViewModel {

    private LanguageActivityViewModelCallback callback;
    public ArrayList<LanguageList> languageLists;
    private Context context;

    public LanguageActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(Context context, LanguageActivityViewModelCallback callback) {
        this.context = context;
        this.callback = callback;
        initLanguageAPICall();
    }

    private void initLanguageAPICall() {

        ApiLinks.getClient().create(LanguageListApi.class).post()
                .enqueue(new Callback<LanguageListResponse>() {
                    @Override
                    public void onResponse(Call<LanguageListResponse> call, Response<LanguageListResponse> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getResponse();
                            if (message.equalsIgnoreCase("Success")) {
                                languageLists = new ArrayList<>();
                                languageLists.addAll(response.body().getData().getLanguageLists());
                                boolean isKurdishAvailable = false;
                                for (LanguageList languageModel : languageLists) {
                                    if (!isKurdishAvailable) {
                                        isKurdishAvailable = languageModel.getLanguageID().equalsIgnoreCase(Utils.LAG_KURDISH_ID);
                                    }
                                }
                                if (!isKurdishAvailable) {
                                    LanguageList languageList = new LanguageList();
                                    languageList.setLanguageID(Utils.LAG_KURDISH_ID);
                                    languageList.setLanguageName(context.getString(R.string.kurdish));
                                    languageLists.add(languageList);
                                }
                                callback.initiateLanguageData();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LanguageListResponse> call, Throwable t) {
                        languageLists = new ArrayList<>();
                        LanguageList languageList = new LanguageList();
                        languageList.setLanguageID(Utils.LAG_ENGLISH_ID);
                        languageList.setLanguageName(Utils.LAG_ENGLISH);
                        languageLists.add(languageList);
                        callback.initiateLanguageData();
                    }
                });
    }


    public void toggleLanguage(Context context, LanguageList language) {
        SessionManager.setLanguageID(context, language.getLanguageID());
        SessionManager.setLanguageIDForLocaleSetUp(context, language.getLanguageID());
        SessionManager.setLanguageName(context, language.getLanguageName());
        Locale locale = new Locale(language.getLanguageID().equalsIgnoreCase(Utils.LAG_ENGLISH_ID) ? "en" : language.getLanguageID().equalsIgnoreCase(Utils.LAG_ARABIC_ID) ? "ar" : "ku");
        Locale.setDefault(locale);
        Configuration config = context.getResources().getConfiguration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        callback.languageUpdateSuccessfully();
    }

    public LanguageList getLanguageFromList(String selectedLanguageId) {
        for (LanguageList language : languageLists) {
            if (selectedLanguageId.equalsIgnoreCase(language.getLanguageID())) {
                return language;
            }
        }
        LanguageList languageList = new LanguageList();
        languageList.setLanguageID(Utils.LAG_ARABIC_ID);
        languageList.setLanguageName(context.getString(R.string.arabic));
        return languageList;
    }


    public interface LanguageActivityViewModelCallback {

        void initiateLanguageData();

        void languageUpdateSuccessfully();
    }
}
