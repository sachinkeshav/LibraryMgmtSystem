package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	private static SystemController INSTANCE = new SystemController();

	private SystemController() {

	}

	public static SystemController getInstance() {
		return INSTANCE;
	}

	public static Auth currentAuth = null;

	@Override
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if (!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if (!passwordFound.equals(password)) {
			throw new LoginException("Passord does not match password on record");
		}
		currentAuth = map.get(id).getAuthorization();

	}

	@Override
	public Address addAddress(String street, String city, String state, String zip) {
		return new Address(street, city, state, zip);
	}

	/**
	 * This method checks if memberId already exists -- if so, it cannot be
	 * added as a new member, and an exception is thrown. If new, creates a new
	 * LibraryMember based on input data and uses DataAccess to store it.
	 * 
	 */

	@Override
	public void addNewMember(String memberId, String firstName, String lastName, String telNumber, Address addr)
			throws LibrarySystemException {
		DataAccess dataAccess = new DataAccessFacade();
		HashMap<String, LibraryMember> map = dataAccess.readMemberMap();
		if (map.containsKey(memberId)) {
			throw new LibrarySystemException("Member already exists");
		}
		dataAccess.saveNewMember(new LibraryMember(firstName, lastName, telNumber, addr, memberId));
	}

	/**
	 * Reads data store for a library member with specified id. Ids begin at
	 * 1001... Returns a LibraryMember if found, null otherwise
	 * 
	 * @throws LibrarySystemException
	 * 
	 */
	@Override
	public LibraryMember search(String memberId) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		return da.searchMember(memberId);
	}

	/**
	 * Same as creating a new member (because of how data is stored)
	 */
	// public void updateMemberInfo(String memberId, String firstName, String
	// lastName,

	/**
	 * Looks up Book by isbn from data store. If not found, an exception is
	 * thrown. If no copies are available for checkout, an exception is thrown.
	 * If found and a copy is available, member's checkout record is updated and
	 * copy of this publication is set to "not available"
	 */
	// public void checkoutBook(String memberId, String isbn) throws
	// LibrarySystemException {

	@Override
	public Book searchBook(String isbn) {
		DataAccess da = new DataAccessFacade();
		return da.searchBook(isbn);
	}

	/**
	 * Looks up book by isbn to see if it exists, throw exceptioni. Else add the
	 * book to storage
	 */
	@Override
	public boolean addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors)
			throws LibrarySystemException {
		Book book = new Book(isbn, title, maxCheckoutLength, authors);
		DataAccess da = new DataAccessFacade();
		da.saveNewBook(book);
		return false;
	}

	@Override
	public Author searchAuthor(String author) {
		DataAccess da = new DataAccessFacade();
		return da.searchAuthor(author);
	}

	@Override
	public List<Author> getAllAuthors() {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Author> authors = da.readAuthorMap();
		List<Author> authorList = new ArrayList<>();
		for (Author a : authors.values()) {
			authorList.add(a);
		}
		return authorList;
	}

	@Override
	public boolean addBookCopy(String isbn) throws LibrarySystemException {
		Book book = searchBook(isbn);
		if (book == null)
			throw new LibrarySystemException("No book with isbn " + isbn + " is in the library collection!");
		book.addCopy();

		DataAccess da = new DataAccessFacade();
		da.saveNewBook(book);

		return true;
	}

	public static void main(String[] args) throws LibrarySystemException {

	}

	@Override
	public boolean checkoutBook(String memberId, String isbn) throws LibrarySystemException {
		Book currentBook = searchBook(isbn);
		BookCopy[] book = currentBook.getCopies();
		for (BookCopy bc : book) {
			if (computeStatus(bc)) {
				DataAccessFacade dc = new DataAccessFacade();
				LocalDate currentDate = LocalDate.now();
				LocalDate dueDate = currentDate.plusDays(bc.getBook().getMaxCheckoutLength());
				CheckoutRecordEntry newCheckoutRecordEntry = new CheckoutRecordEntry(currentDate, dueDate, bc);
				LibraryMember member = search(memberId);
				CheckoutRecord rc = member.getCheckoutRecord();
				if (rc.getCheckoutRecordEntries() == null) {
					List<CheckoutRecordEntry> entries = new ArrayList<>();
					entries.add(newCheckoutRecordEntry);
					member.getCheckoutRecord().setCheckoutRecordEntries(entries);
				} else {
					member.getCheckoutRecord().getCheckoutRecordEntries().add(newCheckoutRecordEntry);
				}
				bc.changeAvailability();
				dc.updateMember(member);
				dc.saveNewBook(currentBook);
				return true;
			}
		}
		return false;

	}

	@Override
	public boolean computeStatus(BookCopy copy) {
		return copy.isAvailable();
	}

	public boolean availableForCheckout(String memberId, String isbn) throws LibrarySystemException {

		if (search(memberId) != null) {
			if (searchBook(isbn) != null) {
				Book currentBook = searchBook(isbn);
				BookCopy[] book = currentBook.getCopies();
				for (BookCopy bc : book) {
					if (computeStatus(bc)) {
						return true;
					}
				}
				throw new LibrarySystemException("No book copy with isbn " + isbn + " is in the library collection!");
			} else {
				throw new LibrarySystemException("No book with isbn " + isbn + " is in the library collection!");
			}
		} else {
			throw new LibrarySystemException("No member with id " + memberId + " is in the library system!");
		}

	}

	@Override
	public HashMap<String, LibraryMember> getAllCheckoutEntries() {
		DataAccess data = new DataAccessFacade();
		return data.readMemberMap();

	}

}
