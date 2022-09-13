package PAThree;

public class KDTreeTester {
    public static void main(String[] args) {
        System.out.println("Testing creation of k-d Tree and the In-order Traversal:");
        KDTree kdtree = new KDTree("ATMLocations.csv");
        KDTree kdtree2 = new KDTree("ATMLocations2.csv");
        System.out.println("KDTree One");
        System.out.println(kdtree);
        System.out.println(kdtree.nodesList());
        System.out.println("KDTree Two");
        System.out.println(kdtree2);
        System.out.println(kdtree2.nodesList());

        System.out.println("------------------------------");
        System.out.println("Testing the nearest neighbour method:");
//        System.out.println("KDTree One: " + kdtree.nearestNeighbour(8, 7, kdtree.getRoot() , 0, kdtree.getRoot().getItem()));
//        System.out.println("KDTree Two: " + kdtree2.nearestNeighbour(8, 7, kdtree2.getRoot() , 0, kdtree2.getRoot().getItem()));
//        System.out.println("KDTree Two: " + kdtree2.nearestNeighbour(9, 3, kdtree2.getRoot() , 0, kdtree2.getRoot().getItem()));
//        System.out.println("KDTree Two: " + kdtree2.nearestNeighbour(5, 4, kdtree2.getRoot() , 0, kdtree2.getRoot().getItem()));
//        System.out.println("KDTree Two: " + kdtree2.nearestNeighbour(10, 10, kdtree2.getRoot() , 0, kdtree2.getRoot().getItem()));

        System.out.println("KDTree One: " + kdtree.nearestNeighbour(8, 7, kdtree.getRoot()));
        System.out.println("KDTree Two: " + kdtree2.nearestNeighbour(8, 7, kdtree2.getRoot()));
        System.out.println("KDTree Two: " + kdtree2.nearestNeighbour(9, 3, kdtree2.getRoot()));
        System.out.println("KDTree Two: " + kdtree2.nearestNeighbour(5, 4, kdtree2.getRoot()));
        System.out.println("KDTree Two: " + kdtree2.nearestNeighbour(10, 10, kdtree2.getRoot()));

        /*Answers for NN before deletion
        KDTree One: Block 781 S(522781) at (9.0,6.0)
        KDTree Two: Coffeeshop S(521432) at (7.0,6.0)
        KDTree Two: Near Minimart S(521203) at (7.0,2.0)
        KDTree Two: Near Police Post S(521451) at (5.0,4.0)
        KDTree Two: Near Playground S(520132) at (10.0,10.0)
         */

        System.out.println("------------------------------");
        System.out.println("Testing deletion and rebalance:");
        ATM atm = new ATM("Block 781", "522781", 9, 6);

        kdtree.deleteNode(atm);
        System.out.println(kdtree);

        kdtree2.deleteNode(atm);
        System.out.println(kdtree2);

        System.out.println("Testing the nearest neighbour method again:");
//        System.out.println("KDTree One: " + kdtree.nearestNeighbour(8, 7, kdtree.getRoot(), 0, kdtree.getRoot().getItem()));
//        System.out.println("KDTree Two: " + kdtree2.nearestNeighbour(8, 7, kdtree2.getRoot(), 0, kdtree2.getRoot().getItem()));
//        System.out.println("KDTree Two: " + kdtree2.nearestNeighbour(9, 3, kdtree2.getRoot(), 0, kdtree2.getRoot().getItem()));
//        System.out.println("KDTree Two: " + kdtree2.nearestNeighbour(5, 4, kdtree2.getRoot(), 0, kdtree2.getRoot().getItem()));
//        System.out.println("KDTree Two: " + kdtree2.nearestNeighbour(9, 6, kdtree2.getRoot() , 0, kdtree2.getRoot().getItem()));

        System.out.println("KDTree One: " + kdtree.nearestNeighbour(8, 7, kdtree.getRoot()));
        System.out.println("KDTree Two: " + kdtree2.nearestNeighbour(8, 7, kdtree2.getRoot()));
        System.out.println("KDTree Two: " + kdtree2.nearestNeighbour(9, 3, kdtree2.getRoot()));
        System.out.println("KDTree Two: " + kdtree2.nearestNeighbour(5, 4, kdtree2.getRoot()));
        System.out.println("KDTree Two: " + kdtree2.nearestNeighbour(9, 6, kdtree2.getRoot()));
        /*Answers for NN after deletion
        KDTree One: Near Supermarket S(522876) at (4.0,7.0)
        KDTree Two: Coffeeshop S(521432) at (7.0,6.0)
        KDTree Two: Near Minimart S(521203) at (7.0,2.0)
        KDTree Two: Near Police Post S(521451) at (5.0,4.0)
        KDTree Two: Coffeeshop S(521432) at (7.0,6.0)
         */
    }
}
