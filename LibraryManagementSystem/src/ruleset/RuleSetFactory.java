package ruleset;

import java.awt.Component;
import java.util.HashMap;

import ui.AdminController;

final public class RuleSetFactory {
	private RuleSetFactory() {
	}

	static HashMap<Class<? extends Object>, RuleSet> map = new HashMap<>();

	static {
		map.put(AdminController.class, new MemberRuleSet());
	}

	public static RuleSet getRuleSet(Object c) {
		Class<? extends Object> cl = c.getClass();
		if (!map.containsKey(cl)) {
			throw new IllegalArgumentException("No RuleSet found for this Component");
		}
		return map.get(cl);
	}
}
