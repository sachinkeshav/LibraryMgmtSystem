package ui.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AuthController {

	@FXML
	private ImageView adminView;
	@FXML
	private ImageView librarianView;

	@FXML
	protected void handleAdminAuth(MouseEvent event) throws IOException {
		Pane mainMenuRoot = FXMLLoader.load(getClass().getResource("../Admin.fxml"));
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		// stage.initStyle(StageStyle.UNDECORATED);
		stage.setTitle("Admin Panel");
		stage.setScene(new Scene(mainMenuRoot));
		stage.show();
	}

	@FXML
	protected void handleLibrarianAuth(MouseEvent event) throws IOException {
		Pane mainMenuRoot = FXMLLoader.load(getClass().getResource("../Librarian.fxml"));
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		// stage.initStyle(StageStyle.UNDECORATED);
		stage.setTitle("Librarian Panel");
		stage.setScene(new Scene(mainMenuRoot));
		stage.show();
	}

	@FXML
	protected void addHoverAdmin() {
		addHoverEffect(adminView);
	}

	@FXML
	protected void removeHoverAdmin() {
		removeHoverEffect(adminView);
	}

	@FXML
	protected void addHoverLibrarian() {
		addHoverEffect(librarianView);
	}

	@FXML
	protected void removeHoverLibrarian() {
		removeHoverEffect(librarianView);
	}

	private void addHoverEffect(ImageView imageView) {
		// apply a shadow effect.
		imageView.setEffect(new DropShadow(20, Color.web("#FF3300")));
		imageView.setCursor(Cursor.HAND);
	}

	private void removeHoverEffect(ImageView imageView) {
		imageView.setEffect(null);
	}
}
