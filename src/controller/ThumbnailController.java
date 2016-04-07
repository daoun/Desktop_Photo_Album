package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
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
	
	
	private Desktop desktop = Desktop.getDesktop();
	
	
	
	private void openFile(File file){
		try{
			desktop.open(file);
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	
	
	public void addPhoto(ActionEvent event){
		
		
		
		final FileChooser fc = new FileChooser();
		
		File file = fc.showOpenDialog(AlbumController.thumbnailStage);
		
		if(file != null){
			openFile(file);
		}
		
		
		String name = createAlbum();
		if(name == null){
			return;
		}
		
		int row = numPhoto/5;
        int col = numPhoto%5;
        
        if(row >2 && col == 0){
        	RowConstraints rc = new RowConstraints();
            rc.setPrefHeight(116);
            rc.setVgrow(Priority.ALWAYS);
            photoListGP.getRowConstraints().add(rc);
        }
        BorderPane album = new BorderPane();
        
        ImageView image = new ImageView("/view/no_photo.png");
        image.setFitHeight(100);
        image.setFitWidth(100);
        
        Text albumtitle = new Text(name);
        AnchorPane.setBottomAnchor(albumtitle, 10.0);
        
        album.setCenter(image);
        album.setBottom(albumtitle);
        BorderPane.setAlignment(albumtitle, Pos.CENTER);
        
        photoListGP.add(album, col, row);
        
        album.setOnMouseClicked(e ->{
        	
        	openAlbum(name, row, col);

        });
		
		numPhoto++;
	}
	
	public void openAlbum(String name, int row, int col){
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
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		albumTitle.setText(LoginController.username + "'s Album");
		photoListSP.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		
	}
	
	
}
