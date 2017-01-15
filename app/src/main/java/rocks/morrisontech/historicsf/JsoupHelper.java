package rocks.morrisontech.historicsf;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Quinn on 1/13/17.
 */

public class JsoupHelper {

    private static final String LOG_TAG = "JsoupHelper.class";
    private static String htmlUrl;
    private static String imageUrl = "";


    public static String getImageUrl(String urlString) {

        try {
            Document doc = Jsoup.connect(urlString).get();
            String title = doc.title();
            Log.i(LOG_TAG, title);
            Elements media = doc.select("[src]");
            imageUrl = media.attr("src");
            Log.i(LOG_TAG, imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageUrl;
    }
}
