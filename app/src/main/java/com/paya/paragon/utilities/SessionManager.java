package com.paya.paragon.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.paya.paragon.model.RequirementModel;

import java.util.HashMap;

@SuppressWarnings("HardCodedStringLiteral")
public class SessionManager {
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    // Shared preferences file name
    private static final String PREF_NAME = "hodPreferences";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private static final String KEY_IS_LOGIN = "isLogin";
    private static final String KEY_COMPANY_NAME = "companyName";
    private static final String KEY_FIRST_NAME = "firstName";
    private static final String KEY_LAST_NAME = "lastName";
    private static final String KEY_USER_ID = "userID";
    private static final String KEY_USER_TYPE_ID = "userTypeID";
    private static final String KEY_ACCESS_TOKEN = "accessToken";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PROFILE_IMAGE = "userImage";
    private static final String KEY_COUNTRY_CODE = "countryCode";
    private static final String KEY_COUNTRY_CODE_PLUS = "countryCodePlus";
    private static final String KEY_PREFERENCE_NEWSLETTER = "newsletterPreferences";
    private static final String KEY_PREFERENCE_SAVED_SEARCH = "savedSearchPreferences";
    private static final String KEY_PREFERENCE_SAVED_SEARCH_TYPE = "savedSearchTypePreferences";
    private static final String KEY_PREFERENCE_SMS = "smsPreferences";
    private static final String KEY_LANGUAGE = "language_id";
    private static final String KEY_LANGUAGE_FOR_LOCALE = "language_id_locale";
    private static final String KEY_LANGUAGE_NAME = "language_name";
    private static final String KEY_STATE_ID = "state_id";
    private static final String KEY_LOCATION_ID = "location_id";
    private static final String KEY_SELECTED_LOCATION_ID = "user_selected_location_id";
    private static final String KEY_LOCATION_NAME = "location_name";
    private static final String KEY_SELECTED_LOCATION_NAME = "user_selected_location_name";
    private static final String KEY_LOCATION_LAT = "location_latitude";
    private static final String KEY_LOCATION_LONG = "location_longitude";
    private static final String KEY_REQUIREMENT = "post_requirement";
    private static final String KEY_DEVICE_FCM_TOKEN = "device_fcm_token";
    private static final String KEY_OTP_VERIFIED = "otpVerified";

