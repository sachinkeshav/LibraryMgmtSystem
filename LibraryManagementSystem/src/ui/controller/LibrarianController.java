package ui.controller;

import business.LibrarySystemException;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class LibrarianController {

	@FXML
	private TextField memberId;

	@FXML
	private TextField isbn;

	@FXML
	public void searchButtonAction(ActionEvent evt) {
		SystemController mainController = SystemController.getInstance();
		boolean checkout = false;
		try {
			if (mainController.availableForCheckout(memberId.getText(), isbn.getText())) {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to checkout?", ButtonType.YES,
						ButtonType.NO, ButtonType.CANCEL);
				alert.showAndWait();

				if (alert.getResult() == ButtonType.YES) {
					checkout = mainController.checkoutBook(memberId.getText(), isbn.getText());
				}
				if (checkout) {
					Alert success = new Alert(AlertType.CONFIRMATION);
					success.setTitle("Success");
					success.setHeaderText("Successfully checked out!");
					success.show();
				}
			}
		} catch (LibrarySystemException ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Checkout Error!");
			alert.setHeaderText("Incorrect checkout information!");
			alert.setContentText(ex.getMessage());
			alert.show();
		}
	}

}
