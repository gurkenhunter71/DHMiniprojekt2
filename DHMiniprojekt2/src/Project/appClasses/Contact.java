package Project.appClasses;

import java.util.ArrayList;
import java.util.Date;

import javafx.scene.image.ImageView;

public class Contact implements Comparable<Contact> {
	
	//define attributes
	private String firstName;
	private String lastName;
	
	

	
	private int phoneNumber;
	private static int IDNr = 0;
	private final int ID;
	private String eMail;
	private Group group;
	private Date birthday;
	
	public Contact( String firstName, String lastName, int i, String string) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = i;
		this.eMail = string;
		this.ID = getNextID();
	}
	public Contact(String firstName, String lastName, String eMail, Group group, int phoneNumber) {
		this.ID = getNextID();
		this.firstName = firstName;
		this.lastName = lastName;
		this.eMail = eMail;
		this.group = group;
		this.phoneNumber = phoneNumber;
	}
	
	public Contact(String firstName, String lastName, String eMail, Group group, Date birthday, int phoneNumber) {
		this.ID = getNextID();
		this.firstName = firstName;
		this.lastName = lastName;
		this.eMail = eMail;
		this.group = group;
		this.birthday = birthday;
		this.phoneNumber = phoneNumber; 
	} 
	

	/*public ImageView getprofilePicture() {
		return this.profilePicture;
	}
	
	public void setImageView (ImageView profilePicture) {
		this.profilePicture = profilePicture;
	}*/
	
	public String getfirstName() {
		return this.firstName;
	}

	public void setfirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getlastName() {
		return this.lastName;
	}

	public void setlastName(String lastName) {
		this.lastName = lastName;
	}

	public String geteMail() {
		return this.eMail;
	}

	public void seteMail(String eMail) {//maybe need to add index input to get back the values
		this.eMail = eMail;
	}

	public int getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	

	//toString
	public String toString() {
		return lastName + " " + firstName;
	}

	//implement Comparable - compare contacts by lastName
	//must-have for treeSet
	public int compareTo(Contact o) {
		int valCompare = this.getlastName().compareTo(o.getlastName());
		if(valCompare  == 0) {
		return 0;}
		else
			if (valCompare < 0)
				return -1;
			else
				return 1;
		
	}
	
	private static int getNextID() {
		return ++IDNr;
	}
	public Date getBirthday() {
		// TODO Auto-generated method stub
		return this.birthday;
	}
	public Group getGroup() {
		// TODO Auto-generated method stub
		return group;
	}
	public int getID() {
		// TODO Auto-generated method stub
		return this.ID;
	}
	public void setBirthday(Date birthday2) {
		// TODO Auto-generated method stub
		this.birthday = birthday2;
	}


}
