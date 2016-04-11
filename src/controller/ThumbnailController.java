package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
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
	@FXML private ChoiceBox<String> photoOption;

	final ToggleGroup group = new ToggleGroup();
	

	public int selected;
	public int numPhoto = 0;
	public static int currentAlbum;
	
	public void search(ActionEvent event){
		
		String text = "";
		Album album = AdminController.userlist.get(AlbumController.currentUser).getAlbum(currentAlbum);
		
		if(tagBtn.isSelected()){ //Tag search
			
			text = tagBar.getText();
			
			for(Photo p : album.getPhotolist()){
				List<String> t = p.getTaglist();
				for(String s : t){
					if(s.equals(text)){
						//Show pictures
					}
				}
			}
			
		}else{ //Date Search
			
			LocalDate startD = startDate.getValue();
			LocalDate endD = endDate.getValue();
			
			if(startD == null || endD == null){
				dateDoesNotExist();
			}else{
				Date date1 = Date.from(startD.atStartOfDay(ZoneId.systemDefault()).toInstant());
				Date date2 = Date.from(endD.atStartOfDay(ZoneId.systemDefault()).toInstant());
				
				for(Photo p : album.getPhotolist()){
					Date d = p.getDate();
					
					if(d.after(date1) && d.before(date2)){
						//add photo
					}
					
					
				}
				
				
			}
		
		}
	}
	
	public void showDate(ActionEvent event){
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
	
	public void showEndDate(ActionEvent event){
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
        	if (e.getClickCount() == 1) {
        		selected = row*5 + col;
        		clearSelected();
        		photoBP.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

        	}
        	else if(e.getClickCount() == 2){
        		openPhoto(caption, row, col);
        	}

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
            	
            	if (e.getClickCount() == 1) {
            		selected = row*5 + col;
            		clearSelected();
            		photoBP.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

            	}
            	else if(e.getClickCount() == 2){
            		openPhoto(caption, row, col);
            	}

            });
        }
        
	}
	
	public void back(ActionEvent e){
		AlbumController.thumbnailStage.close();
		LoginController.albumStage.show();
	}
	
	public void clearSelected(){
		int size = AdminController.userlist.get(AlbumController.currentUser).getAlbumlistSize();
		for(int i = 0; i <size; i++){
			BorderPane bp = (BorderPane)photoListGP.getChildren().get(i);
			bp.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.NONE, CornerRadii.EMPTY, new BorderWidths(2))));
		}
	}
	
	public void recaption(){
		Dialog<String> dialog = new TextInputDialog("Enter a new caption.");
		dialog.setHeaderText("Recaption Photo");
		dialog.setTitle("Modify Photo");
		Optional<String> result = dialog.showAndWait();
		
		if(result.isPresent()){
			AdminController.userlist.get(AlbumController.currentUser).getAlbum(currentAlbum).getPhoto(selected).setCaption(result.get());
			photoListGP.getChildren().remove(0, numPhoto);
			loadPhotos();
		}
	}
	
	public void tag(){
		
	}

	public void delete(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Modify Album");
		alert.setHeaderText("Delete Album");
		alert.setContentText("Are you sure you want to delete this album?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			photoListGP.getChildren().remove(0, numPhoto);
			AdminController.userlist.get(AlbumController.currentUser).getAlbum(currentAlbum).remove(selected);
			numPhoto--;
			loadPhotos();
			
		} else {
		    // ... user chose CANCEL or closed the dialog
		}
		
		
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
				delete();
			}
			
			photoOption.setValue(null);
		});
		
	}
	
	public void dateDoesNotExist(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Invalid Date");
		alert.setHeaderText(null);
		alert.setContentText("Please select a valid date range");

		alert.showAndWait();
	}
	
	
}
