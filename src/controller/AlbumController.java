
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextInputDialog;
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
import model.User;

/**
 * This controller controls the Album stage
 * @author Capki Kim, Daoun Oh
 *
 */
public class AlbumController implements Initializable{
	@FXML private Button albumAddBtn;
	@FXML private Button backBtn;
	@FXML private Text userTitle;
	@FXML private GridPane albumListGP;
	@FXML private ScrollPane albumListSP;
	@FXML private String rename;
	@FXML private String delete;
	@FXML private String detail;
	@FXML private ChoiceBox<String> albumOption;
	
	/**
	 * Stores the thumbnail stage
	 */
	public static Stage thumbnailStage;
	/**
	 * Keeps track of number of albums
	 */
	public int numAlbum = 0;
	/**
	 * Denotes the selected album
	 */
	public static int selected;
	/**
	 * Stores index of the current user being used in the userlist 
	 */
	public static int currentUser;
	
	/**
	 * Called by the addAlbum Button.
	 * Adds the new album prompted by the user.
	 * Checks for duplicate album name and empty name string.
	 */
	public void addAlbum(){
		String name = createAlbum();
		if(name == null){
			return;
		}
		
		Album album = new Album(name);
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
	
	/**
	 * Opens the stage of the selected album.
	 * 
	 * @param name album name
	 * @param row current row in the GridPane
	 * @param col current col in the GridPane
	 */
	public void openAlbum(String name, int row, int col){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/thumbnail.fxml"));
			
			int index = row*3 + col;
			ThumbnailController.currentAlbum = index;
			
			AnchorPane root = (AnchorPane)loader.load();
			Stage stage = new Stage();
            Scene scene = new Scene(root);
			
            stage.setScene(scene);  
            stage.setResizable(false);  
            stage.setTitle(name);
            
            thumbnailStage = stage;
            LoginController.albumStage.close();
            stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Prompts the user to input the name of the new album to create a new Album.
	 * 
	 * @return name of the new album, 
	 * 		null if there is a duplicate or if there is no name
	 */
	public static String createAlbum(){
		Dialog<String> dialog = new TextInputDialog("Enter a album name.");
		dialog.setHeaderText("Create Album");
		dialog.setTitle("Photo Album");
		Optional<String> result = dialog.showAndWait();
		
		if(result.isPresent()){
			if(!duplicates(result.get())){
				if(result.get().length() != 0){
					return result.get();
				}
				else{
					noNameAlert();
				}
			}
		}
		return null;
	}
	
	/**
	 * Loads albums to the GridPane with the thumbnail of the first photo in the album and the name of the album.
	 */
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
	
	/**
	 * Called by the back button.
	 * Brings user back to the log in stage.
	 */
	public void back(){
		LoginController.albumStage.close();
		PhotoAlbum.loginStage.show();
	}
	
	/**
	 * Clears all borders of the selected item.
	 */
	public void clearSelected(){
		int size = AdminController.userlist.get(AlbumController.currentUser).getAlbumlistSize();
		for(int i = 0; i <size; i++){
			BorderPane bp = (BorderPane)albumListGP.getChildren().get(i);
			bp.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.NONE, CornerRadii.EMPTY, new BorderWidths(2))));
		}
	}
	
	/**
	 * Checks for duplicate album name stored in albumlist.
	 * 
	 * @param name the name of the new user
	 * @return true if there is a duplicate user,
	 * 			false otherwise
	 */
	public static boolean duplicates(String name){
		List<Album> list = AdminController.userlist.get(AlbumController.currentUser).getAlbumlist();
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getName().equals(name)){
				warnDuplicate();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Alerts the user if there is a duplicate album name.
	 */
	public static void warnDuplicate(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Duplicate Warning");
		alert.setHeaderText("Invalid");
		alert.setContentText("There exists an album with the same name.");

		alert.showAndWait();
	}
	
	/**
	 * Alerts the user if there is empty input for name.
	 */
	public static void noNameAlert(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("No Name Warning");
		alert.setHeaderText("Invalid");
		alert.setContentText("You need to enter at least one character.");

		alert.showAndWait();
	}
	
	/**
	 * Called when rename is selected in the drop down menu.
	 * Prompts the user to rename the selected album.
	 * Checks for duplicate album name.
	 */
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
	
	/**
	 * Called when delete is selected in the drop down menu.
	 * Asks the user to confirm to delete selected album.
	 */
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
		}
	}
	
	/**
	 * Called when detail is selected in the drop down menu.
	 * Shows name, number of photos, oldest photo date, and range of dates of the photos in the album.
	 */
	public void details(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/detail.fxml"));
			
			AnchorPane root = (AnchorPane)loader.load();
			Stage stage = new Stage();
            Scene scene = new Scene(root);
			
            stage.setScene(scene);  
            stage.setResizable(false);  
            stage.setTitle("Detail");
            stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userTitle.setText(LoginController.username);
		albumListSP.setHbarPolicy(ScrollBarPolicy.NEVER);
		numAlbum = AdminController.userlist.get(currentUser).getAlbumlistSize();
		
		if(AdminController.userlist.get(currentUser).getAlbumlistSize() > 0){
			loadAlbums();
		}
		clearSelected();
		
		albumOption.setOnAction(e->{
			if(albumOption.getSelectionModel().getSelectedIndex() == 0){
				rename();
			}else if(albumOption.getSelectionModel().getSelectedIndex() == 1){
				delete();
			}else if(albumOption.getSelectionModel().getSelectedIndex() == 2){
				details();
			}
			albumOption.setValue(null);
		});
	}
}

