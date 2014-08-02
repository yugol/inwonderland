package aibroker.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class WebUtil {

    public static String getPageHtml(final String pageAddress) throws IOException {
        final URL url = new URL(pageAddress);
        try (BufferedReader urlReader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            final StringBuilder html = new StringBuilder();
            String line = null;
            while ((line = urlReader.readLine()) != null) {
                html.append(line);
            }
            return html.toString();
        }
    }

}
