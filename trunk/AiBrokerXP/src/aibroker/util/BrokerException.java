package aibroker.util;

import javax.swing.JOptionPane;
import aibroker.Context;
import static aibroker.util.StringUtil.isNullOrBlank;

@SuppressWarnings("serial")
public class BrokerException extends RuntimeException {

    public static void reportUi(final Exception ex) {
        ex.printStackTrace();
        final String message = ex.getClass().getSimpleName() + ":\n" + (isNullOrBlank(ex.getMessage()) ? "N/A" : ex.getMessage());
        JOptionPane.showMessageDialog(null, message, Context.APPLICATION_NAME + " - Exception", JOptionPane.ERROR_MESSAGE);
    }

    public BrokerException() {
        super();
    }

    public BrokerException(final String arg0) {
        super(arg0);
    }

    public BrokerException(final String arg0, final Throwable arg1) {
        super(arg0, arg1);
    }

    public BrokerException(final String arg0, final Throwable arg1, final boolean arg2, final boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

    public BrokerException(final Throwable arg0) {
        super(arg0);
    }

}
