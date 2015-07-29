package ruleset;

import ui.AdminController;

final public class MemberRuleSet implements RuleSet {
	// package level access
	MemberRuleSet() {
	}

	@Override
	public void applyRules(Object ob) throws RuleException {
		AdminController admin = (AdminController) ob;
		applyNonEmptyFieldsRule(admin);

	}

	private void applyNonEmptyFieldsRule(AdminController admin) throws RuleException {
		if ((admin.getMemberId().isEmpty()) || (admin.getCity().isEmpty()) || admin.getFirstName().isEmpty()
				|| admin.getLastName().isEmpty() || admin.getPhone().isEmpty() || admin.getState().isEmpty()
				|| admin.getStreet().isEmpty() || admin.getZip().isEmpty()) {
			throw new RuleException("Empty field(s) found !!");
		}
	}

}
