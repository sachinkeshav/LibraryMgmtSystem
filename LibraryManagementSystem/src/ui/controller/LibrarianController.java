package ui.controller;

import business.LibrarySystemException;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class LibrarianController {

	@FXML
	private TextField memberId;

	@FXML
	private TextField isbn;

	@FXML
	public void searchButtonAction(ActionEvent evt) {
		SystemController mainController = SystemController.getInstance();
		try {
			if (mainController.availableForCheckout(memberId.getText(), isbn.getText())) {

			}
			mainController.checkoutBook(memberId.getText(), isbn.getText());
		} catch (LibrarySystemException ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Checkout Error!");
			alert.setHeaderText("Incorrect checkout information!");
			alert.setContentText(ex.getMessage());
			alert.show();
		}
	}

}
