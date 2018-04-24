package id.pptik.ilham.sahabatbawaslu.networks;

import org.jsoup.Jsoup;

/**
 * Created by Ilham on 24/04/18.
 */

public class HTMLParser {
    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
}
