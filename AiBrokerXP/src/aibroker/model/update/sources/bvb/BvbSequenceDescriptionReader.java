package aibroker.model.update.sources.bvb;

import java.io.IOException;
import aibroker.model.SequenceDescriptor;
import aibroker.util.NumberUtil;
import aibroker.util.WebUtil;

public class BvbSequenceDescriptionReader {

    public static SequenceDescriptor readDescription(final String name) throws IOException {
        final String pageAddress = "http://www.bvb.ro/ListedCompanies/SecurityDetail.aspx?s=" + name;
        final String html = WebUtil.getPageHtml(pageAddress);
        // System.out.println(html);
        final SequenceDescriptor sDesc = new SequenceDescriptor(name);

        int beginIndex = html.indexOf("ctl00_central_wcTunel_lbBlockSize");
        beginIndex = html.indexOf(">", beginIndex) + 1;
        int endIndex = html.indexOf("<", beginIndex);
        sDesc.setBlockSize(NumberUtil.parseInt(html.substring(beginIndex, endIndex)));

        beginIndex = html.indexOf("ctl00_central_lbPrice");
        beginIndex = html.indexOf(">", beginIndex) + 1;
        endIndex = html.indexOf("<", beginIndex);
        sDesc.setLastPrice(NumberUtil.parseFloat(html.substring(beginIndex, endIndex)));

        return sDesc;
    }
}