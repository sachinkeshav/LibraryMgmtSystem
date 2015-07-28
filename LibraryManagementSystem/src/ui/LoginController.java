package ui;

import java.io.IOException;

import business.ControllerInterface;
import business.LoginException;
import business.SystemController;
import dataaccess.Auth;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private TextField userIdField;

	@FXML
	private TextField passwordField;

	@FXML
	private Button librarianBtn;

	@FXML
	private Button adminBtn;

	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
		String id = userIdField.getText();
		String password = passwordField.getText();
		ControllerInterface controller = SystemController.getInstance();
		try {
			controller.login(id, password);
			Auth auth = SystemController.currentAuth;
			String fxmlFile = "";

			switch (auth) {
			case ADMIN:
				fxmlFile = "Admin.fxml";
				String s = "";
				break;
			case LIBRARIAN:
				fxmlFile = "Librarian.fxml";
				break;
			case BOTH:
				fxmlFile = "MainMenu.fxml";
				break;
			}

			Pane mainMenuRoot = FXMLLoader.load(getClass().getResource(fxmlFile));
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			// stage.initStyle(StageStyle.UNDECORATED);
			stage.setTitle("Main Menu");
			stage.setScene(new Scene(mainMenuRoot));
			stage.show();

		} catch (LoginException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Login Error!");
			alert.setHeaderText("Incorrect login information!");
			alert.setContentText(e.getMessage());
			alert.show();
		}
	}
}
