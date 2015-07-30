package ruleset;

import ui.controller.LoginController;

public class LoginRuleSet implements RuleSet {
	LoginRuleSet() {
	}

	@Override
	public void applyRules(Object ob) throws RuleException {
		LoginController controller = (LoginController) ob;
		applyNonEmptyRule(controller);
	}

	private void applyNonEmptyRule(LoginController controller) throws RuleException {
		String id = controller.getUserIdField();
		String password = controller.getPasswordField();

		if (id.isEmpty() && password.isEmpty())
			throw new RuleException("Username and paswword field cannot be blank!");
		if (id.isEmpty())
			throw new RuleException("Username  field cannot be blank!");
		if (password.isEmpty())
			throw new RuleException("Paswword field cannot be blank!");
	}
}
