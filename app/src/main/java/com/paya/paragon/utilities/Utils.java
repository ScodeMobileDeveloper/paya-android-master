package com.paya.paragon.utilities;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.paya.paragon.PayaAppClass;
import com.paya.paragon.R;
import com.paya.paragon.activity.login.ActivityLoginEmail;
import com.paya.paragon.api.budgetList.BudgetPriceData;
import com.paya.paragon.model.ImageDataClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Handler;
import java.util.regex.Pattern;

@SuppressWarnings("HardCodedStringLiteral")
public class Utils {
    public static final int minLength = 5,
            maxLength = 50,
            minNumbers = 0,
            minLetters = 0,
            minLowerCase = 0,
            minUpperCase = 0,
            minSymbols = 0,
            maxSymbols = 3;
    public static final String allowedSymbols = "#, _ , ! ";
    public static final String TYPE = "Property_project_type";
    public static final String ATTR_PHONE_NO_ID = "242";
    public static final String ATTR_AREA_ID = "4";
    public static final String ATTR_BATH_ROOM_ID = "3";
    public static final String ATTR_KITCHEN_ID = "216";
    public static final String ATTR_LIVING_ROOM_ID = "208";
    public static final String ATTR_BED_ROOM_ID = "1";
    public static final String ATTR_FLOOR_NUMBER_ID = "215";
    public static final String ATTR_FURNISHED_ID = "214";


    public static final String ATTR_BED_ROOM_STUDIO_ID = "482";
    public static final String ATTR_BED_ROOM_1_ID = "1";
    public static final String ATTR_BED_ROOM_2_ID = "2";
    public static final String ATTR_BED_ROOM_3_ID = "3";
    public static final String ATTR_BED_ROOM_4_ID = "4";
    public static final String ATTR_BED_ROOM_5_ID = "5";
    public static final String ATTR_BED_ROOM_6_ID = "21";

    public static final String ATTR_BED_ROOM_7_ID = "863";

    public static final String ATTR_BED_ROOM_8_ID = "864";

    public static final String LAG_ARABIC = "Arabic";
    public static final String LAG_ENGLISH = "English";
    public static final String LAG_KURDISH = "Kurdish";
    public static final String LAG_KURDISH_ID = "9";
    public static final String LAG_ARABIC_ID = "6";
    public static final String LAG_ENGLISH_ID = "1";
    public static final String DECIMAL_4 = ".####";
    public static String ATTR_AMENITIES_ID = "6";


    private static Map<String, Integer> cityMap = new HashMap<String, Integer>() {{
        put("864", R.string.city_864);
        put("2558", R.string.city_2558);
        put("1358", R.string.city_1358);
        put("1188", R.string.city_1188);
        put("41", R.string.city_41);
        put("861", R.string.city_861);
        put("2283", R.string.city_2283);
        put("1555", R.string.city_1555);
        put("57", R.string.city_57);
        put("44", R.string.city_44);
        put("1459", R.string.city_1459);
        put("829", R.string.city_829);
        put("869", R.string.city_869);
        put("1284", R.string.city_1284);
        put("881", R.string.city_881);
        put("58", R.string.city_58);
        put("1268", R.string.city_1268);
        put("56", R.string.city_56);
        put("1604", R.string.city_1604);

    }};


    private static Map<String, Integer> amenitiesIconMapWithId;
    private static Map<String, String> selectedAttrIdValueMap;
    public static Map<String, String> selectedNeighbourhoodValueMap = new HashMap<>();

