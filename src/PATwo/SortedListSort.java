package PATwo;

import java.util.ArrayList;
import java.util.Comparator;
public class SortedListSort implements Comparator<ArrayList<Item>> {
    @Override
    public int compare(ArrayList<Item> o1, ArrayList<Item> o2) {
        return  o2.get(0).getValue() - o1.get(0).getValue();
    }
}
