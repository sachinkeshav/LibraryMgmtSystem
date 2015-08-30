package ui.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import business.CheckoutRecordEntry;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class OverdueController {
	@FXML
	TextField isbn;
	@FXML
	TableView<OverdueData> table;
	@FXML
	TableColumn<OverdueData, String> isbnCol;
	@FXML
	TableColumn<OverdueData, String> titleCol;
	@FXML
	TableColumn<OverdueData, Integer> copyNumCol;
	@FXML
	TableColumn<OverdueData, LocalDate> dueDateCol;
	@FXML
	TableColumn<OverdueData, String> checkedByCol;

	public void handleOverDue() {
		ControllerInterface controller = SystemController.getInstance();
		List<OverdueData> overDueList = new ArrayList<>();
		HashMap<String, LibraryMember> memberMap = controller.getAllCheckoutEntries();
		for (LibraryMember member : memberMap.values()) {
			List<CheckoutRecordEntry> checkoutRecordEntries = member.getCheckoutRecord().getCheckoutRecordEntries();
			for (CheckoutRecordEntry c : checkoutRecordEntries) {
				if (c.getCopyNum().getBook().getIsbn().equals(isbn.getText())) {
					LocalDate currentDate = LocalDate.now();
					// c.getCheckoutDate().plusDays((c.getCopyNum().getBook().getMaxCheckoutLength()))
					if (c.getDueDate().isBefore(currentDate)) {
						overDueList.add(
								new OverdueData(c.getCopyNum().getBook().getIsbn(), c.getCopyNum().getBook().getTitle(),
										c.getCopyNum().getCopyNum(), c.getDueDate(), member.getMemberId()));
					}

				}

			}

		}

		ObservableList<OverdueData> data = FXCollections.observableArrayList(overDueList);

		isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		copyNumCol.setCellValueFactory(new PropertyValueFactory<>("copyNum"));
		dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
		checkedByCol.setCellValueFactory(new PropertyValueFactory<>("checkedBy"));

		table.setItems(data);
	}

}
