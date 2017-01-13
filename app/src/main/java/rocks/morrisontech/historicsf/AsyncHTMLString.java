package rocks.morrisontech.historicsf;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Quinn on 1/9/17.
 */

public class AsyncHTMLString extends AsyncTask<String, Void, String> {

    private OnHTMLReceived dlComplete;
    private HttpURLConnection urlConnection;
    private BufferedReader reader;
    StringBuilder htmlString = new StringBuilder();

    public AsyncHTMLString() {}

    public AsyncHTMLString(OnHTMLReceived activityContext) {
        this.dlComplete = activityContext;
    }

    @Override
    protected String doInBackground(String... strings) {
        String urlString = strings[0];

        try {
            URL serviceUrl = new URL(urlString);
            urlConnection = (HttpsURLConnection) serviceUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream ins = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(ins);
            reader = new BufferedReader(isr);
            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                htmlString.append(inputLine);
            }

            Log.i("HTML_STRING", htmlString.toString());
            urlConnection.disconnect();


        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(htmlString);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
