package business;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class AdminController {

	@FXML
	private GridPane addMemberGrid;

	@FXML
	private ScrollPane scrollContainer;

	@FXML
	public void handleNewMember(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("../ui/AddMember.fxml"));
		scrollContainer.setContent(root);
	}
	
	@FXML
	public void handleNewCopy(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("../ui/AddCopy.fxml"));
		scrollContainer.setContent(root);
	}
	@FXML
	public void handleEditBook(ActionEvent e) throws IOException {
	Parent root = FXMLLoader.load(getClass().getResource("../ui/EditBook.fxml"));
		scrollContainer.setContent(root);
	}	

	@FXML
	public void handleNewBook(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("../ui/AddBook.fxml"));
		scrollContainer.setContent(root);
	}

}
