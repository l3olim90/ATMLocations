package PATwo;

import java.io.*;
import java.util.*;

public class DonationDB {
    private int numItems;
    private ArrayList<Item> itemList;

    public DonationDB(String filename){
        BufferedReader inStream = null;
        itemList = new ArrayList<>();
        try {
            String line;
            boolean firstLineRead = false;
            inStream = new BufferedReader(new FileReader(filename));
            while ((line = inStream.readLine()) != null){
                if (!firstLineRead){
                    numItems = Integer.parseInt(line);
                    firstLineRead = true;
                } else {
                    String[] tokens = line.split("[,]+");
                    itemList.add(new Item(tokens[0], Integer.parseInt(tokens[1])));
                }
            }
            inStream.close();
        } catch(FileNotFoundException fEx){
            System.out.println("Error: File not found");
        } catch(IOException ioEx){
            System.out.println("Error: Cannot read file contents properly.");
        }
    }

    //Return deep copy of the donations database
    public ArrayList<Item> getDonationDB(){
        ArrayList<Item> temp = new ArrayList<>();
        for(Item i : itemList){
            temp.add(new Item(i));
        }
        return temp;
    }

    private static boolean checkSum(int[] sumLeft, int k) {
        boolean r = true;
        for (int i = 0; i < k; i++) {
            if (sumLeft[i] != 0) {
                r = false;
            }
        }
        return r;
    }

    // Helper function for solving k partition problem.
    // It returns true if there exist k subsets with the given sum
    private boolean subsetSum(int n, int[] sumLeft, int[] A, int k)  {
        // return true if a subset is found
        if (checkSum(sumLeft, k)) { return true; }

        // base case: no items left
        if (n < 0) {
            return false;
        }

        boolean result = false;

        // consider current item S[n] and explore all possibilities using backtracking
        for (int i = 0; i < k; i++) {
            if (!result && (sumLeft[i] - itemList.get(n).getValue()) >= 0) {
                A[n] = i + 1; // mark the current element subset
                sumLeft[i] = sumLeft[i] - itemList.get(n).getValue(); // add the current item to the ith subset
                result = subsetSum(n - 1, sumLeft, A, k); // recur for remaining items

                sumLeft[i] = sumLeft[i] + itemList.get(n).getValue(); // backtrack: remove the current item from the ith subset
            }
        }

        return result; // return true if a solution is obtained
    }

    // Function for solving k partition problem. It prints the subsets if
    // set 'S[0 ... n-1] can be divided into k subsets with equal sum
    public ArrayList<ArrayList<Item>> allocations(int k) {
        int n = itemList.size(); // get the total number of items in 'S'

        // base case
        if (n < k) {
            System.out.println("k-partition of set S is not possible");
            return new ArrayList<ArrayList<Item>>();
        }

        // get the sum of all elements in the set
        int sum = sum(itemList);
        int[] A = new int[n];

        // create an array of size `k` for each subset and initialize it
        // by their expected sum, i.e., `sum/k`
        int[] sumLeft = new int[k];
        Arrays.fill(sumLeft, sum/k);

        // return true if the sum is divisible by k and set S can
        // be divided into k subsets with equal sum
        boolean result = (sum % k) == 0 && subsetSum(n - 1, sumLeft, A, k);

        if (!result) { return new ArrayList<ArrayList<Item>>(); }

        // Store all k-partitions in the ArrayList of ArrayLists
        ArrayList<ArrayList<Item>> kPartitions = new ArrayList<ArrayList<Item>>();
        for (int i = 0; i < k; i++) {
            ArrayList<Item> partition = new ArrayList<Item>();
            for (int j = 0; j < n; j++) {
                if (A[j] == i + 1) { partition.add(itemList.get(j)); }
            }
            Collections.sort(partition, new ItemSort());
            kPartitions.add(partition);
        }
        return kPartitions;
    }

    private int sum(ArrayList<Item> S){
        int result = 0;
        for (Item s : S){
            result += s.getValue();
        }
        return result;
    }
}
