package ui.controller;

import java.util.ArrayList;
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
	private TableView<CheckoutData> table;
	@FXML
	private TableColumn<CheckoutData, String> memberIdCol;
	@FXML
	private TableColumn<CheckoutData, String> bookCol;
	@FXML
	private TableColumn<CheckoutData, String> issueDateCol;
	@FXML
	private TableColumn<CheckoutData, CheckoutData> dueDateCol;

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
		LibraryMember member;
		try {
			member = mainController.search(memberId.getText());
			List<CheckoutRecordEntry> checkoutRecordEntries = member.getCheckoutRecord().getCheckoutRecordEntries();

			List<CheckoutData> list = new ArrayList<>();
			for (CheckoutRecordEntry currentEntry : checkoutRecordEntries) {
				list.add(new CheckoutData(memberId.getText(), currentEntry.getCopyNum().getBook().getTitle(),
						currentEntry.getCheckoutDate(), currentEntry.getDueDate()));
			}

			ObservableList<CheckoutData> data = FXCollections.observableArrayList(list);

			memberIdCol.setCellValueFactory(new PropertyValueFactory<>("memberId"));
			bookCol.setCellValueFactory(new PropertyValueFactory<>("title"));
			dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
			issueDateCol.setCellValueFactory(new PropertyValueFactory<>("checkoutDate"));

			table.setItems(data);
		} catch (LibrarySystemException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void handleSearchByMemberId() {
		SystemController controller = SystemController.getInstance();
		LibraryMember member = null;
		try {
			member = controller.search(memberId.getText());
			Alert alert = new Alert(AlertType.CONFIRMATION, "Print All Records?", ButtonType.YES, ButtonType.NO,
					ButtonType.CANCEL);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {
				print(member);
				memberId.clear();
			}
		} catch (LibrarySystemException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("Member not found!");
			alert.setContentText(e.getMessage());
			alert.show();

		}

	}

	private void print(LibraryMember member) {
		List<CheckoutRecordEntry> checkoutRecordEntries = member.getCheckoutRecord().getCheckoutRecordEntries();
		System.out.println("MemberId " + memberId.getText());
		System.out.println("ISBN\t\tTitle\t\tAuthor(s)\t\tCopyNumber\t\tCheckoutDate\t\tDueDate");
		for (CheckoutRecordEntry entry : checkoutRecordEntries) {
			System.out.println(entry.getCopyNum().getBook().getIsbn() + " " + entry.getCopyNum().getBook().getTitle()
					+ " " + entry.getCopyNum().getBook().getAuthors() + "\t " + entry.getCopyNum().getCopyNum()
					+ "\t\t\t " + entry.getCheckoutDate() + "\t \t" + entry.getCheckoutDate());
		}
	}

}
