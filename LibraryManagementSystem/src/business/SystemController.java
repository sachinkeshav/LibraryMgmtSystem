package business;

import java.util.HashMap;

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
	 */
	@Override
	public LibraryMember search(String memberId) {

		System.out.println("test");
		return null;

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
	// public boolean addBook(String isbn, String title, int maxCheckoutLength,
	// List<Author> authors)
	// throws LibrarySystemException {

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
	public void checkoutBook(String memberId, String isbn) throws LibrarySystemException {
		// TODO Auto-generated method stub

	}

}
