package aibroker.model.drivers.csv;

import aibroker.model.SequenceBuilder;
import aibroker.model.Sequence;
import aibroker.model.domains.Grouping;

public class CsvSequence extends Sequence {

    private static String normalizeName(final String name) {
        return name.replace("/", "");
    }

    private final Grouping type;

    CsvSequence(final CsvDatabase csvDb, final SequenceBuilder sb) {
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
