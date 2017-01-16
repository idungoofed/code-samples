import java.util.ArrayList;

public class Element {
    String name;
    ArrayList<Element> combos;
    
    public Element(String name) {
	this.name = name;
	combos = new ArrayList<>();
    }
}
