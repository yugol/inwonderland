package aibroker.model.domains;

import aibroker.model.drivers.amibroker.AmibrokerDb;
import aibroker.model.drivers.csv.CsvDb;
import aibroker.model.drivers.metastock.MetastockDb;
import aibroker.model.drivers.sql.SqlDb;

public enum DbEngine {

    AMIBROKER (AmibrokerDb.class),
    CSV (CsvDb.class),
    METASTOCK (MetastockDb.class),
    SQL (SqlDb.class),

    ;

    public final Class<?> clazz;

    private DbEngine(final Class<?> clazz) {
        this.clazz = clazz;
    }

}
