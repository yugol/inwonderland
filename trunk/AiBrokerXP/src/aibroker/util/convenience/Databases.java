package aibroker.util.convenience;

import java.io.File;
import aibroker.Context;
import aibroker.model.QuotesDatabase;
import aibroker.model.domains.DbEngine;
import aibroker.model.drivers.sql.SqlDatabase;

public enum Databases {

    AMI_DEFAULT (DbEngine.AMIBROKER, "AMI", "amibroker.480"),
    CSV_DEFAULT (DbEngine.CSV, "CSV", "aibrokerxp.csv"),
    SQL_DEFAULT (DbEngine.SQL, "SQL", "aibrokerxp.qdb"),

    ;

    public static SqlDatabase DEFAULT() {
        return (SqlDatabase) SQL_DEFAULT.getInstance();
    }

    public final DbEngine  type;
    public final String    name;
    public final File      url;

    private QuotesDatabase instance;

    private Databases(final DbEngine type, final String name, final String url) {
        this.type = type;
        this.name = name;
        this.url = new File(Context.getQuotesFolder(), url);
    }

    public QuotesDatabase getInstance() {
        if (instance == null) {
            try {
                instance = (QuotesDatabase) type.clazz.getDeclaredConstructor(File.class).newInstance(url);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

}