    public static ArrayList<PasswordValidation> validatePassword(String password) {
        char[] passwordCharArray = password.toCharArray();
        ArrayList<PasswordValidation> arrayList = new ArrayList<>();
        if (password.length() < minLength)
            arrayList.add(PasswordValidation.MIN_LENGTH);
        if (password.length() > maxLength)
            arrayList.add(PasswordValidation.MAX_LENGTH);
        int checkFlag = 0;
        int i;
        for (i = 0; i < passwordCharArray.length; i++) {
            if (Character.isDigit(passwordCharArray[i])) {
                checkFlag++;
            }
        }
        if (checkFlag < minNumbers) {
            arrayList.add(PasswordValidation.MIN_NUMBERS);
        }
        checkFlag = 0;
        for (i = 0; i < passwordCharArray.length; i++) {
            if (Character.isLetter(passwordCharArray[i])) {
                checkFlag++;
            }
        }
        if (checkFlag < minLetters) {
            arrayList.add(PasswordValidation.MIN_LETTERS);
        }
        checkFlag = 0;
        for (i = 0; i < passwordCharArray.length; i++) {
            if (Character.isLetter(passwordCharArray[i])) {
                if (Character.isLowerCase(passwordCharArray[i])) {
                    checkFlag++;
                }
            }
        }
        if (checkFlag < minLowerCase) {
            arrayList.add(PasswordValidation.MIN_LOWER_CASE);
        }
        checkFlag = 0;
        for (i = 0; i < passwordCharArray.length; i++) {
            if (Character.isLetter(passwordCharArray[i])) {
                if (Character.isUpperCase(passwordCharArray[i])) {
                    checkFlag++;
                }
            }
        }
        if (checkFlag < minUpperCase) {
            arrayList.add(PasswordValidation.MIN_UPPER_CASE);
        }

        checkFlag = 0;
        for (i = 0; i < passwordCharArray.length; i++) {
            if (!Character.isLetterOrDigit(passwordCharArray[i])) {
                if (allowedSymbols.contains(passwordCharArray[i] + ""))
                    checkFlag++;
            }
        }
        if (checkFlag < minSymbols) {
            arrayList.add(PasswordValidation.MIN_SYMBOLS);
        }
        if (checkFlag >= maxSymbols) {
            arrayList.add(PasswordValidation.MAX_SYMBOLS);
        }
        checkFlag = 0;
        for (i = 0; i < passwordCharArray.length; i++) {
            if (!Character.isLetterOrDigit(passwordCharArray[i])) {
                if (passwordCharArray[i] != '#' && passwordCharArray[i] != '_' && passwordCharArray[i] != '!')
                    checkFlag++;
            }
        }
        if (checkFlag > 0) {
            arrayList.add(PasswordValidation.NOT_ALLOWED_SYMBOLS);
        }

        if (password.contains(" ")) {
            arrayList.add(PasswordValidation.CONTAIN_BLANK_SPACE);
        }
        if (arrayList.size() == 0)
            arrayList.add(PasswordValidation.PASSWORD_OK);
        return arrayList;
    }

