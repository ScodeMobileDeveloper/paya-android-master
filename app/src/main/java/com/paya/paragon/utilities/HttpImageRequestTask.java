package com.paya.paragon.utilities;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

public class HttpImageRequestTask extends AsyncTask<Void, Void, Drawable> {
    @Override
    protected Drawable doInBackground(Void... voids) {
       /* try {


            final URL url = new URL("http://upload.wikimedia.org/wikipedia/commons/e/e8/Svg_example3.svg");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            SVG svg = SVGParser.getSVGFromInputStream(inputStream);
            Drawable drawable = svg.createPictureDrawable();
            return drawable;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
*/
        return null;
    }
    @Override
    protected void onPostExecute(Drawable drawable) {
        // Update the view
        updateImageView(drawable);
    }
    @SuppressLint("NewApi")
    private void updateImageView(Drawable drawable){
        if(drawable != null){

          /*  // Try using your library and adding this layer type before switching your SVG parsing
            mImageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            mImageView.setImageDrawable(drawable);*/
        }
    }
}
