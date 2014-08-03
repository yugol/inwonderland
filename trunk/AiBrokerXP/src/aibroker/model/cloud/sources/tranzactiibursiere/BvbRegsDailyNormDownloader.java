package aibroker.model.cloud.sources.tranzactiibursiere;

import aibroker.util.Moment;

public class BvbRegsDailyNormDownloader extends BvbRegsDailyOrigDownloader {

    private static final BvbRegsDailyNormDownloader instance = new BvbRegsDailyNormDownloader();

    public static BvbRegsDailyNormDownloader getInstance() {
        return instance;
    }

    @Override
    protected StringBuilder getDownloadUrl(final String symbol, final Moment firstDate, final Moment lastDate) {
        final StringBuilder url = new StringBuilder();
        url.append("http://www.tranzactiibursiere.ro/detalii/istoric_csv?lang=en&symbol=");
        url.append(symbol);
        url.append("&market=REGS&sdate=");
        url.append(firstDate.toIsoDate());
        url.append("&edate=");
        url.append(lastDate.toIsoDate());
        url.append("&type=ajustat&x=%2C");
        return url;
    }

}
