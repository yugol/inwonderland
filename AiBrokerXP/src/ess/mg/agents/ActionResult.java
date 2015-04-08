package ess.mg.agents;

public class ActionResult {

    private boolean successful = false;
    private String  message;

    public String getMessage() {
        return message;
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
