package Project.appClasses;

import java.util.Locale;
import java.util.logging.Logger;

import Project.ServiceLocator;
import Project.abstractClasses.View;
import Project.commonClasses.Translator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import junit.framework.Test;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class App_View extends View<App_Model> {
	//behälter definieren
		protected Menu menuFile, menuFileLanguage, menuHelp;
	    Label lblNumber, lblfirstName, lbllastName, lblEmail, lblBirthDate, lblGroup;
	    Button btnClick, plusButton, searchButton, groupButton, newViewButton, saveAndUpdateButton, deleteButton, editButton, backButton, backButtonGroup, updateButton,
		searchGroupButton, newButton;
	    

	    //Borderpane als root definieren
	    protected BorderPane root;
	    protected BorderPane root2, groupView, contactView;
	    
	    protected ListView<Contact> contactList, groupList;
	    
	    protected HBox buttonBar, searchBar, bottomBar, saveBar, BackBar;
	    protected ComboBox<String> comboGroup;
		protected ComboBox<Group> comboGroup2;
	    
	    protected TextField searchField;
	    protected VBox listCenter, centerContact, centerGroup, center;
	    protected MenuBar menuBar;
	    public ImageView searchIcon, iconSave, addIcon;
	    protected TextField txtfirstName, txtlastName, txtNumber, txtEmail, txtID, txtSearch;

		
		protected DatePicker birthDate;
		protected Scene scene1, scene2, scene3;
	    
	    
	   //public static Image SEARCHICON = new Image("Profilblild.jpg");
	   protected static Image ADDICON = new Image("/addIcon.png");
	   
		
	    
	    
		public App_View(Stage stage, App_Model model) {
	        super(stage, model);
	        ServiceLocator.getServiceLocator().getLogger().info("Application view initialized");
	    }
		

		@Override //template method -- add everything in the GUI
		protected Scene create_GUI() {
		    //template menu and logger
			ServiceLocator sl = ServiceLocator.getServiceLocator();  
		    //Logger logger = sl.getLogger();
		  
		    menuBar = new MenuBar();
		    menuFile = new Menu();
		    menuFileLanguage = new Menu();
		    menuFile.getItems().add(menuFileLanguage);
		    
		   
		    this.root = new BorderPane();
		    
		    //template for the language options
	       for (Locale locale : sl.getLocales()) {
	           MenuItem language = new MenuItem(locale.getLanguage());
	           menuFileLanguage.getItems().add(language);
	           language.setOnAction( event -> {
					sl.getConfiguration().setLocalOption("Language", locale.getLanguage());
	                sl.setTranslator(new Translator(locale.getLanguage()));
	                this.comboGroup.getItems().removeAll(this.comboGroup.getItems());
	                updateTexts();
	            });
	       }
	        //implement ListView
	           this.contactList = new ListView<Contact>();
	           //load the data into the list
	           super.model.fillSet();
	           for(Contact c : super.model.contactsTree) {
	    		   
	    		   
	       	   	this.contactList.getItems().add(c);
	       	    }
	           
		     	this.menuBar.getMenus().addAll(menuFile);
		     	
		   	   
				   
			   	this.newButton = new Button();
			    this.newButton.getStyleClass().add("newButton");
			    
			    
			     // add icon to newButton
			    this.addIcon = new ImageView(ADDICON);
			    this.newButton.setGraphic(this.addIcon);
			    this.addIcon.setFitHeight(28);
			    this.addIcon.setFitWidth(28);
			   	   
			    
			    this.groupButton = new Button("enter group view");
			    this.groupButton.getStyleClass().add("groupButton");
			    
			    
			  //TODO add icon to group-Button
			    
			    
			   //ButtonBar wird definiert 
			   this.buttonBar = new HBox();
			   this.buttonBar.getStyleClass().add("buttonBar");
			   
			   this.buttonBar.getChildren().addAll(this.groupButton, this.newButton);
			   
		
			   //Searchbar wird definiert
			   this.txtSearch = new TextField();
			   this.txtSearch.getStyleClass().add("txtSearch");
			   
			   
			   this.searchButton = new Button("search");
			   this.searchButton.getStyleClass().add("searchButton");
			   
			 //TODO add icon to search Button (lupe)
			   
			   
			   this.searchBar = new HBox();
			   this.searchBar.getStyleClass().add("searchBar");
			   
			   this.searchBar.getChildren().addAll(this.txtSearch, this.searchButton);
			   
			   
			   
			   //fill mainView VBox, search and buttons and list
			   this.center = new VBox();
			   this.center.getStyleClass().add("center");
			   this.center.getChildren().addAll( this.searchBar, this.contactList, this.buttonBar);
			   VBox.setVgrow(this.contactList, Priority.ALWAYS);
			   
			   this.root.setTop(this.menuBar);
			   this.root.setCenter(this.center);
			   
			   
		   
		   //contactView, create new contact , edit, delete, update
		   
			   this.contactView = new BorderPane();
			   
			   GridPane listCenter = new GridPane();
			   listCenter.getStyleClass().add("listCenter");
			   
			   //Back Button
			   this.backButton = new Button("program.center.button.back");
			   this.backButton.getStyleClass().add("backButton");
			   
			 //TODO add icon to back Button (arrow left)
			  
			   
			   //Save Button		   
			   this.saveAndUpdateButton = new Button("save");
			   this.saveAndUpdateButton.getStyleClass().add("saveButton");
			   
			 //TODO add icon to save Button
			   
			   //Update Button
			   this.updateButton = new Button("save");
			   this.updateButton.getStyleClass().add("updateButton");
			   
			 //TODO add icon to update Button
			   
			   
			   //Edit Button
			   this.editButton = new Button("program.center.button.edit");
			   this.editButton.getStyleClass().add("editButton");
			   
			   
			 //TODO add icon to edit Button
			   
			   //Delete Button
			   this.deleteButton = new Button("program.center.button.delete");
			   this.deleteButton.getStyleClass().add("deleteButton");
			   
			   
			 //TODO add icon to delete Button
			   
			   
			   //labels
			   this.lbllastName = new Label();
			   this.lblfirstName = new Label();
			   this.lblBirthDate = new Label();
			   this.lblEmail = new Label();
			   this.lblGroup = new Label();
			   this.lblNumber = new Label();
			   
			   
			   //CSS for contactView labels
			   
			   this.lbllastName.getStyleClass().add("lblContactForm");
			   this.lblfirstName.getStyleClass().add("lblContactForm");
			   this.lblBirthDate.getStyleClass().add("lblContactForm");
			   this.lblEmail.getStyleClass().add("lblContactForm");
			   this.lblGroup.getStyleClass().add("lblContactForm");
			   this.lblNumber.getStyleClass().add("lblContactForm");
			   
			   
			   
			   //TextFields contactView
			  
			   this.txtEmail = new TextField();
			   this.txtlastName = new TextField();
			   this.txtNumber = new TextField();
			   this.txtfirstName = new TextField();
			   this.txtID = new TextField();
			   
			   this.comboGroup = new ComboBox<String>();
			   this.comboGroup.setEditable(false);
			  
			   
			   this.birthDate = new DatePicker();
			   this.birthDate.setEditable(false);
			   
			   
			   //entering text CSS

			   this.txtEmail.getStyleClass().add("txtContactForm");
			   this.txtlastName.getStyleClass().add("txtContactForm");
			   this.txtNumber.getStyleClass().add("txtContactForm");
			   this.txtfirstName.getStyleClass().add("txtContactForm");
			   this.comboGroup.getStyleClass().add("txtContactForm");
			   this.birthDate.getStyleClass().add("txtBirthDate");
			   this.txtID.getStyleClass().add("txtID");
			   
			
			
			   
			   //adding to behälter
			   listCenter.add(this.lblfirstName, 0, 0);
			   listCenter.add(this.txtfirstName, 1, 0);
			   listCenter.add(this.lbllastName, 0, 1);
			   listCenter.add(this.txtlastName, 1, 1);
			   listCenter.add(this.lblNumber, 0, 2);
			   listCenter.add(this.txtNumber, 1, 2);
			   listCenter.add(this.lblEmail, 0, 3);
			   listCenter.add(this.txtEmail, 1, 3);
			   listCenter.add(this.lblBirthDate, 0, 4);
			   listCenter.add(this.birthDate, 1, 4);
			   listCenter.add(this.lblGroup, 0, 5);
			   listCenter.add(this.comboGroup, 1, 5);
			   
			   
			   
			   this.saveBar = new HBox();
			   this.saveBar.getStyleClass().add("saveBar");
			   
			   HBox spacer = new HBox();
			   HBox.setHgrow(spacer, Priority.ALWAYS);
			   
			   this.saveBar.getChildren().addAll(this.backButton, spacer, this.updateButton, this.saveAndUpdateButton);
			   
			   
			   this.bottomBar = new HBox();
			   this.bottomBar.getChildren().addAll( this.deleteButton, this.txtID, this.editButton);
			   
			   
			   this.txtID.setVisible(false);
			   this.bottomBar.getStyleClass().add("bottomBar");
			   
			   this.centerContact = new VBox();
			   this.centerContact.getStyleClass().add("centerContact");
			   VBox.setVgrow(listCenter, Priority.ALWAYS);
			   
			   
			   this.centerContact.getChildren().addAll(this.saveBar, listCenter);

			   this.contactView.setCenter(this.centerContact);
			   this.contactView.setBottom(this.bottomBar);
			   
			   // GroupView
			   this.groupView = new BorderPane();

			   this.centerGroup = new VBox(); 
			   this.centerGroup.getStyleClass().add("centerGroup");
			   
			   
			   
			 //TODO GroupView: add icon to back Button
			   this.backButtonGroup = new Button("back");
			   
			   this.BackBar = new HBox();
			   this.BackBar.getStyleClass().add("BackBar");
			   this.BackBar.getChildren().add(this.backButtonGroup);
			   
		       this.centerGroup.getChildren().addAll(this.backButtonGroup);
		       this.groupView.setTop(this.centerGroup);
		       
		       // groupList
		       
		       this.groupList = new ListView<Contact>();
		       this.groupList.getStyleClass().add("groupList");
		       
		       
		       
		       
		       this.searchGroupButton = new Button("search");
			   this.searchGroupButton.getStyleClass().add("searchButton");
			   
			 //TODO add icon to search Button
			   
			   this.comboGroup2 = new ComboBox<Group>();
			   comboGroup2.getItems().addAll(Group.values());
			   
			   HBox searchBar2 = new HBox();
			   
			   searchBar2.getChildren().addAll(comboGroup2, searchGroupButton);
			   
			   groupView.setLeft(searchBar2);
			   
			   groupView.setRight(groupList);
			   
			   
		      
	        updateTexts();
			
	        scene1 = new Scene(root, 450, 750);
	        scene2 = new Scene(contactView, 450, 750);
	        scene3 = new Scene(groupView, 450, 750);
	        scene1.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
	        scene2.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
	        scene3.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
	        return scene1;
	        


	        
	        
	     
		}
		
		   protected void updateTexts() {
		       Translator t = ServiceLocator.getServiceLocator().getTranslator();
		        
		        // The menu entries
		       menuFile.setText(t.getString("program.menu.file"));
		       menuFileLanguage.setText(t.getString("program.menu.file.language"));
	           //menuHelp.setText(t.getString("program.menu.help"));
		        
		        // Other controls
	           //btnClick.setText(t.getString("button.clickme"));
	           
	           stage.setTitle(t.getString("program.name"));
	           
	           //filling labels contactView
	           
		       this.deleteButton.setText(t.getString("program.center.button.delete"));
		       this.editButton.setText(t.getString("program.center.button.edit"));
		       this.backButton.setText(t.getString("program.center.button.back"));
		       this.saveAndUpdateButton.setText(t.getString("program.center.button.save"));
		       this.searchButton.setText(t.getString("program.center.button.search"));
		       this.updateButton.setText(t.getString("program.center.button.save"));
		       this.newButton.setText(t.getString("program.center.button.new"));
		       
		       this.lbllastName.setText(t.getString("program.label.contact.lastname"));
		       this.lblfirstName.setText(t.getString("program.label.contact.firstname"));
		       this.lblNumber.setText(t.getString("program.label.contact.number"));
		       this.lblBirthDate.setText(t.getString("program.label.contact.birthdate"));
		       this.lblEmail.setText(t.getString("program.label.contact.email"));
		       
		       this.lblGroup.setText(t.getString("program.label.contact.group"));
		       this.groupButton.setText(t.getString("program.center.button.groupView"));
		       this.comboGroup.getItems().addAll(
		    		   t.getString("program.label.contact.comboBox.value.1"),
		    		   t.getString("program.label.contact.comboBox.value.2"), 
		    		   t.getString("program.label.contact.comboBox.value.3"), 
		    		   t.getString("program.label.contact.comboBox.value.4"), 
		    		   t.getString("program.label.contact.comboBox.value.5"), 
		    		   t.getString("program.label.contact.comboBox.value.6"));
		/*
		       this.comboGroup2.getItems().addAll(
		    		   Group.valueOf(t.getString("program.label.contact.comboBox.value.1")),
		    		   Group.valueOf(t.getString("program.label.contact.comboBox.value.2")), 
		    				   Group.valueOf(t.getString("program.label.contact.comboBox.value.3")), 
		    						   Group.valueOf(t.getString("program.label.contact.comboBox.value.4")),
		    								   Group.valueOf(t.getString("program.label.contact.comboBox.value.5")), 
		    										   Group.valueOf(t.getString("program.label.contact.comboBox.value.6")));
		   */ }
		   
		   
		   
		   //helper for control
		   public void disableTextField() {
				this.txtfirstName.setDisable(true);
				this.txtlastName.setDisable(true);
				this.txtNumber.setDisable(true);
				this.birthDate.setDisable(true);
				this.comboGroup.setDisable(true);
				this.txtEmail.setDisable(true);
				this.txtID.setDisable(true);
			}

			public void enableTextField() {
				this.txtfirstName.setDisable(false);
				this.txtlastName.setDisable(false);
				this.txtNumber.setDisable(false);
				this.birthDate.setDisable(false);
				this.comboGroup.setDisable(false);
				this.txtEmail.setDisable(false);
				
			}
			
		   public void changeContactView() {
				stage.setScene(scene2);
				stage.show();
			}
		   
		   
		   public void backBack() {
				stage.setScene(scene1);
				stage.show();
			}
		   
		   public void changeGroupView() {
				stage.setScene(scene3);
				stage.show();
			}
		  

}