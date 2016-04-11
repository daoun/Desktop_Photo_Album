package controller;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Photo;

public class ThumbnailController implements Initializable{

	public static Stage photoStage;
	
	@FXML private Button addPhoto;
	@FXML private TextField tagBar;
	@FXML private Button deletePhoto;
	@FXML private Button searchBtn;
	@FXML private DatePicker startDate;
	@FXML private DatePicker endDate;
	@FXML private RadioButton tagBtn;
	@FXML private RadioButton dateBtn;
	@FXML private Button photoAddBtn;
	@FXML private Text albumTitle;
	@FXML private GridPane photoListGP;
	@FXML private ScrollPane photoListSP;
	
	
	@FXML private ComboBox<?> photoOption;
	
	public int numPhoto = 0;
	public static int currentAlbum;
	
	public void showDate(ActionEvent event){
		tagBtn.setSelected(false);
		tagBar.setVisible(false);
		startDate.setVisible(true);
		endDate.setVisible(true);

	}
	
	public void hideDate(ActionEvent event){
		startDate.setVisible(false);
		endDate.setVisible(false);
		tagBar.setVisible(true);
		
	}
	
	
	public void addPhoto(ActionEvent event){
		
		
		final FileChooser fc = new FileChooser();
		String path = "";
		File file = fc.showOpenDialog(AlbumController.thumbnailStage);
		if(file != null){
			path = file.getPath();
		}
		
		String caption = getCaption();
		
		Photo photo = new Photo(caption);
		System.out.println(file.lastModified());
		
		int row = numPhoto/5;
        int col = numPhoto%5;
        
        if(row >2 && col == 0){
        	RowConstraints rc = new RowConstraints();
            rc.setPrefHeight(116);
            rc.setVgrow(Priority.ALWAYS);
            photoListGP.getRowConstraints().add(rc);
        }
        BorderPane photoBP = new BorderPane();
        
        ImageView image = new ImageView("file:" + path);
        photo.setURL("file:" + path);
        image.setFitHeight(100);
        image.setFitWidth(100);
        
        Text captionT = new Text(caption);
        AnchorPane.setBottomAnchor(captionT, 10.0);
        
        photoBP.setCenter(image);
        photoBP.setBottom(captionT);
        BorderPane.setAlignment(captionT, Pos.CENTER);
        
        photoListGP.add(photoBP, col, row);
        AdminController.userlist.get(AlbumController.currentUser).getAlbum(currentAlbum).addPhoto(photo);
        
        photoBP.setOnMouseClicked(e ->{
        	
        	openPhoto(caption, row, col);

        });
		
		numPhoto++;
	}
	
	public void openPhoto(String name, int row, int col){
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/photo.fxml"));
			
			
			int index = row*3 + col;
			PhotoController.currentPhoto = index;
			
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
	
	public String getCaption(){
		Dialog<String> dialog = new TextInputDialog("Enter a caption. ");
		dialog.setHeaderText("Add Caption");
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
	
	public void loadPhotos(){
        int i = 0;
        for(i = 0; i < AdminController.userlist.get(AlbumController.currentUser).getAlbum(currentAlbum).getPhotolistSize(); i++){
        	int row = i/5;
        	int col = i%5;
        	
        	if(row >1 && col == 0){
            	RowConstraints rc = new RowConstraints();
                rc.setPrefHeight(116);
                rc.setVgrow(Priority.ALWAYS);
                photoListGP.getRowConstraints().add(rc);
            }
            BorderPane photoBP = new BorderPane();
            
            ImageView image = new ImageView(AdminController.userlist.
            		get(AlbumController.currentUser).
            		getAlbum(currentAlbum).getPhoto(i).getURL());
            image.setFitHeight(100);
            image.setFitWidth(100);
            
            String caption = AdminController.userlist.get(AlbumController.currentUser).getAlbum(currentAlbum).getPhoto(i).getCaption();
            Text captionT = new Text(caption);
            AnchorPane.setBottomAnchor(captionT, 10.0);
            
            photoBP.setCenter(image);
            photoBP.setBottom(captionT);
            BorderPane.setAlignment(captionT, Pos.CENTER);
            
            photoListGP.add(photoBP, col, row);
            
            photoBP.setOnMouseClicked(e ->{
            	
            	openPhoto(caption, row, col);

            });
        }
        
	}
	
	public void back(ActionEvent e){
		AlbumController.thumbnailStage.close();
		LoginController.albumStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		albumTitle.setText(AdminController.userlist.get(AlbumController.currentUser).getAlbum(currentAlbum).getName());
		photoListSP.setHbarPolicy(ScrollBarPolicy.NEVER);
		numPhoto = AdminController.userlist.get(AlbumController.currentUser).getAlbum(currentAlbum).getPhotolistSize();
		
		System.out.println("Album Name: "+AdminController.userlist.get(AlbumController.currentUser).getAlbum(currentAlbum).getName());
		if(AdminController.userlist.get(AlbumController.currentUser).getAlbum(currentAlbum).getPhotolistSize() > 0){
			loadPhotos();
		}
		
	}
	
	
}
