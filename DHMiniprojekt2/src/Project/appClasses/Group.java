package Project.appClasses;

public enum Group implements Comparable<Group> {

	Favourites, Family, Work, University, Leisure, Other;
	
	public boolean contains(String searchString) {
		return (this.name().contains(searchString));
	}
	
	public boolean equals(String searchString) {
		return (this.name().equals(searchString));
	}

}
	

