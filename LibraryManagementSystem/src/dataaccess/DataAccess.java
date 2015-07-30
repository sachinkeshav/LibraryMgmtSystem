package dataaccess;

import java.util.HashMap;

import business.Author;
import business.Book;
import business.LibraryMember;
import business.LibrarySystemException;

public interface DataAccess {
	public LibraryMember searchMember(String memberId)throws LibrarySystemException;

	public Book searchBook(String isbn);

	public Author searchAuthor(String author);

	/////// save methods
	// public void saveNewMember(LibraryMember member);
	// public void updateMember(LibraryMember member);

	// save new book
	public void saveNewBook(Book book);

	///////save methods
	public void saveNewMember(LibraryMember member);
	//public void updateMember(LibraryMember member);
	
	public void addNewAuthor(Author author);

	//////read methods 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();

	public HashMap<String, Author> readAuthorMap();
}
