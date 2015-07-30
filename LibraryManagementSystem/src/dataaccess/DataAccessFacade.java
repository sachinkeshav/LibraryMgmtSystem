package dataaccess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import business.Author;
import business.Book;
import business.LibraryMember;
import business.LibrarySystemException;

public class DataAccessFacade implements DataAccess {

	enum StorageType {
		BOOKS, MEMBERS, USERS, AUTHORS;
	}

	public static final String OUTPUT_DIR = System.getProperty("user.dir") + "/src/dataaccess/storage";
	public static final String DATE_PATTERN = "MM/dd/yyyy";

	//// specialized lookup methods
	@Override
	public LibraryMember searchMember(String memberId) throws LibrarySystemException{
		HashMap<String, LibraryMember> libraryMembersmap = readMemberMap();
		LibraryMember m = libraryMembersmap.get(memberId);
		if(m==null) throw new LibrarySystemException("Member not found !!");
		return m;
	}

	@Override
	public Book searchBook(String isbn) {
		HashMap<String, Book> booksMap = readBooksMap();
		Book b = booksMap.get(isbn);
		return b;
	}

	@Override
	public Author searchAuthor(String author) {
		HashMap<String, Author> authorMap = readAuthorMap();
		Author a = authorMap.get(author);
		return a;
	}

	public Auth login(String id, String pwd) {
		HashMap<String, User> userMap = readUserMap();
		if (!userMap.containsKey(id))
			return null;
		User user = userMap.get(id);
		if (!pwd.equals(user.getPassword())) {
			return null;
		}
		return user.getAuthorization();
	}

	/////// save methods
	// saveNewMember
	// public void saveNewMember(LibraryMember member)

	public void updateMember(LibraryMember member) {
		HashMap<String, LibraryMember> libraryMembersmap = readMemberMap();
		libraryMembersmap.put(member.getMemberId(), member);
		saveToStorage(StorageType.MEMBERS, libraryMembersmap);
	}

	// save new lendable item
	@Override
	public void saveNewBook(Book book) {
		HashMap<String, Book> bookMap = readBooksMap();
		String isbn = book.getIsbn();
		bookMap.put(isbn, book);
		saveToStorage(StorageType.BOOKS, bookMap);
	}

	@Override
	public void addNewAuthor(Author author) {
		HashMap<String, Author> authorMap = readAuthorMap();
		String a = author.getFirstName() + " " + author.getLastName();
		authorMap.put(a, author);
		saveToStorage(StorageType.AUTHORS, authorMap);
	}

	////// read methods that return full maps

	@Override
	@SuppressWarnings("unchecked")
	public HashMap<String, Book> readBooksMap() {
		return (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
	}

	@Override
	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readMemberMap() {
		return (HashMap<String, LibraryMember>) readFromStorage(StorageType.MEMBERS);
	}

	@Override
	@SuppressWarnings("unchecked")
	public HashMap<String, User> readUserMap() {
		return (HashMap<String, User>) readFromStorage(StorageType.USERS);
	}

	@Override
	@SuppressWarnings("unchecked")
	public HashMap<String, Author> readAuthorMap() {
		return (HashMap<String, Author>) readFromStorage(StorageType.AUTHORS);
	}

	@Override
	public void saveNewMember(LibraryMember member) {
		HashMap<String, LibraryMember> memberMap = readMemberMap();
		String memerId = member.getMemberId();
		memberMap.put(memerId, member);
		saveToStorage(StorageType.MEMBERS, memberMap);

	}

	///// load methods - these place test data into the storage area
	///// - used just once at startup
	static void loadMemberMap(List<LibraryMember> memberList) {
		HashMap<String, LibraryMember> members = new HashMap<>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}

	static void loadBookMap(List<Book> bookList) {
		HashMap<String, Book> books = new HashMap<String, Book>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOKS, books);
	}

	static void loadUserMap(List<User> userList) {
		HashMap<String, User> users = new HashMap<String, User>();
		userList.forEach(user -> users.put(user.getId(), user));
		saveToStorage(StorageType.USERS, users);
	}

	static void loadAuthorMap(List<Author> authorList) {
		HashMap<String, Author> authors = new HashMap<String, Author>();
		authorList.forEach(author -> authors.put(author.getFirstName() + " " + author.getLastName(), author));
		saveToStorage(StorageType.AUTHORS, authors);
	}

	static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}
	}

	static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		return retVal;
	}

	final static class Pair<S, T> implements Serializable {

		S first;
		T second;

		Pair(S s, T t) {
			first = s;
			second = t;
		}

		@Override
		public boolean equals(Object ob) {
			if (ob == null)
				return false;
			if (this == ob)
				return true;
			if (ob.getClass() != getClass())
				return false;
			@SuppressWarnings("unchecked")
			Pair<S, T> p = (Pair<S, T>) ob;
			return p.first.equals(first) && p.second.equals(second);
		}

		@Override
		public int hashCode() {
			return first.hashCode() + 5 * second.hashCode();
		}

		@Override
		public String toString() {
			return "(" + first.toString() + ", " + second.toString() + ")";
		}

		private static final long serialVersionUID = 5399827794066637059L;
	}

}
