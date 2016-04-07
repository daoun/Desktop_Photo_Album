package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Photo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ThumbnailController implements Initializable{

	public static Stage photoStage;
	
	@FXML private Button addPhoto;
	@FXML private Button deletePhoto;
	
	@FXML private Button photoAddBtn;
	@FXML private Text albumTitle;
	@FXML private GridPane photoListGP;
	@FXML private ScrollPane photoListSP;
	
	@FXML private ComboBox<?> photoOption;
	
	public int numPhoto = 0;
	public static int currentAlbum;
	
	public void addPhoto(ActionEvent event){
		
		
		
		final FileChooser fc = new FileChooser();
		
		File file = fc.showOpenDialog(AlbumController.thumbnailStage);
		String path = file.getPath();
		System.out.println(path);
		
		String caption = "add caption";
		/*
		String name = createAlbum();
		if(name == null){
			return;
		}*/
		
		Photo photo = new Photo(caption);
		
		
		int row = numPhoto/5;
        int col = numPhoto%5;
        
        if(row >2 && col == 0){
        	RowConstraints rc = new RowConstraints();
            rc.setPrefHeight(116);
            rc.setVgrow(Priority.ALWAYS);
            photoListGP.getRowConstraints().add(rc);
        }
        BorderPane photoBP = new BorderPane();
        
        ImageView image = new ImageView("file:\\" + path);
        image.setFitHeight(100);
        image.setFitWidth(100);
        
        Text captionT = new Text(caption);
        AnchorPane.setBottomAnchor(captionT, 10.0);
        
        photoBP.setCenter(image);
        photoBP.setBottom(captionT);
        BorderPane.setAlignment(captionT, Pos.CENTER);
        
        photoListGP.add(photoBP, col, row);
        
        photoBP.setOnMouseClicked(e ->{
        	
        	openPhoto(path, row, col);

        });
		
		numPhoto++;
	}
	
	public void openPhoto(String name, int row, int col){
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/photo.fxml"));
			
			AnchorPane root = (AnchorPane)loader.load();
			//Stage currentStage = (Stage) loginStage.getScene().getWindow();
        	
			Stage stage = new Stage();
            Scene scene = new Scene(root);
			
            stage.setScene(scene);  
            stage.setResizable(false);  
            stage.setTitle(name);
            
            photoStage = stage;
            
            stage.show();
            //currentStage.close();
            
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	
	}
	/*
	public String createAlbum(){
		Dialog<String> dialog = new TextInputDialog("Enter a user name.");
		dialog.setHeaderText("Add Photo");
		dialog.setTitle("Photo Album");
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()){
			//System.out.println(result);
			return result.get();
		}
		else{
			return null;
		}
	}*/

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		albumTitle.setText(LoginController.username + "'s Album");
		photoListSP.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		
	}
	
	
}
