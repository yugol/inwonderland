package aibroker.model.cloud.sources.sibex;

import java.io.IOException;
import aibroker.model.SequenceDescriptor;
import aibroker.util.NumberUtil;
import aibroker.util.WebUtil;

public class SibexSequenceDescriptionReader {

    public static SequenceDescriptor readDescription(final String name) throws IOException {
        int beginIndex, endIndex, foo, bar;
        final SequenceDescriptor sDesc = new SequenceDescriptor(name);

        final String symbolPageAddress = "http://www.sibex.ro/index.php?p=specfut&s=" + sDesc.symbol() + "&l=en";
        String html = WebUtil.getPageHtml(symbolPageAddress);

        beginIndex = html.indexOf("Underlying asset");
        beginIndex = html.indexOf("<td", beginIndex) + 1;
        beginIndex = html.indexOf(">", beginIndex) + 1;
        endIndex = html.indexOf("</td>", beginIndex);
        String hay = html.substring(beginIndex, endIndex);
        foo = hay.indexOf("Symbol");
        if (foo > 0) {
            foo = hay.indexOf(" ", foo + 1) + 1;
            bar = hay.indexOf(")", foo);
            sDesc.support(hay.substring(foo, bar));
        }

        final String tablePageAddress = "http://www.sibex.ro/index.php?p=specAllFut&l=en";
        html = WebUtil.getPageHtml(tablePageAddress);

        beginIndex = html.lastIndexOf(">" + sDesc.symbol() + "<");
        beginIndex = html.indexOf("<td", beginIndex) + 1;
        beginIndex = html.indexOf(">", beginIndex) + 1;
        endIndex = html.indexOf("</td>", beginIndex);
        hay = html.substring(beginIndex, endIndex);
        sDesc.multiplier(NumberUtil.parseDouble(hay));

        beginIndex = html.indexOf("<td", beginIndex) + 1;
        beginIndex = html.indexOf("<td", beginIndex) + 1;
        beginIndex = html.indexOf(">", beginIndex) + 1;
        endIndex = html.indexOf("</td>", beginIndex);
        hay = html.substring(beginIndex, endIndex);
        foo = hay.indexOf("RON");
        if (foo > 0) {
            double fee = 0;
            String chunk = hay.substring(0, foo - 1);
            fee += NumberUtil.parseDouble(chunk);
            foo = hay.indexOf("RON", foo + 1);
            if (foo > 0) {
                chunk = hay.substring(hay.lastIndexOf(" ", foo - 2), foo);
                fee += NumberUtil.parseDouble(chunk);
            }
            sDesc.fee(fee);
        }

        return sDesc;
    }

}
