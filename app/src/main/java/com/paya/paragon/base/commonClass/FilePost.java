package com.paya.paragon.base.commonClass;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.paya.paragon.activity.postProperty.PostPropertyPage05Activity;
import com.paya.paragon.utilities.DialogProgress;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import timber.log.Timber;

@SuppressWarnings("HardCodedStringLiteral")
public class FilePost extends AsyncTask<Void, Integer, String>  {

    private String mUrl;
    private ArrayList<PairedValues> mPairedValues;
    public boolean socketTimedOut = false;
    public DialogProgress dialog;
    Context context;
    long totalSize = 0;


    public FilePost(String url, ArrayList<PairedValues> pairedValues, PostPropertyPage05Activity context) {
        this.mUrl = url;
        this.mPairedValues = pairedValues;
        this.context = context;
        dialog = new DialogProgress(context);


    }


    @Override
    protected String doInBackground(Void... params) {
        return uploadFile();

    }

    private boolean isCompleted = false;

    private String uploadFile() {
        try {
            URL url = new URL(mUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      /*      conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setUseCaches(false);
            conn.setDoInput(true);*/
            conn.setRequestMethod("POST");
            conn.setChunkedStreamingMode(0);
            conn.setDoOutput(true);

            AndroidMultiPartEntity reqEntity = new AndroidMultiPartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, StandardCharsets.UTF_8, num -> publishProgress((int) ((num / (float) totalSize) * 100)));

//            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName("UTF-8"));
            for (int i = 0; i < mPairedValues.size(); i++) {
                PairedValues pairedValue = mPairedValues.get(i);
                Timber.tag("filePost").e("----------------------------------------------------------");
                Timber.tag("filePost").e("filePost---"+"pairedValue.getType()--->"+pairedValue.getType());
                Timber.tag("filePost").e("filePost---"+"pairedValue.getKey()--->"+pairedValue.getKey());
                if (pairedValue.getType() == PairedValues.TYPE_FILE) {
                    reqEntity.addPart(pairedValue.getKey(), new FileBody(pairedValue.getFile()));
                    Timber.tag("filePost").e("filePost---"+"pairedValue.getFile()--->"+pairedValue.getFile().getAbsolutePath());
                } else {
                    Timber.tag("filePost").e("filePost---"+"pairedValue.getText()--->"+pairedValue.getText());
//                    reqEntity.addPart(pairedValue.getKey(), new StringBody(pairedValue.getText()));
                    reqEntity.addPart(pairedValue.getKey(), new StringBody(pairedValue.getText(), ContentType.TEXT_PLAIN));
                }


                totalSize = reqEntity.getContentLength();

            }

            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.addRequestProperty("Content-length", reqEntity.getContentLength() + "");
            conn.addRequestProperty(reqEntity.getContentType().getName(), reqEntity.getContentType().getValue());



            OutputStream os = conn.getOutputStream();
            reqEntity.writeTo(conn.getOutputStream());
            os.close();
            conn.connect();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                isCompleted = true;
                return readStream(conn.getInputStream());
            }

        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            Log.e("test", "multipart post error " + e + "(" + mUrl + ")");
        }
        return null;
    }

    private static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        dialog.dismiss();
        if (isCompleted) {
            try {
                fireC(new JSONObject(s));
            } catch (JSONException e) {
                e.printStackTrace();
                fireE(e + " " + s);
            }
        } else {
            fireE(s);
        }
    }

    @Override
    public void onProgressUpdate(Integer... values) {
        dialog.setProgress(String.valueOf(values[0]));
    }

    @Override
    public void onPreExecute() {

        dialog.show();
      }

    public interface OnPostEntityListener {
        void onCompleted(JSONObject jsonObject);

        void onError(String error);
    }

    private OnPostEntityListener onPostEntityListener;

    public void start(OnPostEntityListener onPostEntityListener) {
        this.onPostEntityListener = onPostEntityListener;
        super.execute();
    }

    public void execute() {
        throw new RuntimeException("Call PostFileToServer.start(), instead of PostFileToServer.execute() ");
    }

    private void fireC(JSONObject jsonObject) {
        if (onPostEntityListener != null)
            onPostEntityListener.onCompleted(jsonObject);
    }

    private void fireE(String error) {
        if (onPostEntityListener != null)
            onPostEntityListener.onError(error);
    }

}
