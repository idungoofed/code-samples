import java.util.ArrayList;
import java.util.Scanner;

public class LittleAlchemyHelper {

    private ArrayList<Element> elements;
    
    public LittleAlchemyHelper() {
	elements = new ArrayList<>();
    }
    
    public String toString() {
	String retval = "";
	for (int i = 0; i < elements.size()-1; i++) {
	    retval+= elements.get(i).name + "\n";
	}
	return retval + elements.get(elements.size()-1).name;
    }

    private void add(String name) {
	if (!this.contains(name)) {
	    Element elem = new Element(name);
	    // intentionally adding this Element to itself
	    elements.add(elem);
	    for (Element other : elements) {
		other.combos.add(elem);
	    }
	}
	else {
	    System.out.println("Already created this element.");
	}
    }

    private int numCombos() {
	int retval = 0;
	for (Element elem : elements) {
	    retval+= elem.combos.size();
	}
	return retval;
    }

    private boolean contains(String elem) {
	for (Element other : elements) {
	    if (other.name.equals(elem)) {
		return true;
	    }
	}
	return false;
    }

    private String getCombo() {
	for (Element elem : elements) {
	    if (elem.combos.size() > 0) {
		return elem.name + " + " + elem.combos.remove(0).name;
	    }
	}
	// should never get here
	System.out.println("ERROR!");
	return "";
    }
    
    public static void main(String[] args) {
	
	LittleAlchemyHelper helper = new LittleAlchemyHelper();
	Scanner scan = new Scanner(System.in);
	
	System.out.println("What elements have you unlocked?\n(press ctrl-d when you're done)");
	System.out.print("\t1: ");
	for (int i = 2; scan.hasNext(); i++) {
	    String name = scan.nextLine();
	    helper.add(name);	    
	    System.out.print("\t" + i + ": ");
	}
	System.out.println("-----\n");
	//System.out.println(helper);

	System.out.println("Combinations list (press enter when you're done with the combo):");
	for (int i = 1; helper.numCombos()>0; i++) {
	    scan = new Scanner(System.in);
	    System.out.print(i + ": " + helper.getCombo() + " = ");
	    String name = scan.nextLine();

	    if (name.length() > 0) {
		helper.add(name);
	    }
	}
    }
}
