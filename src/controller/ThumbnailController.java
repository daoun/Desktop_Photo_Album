package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.Photo;

/**
 * This controller controls the Thumbnail stage
 * @author Capki Kim, Daoun Oh
 *
 */
public class ThumbnailController implements Initializable{

	@FXML private Button addPhoto;
	@FXML private TextField tagBar;
	@FXML private Button deletePhoto;
	@FXML private Button searchBtn;
	@FXML private Button searchBtn2;
	@FXML private DatePicker startDate;
	@FXML private DatePicker endDate;
	@FXML private RadioButton tagBtn;
	@FXML private RadioButton dateBtn;
	@FXML private Button photoAddBtn;
	@FXML private Text albumTitle;
	@FXML private GridPane photoListGP;
	@FXML private ScrollPane photoListSP;
	@FXML private ChoiceBox<String> photoOption;

	/**
	 * toggles between date search and tag search
	 */
	final ToggleGroup group = new ToggleGroup();
	/**
	 * Stores the photo stage
	 */
	public static Stage photoStage;
	/**
	 * Stores the tag stage
	 */
	public static Stage tagStage;
	/**
	 * Stores the move stage
	 */
	public static Stage moveStage;
	
	/**
	 * Stores the list of search photos.
	 */
	public List<Photo> searchedPhotos = new ArrayList<Photo>();
	/**
	 * Denates the selected photo in the GridPane
	 */
	public static int selected;
	/**
	 * Keeps track of the number of photos in the GridPane
	 */
	public int numPhoto = 0;
	/**
	 * Stores index of the current album being used in the albumlist 
	 */
	public static int currentAlbum;
	
	/**
	 * Called by the CreateAlbumWithSearch Button.
	 * Creates a new album with the searched result.
	 */
	public void createAlbumWithSearch(){
		String name = AlbumController.createAlbum();
		if(name == null){
			return;
		}
		Album al = new Album(name);
		al.setPhotolist(searchedPhotos);
		AdminController.userlist.get(AlbumController.currentUser).addAlbum(al);
	}
	
	/**
	 * Called by the search Button.
	 * Searches the album with the specified date range or a tag.
	 * Displays the result of the search into the GridPane.
	 */
	public void search(){
		String text = "";
		Album album = AdminController.userlist.get(AlbumController.currentUser).getAlbum(currentAlbum);
		
		if(tagBtn.isSelected()){ //Tag search
			
			text = tagBar.getText();
			photoListGP.getChildren().remove(0, numPhoto);
			List<Photo> photos = new ArrayList<Photo>();
			
			for(Photo p : album.getPhotolist()){
				List<String> t = p.getTaglist();
				for(String s : t){
					if(s.equals(text)){
						
						photos.add(p);
					}
				}
				
			}
			searchedPhotos = photos;
			loadPhotos(photos);
			numPhoto = photos.size();
			
		}else{ //Date Search
			
			LocalDate startD = startDate.getValue();
			LocalDate endD = endDate.getValue();
			
			if(startD == null || endD == null){
				dateDoesNotExist();
			}else{
				Date date1 = Date.from(startD.atStartOfDay(ZoneId.systemDefault()).toInstant());
				Date date2 = Date.from(endD.atStartOfDay(ZoneId.systemDefault()).toInstant());
				
				photoListGP.getChildren().remove(0, numPhoto);
				List<Photo> photos = new ArrayList<Photo>();
				
				for(Photo p : album.getPhotolist()){
					Date d = p.getDate();
					
					if(d.after(date1) && d.before(date2)){
						photos.add(p);
					}
				}
				searchedPhotos = photos;
				loadPhotos(photos);
				numPhoto = photos.size();
			}
		}
	}
	
