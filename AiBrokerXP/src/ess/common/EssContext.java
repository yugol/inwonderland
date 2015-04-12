package ess.common;

public abstract class EssContext {

    private int loginCount;

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(final int loginCount) {
        this.loginCount = loginCount;
    }

}
