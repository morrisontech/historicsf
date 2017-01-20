package rocks.morrisontech.historicsf;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import rocks.morrisontech.historicsf.entity.LandmarkEntity;

/**
 * Created by Quinn on 1/7/17.
 */
public class AsyncLandmarkEntities extends AsyncTask<String, Void, String> {

    private final String LOG_TAG = "AsyncLandmarks.class";
    StringBuilder jsonString = new StringBuilder();
    HttpsURLConnection urlConnection;
    BufferedReader reader = null;
    private OnLandmarksReceived dlComplete;

    // default constructor to get context, etc
    public AsyncLandmarkEntities() {

    }

    public AsyncLandmarkEntities(OnLandmarksReceived activityContext) {
        this.dlComplete = activityContext;
    }

    @Override
    protected String doInBackground(String... strings) {

        // filter params
        String searchParam;

        // build uri
        // download and return json string

        try {

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https");
            builder.authority("data.sfgov.org");
            builder.appendPath("resource");
            builder.appendPath(/*endpoint*/"798h-cfqf.json");
            builder.appendQueryParameter("$$app_token", "XmhHBPPmpGboNkk0yEwWb3R46");


            String url = builder.build().toString();
            Log.i("URL", url);

            // build custom URL here based on params passed in String... strings
            URL serviceUrl = new URL(url);
            urlConnection = (HttpsURLConnection) serviceUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream ins = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(ins);
            reader = new BufferedReader(isr);
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                jsonString.append(inputLine);
            }

            Log.i("JSON", jsonString.toString());
            urlConnection.disconnect();
            // only returns null if there is an exception
            return String.valueOf(jsonString);

        } catch (IOException e) {
            Log.e(LOG_TAG, "unable to retrieve data");
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
    }


    @Override
    protected void onPostExecute(String s) {
        Gson gson = new Gson();

        LandmarkEntity[] landmarkEntities = gson.fromJson(s, LandmarkEntity[].class);
        dlComplete.onLandmarksReceived(landmarkEntities);

        super.onPostExecute(s);
    }

}