	/**
	 * Called by the toggle Button.
	 * Shows the Start Date input.
	 */
	public void showDate(){
		tagBar.setVisible(false);
		startDate.setVisible(true);
		
		final Callback<DatePicker, DateCell> dayCellFactory = 
				new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (item.isBefore(
								startDate.getValue().plusDays(1))
								) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						}   
					}
				};
			}
	     };
	     endDate.setDayCellFactory(dayCellFactory);
	}
	
	/**
	 * Shows the End Date input.
	 */
	public void showEndDate(){
		endDate.setVisible(true);
	}
	
	/**
	 * Hides the Start and End Date inputs.
	 */
	public void hideDate(){
		startDate.setVisible(false);
		endDate.setVisible(false);
		tagBar.setVisible(true);
	}
	
	/**
	 * Called by the addPhoto Button.
	 * Adds the photo by browsing through your directory and inputing a caption.
	 * Added on to GridPane.
	 */
	public void addPhoto(){
		final FileChooser fc = new FileChooser();
		String path = "";
		File file = fc.showOpenDialog(AlbumController.thumbnailStage);
		if(file != null){
			path = file.getPath();
		}
		else{
			return;
		}
		
		String caption = getCaption();
		Photo photo = new Photo(caption);
		
		long millisec = file.lastModified();
		photo.setDate(new Date(millisec));
		
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
        	selected = row*5 + col;
        	if (e.getClickCount() == 1) {
        		
        		clearSelected();
        		photoBP.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

        	}
        	else if(e.getClickCount() == 2){
        		openPhoto(caption, row, col);
        	}

        });
		
		numPhoto++;
	}
	
	/**
	 * Opens the photo detail stage of the selected photo.
	 * 
	 * @param name caption of the photo
	 * @param row current row in the GridPane
	 * @param col current col in the GridPane
	 */
	public void openPhoto(String name, int row, int col){
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/photo.fxml"));
			
			int index = row*5 + col;
			PhotoController.currentPhoto = index;
			
			AnchorPane root = (AnchorPane)loader.load();
        	
			Stage stage = new Stage();
            Scene scene = new Scene(root);
			
            stage.setScene(scene);  
            stage.setResizable(false);  
            stage.setTitle(name);
            
            photoStage = stage;
            
            stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Prompts user to input caption for a photo.
	 * 
	 * @return returns the inputed caption
	 */
	public String getCaption(){
		Dialog<String> dialog = new TextInputDialog("Enter a caption. ");
		dialog.setHeaderText("Add Caption");
		dialog.setTitle("Photo Album");
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()){
			return result.get();
		}
		else{
			return null;
		}
	}
	
	/**
	 * Loads the photos in photos to the GridPane
	 * @param photos list of the photos
	 */
	public void loadPhotos(List<Photo> photos){
        int i = 0;
        for(i = 0; i < photos.size(); i++){
        	int row = i/5;
        	int col = i%5;
        	
        	if(row >1 && col == 0){
            	RowConstraints rc = new RowConstraints();
                rc.setPrefHeight(116);
                rc.setVgrow(Priority.ALWAYS);
                photoListGP.getRowConstraints().add(rc);
            }
            BorderPane photoBP = new BorderPane();
            
            ImageView image = new ImageView(photos.get(i).getURL());
            
            image.setFitHeight(100);
            image.setFitWidth(100);
            
            String caption = photos.get(i).getCaption();
            Text captionT = new Text(caption);
            AnchorPane.setBottomAnchor(captionT, 10.0);
            
            photoBP.setCenter(image);
            photoBP.setBottom(captionT);
            BorderPane.setAlignment(captionT, Pos.CENTER);
            
            photoListGP.add(photoBP, col, row);
            
            photoBP.setOnMouseClicked(e ->{
            	selected = row*5 + col;
        		
            	if (e.getClickCount() == 1) {
            		clearSelected();
            		photoBP.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

            	}
            	else if(e.getClickCount() == 2){
            		openPhoto(caption, row, col);
            	}

            });
        }
        
	}
	
	/**
	 * Called by the back button.
	 * Brings user back to the albums stage.
	 */
	public void back(){
		AlbumController.thumbnailStage.close();
		LoginController.albumStage.show();
	}
	
	/**
	 * Clears all borders of the selected item.
	 */
	public void clearSelected(){
		int size = AdminController.userlist.get(AlbumController.currentUser).getAlbum(currentAlbum).getPhotolistSize();
		for(int i = 0; i <size; i++){
			BorderPane bp = (BorderPane)photoListGP.getChildren().get(i);
			bp.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.NONE, CornerRadii.EMPTY, new BorderWidths(2))));
		}
	}
	
	/**
	 * Called when recaption is selected in the drop down menu.
	 * Prompts the user to edit the caption.
	 */
	public void recaption(){
		Dialog<String> dialog = new TextInputDialog("Enter a new caption.");
		dialog.setHeaderText("Recaption Photo");
		dialog.setTitle("Modify Photo");
		Optional<String> result = dialog.showAndWait();
		
		if(result.isPresent()){
			AdminController.userlist.get(AlbumController.currentUser).getAlbum(currentAlbum).getPhoto(selected).setCaption(result.get());
			photoListGP.getChildren().remove(0, numPhoto);
			loadPhotos(AdminController.userlist.get(AlbumController.currentUser).getAlbum(currentAlbum).getPhotolist());
		}
	}
	
	/**
	 * Called when edit tag is selected in the drop down menu.
	 * Prompts the user to edit the tags.
	 */
	public void tag(){
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/tag.fxml"));
			
			AnchorPane root = (AnchorPane)loader.load();
        	
			Stage stage = new Stage();
            Scene scene = new Scene(root);
			
            stage.setScene(scene);  
            stage.setResizable(false);  
            stage.setTitle("Edit Tag");
            
            tagStage = stage;
            
            stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Called when move is selected in the drop down menu.
	 * Opens a move stage which show the list of the albums the user can choose from.
	 */
	public void move(){
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/move.fxml"));
			
			AnchorPane root = (AnchorPane)loader.load();
			Stage stage = new Stage();
            Scene scene = new Scene(root);
			
            stage.setScene(scene);  
            stage.setResizable(false);  
            stage.setTitle("Move Photo");
            
            moveStage = stage;
            
            stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Called when delete is selected in the drop down menu.
	 * Asks the user to confirm deleting the selected photo.
	 */
	public void delete(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Modify Photo");
		alert.setHeaderText("Delete Photo");
		alert.setContentText("Are you sure you want to delete this photo?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			photoListGP.getChildren().remove(0, numPhoto);
			AdminController.userlist.get(AlbumController.currentUser).getAlbum(currentAlbum).remove(selected);
			numPhoto--;
			loadPhotos(AdminController.userlist.get(AlbumController.currentUser).getAlbum(currentAlbum).getPhotolist());
		} 
	}
	
	/**
	 * alerts the user that the inputed date does not exists.
	 */
	public void dateDoesNotExist(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Invalid Date");
		alert.setHeaderText(null);
		alert.setContentText("Please select a valid date range");

		alert.showAndWait();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		albumTitle.setText(AdminController.userlist.get(AlbumController.currentUser).getAlbum(currentAlbum).getName());
		photoListSP.setHbarPolicy(ScrollBarPolicy.NEVER);
		numPhoto = AdminController.userlist.get(AlbumController.currentUser).getAlbum(currentAlbum).getPhotolistSize();
		
		if(AdminController.userlist.get(AlbumController.currentUser).getAlbum(currentAlbum).getPhotolistSize() > 0){
			loadPhotos(AdminController.userlist.get(AlbumController.currentUser).getAlbum(currentAlbum).getPhotolist());
		}
		
		startDate.setVisible(false);
		endDate.setVisible(false);
		tagBtn.setToggleGroup(group);
		dateBtn.setToggleGroup(group);
		
		photoOption.setOnAction(e->{
			if(photoOption.getSelectionModel().getSelectedIndex() == 0){
				recaption();
			}
			else if(photoOption.getSelectionModel().getSelectedIndex() == 1){
				tag();
			}
			else if(photoOption.getSelectionModel().getSelectedIndex() == 2){
				move();
			}
			else if(photoOption.getSelectionModel().getSelectedIndex() == 3){
				delete();
			}
			
			photoOption.setValue(null);
		});
	}
}
