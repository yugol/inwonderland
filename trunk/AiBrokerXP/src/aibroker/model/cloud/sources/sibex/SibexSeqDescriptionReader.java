package aibroker.model.cloud.sources.sibex;

import java.io.IOException;
import aibroker.model.SeqDesc;
import aibroker.util.NumberUtil;
import aibroker.util.WebUtil;

public class SibexSeqDescriptionReader {

    public static SeqDesc readDescription(final String name) throws IOException {
        int beginIndex, endIndex, foo, bar;
        final SeqDesc sDesc = new SeqDesc(name);

        final String symbolPageAddress = "http://www.sibex.ro/index.php?p=specfut&s=" + sDesc.getSymbol() + "&l=en";
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
            sDesc.setSupport(hay.substring(foo, bar));
        }

        final String tablePageAddress = "http://www.sibex.ro/index.php?p=specAllFut&l=en";
        html = WebUtil.getPageHtml(tablePageAddress);

        beginIndex = html.lastIndexOf(">" + sDesc.getSymbol() + "<");
        beginIndex = html.indexOf("<td", beginIndex) + 1;
        beginIndex = html.indexOf(">", beginIndex) + 1;
        endIndex = html.indexOf("</td>", beginIndex);
        hay = html.substring(beginIndex, endIndex);
        sDesc.setMultiplier(NumberUtil.parseDoubleRo(hay));

        beginIndex = html.indexOf("<td", beginIndex) + 1;
        beginIndex = html.indexOf("<td", beginIndex) + 1;
        beginIndex = html.indexOf(">", beginIndex) + 1;
        endIndex = html.indexOf("</td>", beginIndex);
        hay = html.substring(beginIndex, endIndex);
        foo = hay.indexOf("RON");
        if (foo > 0) {
            double fee = 0;
            String chunk = hay.substring(0, foo - 1);
            fee += NumberUtil.parseDoubleRo(chunk);
            foo = hay.indexOf("RON", foo + 1);
            if (foo > 0) {
                chunk = hay.substring(hay.lastIndexOf(" ", foo - 2), foo);
                fee += NumberUtil.parseDoubleRo(chunk.replace(".", ","));
            }
            sDesc.setFee(fee);
        }

        return sDesc;
    }

}
