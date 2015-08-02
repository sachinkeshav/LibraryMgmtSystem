package ruleset;

import java.util.List;

import business.Author;
import ui.controller.BookController;

public class BookRuleSet implements RuleSet {
	private String isbn;
	private String title;
	private String coLength;
	private List<Author> authors;

	@Override
	public void applyRules(Object ob) throws RuleException {
		BookController controller = (BookController) ob;
		isbn = controller.getBookIsbn();
		title = controller.getBookTitle();
		coLength = controller.getCheckoutLength();
		authors = controller.getAuthors();

		applyNonEmptyRule();
		validateIsbn();
		validatecheckoutLength();
	}

	private void applyNonEmptyRule() throws RuleException {
		if (isbn.isEmpty() || title.isEmpty() || coLength.isEmpty() || authors.isEmpty())
			throw new RuleException("Fields cannot be empty!!");
	}

	private void validateIsbn() throws RuleException {
		if (!isbn.matches("[0-9]{2}\\-[0-9]{5}"))
			throw new RuleException("Invalid ISBN!! Please input valid ISBN.");
	}

	private void validatecheckoutLength() throws RuleException {
		try{int co = Integer.parseInt(coLength);
		if (!(co == 7 || co == 21))
				throw new RuleException("Invalid checkout length. The checkout length should be either 7 or 21 days.");
		} catch (NumberFormatException e) {
			throw new RuleException("Invalid checkout length. It should be numberical value.");
		}
	}
}
