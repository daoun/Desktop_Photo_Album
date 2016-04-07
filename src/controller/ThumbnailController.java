package controller;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

public class ThumbnailController {

	
	@FXML private Button addPhoto;
	@FXML private Button deletePhoto;
	
	public void addPhoto(ActionEvent e){
		
		final FileChooser fc = new FileChooser();
		
		File file = fc.showOpenDialog(ownerWindow)
		
		
	}
	
}