    public static Boolean NoInternetConnection(Context context) {
        Boolean isWifiConn = false, isMobileConn = false;
        try {
            ConnectivityManager cMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cMgr.getActiveNetworkInfo();
            if (networkInfo != null) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    isWifiConn = networkInfo.isConnected();
                }
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    isMobileConn = networkInfo.isConnected();
                }
            }
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }
        return (!isWifiConn && !isMobileConn);
    }

    public static String parseDate(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date startDate;
        try {
            startDate = df.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            String month = getMonthForInt(cal.get(Calendar.MONTH));
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String year = cal.get(Calendar.YEAR) + "";
            //return month+" "+day+", "+year;

            day = Integer.parseInt(date.substring(8, 10));

            return day + " " + month + " " + year;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getMonthForInt(int num) {
        String month = "" + num;
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
            return month;
        }
        return (num + 1) + "";
    }

    public static void showAlertNoInternet(final Context context) {
        final Dialog alertDialog = new Dialog(context);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_no_intenet, null);
        TextView ok = alert_layout.findViewById(R.id.alert_text_no_internet);

        ok.setOnClickListener(new View.OnClickListener() {
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


    public static void showAlertLogout(final Activity context, String message) {
        final Dialog alertDialog = new Dialog(context);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_no_intenet, null);
        TextView ok = alert_layout.findViewById(R.id.alert_text_no_internet);
        TextView alert_title = alert_layout.findViewById(R.id.alert_title_no_internet);
        if (!message.isEmpty()) {
            alert_title.setText(message);
        }
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                SessionManager.logout(context);
                context.startActivity(new Intent(context, ActivityLoginEmail.class));
                context.finish();
            }
        });

        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    public static void showAlertNoInternetFinish(final Activity context) {
        final Dialog alertDialog = new Dialog(context);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_no_intenet, null);
        TextView ok = alert_layout.findViewById(R.id.alert_text_no_internet);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                context.finish();
            }
        });

        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    public static void showAlertErrorMessage(final Activity context, String message) {
        final Dialog alertDialog = new Dialog(context);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater factory = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") final View alert_layout = factory.inflate(R.layout.alert_error_message, null);
        TextView title = alert_layout.findViewById(R.id.alert_title_message);
        TextView ok = alert_layout.findViewById(R.id.alert_text_message_ok);
        if (message != null && !message.isEmpty()) {
            title.setText(message);
        } else {
            title.setText(context.getString(R.string.please_try_after_some_times));
        }
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                context.finish();
            }
        });

        alertDialog.setContentView(alert_layout);
        //noinspection ConstantConditions
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public static void expand(final View view, int duration) {
        view.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = view.getMeasuredHeight();
        view.getLayoutParams().height = 1;
        view.setVisibility(View.VISIBLE);
        Animation anim = new Animation() {
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                view.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                view.requestLayout();
            }

            public boolean willChangeBounds() {
                return true;
            }
        };
        anim.setDuration(duration);
        view.startAnimation(anim);
    }

    public static void collapse(final View view, int duration) {
        final int initialHeight = view.getMeasuredHeight();
        Animation anim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        anim.setDuration(duration);
        view.startAnimation(anim);
    }

    public static String convertDateFormat(String strDate) {
        String formattedDate = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy  hh:mm a", Locale.getDefault());
        try {
            Date date = format.parse(strDate);
            formattedDate = formatDate.format(date).toUpperCase();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static String convertToDateOnlyFormat(String strDate) {
        String formattedDate = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat formatDate = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault());
        try {
            Date date = format.parse(strDate);
            formattedDate = formatDate.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static String convertToDateOnlyFormat(String strDate, boolean month) {
        String formattedDate = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat formatDate = new SimpleDateFormat("MMM dd yyyy", Locale.getDefault());
        try {
            Date date = format.parse(strDate);
            formattedDate = formatDate.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static Toast showToastMessage(Context context, String message) {
        return Toast.makeText(context, message, Toast.LENGTH_SHORT);
    }

    //TODO Validation
    public static boolean isValidMobile(String phone) {
        boolean check;
        //TODO after change in number format
        check = (!Pattern.matches("[a-zA-Z]+", phone)) && (phone.length() < 15 && phone.length() >= 7);
        return check;
    }

    public static boolean isValidName(String inputString) {
        inputString = inputString.trim();
        String specialCharacters = "!#$%&'()*+,-./:;<=>?@[]^_`{|}~0123456789";
        String[] strCharactersArray = new String[inputString.length()];
        for (int i = 0; i < inputString.length(); i++) {
            strCharactersArray[i] = Character
                    .toString(inputString.charAt(i));
        }
        int count = 0;
        for (String aStrCharactersArray : strCharactersArray) {
            if (specialCharacters.contains(aStrCharactersArray)) {
                count++;
            }

        }

        if (count == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidEmail(Context context, String strEmail) {
        if (strEmail != null && !strEmail.equals("")) {
            if (!TextUtils.isEmpty(strEmail) && Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                return true;
            } else {
                Toast.makeText(context, context.getString(R.string.please_enter_valid_email_address), Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(context, context.getString(R.string.please_enter_email_address), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static String validateContactPopUp(Context context, String strName, String strEmail, String strPhone, CheckBox cbTerms) {
        String message = "ok";
        if (strName.isEmpty() || strName.equals("")) {
            return context.getString(R.string.please_enter_name);
        }
        if (!isValidName(strName)) {
            return context.getString(R.string.please_enter_valid_name);
        }
        if (!isValidEmail(context, strEmail)) {
            return "notOk";
        }
        if (!isValidMobile(strPhone)) {
            return context.getString(R.string.valid_phone_number);
        }
        if (!cbTerms.isChecked()) {
            return context.getString(R.string.accept_terms_conditions);
        }
        return message;
    }

    public static String validateAmount(String amount) {
        Pattern.matches("[a-zA-Z]+", amount);
        return amount;
    }

    public static ArrayList<BudgetPriceData> getBuyPriceList() {
        ArrayList<BudgetPriceData> buyPriceList = new ArrayList<>();
        int minPrice = 0;
        int maxPrice = 500000;
        int differenceValue = 1000;
        BudgetPriceData budgetPriceData;
        for (int i = minPrice; i <= maxPrice / differenceValue; i++) {
            budgetPriceData = new BudgetPriceData();
            budgetPriceData.setPrice(String.valueOf(i * differenceValue));
            budgetPriceData.setPriceValue(convertToCurrencyFormat(maxPrice, String.valueOf(i * differenceValue)));
            buyPriceList.add(budgetPriceData);
        }
        return buyPriceList;
    }

    public static String convertToCurrencyFormat(int maxPrice, String amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###", symbols);
        String value = decimalFormat.format(Double.parseDouble(amount));
        return maxPrice == Integer.parseInt(amount) ? value + "+" : value;
    }

    public static void changeLayoutOrientationDynamically(Context context, View parentView) {
        if (parentView != null) {
            boolean isRTLLanguageSelected = ((Integer.parseInt(SessionManager.getLanguageID(context)) == Integer.parseInt(Utils.LAG_ARABIC_ID)) || (Integer.parseInt(SessionManager.getLanguageID(context)) == Integer.parseInt(Utils.LAG_KURDISH_ID)));
            parentView.setLayoutDirection(isRTLLanguageSelected ? View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR);
//            parentView.setTextDirection(isRTLLanguageSelected ? View.TEXT_DIRECTION_RTL : View.TEXT_DIRECTION_LTR);
        }
    }

    public static void loadUrlImage(ImageView imageView, String imageUrl, int placeHolderImage, boolean isCircleCrop) {
        Context context = imageView.getContext();
        if (context == null) {
            context = PayaAppClass.getAppInstance();
        }
        if (context != null) {
            try {
                if (isCircleCrop) {
                    Glide.with(context)
                            .load(imageUrl)
                            .apply(new RequestOptions().override(imageView.getWidth(), imageView.getHeight()))
                            .placeholder(placeHolderImage)
                            .error(placeHolderImage)
                            .centerCrop()
                            .into(imageView);
                } else {
                    Glide.with(context)
                            .load(imageUrl)
                            .apply(new RequestOptions().override(imageView.getWidth(), imageView.getHeight()))
                            .placeholder(placeHolderImage)
                            .error(placeHolderImage)
                            .into(imageView);
                }
            } catch (Exception ignored) {

            }
        }

    }

    public static void loadUrlImage(ImageView imageView, String imageUrl, int placeHolderImage) {
        try {
            Glide.with(imageView)
                    .load(imageUrl)
                    .placeholder(placeHolderImage)
                    .error(placeHolderImage)
                    .into(imageView);
        } catch (Exception ignored) {

        }
    }

    public static boolean isGooglePlayServiceActivated(Context context) {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS;
    }

    public static int getAmenityImagesFromDrawable(String amenityIconId) {
        if (!amenityIconId.isEmpty() && amenitiesIconMapWithId.containsKey(amenityIconId)) {
            return amenitiesIconMapWithId.get(amenityIconId);
        } else {
            return R.drawable.no_image;
        }
    }

    public static void setAmenitiesIcon() {
        /**
         * these keys which i mentioned in this map are comming from the service
         * "https://www.paya-realestate.com/mobilapp/dashboard/bindAttributeForm"
         * hereby based on the keys we need to get the images which i stored in local drawable.
         */
        if (amenitiesIconMapWithId == null) {
            amenitiesIconMapWithId = new HashMap<>();
            amenitiesIconMapWithId.put("564", R.drawable.ic_private_green);
            amenitiesIconMapWithId.put("623", R.drawable.ic_super_market);
            amenitiesIconMapWithId.put("631", R.drawable.ic_ac);
            amenitiesIconMapWithId.put("632", R.drawable.ic_fully_furnished);
            amenitiesIconMapWithId.put("633", R.drawable.ic_gym);
            amenitiesIconMapWithId.put("634", R.drawable.ic_maid_room);
            amenitiesIconMapWithId.put("635", R.drawable.ic_swimming_pool);
            amenitiesIconMapWithId.put("636", R.drawable.ic_pets_allowed);
            amenitiesIconMapWithId.put("637", R.drawable.ic_furnished_kitchen);
            amenitiesIconMapWithId.put("638", R.drawable.ic_kids_play_area);
            amenitiesIconMapWithId.put("639", R.drawable.ic_covered_parking);
            amenitiesIconMapWithId.put("640", R.drawable.ic_mosque);
            amenitiesIconMapWithId.put("641", R.drawable.ic_school);
            amenitiesIconMapWithId.put("642", R.drawable.ic_fire_station);
            amenitiesIconMapWithId.put("643", R.drawable.ic_medical_center);
            amenitiesIconMapWithId.put("644", R.drawable.ic_internet_wifi);
            amenitiesIconMapWithId.put("645", R.drawable.ic_car_parking);
            amenitiesIconMapWithId.put("646", R.drawable.ic_maintance_support);
            amenitiesIconMapWithId.put("647", R.drawable.ic_24hrs_emergency);
            amenitiesIconMapWithId.put("648", R.drawable.ic_security);
            amenitiesIconMapWithId.put("649", R.drawable.ic_green_area);
            amenitiesIconMapWithId.put("650", R.drawable.ic_restaurant_access);
            amenitiesIconMapWithId.put("665", R.drawable.ic_atm);
            amenitiesIconMapWithId.put("666", R.drawable.ic_cafeteria);
            amenitiesIconMapWithId.put("677", R.drawable.ic_electrical_generator);
            amenitiesIconMapWithId.put("791", R.drawable.ic_reception);
            amenitiesIconMapWithId.put("792", R.drawable.ic_exchange);
            amenitiesIconMapWithId.put("793", R.drawable.ic_women_shaloon);
            amenitiesIconMapWithId.put("794", R.drawable.ic_women_gym);
            amenitiesIconMapWithId.put("798", R.drawable.ic_sewer);
            amenitiesIconMapWithId.put("808", R.drawable.ic_chruch);
            amenitiesIconMapWithId.put("810", R.drawable.ic_library);
            amenitiesIconMapWithId.put("826", R.drawable.ic_home_security_system);
        }
    }

    public static void loadDrawableImage(ImageView imageView, int amenityImagesFromDrawable) {
        /**
         * If I try to use Picasso there is light shaken happens when we scroll this recyclerview thats y here i use glide.
         */
        //        Picasso.get().load(amenityImagesFromDrawable)
        //                .error(R.drawable.no_image_placeholder)
        //                .into(imageView);
        Context context = imageView.getContext();
        if (context == null) {
            context = PayaAppClass.getAppInstance();
        }
        if (context != null) {
            Glide.with(context).load(amenityImagesFromDrawable)
                    .thumbnail(0.5f)
                    .error(R.drawable.no_image_placeholder)
                    .into(imageView);
        }
    }

    public static List<String> getAttributeDefaultList() {
        List<String> defaultList = new ArrayList<>();
        defaultList.add(ATTR_PHONE_NO_ID);
        defaultList.add(ATTR_AREA_ID);
        defaultList.add(ATTR_BATH_ROOM_ID);
        defaultList.add(ATTR_LIVING_ROOM_ID);
        defaultList.add(ATTR_BED_ROOM_ID);
        defaultList.add(ATTR_KITCHEN_ID);
        defaultList.add(ATTR_FLOOR_NUMBER_ID);
        return defaultList;
    }

    public static String getPackageVersionName(Context context) {
        String version = "";
        if (context != null) {
            try {
                PackageManager manager = context.getPackageManager();
                PackageInfo info = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
                version = info.versionName + " v" + info.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return version;
    }

    public static Drawable getLanguageFlag(String langName) {
        return ContextCompat.getDrawable(PayaAppClass.getAppInstance(), langName.contains(Utils.LAG_ENGLISH) ? R.drawable.menu_flag_uk : langName.contains(Utils.LAG_ARABIC) ? R.drawable.menu_flag_iraq : R.drawable.flag_kurdish);
    }

    public static String getPreviousEditedValue(String attrId) {
        if (selectedAttrIdValueMap != null && selectedAttrIdValueMap.containsKey(attrId)) {
            return selectedAttrIdValueMap.get(attrId);
        } else {
            return "";
        }
    }

    public static void setPreviousEditedValue(String attrId, String value, boolean isSelected, boolean isForAmenities) {
        if (selectedAttrIdValueMap != null) {
            if (selectedAttrIdValueMap.containsKey(attrId)) {
                if (!isForAmenities) {
                    if (value.isEmpty()) {
                        selectedAttrIdValueMap.remove(attrId);
                    } else {
                        selectedAttrIdValueMap.put(attrId, value);
                    }
                } else {
                    String[] amenValueList = selectedAttrIdValueMap.get(attrId).split(",");
                    selectedAttrIdValueMap.remove(attrId);
                    List<String> amenitiesList = new ArrayList<>(Arrays.asList(amenValueList));
                    if (isSelected) {
                        amenitiesList.add(value);
                    } else {
                        amenitiesList.remove(value);
                    }
                    for (String str : amenitiesList) {
                        if (!selectedAttrIdValueMap.containsKey(attrId)) {
                            selectedAttrIdValueMap.put(attrId, str);
                        } else {
                            selectedAttrIdValueMap.put(attrId, selectedAttrIdValueMap.get(attrId) + "," + str);
                        }
                    }
                }
            } else {
                selectedAttrIdValueMap.put(attrId, value);
            }
        } else {
            selectedAttrIdValueMap = new HashMap<>();
            selectedAttrIdValueMap.put(attrId, value);
        }
    }

    public static void setNeighbourhoodvalue(String Id, String value) {
        if (selectedNeighbourhoodValueMap != null) {
            if (selectedNeighbourhoodValueMap.containsKey(Id)) {
                selectedNeighbourhoodValueMap.remove(Id);
            } else {
                selectedNeighbourhoodValueMap.put(Id, value);
            }

        } else {
            selectedNeighbourhoodValueMap = new HashMap<>();
            selectedNeighbourhoodValueMap.put(Id, value);
        }


    }

    public static boolean getSelectedNeighbourValue(String Id) {
        if (selectedNeighbourhoodValueMap != null && selectedNeighbourhoodValueMap.containsKey(Id)) {
            return true;
        } else {
            return false;
        }
    }

    public static Map<String, String> getAttrAmenMap() {
        return selectedAttrIdValueMap;
    }

    public static void setAttrAmenMap(Map<String, String> attrAmenMap) {
        selectedAttrIdValueMap = attrAmenMap;
    }

    public static String getPath(final Context context, final Uri uri) {
        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                try {
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(id));
                    return getDataColumn(context, contentUri, null, null);
                } catch (Exception ex) {
                    FirebaseCrashlytics.getInstance().recordException(ex);
                    return "";
                }
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            String url = getDataColumn(context, uri, null, null);
            //For google photos without upload directly to paya server we download photos to app cache and uploaded to server
            //content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F21851/ORIGINAL/NONE/image%2Fjpeg/1870884169
            if (url == null) {
                url = getImagePathFromInputStreamUri(context, uri);
            }
            return url;
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getImagePathFromInputStreamUri(Context context, Uri uri) {
        InputStream inputStream = null;
        String filePath = null;

        if (uri.getAuthority() != null) {
            try {
                inputStream = context.getContentResolver().openInputStream(uri); // context needed
                File photoFile = createTemporalFileFrom(context, inputStream);

                filePath = photoFile.getPath();

            } catch (FileNotFoundException e) {
                FirebaseCrashlytics.getInstance().recordException(e);
                // log
            } catch (IOException e) {
                FirebaseCrashlytics.getInstance().recordException(e);
                // log
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    FirebaseCrashlytics.getInstance().recordException(e);
                    e.printStackTrace();
                }
            }
        }

        return filePath;
    }

    private static File createTemporalFileFrom(Context context, InputStream inputStream) throws IOException {
        File targetFile = null;

        if (inputStream != null) {
            int read;
            byte[] buffer = new byte[8 * 1024];

            targetFile = createTemporalFile(context);
            OutputStream outputStream = new FileOutputStream(targetFile);

            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();

            try {
                outputStream.close();
            } catch (IOException e) {
                FirebaseCrashlytics.getInstance().recordException(e);
                e.printStackTrace();
            }
        }

        return targetFile;
    }

    private static File createTemporalFile(Context context) {
        return new File(context.getExternalCacheDir(), System.currentTimeMillis() + "tempFile.jpg"); // context needed
    }

    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            File file = new File(uri.getPath());
            return file.getAbsolutePath();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    public static Boolean validateYoutubeUrl(String url) {
        return Patterns.WEB_URL.matcher(url).matches();
    }

    public static String getCurrentMonth() {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("MMM");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentYear() {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static List<ImageDataClass> getImageList(Activity activity) {
        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media._ID;
        Cursor imageCursor = activity.managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
        List<ImageDataClass> imageList = new ArrayList<>();
        for (int i = 0; i < imageCursor.getCount(); i++) {
            imageCursor.moveToPosition(i);
            ImageDataClass dataClass = new ImageDataClass();
            dataClass.setImageUrl(imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA)));
            dataClass.setImageId(Long.parseLong(imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media._ID))));
            dataClass.setImageSelected(false);
            imageList.add(dataClass);
        }
        Collections.reverse(imageList);
        return imageList;
    }

    public static String getDecimalFormat(double value, String decimalFormate) {
        return new DecimalFormat(decimalFormate).format(value);
    }

    public static boolean checkPriceNotHaveOnlyDot(String inputValue) {
        String value = inputValue;
        return !value.replace(".", "").isEmpty();
    }


    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static BitmapDrawable getBitmapDrawableLocal(Context context, String value, int i) {
        return (BitmapDrawable) ContextCompat.getDrawable(context, getDrawableID(value));
    }

    private static int getDrawableID(String value) {
        switch (value) {
            case "ATM":
                return R.drawable.ic_location_atm;
            case "Bus Station":
                return R.drawable.ic_location_bus_station;
            case "Food":
            case "Restaurant":
                return R.drawable.ic_location_food;
            case "Gym":
                return R.drawable.ic_location_gym;
            case "Hospital":
                return R.drawable.ic_location_hospital;
            case "Pharmacy":
                return R.drawable.ic_location_pharmacy;
            case "School":
                return R.drawable.ic_location_school;
            case "Temple":
                return R.drawable.ic_location_temple;
            case "Train":
                return R.drawable.ic_location_train;
            default:
                return R.drawable.ic_location_bank;
        }
    }

    public static String currencyFormat(String amount) {
        if (!amount.contains(",")) {
            DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
            return formatter.format(Double.parseDouble(amount));
        }
        return amount;
    }

    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public static Integer getCitNameBasedOnLanguage(String locationId) {
        if (cityMap.containsKey(locationId)) {
            return cityMap.get(locationId);
        } else {
            return 0;
        }
    }

    public static Map<String, Integer> getCityList() {
        return cityMap;
    }


    public static boolean appInstalledOrNot(Activity context, String uri) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static void setLanguage(Context context) {
        Locale locale = new Locale(SessionManager.getLanguageID(context).equalsIgnoreCase(Utils.LAG_ENGLISH_ID) ? "en" : SessionManager.getLanguageID(context).equalsIgnoreCase(Utils.LAG_ARABIC_ID) ? "ar" : "ku");
        Locale.setDefault(locale);
        Configuration config = context.getResources().getConfiguration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }


}
