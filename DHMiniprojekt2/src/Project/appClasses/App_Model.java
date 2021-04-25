package Project.appClasses;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;

import Project.ServiceLocator;
import Project.abstractClasses.Model;
import javafx.scene.image.ImageView;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class App_Model extends Model {
	
	ServiceLocator serviceLocator;
	protected TreeSet<Contact> contactsTree = new TreeSet<Contact>();
	
	private static String SEPARATOR = ";"; // Separator for "split"
	protected SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
	protected DateTimeFormatter LocalFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private static String CONTACT_FILE = "contacts.txt";
	

	

    private int value;
    
    public App_Model() {
    	value = 0;
        serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("Application model initialized");
        
    }
    
    public void readFile() {
    	File contactFile = new File(CONTACT_FILE);
    	try(Reader inReader = new FileReader(contactFile)) {
    		BufferedReader fileIn = new BufferedReader(inReader);
    		String line = fileIn.readLine(); // Zeile lesen
    		while(line != null) {
    			Contact contact = readContact(line);
    			contactsTree.add(contact);
    			line = fileIn.readLine();
    		}
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private Contact readContact(String line) throws ParseException { 
		String [] attributes = line.split(SEPARATOR);
		String firstName = attributes[0];
		String lastName = attributes[1];
		String eMail = attributes[2];
		String s = attributes[3];
		Group group = Group.valueOf(s);
		String date = attributes[4];
		Date birthday = formatter.parse(date);
		int phoneNumber = Integer.parseInt(attributes[5]);
		Contact contact = new Contact(firstName, lastName, eMail, group, birthday, phoneNumber);
		return contact;
	}
    
    public void saveContact() { //save Contacts in File
    	File contactFile = new File(CONTACT_FILE);
    	/*try(PrintWriter pw = new PrintWriter(new FileOutputStream(contactFile))){
    	for(Contact contact : contactsTree) {
			String line = writeContact(contact);
			pw.write(line);
			}
    	} catch (Exception e) {
			e.printStackTrace();
		}*/
    	try(Writer out = new FileWriter(contactFile)) {
    		for(Contact contact : contactsTree) {
    			String line = writeContact(contact);
    			out.write(line);
    		}
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }

	private String writeContact(Contact contact) {
		String line = contact.getfirstName() + SEPARATOR + contact.getlastName() + SEPARATOR
				+ contact.geteMail() + SEPARATOR + contact.getGroup() + SEPARATOR 
				+ formatter.format(contact.getBirthday()) + SEPARATOR + contact.getPhoneNumber() + "\n";
		return line;
	}

	

	public Contact createContact(String firstName, String lastName, String eMail, Group group, Date birthday, int phoneNumber) {
		Contact contact = new Contact(firstName, lastName, eMail, group, birthday, phoneNumber);
		serviceLocator.getLogger().info("Create new Contact: " + contact);
		contactsTree.add(contact);
		return contact;
		
	}

	public Contact getSelectedContact(String name) {
		Contact contact = null;
		for(Contact c : contactsTree)
			if(c.getlastName().contains(name)) {
				contact = c;
			} else {
				if(c.getfirstName().contains(name))
					contact = c;
			}
		return contact;
	}

	public Contact getSelectedID(int id) {
		Contact contact = null;
		for(Contact c : contactsTree) {
			if(c.getID() == id) {
				contact = c;
			} 
		}
		return contact;
	}

	public ArrayList<Contact> getSelectedGroup(Group group) {
		ArrayList<Contact> groupArrayList = new ArrayList<Contact>();
		
		
		for(Contact c : contactsTree)
			if(c.getGroup().equals(group)) {
				groupArrayList.add(c);
			}
		return groupArrayList;
	}
	
	
	
    //fill list test
    public void fillSet() {
    	this.contactsTree.add(new Contact( "false data", "Example for", "x.x", Group.Family, 000));
    	this.contactsTree.add(new Contact( "Sarikaya", "Hüseyin", "h.sarikaya@gmx.ch", Group.Family, 788515333));
    }
    
    
    //template methods
    public int getValue() {
        return value;
    }
    
    public int incrementValue() {
        value++;
        serviceLocator.getLogger().info("Application model: value incremented to " + value);
        return value;
    }
}