    private static final String KEY_CITY_NOTIFICATION_DATA_UPDATE = "KEY_CITY_NOTIFICATION_DATA_UPDATE";

    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();
        editor.apply();
    }

    public static void saveLogin(Context context, String firstName, String lastName,String userID, String accessToken,
                                 String email, String phone, String countryCode, String userTypeId,
                                 String userImage, boolean isLogin) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_FIRST_NAME, firstName);
        editor.putString(KEY_LAST_NAME, lastName);
        editor.putString(KEY_USER_ID, userID);
        editor.putString(KEY_USER_TYPE_ID, userTypeId);
        editor.putString(KEY_ACCESS_TOKEN, accessToken);
        editor.putString(KEY_EMAIL, email.replace("%40", "@"));
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_PROFILE_IMAGE, userImage);
        editor.putString(KEY_COUNTRY_CODE, countryCode);
        editor.putBoolean(KEY_IS_LOGIN, isLogin);
        editor.apply();
    }
    public static void saveAgentLogin(Context context, String companyName, String firstName,String userID, String accessToken,
                                 String phone, String countryCode, String userTypeId,
                                 String companyLogo, boolean isLogin) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_COMPANY_NAME, companyName);
        editor.putString(KEY_FIRST_NAME, firstName);
        editor.putString(KEY_USER_ID, userID);
        editor.putString(KEY_USER_TYPE_ID, userTypeId);
        editor.putString(KEY_ACCESS_TOKEN, accessToken);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_PROFILE_IMAGE, companyLogo);
        editor.putString(KEY_COUNTRY_CODE, countryCode);
        editor.putBoolean(KEY_IS_LOGIN, isLogin);
        editor.apply();
    }
    public static void setCountryCodeWithPlus(Context context, String countryCodePlus) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_COUNTRY_CODE_PLUS, countryCodePlus);
        editor.apply();
    }
    public static String getCountryCodePlus(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getString(KEY_COUNTRY_CODE_PLUS, "");
    }
    public static void setisLogin(Context context, boolean isLogin) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(KEY_IS_LOGIN, isLogin);

        editor.apply();
    }
    public static void setOTPverified(Context context, boolean otpVerified) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(KEY_OTP_VERIFIED, otpVerified);
        editor.apply();
    }
    public static boolean isOTPVerified(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getBoolean(KEY_OTP_VERIFIED, true);
    }
    public static void setFirstTimeLaunch(Context context, boolean isFirstTime) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.apply();
    }

    public static boolean isFirstTimeLaunch(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    public static String getAccessToken(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getString(KEY_ACCESS_TOKEN, "");
    }

    public static boolean getLoginStatus(Context context) {
        if (context != null) {
            pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
            return pref.getBoolean(KEY_IS_LOGIN, false);
        } else
            return false;
    }

    public static String getFullName(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getString(KEY_FIRST_NAME, "") + " " + pref.getString(KEY_LAST_NAME, "");
    }

    public static String getEmail(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getString(KEY_EMAIL, "");
    }

    public static String getPhone(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getString(KEY_PHONE, "");
    }

    public static String getUserId(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getString(KEY_USER_ID, "");
    }

    public static int getUserTypeId(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return Integer.parseInt(pref.getString(KEY_USER_TYPE_ID, "" + AppConstant.USER_TYPE_ID_USER));
    }

    public static String getCountryCode(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getString(KEY_COUNTRY_CODE, "");
    }

    public static void setNewsLetterStatus(Context context, String status) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_PREFERENCE_NEWSLETTER, status);
        editor.apply();
    }

    public static void setSavedSearchStatus(Context context, String status) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_PREFERENCE_SAVED_SEARCH, status);
        editor.apply();
    }

    public static void setSavedSearchTypeStatus(Context context, String status) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_PREFERENCE_SAVED_SEARCH_TYPE, status);
        editor.apply();
    }

    public static void setSavedSMSStatus(Context context, String status) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_PREFERENCE_SMS, status);
        editor.apply();
    }


    public static String getNewsLetterStatus(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getString(KEY_PREFERENCE_NEWSLETTER, "");
    }

    public static String getSavedSearchStatus(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getString(KEY_PREFERENCE_SAVED_SEARCH, "");
    }

    public static String getSavedSearchTypeStatus(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getString(KEY_PREFERENCE_SAVED_SEARCH_TYPE, "");
    }

    public static String getSavedSMSStatus(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getString(KEY_PREFERENCE_SMS, "");
    }

    public static void logout(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor e = pref.edit();
        e.remove(KEY_IS_LOGIN);
        e.remove(KEY_FIRST_NAME);
        e.remove(KEY_COMPANY_NAME);
        e.remove(KEY_LAST_NAME);
        e.remove(KEY_USER_ID);
        e.remove(KEY_USER_TYPE_ID);
        e.remove(KEY_ACCESS_TOKEN);
        e.remove(KEY_EMAIL);
        e.remove(KEY_PHONE);
        e.remove(KEY_PROFILE_IMAGE);
        e.remove(KEY_COUNTRY_CODE);
        e.remove(KEY_COUNTRY_CODE_PLUS);
        e.remove(KEY_OTP_VERIFIED);
        e.remove(KEY_PREFERENCE_NEWSLETTER);
        e.remove(KEY_PREFERENCE_SAVED_SEARCH);
        e.apply();
    }

    public static void setProfileImage(Context context, String url) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_PROFILE_IMAGE, url);
        editor.apply();
    }
    public static void setFullName(Context context, String firstname,String lastname) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_FIRST_NAME, firstname);
        editor.putString(KEY_LAST_NAME,lastname);
        editor.apply();
    }
    public static void setMobileNumber(Context context, String url) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_PHONE, url);
        editor.apply();
    }

    public static void setCountryCode(Context context, String url) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_COUNTRY_CODE, url);
        editor.apply();
    }

    public static String getProfileImage(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getString(KEY_PROFILE_IMAGE, "");
    }

    public static void setLanguageID(Context context, String languageId) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_LANGUAGE, languageId);
        editor.apply();
    }

    public static String getLanguageID(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getString(KEY_LANGUAGE, Utils.LAG_ARABIC_ID);
    }

    public static void setLanguageIDForLocaleSetUp(Context context, String languageId) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_LANGUAGE_FOR_LOCALE, languageId);
        editor.apply();
    }

    public static String getLanguageIDForLocaleSetUp(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getString(KEY_LANGUAGE_FOR_LOCALE, Utils.LAG_ARABIC_ID);
    }

    public static void setLanguageName(Context context, String locationId) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_LANGUAGE_NAME, locationId);
        editor.apply();
    }

    public static String getLanguageName(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getString(KEY_LANGUAGE_NAME, Utils.LAG_ARABIC);
    }


    public static void setLocationId(Context context, String locationId) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_LOCATION_ID, locationId);
        editor.apply();
    }

    public static String getLocationId(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getString(KEY_LOCATION_ID, "44");
    }


    public static void setSelectedLocationId(Context context, String locationId) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_SELECTED_LOCATION_ID, locationId);
        editor.apply();
    }

    public static String getSelectedLocationId(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getString(KEY_SELECTED_LOCATION_ID, "44");
    }

    public static void setLocationName(Context context, String locationName, String latitude, String longitude) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_LOCATION_NAME, locationName);
        editor.putString(KEY_LOCATION_LAT, latitude);
        editor.putString(KEY_LOCATION_LONG, longitude);
        editor.apply();
    }

    public static String getLocationName(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getString(KEY_LOCATION_NAME, "Erbil");
    }

    public static void setSelectedLocationName(Context context, String locationName, String latitude, String longitude) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_SELECTED_LOCATION_NAME, locationName);
        editor.putString(KEY_LOCATION_LAT, latitude);
        editor.putString(KEY_LOCATION_LONG, longitude);
        editor.apply();
    }

    public static String getSelectedLocationName(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getString(KEY_SELECTED_LOCATION_NAME, "Erbil");
    }

    public static HashMap<String, String> getLocationValues(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        HashMap<String, String> map = new HashMap<>();
        map.put("lat", pref.getString(KEY_LOCATION_LAT, "0.00"));
        map.put("long", pref.getString(KEY_LOCATION_LONG, "0.00"));
        return map;
    }

    public static void setStateID(Context context, int stateId) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(KEY_STATE_ID, stateId);
        editor.apply();
    }

    public static int getStateID(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getInt(KEY_STATE_ID, -1);
    }

    public static void setPostRequirement(Context context, RequirementModel requirementModel) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String requirementString = gson.toJson(requirementModel);
        editor.putString(KEY_REQUIREMENT, requirementString);
        editor.apply();
    }

    public static RequirementModel getPostRequirement(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        Gson gson = new Gson();
        String json = pref.getString(KEY_REQUIREMENT, "");
        if (json.equals("")) {
            json = gson.toJson(new RequirementModel());
        }
        return gson.fromJson(json, RequirementModel.class);

    }

    public static void setDeviceTokenForFCM(Context context, String token) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_DEVICE_FCM_TOKEN, token);
        editor.apply();
    }

    public static String getDeviceTokenForFCM(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getString(KEY_DEVICE_FCM_TOKEN, "");
    }

    public static void setCityUpdationForNotification(Context context, boolean isCityDataUpdate) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(KEY_CITY_NOTIFICATION_DATA_UPDATE, isCityDataUpdate);
        editor.apply();
    }

    public static boolean isCityUpdateNotificationUpdated(Context context) {
        pref = context.getSharedPreferences(SessionManager.PREF_NAME, 0);
        return pref.getBoolean(KEY_CITY_NOTIFICATION_DATA_UPDATE, false);
    }
}
