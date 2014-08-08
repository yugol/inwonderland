package aibroker.model.drivers.csv;

import aibroker.model.SeqDesc;
import aibroker.model.Seq;
import aibroker.model.domains.Grouping;

public class CsvSeq extends Seq {

    private static String normalizeName(final String name) {
        return name.replace("/", "");
    }

    private final Grouping type;

    CsvSeq(final CsvDb csvDb, final SeqDesc sb) {
        super(csvDb, sb);
        type = sb.getGrouping();
    }

    @Override
    public String getName() {
        return normalizeName(super.getName());
    }

    public Grouping getType() {
        return type;
    }

}
