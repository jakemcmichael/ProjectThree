/**
 * Project #2
 * CS 2334, Section 010
 * Feb 19, 2016 
 * <P>
 * The Year class is an abstract data type for storing year data in a variety 
 * of formats to aid in display, sorting, searching, and comparing Media by year.
 *</P>
 *@version 1.0
 */

public class Year implements Comparable<Year>{

	/**Stores the nominal value of the year as entered by the user.*/
	private String name;

	/**Stores the start year of a range or indicates that this is a list.*/
	private String startYear;

	/**Stores the end year of a range or indicates that this is a list.*/
	private String endYear;

	/**Stores the list of years if this is a list*/
	private String[] yearList;

	/**
	 * Constructs a Year object based on a String object
	 * @param name The String object in one of the following forms:
	 *  ####, or ####-####, or ####, ####,..., ####
	 */
	public Year(String name){
		this.name = name.replace("????", "UNSPECIFIED");
		if(name.contains("-")){//range
			this.startYear = name.substring(0, 4);
			this.endYear = name.substring(5, 9);
		}
		else if(name.contains(",")){//list
			this.startYear = "list";
			this.endYear = "list";
			this.yearList = name.split(",");
			for(int i = 0; i < this.yearList.length; ++i){
				this.yearList[i] = this.yearList[i].trim();
			}
		}
		else {//single year
			this.startYear = name;
			this.endYear = "single";
		}
	}

	/**
	 * Determines if one of the years specified by this is also specified by otherYear.
	 * @param otherYear The Year object to be compared to this.
	 * @return true if there is at least one matching year and false otherwise.
	 */
	public boolean equals(Year otherYear){
		if(this.endYear.equals("single")){//if this is single
			if(otherYear.endYear.equals("single")){//and other is single
				if(this.startYear.equals(otherYear.name)){
					return true;
				}
				return false;
			}
			if(otherYear.startYear.equals("list")){//and other is list
				for(String s : otherYear.yearList){
					if(new Year(s).equals(this)){
						return true;
					}
				}
				return false;
			}//then other must be range
			if(this.startYear.compareTo(otherYear.startYear) >= 0 &&
					this.startYear.compareTo(otherYear.endYear) <= 0){
				return true;
			}
			return false;
		}
		if(this.startYear.equals("list")){//if this is list
			for(String s : this.yearList){
				if(new Year(s).equals(otherYear)){//run each of this as single
					return true;
				}
			}
			return false;//none matched other
		}//then this is range
		if(otherYear.endYear.equals("single")){//and other is single
			if(otherYear.name.compareTo(this.startYear) >= 0 &&
					otherYear.name.compareTo(this.endYear) <= 0){
				return true;
			}
			return false;
		}
		if(otherYear.startYear.equals("list")){//and other is a list
			for(String s : otherYear.yearList){
				if(new Year(s).equals(this)){//run each as single
					return true;
				}
			}
			return false;//none matched this
		}//then other is range
		if(this.startYear.compareTo(otherYear.endYear) <= 0 &&
				this.endYear.compareTo(otherYear.startYear) >= 0){
			return true;
		}
		return false;	
	}

	/**
	 * Compares this Year object with another lexicographically by name.
	 * @param otherYear The Year object to be compared to this.
	 * @return The value of the String class comparison of this.name 
	 * to otherYear.name
	 */
	public int compareTo(Year otherYear){
		return this.name.compareTo(otherYear.name);
	}

	/**
	 * Returns this year as a String object.
	 * @return The String object this.name.
	 */
	public String toString(){
		return this.name;
	}

}
