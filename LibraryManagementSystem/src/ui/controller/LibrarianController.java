package ui.controller;

import java.time.LocalDate;
import java.util.List;

import business.CheckoutRecordEntry;
import business.LibraryMember;
import business.LibrarySystemException;
import business.SystemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class LibrarianController {

	@FXML
	private TextField memberId;

	@FXML
	private TextField isbn;

	@FXML
	private TableView table;
	@FXML
	private TableColumn memberIdCol;
	@FXML
	private TableColumn bookCol;
	@FXML
	private TableColumn issueDateCol;
	@FXML
	private TableColumn dueDateCol;

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
					handleCheckoutButton();
				}
				if (checkout) {
					Alert success = new Alert(AlertType.INFORMATION);
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

	public void handleCheckoutButton() {
		SystemController mainController = SystemController.getInstance();
		LibraryMember member = mainController.search(memberId.getText());
		List<CheckoutRecordEntry> checkoutRecordEntries = member.getCheckoutRecord().getCheckoutRecordEntries();
		CheckoutRecordEntry currentEntry = checkoutRecordEntries.get(checkoutRecordEntries.size() - 1);
		int copyNum = currentEntry.getCopyNum().getCopyNum();
		LocalDate dueDate = currentEntry.getDueDate();
		LocalDate checkoutDate = currentEntry.getCheckoutDate();

		ObservableList<CheckoutRecordEntry> data = FXCollections.observableArrayList(checkoutRecordEntries);
		for (int i = (data.size() - 1); i > 0; i--) {
			dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
			issueDateCol.setCellValueFactory(new PropertyValueFactory<>("checkoutDate"));
			table.setItems(data);

		}
	}

	public void handleSearchByMemberId() {

	}

}
