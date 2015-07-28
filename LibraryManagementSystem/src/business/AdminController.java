package business;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class AdminController {

	@FXML
	private GridPane addMemberGrid;

	@FXML
	public void handleNewMember(ActionEvent e) {
		addMemberGrid.setVisible(true);
	}
}
