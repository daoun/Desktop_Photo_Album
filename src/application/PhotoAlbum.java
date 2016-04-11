package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import controller.AdminController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;


public class PhotoAlbum extends Application {
	
	public static Stage loginStage;

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		File f = new File("users.ser");
		if(f.exists() && !f.isDirectory()) { 
			try
		      {
				
				printUsers();
				
		         FileInputStream fileIn = new FileInputStream("users.ser");
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		         List<User> userList = (List<User>)in.readObject();
		         AdminController.userlist = FXCollections.observableList(userList);
		         in.close();
		         fileIn.close();
		      }catch(IOException i)
		      {
		         i.printStackTrace();
		         return;
		      }
		}
		
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/login.fxml"));
		
		AnchorPane root = (AnchorPane)loader.load();
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Photo Album Log in");
		primaryStage.setResizable(false);  
		loginStage = primaryStage;
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void stop(){
		try
	      {
	         FileOutputStream fileOut = new FileOutputStream("users.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(new ArrayList<User>(AdminController.userlist));
	         out.close();
	         fileOut.close();
	         printUsers();
	         System.out.printf("Serialized data is saved in users.ser");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	public static void printUsers(){
		
		int i = 0;
		
		for(User u : AdminController.userlist){
			i++;
			System.out.println("User" + i + ": " + u.getName());
		}
		
	}
	

}
