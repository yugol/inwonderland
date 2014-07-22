package aibroker.model.update.sources.bursanoastra;

import java.io.File;
import aibroker.Context;

public class BursanoastraConfig {

    public static final String MAIN_URL                  = "http://bursanoastra.ro";
    public static final String DOWNLOAD_URL              = "https://www.box.com/shared/4ts2utcg4y";
    public static final File   METASTOCK_DATABASE_FOLDER = new File(Context.getQuotesFolder(), "bursanoastra/superbazadedate/");

}
