package ui;

import java.io.IOException;

import business.Book;
import business.ControllerInterface;
import business.LibrarySystemException;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AdminController {

	// addMember controls
	@FXML
	TextField memberId, firstName, lastName, street, city, state, zip, isbn;

	@FXML
	private GridPane addMemberGrid;
	@FXML
	private ScrollPane scrollContainer;

	@FXML
	public void handleNewMember(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AddMember.fxml"));
		scrollContainer.setContent(root);
	}

	@FXML
	public void handleNewCopy(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AddCopy.fxml"));
		scrollContainer.setContent(root);
	}

	@FXML
	public void handleSaveCopy(ActionEvent e) throws IOException {
		String isbnVal = isbn.getText();
		ControllerInterface controller = SystemController.getInstance();
		try {
			controller.addBookCopy(isbnVal);
			Book book = controller.searchBook(isbnVal);
			System.out.println("Successfully added a copy of " + book.getTitle());
		} catch (LibrarySystemException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("Incorrect ISBN information!");
			alert.setContentText(e1.getMessage());
			alert.show();
		}
	}

	@FXML
	public void handleEditBook(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("EditBook.fxml"));
		scrollContainer.setContent(root);
	}

	@FXML
	public void handleNewBook(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AddBook.fxml"));
		scrollContainer.setContent(root);
	}

	@FXML
	public void handleSaveMemberButton(ActionEvent e) {

		/*try {
			ControllerInterface controller = SystemController.getInstance();
			controller.addNewMember(memberId.getText(), firstName.getText(), lastName.getText(), street.getText(),
					city.getText(), state.getText(), zip.getText());

		} catch (LoginException e2) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Saving Failed!");
			alert.setHeaderText("Sorry! Try Again");
			alert.setContentText(e2.getMessage());
			alert.show();
		}*/
	}

}
