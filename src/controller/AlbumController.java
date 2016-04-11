<<<<<<< HEAD
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.PhotoAlbum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Album;

public class AlbumController implements Initializable{

	public static Stage thumbnailStage;
	@FXML private Button albumAddBtn;
	@FXML private Button backBtn;
	@FXML private Text userTitle;
	@FXML private GridPane albumListGP;
	@FXML private ScrollPane albumListSP;
	@FXML private String rename;
	@FXML private String delete;
	@FXML private String detail;
	
	
	@FXML private ChoiceBox<String> albumOption;
	
	public int numAlbum = 0;
	public int selected;
	public static int currentUser;
	
	public void addAlbum(ActionEvent event){
		
		String name = createAlbum();
		if(name == null){
			return;
		}
		
		// check if the album name is unique,
		// if not alert that it is not unique
		
		Album album = new Album(name);
		System.out.println(AdminController.userlist.size());
		AdminController.userlist.get(currentUser).addAlbum(album);
		
		int row = numAlbum/3;
        int col = numAlbum%3;
        
        if(row >1 && col == 0){
        	RowConstraints rc = new RowConstraints();
            rc.setPrefHeight(173);
            rc.setVgrow(Priority.ALWAYS);
            albumListGP.getRowConstraints().add(rc);
        }
        BorderPane albumBP = new BorderPane();
        
        ImageView image = new ImageView("/view/no_photo.png");
        image.setFitHeight(100);
        image.setFitWidth(100);
        
        Text albumtitle = new Text(name);
        AnchorPane.setBottomAnchor(albumtitle, 10.0);
        
        albumBP.setCenter(image);
        albumBP.setBottom(albumtitle);
        BorderPane.setAlignment(albumtitle, Pos.CENTER);
        
        albumListGP.add(albumBP, col, row);
        
        albumBP.setOnMouseClicked(e ->{
        	
        	if (e.getClickCount() == 1) {
        		selected = row*3 + col;
        		clearSelected();
        		albumBP.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        	}
        	else if(e.getClickCount() == 2){
        		openAlbum(name, row, col);
        	}
        });
		
		numAlbum++;
	}
	
	public void openAlbum(String name, int row, int col){
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/thumbnail.fxml"));
			
			int index = row*3 + col;
			//System.out.println(albumListGP.getChildren().get(index).getScene());
			ThumbnailController.currentAlbum = index;
			
			AnchorPane root = (AnchorPane)loader.load();
			//Stage currentStage = (Stage) loginStage.getScene().getWindow();
        	
			Stage stage = new Stage();
            Scene scene = new Scene(root);
			
            stage.setScene(scene);  
            stage.setResizable(false);  
            stage.setTitle(name);
            
            thumbnailStage = stage;
            LoginController.albumStage.close();
            stage.show();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	
	}
	
	public String createAlbum(){
		Dialog<String> dialog = new TextInputDialog("Enter a user name.");
		dialog.setHeaderText("Create Album");
		dialog.setTitle("Photo Album");
		Optional<String> result = dialog.showAndWait();
		if(!duplicates(result.get()) && result.isPresent()){
			
			return result.get();
		}
		else{
			return null;
		}
		
		
	}
	
	public void loadAlbums(){
        int i = 0;
        for(i = 0; i < AdminController.userlist.get(currentUser).getAlbumlistSize(); i++){
        	int row = i/3;
        	int col = i%3;
        	
        	if(row >1 && col == 0){
            	RowConstraints rc = new RowConstraints();
                rc.setPrefHeight(173);
                rc.setVgrow(Priority.ALWAYS);
                albumListGP.getRowConstraints().add(rc);
            }
            BorderPane albumBP = new BorderPane();
            ImageView image;
            if(AdminController.userlist.get(currentUser).getAlbum(i).getPhotolistSize() < 1){
            	image = new ImageView("/view/no_photo.png");
            }
            else{
            	image = new ImageView(AdminController.userlist.get(currentUser).getAlbum(i).getPhoto(0).getURL());
            }
        	
            image.setFitHeight(100);
            image.setFitWidth(100);
            
            String name = AdminController.userlist.get(currentUser).getAlbum(i).getName();
            Text albumtitle = new Text(name);
            AnchorPane.setBottomAnchor(albumtitle, 10.0);
            
            albumBP.setCenter(image);
            albumBP.setBottom(albumtitle);
            BorderPane.setAlignment(albumtitle, Pos.CENTER);
            
            albumListGP.add(albumBP, col, row);
            
            albumBP.setOnMouseClicked(e ->{
            	System.out.println("num click = "+ e.getClickCount());
            	if (e.getClickCount() == 1) {
            		selected = row*3 + col;
            		clearSelected();
            		albumBP.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
            		
                	
            	}
            	else if(e.getClickCount() == 2){
            		openAlbum(name, row, col);
            	}

            });
        }
        
	}
	
	public void back(ActionEvent e){
		LoginController.albumStage.close();
		PhotoAlbum.loginStage.show();
	}
	
	public void clearSelected(){
		int size = AdminController.userlist.get(AlbumController.currentUser).getAlbumlistSize();
		for(int i = 0; i <size; i++){
			BorderPane bp = (BorderPane)albumListGP.getChildren().get(i);
			bp.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.NONE, CornerRadii.EMPTY, new BorderWidths(2))));
		}
	}
	
	public boolean duplicates(String name){
		List<Album> list = AdminController.userlist.get(AlbumController.currentUser).getAlbumlist();
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getName().equals(name)){
				warnDuplicate();
				return true;
			}
		}
		return false;
	}
	
	public void warnDuplicate(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("Invalid");
		alert.setContentText("There exists an album with the same name.");

		alert.showAndWait();
	}
	
	public void rename(){
		
		
		Dialog<String> dialog = new TextInputDialog("Enter a new name for the album.");
		dialog.setHeaderText("Rename Album");
		dialog.setTitle("Modify Album");
		Optional<String> result = dialog.showAndWait();
		
		if(!duplicates(result.get()) && result.isPresent()){
			AdminController.userlist.get(AlbumController.currentUser).getAlbum(selected).setName(result.get());
			albumListGP.getChildren().remove(0, numAlbum);
			loadAlbums();
			
		}
		
	}
	
	public void delete(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Modify Album");
		alert.setHeaderText("Delete Album");
		alert.setContentText("Are you sure you want to delete this album?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			albumListGP.getChildren().remove(0, numAlbum);
			AdminController.userlist.get(AlbumController.currentUser).remove(selected);
			numAlbum--;
			loadAlbums();
			
		} else {
		    // ... user chose CANCEL or closed the dialog
		}
		
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		userTitle.setText(LoginController.username);
		albumListSP.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		numAlbum = AdminController.userlist.get(currentUser).getAlbumlistSize();
		
		System.out.println("Username: "+AdminController.userlist.get(AlbumController.currentUser).getName());
		if(AdminController.userlist.get(currentUser).getAlbumlistSize() > 0){
			loadAlbums();
		}
		
		clearSelected();
		
		
		albumOption.setOnAction(e->{
			if(albumOption.getSelectionModel().getSelectedIndex() == 0){
				rename();
			}
			else if(albumOption.getSelectionModel().getSelectedIndex() == 1){
				delete();
			}
			
			albumOption.setValue(null);
		});
		
	}
	
	
	
}

