package aibroker.util.convenience;

import java.io.File;
import aibroker.Context;
import aibroker.model.QuotesDb;
import aibroker.model.domains.DbEngine;
import aibroker.model.drivers.sql.SqlDb;

public enum Databases {

    AMI_DEFAULT (DbEngine.AMIBROKER, "AMI", "amibroker.480"),
    CSV_DEFAULT (DbEngine.CSV, "CSV", "aibrokerxp.csv"),
    SQL_DEFAULT (DbEngine.SQL, "SQL", "aibrokerxp.qdb"),

    ;

    public static SqlDb DEFAULT() {
        return (SqlDb) SQL_DEFAULT.getInstance();
    }

    public final DbEngine  type;
    public final String    name;
    public final File      url;

    private QuotesDb instance;

    private Databases(final DbEngine type, final String name, final String url) {
        this.type = type;
        this.name = name;
        this.url = new File(Context.getQuotesFolder(), url);
    }

    public QuotesDb getInstance() {
        if (instance == null) {
            try {
                instance = (QuotesDb) type.clazz.getDeclaredConstructor(File.class).newInstance(url);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

}
