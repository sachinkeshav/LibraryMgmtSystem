package ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AuthController {

	@FXML
	protected void handleAdminAuth(ActionEvent event) throws IOException {
		Pane mainMenuRoot = FXMLLoader.load(getClass().getResource("Admin.fxml"));
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		// stage.initStyle(StageStyle.UNDECORATED);
		stage.setTitle("Main Menu");
		stage.setScene(new Scene(mainMenuRoot));
		stage.show();
	}

	@FXML
	protected void handleLibrarianAuth(ActionEvent event) throws IOException {
		Pane mainMenuRoot = FXMLLoader.load(getClass().getResource("Librarian.fxml"));
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		// stage.initStyle(StageStyle.UNDECORATED);
		stage.setTitle("Main Menu");
		stage.setScene(new Scene(mainMenuRoot));
		stage.show();
	}
}
