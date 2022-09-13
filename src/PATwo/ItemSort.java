package PATwo;

import java.util.ArrayList;
import java.util.Comparator;

public class ItemSort implements Comparator<Item> {
    public int compare(Item a, Item b){
        return b.getValue() - a.getValue();
    }
    public ArrayList<Item> sort(ArrayList<Item> list){
        list.sort(this);
        return list;
    }
}
