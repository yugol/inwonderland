package tools;

import java.io.IOException;
import aibroker.model.SequenceDescriptor;
import aibroker.model.cloud.sources.bvb.BvbSequenceDescriptionReader;

public class AddBvbSymbols {

    public static void main(final String[] args) throws IOException {
        final BvbSequenceDescriptionReader bvb = new BvbSequenceDescriptionReader();
        for (final String symbol : SYMBOLS) {
            final SequenceDescriptor sDesc = BvbSequenceDescriptionReader.readDescription(symbol);
            System.out.println(sDesc.name());
        }
    }

    private static final String[] SYMBOLS = { "SNG" };

}
