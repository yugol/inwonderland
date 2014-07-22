package aibroker.model.drivers.amibroker;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import aibroker.model.drivers.amibroker.AmibrokerCodec;
import aibroker.util.Moment;

public class AmiCodecTest {

    @Test
    public void testMoment() {
        final Moment expected = Moment.fromIso("2012-06-03 09:48:05");
        final byte[] buffer = new byte[4];
        AmibrokerCodec.writeMoment(buffer, 0, expected);
        final Moment actual = AmibrokerCodec.readMoment(buffer, 0);
        assertEquals(expected.toIsoDatetime(), actual.toIsoDatetime());
    }

}
