package PATwo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DonationDBTester {
    public static void main(String[] args) {
        DonationDB db = new DonationDB("items.txt");
        System.out.println(db.allocations(3));
        DonationDB db1 = new DonationDB("items2.txt");
        System.out.println(db1.allocations(3));

        //Eval Cases
        DonationDB dbEval3 = new DonationDB("items.txt");
        System.out.println(dbEval3.allocations(1));
        DonationDB dbEval4 = new DonationDB("item4CORR.txt");
        ArrayList<ArrayList<Item>> out = dbEval4.allocations(20);
        Collections.sort(out, new Comparator<ArrayList<Item>>(){
            public int compare(ArrayList<Item> a, ArrayList<Item> b) {
                return (b.toString()).compareTo((a.toString()));
            }
        });
        System.out.println(out);
        DonationDB dbEval5 = new DonationDB("item4CORR.txt");
        System.out.println(dbEval5.allocations(19));

        DonationDB dbEval1 = new DonationDB("item3CORR.txt");
        ArrayList<ArrayList<Item>> out1 = dbEval1.allocations(7);
        Collections.sort(out1, new Comparator<ArrayList<Item>>(){
            public int compare(ArrayList<Item> a, ArrayList<Item> b) {
                return (b.toString()).compareTo((a.toString()));
            }
        });
        System.out.println(out1);
        DonationDB dbEval2 = new DonationDB("item3CORR.txt");
        System.out.println(dbEval2.allocations(8));
    }
}
