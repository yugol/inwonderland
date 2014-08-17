package aibroker.model.cloud.sources.bvb;

import java.io.IOException;
import aibroker.model.SeqDesc;
import aibroker.util.NumberUtil;
import aibroker.util.WebUtil;

public class BvbSeqDescriptionReader {

    public static void fillDescription(final SeqDesc sDesc) throws IOException {
        final String pageAddress = "http://www.bvb.ro/ListedCompanies/SecurityDetail.aspx?s=" + sDesc.getName();
        final String html = WebUtil.getPageHtml(pageAddress);
        // System.out.println(html);

        int beginIndex = html.indexOf("ctl00_central_wcTunel_lbBlockSize");
        beginIndex = html.indexOf(">", beginIndex) + 1;
        int endIndex = html.indexOf("<", beginIndex);
        sDesc.setBlockSize(NumberUtil.parseInt(html.substring(beginIndex, endIndex)));

        beginIndex = html.indexOf("ctl00_central_lbPrice");
        beginIndex = html.indexOf(">", beginIndex) + 1;
        endIndex = html.indexOf("<", beginIndex);
        sDesc.getLastPrice(NumberUtil.parseFloatRo(html.substring(beginIndex, endIndex)));

        beginIndex = html.indexOf("ctl00_central_lbDeni");
        beginIndex = html.indexOf(">", beginIndex) + 1;
        endIndex = html.indexOf("<", beginIndex);
        sDesc.setName(html.substring(beginIndex, endIndex));
    }

    public static SeqDesc readDescription(final String name) throws IOException {
        final SeqDesc sDesc = new SeqDesc(name);
        fillDescription(sDesc);
        return sDesc;
    }

}
