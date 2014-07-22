package aibroker.model.domains;

import aibroker.model.drivers.amibroker.AmibrokerDatabase;
import aibroker.model.drivers.csv.CsvDatabase;
import aibroker.model.drivers.metastock.MetastockDatabase;
import aibroker.model.drivers.sql.SqlDatabase;

public enum DbEngine {

    AMIBROKER (AmibrokerDatabase.class),
    CSV (CsvDatabase.class),
    METASTOCK (MetastockDatabase.class),
    SQL (SqlDatabase.class),

    ;

    public final Class<?> clazz;

    private DbEngine(final Class<?> clazz) {
        this.clazz = clazz;
    }

}
