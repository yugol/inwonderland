package ess.common.actions;

import aibroker.util.Moment;

public class ActionResult {

    public static Moment parseDateTime(final String message) {
        try {
            final int pos = message.indexOf("20");
            final String foo = message.substring(pos, pos + 19);
            return Moment.fromIso(foo);
        } catch (final Exception e) {
        }
        return null;
    }

    private boolean successful = false;

    private String  message;

    public String getMessage() {
        return message;
    }

    public Moment getMessageTime() {
        return parseDateTime(message);
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setSuccessful(final boolean successful) {
        this.successful = successful;
    }

}
