package ruleset;

import ui.controller.LibrarianController;

public class LibrarianRuleSet implements RuleSet {

	@Override
	public void applyRules(Object ob) throws RuleException {
		LibrarianController controller = (LibrarianController) ob;

		applyNonEmptyFieldsRule(controller);

	}

	private void applyNonEmptyFieldsRule(LibrarianController controller) throws RuleException {
		if ((controller.getIsbn().isEmpty()) || (controller.getMemberId().isEmpty())) {
			throw new RuleException("Empty field(s) found !!");
		}
	}
}
