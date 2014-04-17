/**
 * @author Mark Philip (msp3430@rit.edu)
 */

import java.io.*;
import java.util.*;

public class Solution {

    //list of all the cities
    private ArrayList<String[]> cities;
    //list of cities with population over 50,000
    private ArrayList<String[]> citiesOver50k;
    //list of cumulative growth in each state
    private int[] byState;
    //state names corresponding to entries in byState
    private String[] stateName;
    //used in creating byState and stateName in addToState()
    private int spotAt;

    /**
     * The constructor method
     */
    public Solution() {
	cities = new ArrayList<String[]>();
	citiesOver50k = new ArrayList<String[]>();
	byState = new int[51];
	stateName = new String[51];
	for (int i = 0; i < 51; i++) {
	    byState[i] = 0;
	    stateName[i] = null;
	}
	spotAt = 0;
    }
    
    /**
     * A method for reading in the city information from the given data file.
     */
    private void parseCities() {

	//create a scanner to read in the information from the file
	Scanner sc = null;
	
	//try opening the file
	try {
	    sc = new Scanner(new BufferedReader(new FileReader("Metropolitan_Populations__2010-2012_.csv"))).useDelimiter("\r");
	}
	catch (FileNotFoundException fnfe) {
	    System.out.println(fnfe.getMessage());
	    return;
	}

	//read in each line of the file and break the line into 5 separate pieces: 
	//city, state, 2010 population, 2011 population, 2012 population
	int i = 0;
	while (sc.hasNext()) {
	    //ignore the column headings
	    if (i > 3) {
		String next = sc.next();
		//remove quotation marks
		next = next.replace('"', '\0');
		//replace semicolons with commas
		next = next.replace(';', ',');
		//get rid of double spaces
		next = next.replace("  ", " ");
		//split the data into 5 separate parts
		String[] tmp = next.split(",");
		//remove the leading space in the state name
		tmp[1] = tmp[1].substring(1);
		
		//special cases (cities with commas in their names)
		if (tmp[2].charAt(0) == ' ') {
		    tmp[0] = tmp[0].concat(" " + tmp[1]);
		    tmp[1] = tmp[2];
		    tmp[2] = tmp[3];
		    tmp[3] = tmp[4];
		    tmp[4] = tmp[5];
		}
		while (tmp[1].charAt(0) == ' ') {
		    tmp[1] = tmp[1].substring(1);
		}

		//add the data to the list of cities
		cities.add(tmp);
		//update the list of cumulative state growth
		addToState(tmp);
		//set aside cities with 2010 populations of over 50,000
		if (Integer.parseInt(tmp[2]) >= 50000) {
		    //order cities with populations of over 50,000 by population percent growth between 2010 and 2012
		    addOrdered(tmp);
		}
	    }
	    else {
		sc.next();
	    }
	    i++;
	}
	//close the scanner
	if (sc != null) {
	    sc.close();
	}
    }
    
    /**
     * Used to update the list of cumulate state growth
     *
     * @param data     a list of the info for one city
     */
    private void addToState(String[] data) {
	//add the first state
	if (stateName[0] == null) {
	    byState[0] = byState[0] + Integer.parseInt(data[4]) - Integer.parseInt(data[2]);
	    stateName[0] = data[1];
	    spotAt = 1;
	    return;
	}
	//add the other states
	else {
	    for (int i = 0; i < spotAt && i < 51; i++) {
		if (data[1].compareTo(stateName[i]) == 0) {
		    byState[i] = byState[i] + Integer.parseInt(data[4]) - Integer.parseInt(data[2]);
		    return;
		}
	    }
	    stateName[spotAt] = data[1];
	    byState[spotAt] = byState[spotAt] + Integer.parseInt(data[4]) - Integer.parseInt(data[2]);
	    spotAt++;
	}
    }
	    
    /**
     * A method to keep citiesOver50k ordered by percent growth
     *
     * @param data      a list of the info for one city
     */
    private void addOrdered(String[] data) {
	//add the first city
	if (citiesOver50k.size() == 0) {
	    citiesOver50k.add(data);
	    return;
	}
	//add the other cities
	else {
	    for(int i = 0; i < citiesOver50k.size(); i++) {
		if (percentChange(citiesOver50k.get(i), data) == 1) {
		    citiesOver50k.add(i, data);
		    return;
		}
	    }
	    citiesOver50k.add(data);
	}
    }

    /**
     * A method used to calculate which city had a higher percent change
     *
     * @param data1     a list of the info for one city
     * @param data2     a list of the info for one city
     *
     * @return          returns 1 if data2 had a higher percent change, else 0
     */

    private int percentChange(String[] data1, String[] data2) {
	double d1p2010, d1p2012, d2p2010, d2p2012;
	double perCd1, perCd2;
	try {
	    d1p2010 = Integer.parseInt(data1[2]);
	    d1p2012 = Integer.parseInt(data1[4]);
	    d2p2010 = Integer.parseInt(data2[2]);
	    d2p2012 = Integer.parseInt(data2[4]);
	}
	catch (NumberFormatException nfe) {
	    System.out.println(nfe.getMessage());
	    return -55;
	}
	perCd1 = (double)((d1p2012 - d1p2010)/d1p2010);
	perCd2 = (double)((d2p2012 - d2p2010)/d2p2010);
	//System.out.println(d1p2010 + " " + d1p2012 + " " + d2p2010 + " " + d2p2012 + " " + perCd1 + " " + perCd2);
	
	if (perCd2 > perCd1) {
	    return 1;
	}
	else {
	    return 0;
	}
    }

    /**
     * Finds and prints out the five best and worst cities based on percent growth
     */
    private void deliverables12() {
	
	String[] topCities = new String[5];
	String[] worstCities = new String[5];

	//get the 5 best cities and print them
	System.out.println("Top 5 cities to target (deliverable 1)");
	for (int i = 0; i < 5; i++) {
	    System.out.println(i+1 + ": " + citiesOver50k.get(i)[0] + ", " + citiesOver50k.get(i)[1]);
	}
	System.out.println();	
	
	//get the 5 worst cities and print them
	System.out.println("Top 5 cities to avoid (deliverable 2)");
	int j = 0;
	for (int i = citiesOver50k.size() - 1; j < 5; i--) {
	    System.out.println(j+1 + ": " + citiesOver50k.get(i)[0] + ", " + citiesOver50k.get(i)[1]);
	    j++;
	}
    }

    /**
     * Finds the 5 best states based on cumulate growth
     */
    public void deliverable3() {
	String[] states = new String[5];
	int[] statesNum = new int[5];

	for (int i = 0; i < 5; i++) {
	    states[i] = null;
	    statesNum[i] = Integer.MIN_VALUE;
	}

	states[0] = stateName[0];
	statesNum[0] = byState[0];

	//find the states
	for (int i = 1; i < 51; i++) {
	    for (int j = 0; j < 5; j++) {
		if (byState[i] > statesNum[j]) {
		    statesNum[j] = byState[i];
		    states[j] = stateName[i];
		    j = 5;
		}
	    }
	}
	
	//print the states out
	System.out.println("Top 5 cities by cumulative growth (deliverable 3)");
	for (int i = 0; i < 5; i++) {
	    System.out.println(i+1 + ": " + states[i]);
	}
    }

    /**
     * Calls the methods that actually do the work
     *
     * @param args    not used
     */
    public static void main(String[] args) {
	Solution solution = new Solution();
	System.out.println();
	solution.parseCities();
	solution.deliverables12();
	System.out.println();
	solution.deliverable3();
	System.out.println();
	System.out.println("DONE");
    }
}
