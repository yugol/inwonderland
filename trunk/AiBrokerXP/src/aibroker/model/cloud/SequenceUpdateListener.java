package aibroker.model.cloud;

import aibroker.util.Moment;

public interface SequenceUpdateListener {

    void onBeginDownloading(String symbol, String settlement, Moment firstDate, Moment lastDate);

    boolean onDownloaded(String symbol, String settlement);

    void onDownloading(String symbol, String settlement, Moment date);

    void onEndDownloading(String symbol, String settlement);

    void onError(String symbol, String settlement, Moment firstDate, Exception e);

}
