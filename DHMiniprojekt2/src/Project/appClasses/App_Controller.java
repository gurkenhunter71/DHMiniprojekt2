package Project.appClasses;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import Project.ServiceLocator;
import Project.abstractClasses.Controller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class App_Controller extends Controller<App_Model, App_View> {
	 ServiceLocator serviceLocator;

	    public App_Controller(App_Model model, App_View view) {
	        super(model, view);
	        
	     // register ourselves to listen for button clicks
	        /*
	        view.btnClick.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                buttonClick();
	            }
	        });
	       
	*/
	        
	        
	        view.txtEmail.textProperty().addListener(
					// Parameters of any PropertyChangeListener
					(observable, oldValue, newValue) -> validateEmailAddress(newValue));
		
	        view.txtNumber.textProperty().addListener((observable, oldValue, newValue) -> validateTelephoneNr(newValue));
	        view.txtlastName.textProperty().addListener((observable, oldValue, newValue) -> validateLastName(newValue));
	        view.txtfirstName.textProperty().addListener((observable, oldValue, newValue) -> validateFirstName(newValue));
	        // register ourselves to handle window-closing event
	        view.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
	            @Override
	            public void handle(WindowEvent event) {
	                Platform.exit();
	            }
	        });
	        
	        serviceLocator = ServiceLocator.getServiceLocator();        
	        serviceLocator.getLogger().info("Application controller initialized");
	        
	        model.readFile();
	   
	    
	        view.contactList.setOnMouseClicked(this::updateContact);
	        
	        view.backButton.setOnAction(this::updateBack);
	        
	        view.backButtonGroup.setOnAction(this::updateBack);
	        
	        view.newButton.setOnAction(this::newContact);
	        
	        view.saveAndUpdateButton.setOnAction(arg0 -> {
				try {
					saveNewContact(arg0);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
	        
	        view.editButton.setOnAction(this::editContact);
	        
	        view.groupButton.setOnAction(this::showGroup);
	        
	        view.searchGroupButton.setOnAction(this::searchGroup);
	        
	        view.groupList.setOnMouseClicked(this::updateContactGroupView);
	        
	        view.deleteButton.setOnAction(this::delete);
	        
	        view.searchButton.setOnAction(this::search);
	        
	        view.updateButton.setOnAction(arg0 -> {
				try {
					refreshContact(arg0);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}});
	        
	        
	     
	    }
	    
	    
	    
	   
	    private void showGroup(Event e) {
	    	view.changeGroupView();
	    	
	    	
	    }
	    

		private void updateContact(MouseEvent mouseevent1) {
			view.changeContactView();
			view.disableTextField();
			
			view.updateButton.setDisable(true);
			view.editButton.setDisable(false);
			view.deleteButton.setDisable(false);
			view.deleteButton.setVisible(true);
			view.saveAndUpdateButton.setDisable(true);
			view.saveAndUpdateButton.setVisible(false);
			view.saveAndUpdateButton.setManaged(false);
			view.editButton.setVisible(true);
			view.updateButton.setVisible(true);
			
			Contact contact = view.contactList.getSelectionModel().getSelectedItem();
			this.updateView(contact);
		}

		private void updateContactGroupView(MouseEvent mouseevent1) {
			view.changeContactView();
			view.disableTextField();
			view.saveAndUpdateButton.setDisable(true);
			view.saveAndUpdateButton.setVisible(false);
			view.saveAndUpdateButton.setManaged(false);
			view.updateButton.setDisable(true);
			view.editButton.setDisable(false);
			view.deleteButton.setDisable(false);
			view.deleteButton.setVisible(true);
			view.editButton.setVisible(true);
			view.updateButton.setVisible(true);
			
			Contact contact = view.groupList.getSelectionModel().getSelectedItem();
			this.updateView(contact);
		}
		


		private void updateView(Contact contact) {
			
			if (contact != null) {
				view.txtfirstName.setText(contact.getfirstName());
				view.txtlastName.setText(contact.getlastName());
				view.txtEmail.setText(contact.geteMail());
				view.txtNumber.setText("0"+Integer.toString(contact.getPhoneNumber()));
				view.comboGroup.setValue(contact.getGroup().name());
				
				String birthday = model.formatter.format(contact.getBirthday());
				LocalDate date = LocalDate.parse(birthday, model.LocalFormatter);
				view.birthDate.setValue(date);
				view.txtID.setText(Integer.toString(contact.getID()));
				
			} else {
				view.txtfirstName.setText("");
				view.txtlastName.setText("");
				view.txtNumber.setText("");
				view.txtEmail.setText("");
				view.comboGroup.setValue(null);
				view.birthDate.setValue(LocalDate.now());
				view.txtID.setText("");
			}
			
		}



		private void updateBack(Event e) {
			view.backBack();
			this.updateListView();
			view.txtSearch.clear();
		}
		
		private void newContact(Event e) {
			view.changeContactView();
			view.enableTextField();
			
			view.saveAndUpdateButton.setDisable(false);
			view.saveAndUpdateButton.setVisible(true);
			view.saveAndUpdateButton.setManaged(true);
			view.deleteButton.setVisible(false);
			view.editButton.setVisible(false);
			view.updateButton.setVisible(false);
			
			view.txtID.setDisable(true);
			this.updateView(null);
			
		}
		
		//Saving
		private void saveNewContact(Event e) throws ParseException {
			String lastName = view.txtlastName.getText();
			String firstName = view.txtfirstName.getText();
			
			String eMail = view.txtEmail.getText();
			
			String stringGroup =(view.comboGroup.getSelectionModel().getSelectedItem());
			Group group = Group.valueOf(stringGroup);
			
			String phoneNum = view.txtNumber.getText();
			int phoneNumber = Integer.parseInt(phoneNum);
			
			LocalDate date = view.birthDate.getValue();
			String dateString = date.format(model.LocalFormatter);
			Date birthday = model.formatter.parse(dateString);
			
			Contact contact = model.createContact(firstName, lastName, eMail, group, birthday, phoneNumber);
			view.contactList.getItems().add(contact);
			view.backBack();
			model.saveContact();
		}
		
		//D
		private void delete(Event e) {
			String name = view.txtlastName.getText();
			Contact contact = model.getSelectedContact(name);
			
			model.contactsTree.remove(contact);
			model.saveContact();
			
			this.updateListView();
			this.updateView(null);
			view.backBack();
		}
		
		private void updateListView() {
			view.contactList.getItems().clear();
			
			
			for(Contact c : model.contactsTree) {
			   	view.contactList.getItems().add(c);
			    }
		}
		
		//searching
		private void search(Event e) {
			String name = view.txtSearch.getText();
			Contact contact = model.getSelectedContact(name);
			view.contactList.getItems().clear();
			
			view.contactList.getItems().add(contact);
		}
		
		public void searchGroup(Event e) {
			Group group = view.comboGroup2.getValue();
			/*
			String groupString = String.valueOf((view.comboGroup2.getValue()));
			Group group = Group.valueOf(groupString);
			
			if (groupString.equals("Favoriten")) {
				group = Group.Favourites;
			}
			if (groupString.equals("Familie")) {
				 group = Group.Family;
			}
			if (groupString.equals("Arbeit")) {
				 group = Group.Work;
			}
			if (groupString.equals("Favoriten")) {
				 group = Group.University;
			}
			if (groupString.equals("Favoriten")) {
				 group = Group.Leisure;
			}
			if (groupString.equals("Favoriten")) {
				 group = Group.Other;
			}else {
				 group = Group.valueOf(groupString);
			}*/
			
			ArrayList<Contact> arrayGroup = model.getSelectedGroup(group);
			view.groupList.getItems().clear();
			
			for(Contact c : arrayGroup) {
				view.groupList.getItems().add(c);
			}
			
		}
		




		//for update btn
		private void refreshContact(Event e) throws ParseException {
			String ID = view.txtID.getText();
			int contactID = Integer.parseInt(ID);
			Contact contact = model.getSelectedID(contactID);
			
			contact.setfirstName(view.txtfirstName.getText());
			contact.setlastName(view.txtlastName.getText());
			
			int phoneNum = Integer.parseInt(view.txtNumber.getText());
			contact.setPhoneNumber(phoneNum);
			contact.seteMail(view.txtEmail.getText());
			
			LocalDate date = view.birthDate.getValue();
			System.out.println(date);
			String dateString = date.format(model.LocalFormatter);
			System.out.println(dateString);
			Date birthday = model.formatter.parse(dateString);
			System.out.println(birthday);
			contact.setBirthday(birthday);
			
			view.disableTextField();
			view.updateButton.setDisable(true);
			model.saveContact();
		}
		
		private void editContact(Event e) {
			view.updateButton.setDisable(false);
			view.enableTextField();
		}
	    
	    
	    //template method
	    public void buttonClick() {
	        model.incrementValue();
	        String newText = Integer.toString(model.getValue());        

	        view.lblNumber.setText(newText);        
	    }
	    

	    //Methoden für Eingabeprüfung
	    
		private void validateTelephoneNr(String newValue) {
			boolean valid = false;
			
			view.txtNumber.getStyleClass().remove("PhoneNumberNotOk");
			view.txtNumber.getStyleClass().remove("PhoneNumberOk");
			
			if (isASwissNumber(newValue)) {
				valid = true;
			} else {
				valid = false;
			}
			
			if (valid) {
				view.txtNumber.getStyleClass().add("PhoneNumberOk");
			} else {
				view.txtNumber.getStyleClass().add("PhoneNumberNotOk");
			}
		}
		
		 public boolean isASwissNumber(String s){      
		     String regex="[0-9*#+(), -]{10,33}";      
		      return s.matches(regex);//returns true if input and regex matches otherwise false;
		 }
	    

	private void validateEmailAddress(String newValue) {
		boolean valid = false;

		// Split on '@': must give us two not-empty parts
		String[] addressParts = newValue.split("@");
		if (addressParts.length == 2 && !addressParts[0].isEmpty() && !addressParts[1].isEmpty()) {
			// We want to split the domain on '.', but split does not give us an empty
			// string, if the split-character is the last character in the string. So we
			// first ensure that the string does not end with '.'
			if (addressParts[1].charAt(addressParts[1].length() - 1) != '.') {
				// Split domain on '.': must give us at least two parts.
				// Each part must be at least two characters long
				String[] domainParts = addressParts[1].split("\\.");
				if (domainParts.length >= 2) {
					valid = true;
					for (String s : domainParts) {
						if (s.length() < 2) valid = false;
					}
				}
			}
		}

		view.txtEmail.getStyleClass().remove("emailNotOk");
		view.txtEmail.getStyleClass().remove("emailOk");
		if (valid) {
			view.txtEmail.getStyleClass().add("emailOk");
		} else {
			view.txtEmail.getStyleClass().add("emailNotOk");
		}
	}

	private void validateFirstName(String newValue) {
		boolean valid = false;
		
		view.txtfirstName.getStyleClass().remove("FirstNameOk");
		view.txtfirstName.getStyleClass().remove("FirstNameNotOk");
		
		if (isValidName(newValue)) {
			valid = true;
		} else {
			valid = false;
		}
		
		if (valid) {
			view.txtfirstName.getStyleClass().add("FirstNameOk");
		} else {
			view.txtfirstName.getStyleClass().add("FirstNameNotOk");
		}
	}
	
	private void validateLastName(String newValue) {
		boolean valid = false;
		
		view.txtlastName.getStyleClass().remove("LastNameNotOk");
		view.txtlastName.getStyleClass().remove("LastNameOk");
		
		if (isValidName(newValue)) {
			valid = true;
		} else {
			valid = false;
		}
		
		if (valid) {
			view.txtlastName.getStyleClass().add("LastNameOk");
		} else {
			view.txtlastName.getStyleClass().add("LastNameNotOk");
		}
	}
	    
	 public boolean isValidName(String s){      
	     String regex="[A-Za-zÄÖÜäöüßèéà-]{3,30}";      
	      return s.matches(regex);//returns true if input and regex matches otherwise false;
	 }
}

