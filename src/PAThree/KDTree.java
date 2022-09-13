package PAThree;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class KDTree{
    // You are NOT allowed to add more attributes.
    private KDTreeNode<ATM> root; //The root node of the k-d Tree
    private int numNodes; //The number of ATM objects / nodes in the k-d Tree

    // Each KDTreeNode should refer to an element of the following array of ATM objects
    private ATM[] atmArr; //An array of ATM objects read from the file put in order of the file

    // Complete the code for the methods below. You are allowed any additional helper methods.

    // Constructor for the KDTree. See the KDTreeTester for how the constructor should work
    public KDTree(String filename){
        ArrayList<ATM> itemList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            numNodes = Integer.parseInt(br.readLine());//find N which is the number of items in the file

            for(int count = 0; count<numNodes; count++) {
                String line = br.readLine();
                String[] object = line.split(",");
                ATM temp = new ATM(object[0], object[1], Double.parseDouble(object[2]), Double.parseDouble(object[3]));
                itemList.add(temp);
            }
            atmArr = new ATM[numNodes];
            atmArr = itemList.toArray(atmArr);
            root = KDTreeConstructor(atmArr, 0);
        }
        catch (IOException e){
            System.out.println(e);//print exception
        }
    }

    private KDTreeNode<ATM> KDTreeConstructor(ATM[] arr, int depth){
        if(arr.length < 1 || arr == null){
            return null;
        }

        //median index of arr
        int median = arr.length / 2;

        //sort array on axis depending on depth
        if(depth % 2 == 0) Arrays.sort(arr, Comparator.comparingDouble(ATM::getX));
        else Arrays.sort(arr, Comparator.comparingDouble(ATM::getY));

        //define node and the left and right children recursively
        KDTreeNode<ATM> Node = new KDTreeNode<>(arr[median]);
        Node.setLeft(KDTreeConstructor(Arrays.copyOfRange(arr, 0, median), depth+1));
        Node.setRight(KDTreeConstructor(Arrays.copyOfRange(arr, median+1, arr.length), depth+1));

        return Node;
    }

    // Method to perform node insertion. You may define your own parameters in this method
    // The insertNode method is to be utilised in the constructor
    public void insertNode(ATM atmadd){
        atmArr = Arrays.copyOf(atmArr, numNodes+1);
        atmArr[numNodes++] = atmadd;
        rebalance();
    }

    // Method to perform node deletion.
    // DO NOT CHANGE THE METHOD HEADER AND PARAMETERS
    public ATM deleteNode(ATM atmdel){
        ArrayList<ATM> list = new ArrayList<>(Arrays.asList(atmArr));
        list.remove(atmdel);
        numNodes-=1;
        atmArr = new ATM[numNodes];
        atmArr = list.toArray(atmArr);
        rebalance();
        return atmdel;
    }

    // Method does a NON-RECURSIVE in-order traversal of the k-d Tree
    // DO NOT CHANGE THE METHOD HEADER AND PARAMETERS
    public ArrayList<ATM> nodesList(){
        if (root == null) return new ArrayList<>();

        ArrayList<ATM> nodes = new ArrayList();

        Stack<KDTreeNode> s = new Stack<>();
        KDTreeNode<ATM> curr = root;

        // traverse the tree
        while (curr != null || s.size() > 0) {
            /* Reach the left most Node of the
            curr Node */
            while (curr !=  null) {
                /* place pointer to a tree node on
                   the stack before traversing
                  the node's left subtree */
                s.push(curr);
                curr = curr.getLeft();
            }

            //Current is NULL
            curr = s.pop();

            nodes.add(curr.getItem());

            // visit right subtree
            curr = curr.getRight();
        }
        return nodes;
    }

    // Method to perform the nearest neighbour search in the k-d Tree.
    // You may add on your own parameters in this method. Ensure you do the same for the tester if so
    public ATM nearestNeighbour(double x, double y, KDTreeNode<ATM> curr){
        return NNRecursion(x, y, curr, 0, curr).getItem();
    }

    private KDTreeNode<ATM> NNRecursion(double x, double y, KDTreeNode<ATM> curr, int depth, KDTreeNode<ATM> best){
        if(curr.getRight() == null && curr.getLeft() == null){
            double Distance = Math.sqrt(Math.pow(x - curr.getItem().getX(),2) + Math.pow(y - curr.getItem().getY(),2));
            double BestDist = Math.sqrt(Math.pow(x - best.getItem().getX(),2) + Math.pow(y - best.getItem().getY(),2));
            if(Distance < BestDist)
                best = curr;
            else if(Distance == BestDist)
                if(best.getItem().getX() > curr.getItem().getX() || best.getItem().getY() > curr.getItem().getY())
                    best = curr;
            return best;
        }
        else if(curr.getRight() == null){
            best = NNRecursion(x, y, curr.getLeft(), depth+1, best);
        }
        else if(curr.getLeft() == null){
            best = NNRecursion(x, y, curr.getRight(), depth+1, best);
        }

        else if(depth % 2 == 0){
            if(x>curr.getItem().getX()){
                best = NNRecursion(x, y, curr.getRight(), depth+1, best);
            }
            else{
                best = NNRecursion(x, y, curr.getLeft(), depth+1, best);
            }
        }
        else{
            if(y>curr.getItem().getY()){
                best = NNRecursion(x, y, curr.getRight(), depth+1, best);
            }
            else{
                best = NNRecursion(x, y, curr.getLeft(), depth+1, best);
            }
        }

        double Distance = Math.sqrt(Math.pow(x - curr.getItem().getX(),2) + Math.pow(y - curr.getItem().getY(),2));
        double BestDist = Math.sqrt(Math.pow(x - best.getItem().getX(),2) + Math.pow(y - best.getItem().getY(),2));
        if(Distance < BestDist)
            best = curr;
        else if(Distance == BestDist)
            if(best.getItem().getX() > curr.getItem().getX() || best.getItem().getY() > curr.getItem().getY())
                best = curr;

        if(depth % 2 == 0){
            if(Math.abs(x-curr.getItem().getX()) < BestDist){
                if(x <= curr.getItem().getX() && curr.getRight() != null)
                    best = NNRecursion(x, y, curr.getRight(), depth+1, best);
                else if(x > curr.getItem().getX() && curr.getLeft() != null)
                    best = NNRecursion(x, y, curr.getLeft(), depth+1, best);
            }
        }
        else if(Math.abs(y-curr.getItem().getY()) < BestDist) {
            if(y <= curr.getItem().getY() && curr.getRight() != null)
                best = NNRecursion(x, y, curr.getRight(), depth+1, best);
            else if(y > curr.getItem().getY() && curr.getLeft() != null)
                best = NNRecursion(x, y, curr.getLeft(), depth+1, best);
        }

        return best;
    }

    // Method to rebalance the k-d Tree. You may implement this method in the insertNode and deleteNode methods
    public void rebalance(){
        root = KDTreeConstructor(atmArr, 0);
    }

    //DO NOT EDIT! This method returns the root node of the tree
    public KDTreeNode<ATM> getRoot() { return root; }

    //DO NOT EDIT! This method returns the array of ATM objects
    public ATM[] getAtmArr() { return atmArr; }

    //DO NOT EDIT! This method returns the index
    //public int findATM() { return atmArr; }

    //DO NOT EDIT! This method returns a visualisation of k-d tree structure
    //Note that depending on your data, it may not show all levels. If that is the case, you can edit the number
    //levels it will show. BUT, note that Coursemology will reference the original toString() method in the template.
    @Override
    public String toString(){
        if (root == null) return "No tree present";

        int levels = (int) Math.ceil(Math.log(numNodes)/Math.log(2));
        String kdTreeStr = "";
        ArrayList<ArrayList<KDTreeNode<ATM>>> nodesStore = new ArrayList<>();
        ArrayList<KDTreeNode<ATM>> levelNodes = new ArrayList<>();

        for (int k = levels ; k > 1; k--) kdTreeStr += "      ";
        kdTreeStr += "(" + root.getItem().getX() + "," + root.getItem().getY() + ")\n";
        levelNodes.add(root);
        nodesStore.add(levelNodes);

        for (int i = 1; i < levels; i++){
            levelNodes = new ArrayList<>();
            for (int k = levels - i ; k > 1; k--) kdTreeStr += "      ";
            for (KDTreeNode<ATM> n : nodesStore.get(i-1)){
                if (n == null) {
                    levelNodes.add(null);
                    kdTreeStr += "None   ";
                }
                else {
                    levelNodes.add(n.getLeft());
                    if (n.getLeft() == null) kdTreeStr += "None   ";
                    else kdTreeStr += "(" + n.getLeft().getItem().getX() + "," + n.getLeft().getItem().getY() + ")  ";
                    levelNodes.add(n.getRight());
                    if (n.getRight() == null) kdTreeStr += "None   ";
                    else kdTreeStr += "(" + n.getRight().getItem().getX() + "," + n.getRight().getItem().getY() + ")  ";
                }
            }
            nodesStore.add(levelNodes);
            kdTreeStr += "\n";
        }
        return kdTreeStr;
    }
}