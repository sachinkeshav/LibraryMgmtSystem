package ui.controller;

import java.util.ArrayList;
import java.util.List;

import business.Author;
import business.ControllerInterface;
import business.LibrarySystemException;
import business.SystemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class BookController {

	@FXML
	private TextField bookIsbn, bookTitle, checkoutLength;

	@FXML
	private ComboBox<String> authorsList;

	@FXML
	private TableView<AuthorData> authorTable;

	@FXML
	private TableColumn<AuthorData, String> authorName;

	@FXML
	private Button addAuthor, saveBook;

	private static List<String> existingAuthors = new ArrayList<>();

	@FXML
	protected void handleComboBox(MouseEvent event) {
		ControllerInterface controller = SystemController.getInstance();
		List<Author> authors = controller.getAllAuthors();
		authorsList.getItems().clear();
		authors.forEach(author -> {
			if (!existingAuthors.contains(author.toString()))
				authorsList.getItems().add(author.toString());
		});
	}

	@FXML
	protected void addAuthorToTable(ActionEvent event) {
		String name = authorsList.getSelectionModel().getSelectedItem();
		existingAuthors.add(name);
		List<AuthorData> list = new ArrayList<>();
		list.add(new AuthorData(name));
		ObservableList<AuthorData> authors = FXCollections.observableArrayList(list);
		authorName.setCellValueFactory(new PropertyValueFactory<>("name"));
		authorTable.getItems().addAll(authors);
	}

	@FXML
	protected void handleSaveBook(ActionEvent event) {
		String isbn = bookIsbn.getText();
		String title = bookTitle.getText();
		int coLenght = Integer.parseInt(checkoutLength.getText());
		
		ObservableList<AuthorData> ads = authorTable.getItems();
		 List<Author> authors = new ArrayList<>();

		ControllerInterface controller = SystemController.getInstance();
		ads.forEach(ad -> {
			authors.add(controller.searchAuthor(ad.getName()));
		});
		try {
			// TODO: add rule set and show success message
			controller.addBook(isbn, title, coLenght, authors);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Add Book");
			alert.setHeaderText("Successful!!!");
			alert.setContentText("Successfully added \"" + title + "\".");
			alert.show();

			bookIsbn.clear();
			bookTitle.clear();
			checkoutLength.clear();
			authorTable.getItems().clear();
			authorsList.getItems().clear();
			existingAuthors.clear();

		} catch (LibrarySystemException e) {
			// TODO: handle error message
			e.printStackTrace();
		}
	}

	public class AuthorData {
		private String name;

		public AuthorData(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
}
