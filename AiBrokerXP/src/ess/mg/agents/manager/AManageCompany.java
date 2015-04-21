package ess.mg.agents.manager;

import ess.common.actions.Action;
import ess.common.actions.ActionResult;
import ess.mg.agents.MgAgent;

public class AManageCompany extends Action<MgAgent, ActionResult> {

    private String companyUrl;
    private double maxHourlyWage;

    public AManageCompany(final MgAgent performer) {
        super(performer);
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public double getMaxHourlyWage() {
        return maxHourlyWage;
    }

    public void setCompanyUrl(final String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public void setMaxHourlyWage(final double maxHourlyWage) {
        this.maxHourlyWage = maxHourlyWage;
    }

    @Override
    protected ActionResult execute() {
        final ActionResult result = new ActionResult();
        double hourlyWage = getAgent().getContext().getWorkWage() / 80;
        // hourlyWage += 0.01;
        if (hourlyWage > maxHourlyWage) {
            hourlyWage = maxHourlyWage;
        }
        result.setSuccessful(getAgent().getDriver().activateJob(getCompanyUrl(), 0, hourlyWage));
        return result;
    }

}
