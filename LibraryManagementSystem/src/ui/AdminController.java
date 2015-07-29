package ui;

import java.io.IOException;

import business.Address;
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
import ruleset.RuleException;
import ruleset.RuleSet;
import ruleset.RuleSetFactory;

public class AdminController {

	// addMember controls
	@FXML
	TextField memberId, firstName, lastName, street, city, state, zip, phone, isbn;

	public String getMemberId() {
		return memberId.getText();
	}

	public String getFirstName() {
		return firstName.getText();
	}

	public String getLastName() {
		return lastName.getText();
	}

	public String getStreet() {
		return street.getText();
	}

	public String getCity() {
		return city.getText();
	}

	public String getState() {
		return state.getText();
	}

	public String getZip() {
		return zip.getText();
	}

	public String getPhone() {
		return phone.getText();
	}

	/*public String getIsbn() {
		return isbn.getText();
	}*/

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

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Add Book Coy");
			alert.setHeaderText("Successful!!!");
			alert.setContentText("Successfully added a copy of \"" + book.getTitle() + "\", " + "copy number "
					+ book.getNumCopies());
			alert.show();
			isbn.clear();
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

		RuleSet adminRules = RuleSetFactory.getRuleSet(AdminController.this);
		try {
			adminRules.applyRules(AdminController.this);
			try {
				ControllerInterface controller = SystemController.getInstance();
				Address address = controller.addAddress(street.getText(), city.getText(), state.getText(),
						zip.getText());
				controller.addNewMember(memberId.getText(), firstName.getText(), lastName.getText(), phone.getText(),
						address);
				System.out.println("successfully updated");
			} catch (LibrarySystemException e2) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Failed!");
				alert.setHeaderText("Error");
				alert.setContentText(e2.getMessage());
				alert.show();
			}
		} catch (RuleException e11) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error");
			alert.setContentText(e11.getMessage());
			alert.show();
		}

	}

}
