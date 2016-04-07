package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

public class ThumbnailController {

	
	@FXML private Button addPhoto;
	@FXML private Button deletePhoto;
	private Desktop desktop = Desktop.getDesktop();
	
	public void addPhoto(ActionEvent e){
		
		final FileChooser fc = new FileChooser();
		
		File file = fc.showOpenDialog(AlbumController.thumbnailStage);
		
		if(file != null){
			openFile(file);
		}
		
		
	}
	
	private void openFile(File file){
		try{
			desktop.open(file);
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
}
