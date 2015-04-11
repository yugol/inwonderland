package ess.mg.agents.operator;

import ess.mg.actions.Action;
import ess.mg.actions.ActionResult;
import ess.mg.agents.Agent;

public class AManageCompany extends Action<ActionResult> {

    private String companyUrl;

    public AManageCompany(final Agent performer) {
        super(performer);
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(final String companyUrl) {
        this.companyUrl = companyUrl;
    }

    @Override
    protected ActionResult execute() {
        final ActionResult result = new ActionResult();
        final double hourlyWage = getAgent().getGlobal().getWage() / 80;
        result.setSuccessful(getAgent().getDriver().activateJob(getCompanyUrl(), 0, hourlyWage));
        return result;
    }

}
