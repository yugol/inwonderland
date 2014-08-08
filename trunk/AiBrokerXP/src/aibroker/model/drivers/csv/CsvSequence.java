package aibroker.model.drivers.csv;

import aibroker.model.SeqDesc;
import aibroker.model.Seq;
import aibroker.model.domains.Grouping;

public class CsvSequence extends Seq {

    private static String normalizeName(final String name) {
        return name.replace("/", "");
    }

    private final Grouping type;

    CsvSequence(final CsvDatabase csvDb, final SeqDesc sb) {
        super(csvDb, sb);
        type = sb.grouping();
    }

    @Override
    public String getName() {
        return normalizeName(super.getName());
    }

    public Grouping getType() {
        return type;
    }

}
