package business;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public void addNewMember(String memberId, String firstName, String lastName,
		String telNumber, Address addr) throws LibrarySystemException;
	public LibraryMember search(String memberId);
//	public void updateMemberInfo(String memberId, String firstName, String lastName,
//			String telNumber, Address addr) throws LibrarySystemException;
	public void checkoutBook(String memberId, String isbn) throws LibrarySystemException;
	//public boolean addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors)
	//	throws LibrarySystemException;
	public boolean addBookCopy(String isbn) throws LibrarySystemException;
	public Address addAddress(String street, String city, String state, String zip );
//	public void printCheckoutRecord(String memberId) throws LibrarySystemException;
	public boolean computeStatus(BookCopy copy);
	public Book searchBook(String isbn);
	
}
