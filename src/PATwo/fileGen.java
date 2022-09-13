package PATwo;

import java.io.IOException;
import java.io.PrintWriter;

public class fileGen {
    public static void main(String[] args) {
        int number = 50;
        try{
            PrintWriter out = new PrintWriter("item4CORR.txt");
            out.println(number);
            for (int i = 1; i <= number; i++){
                out.println("Item " + i + "," + number);
            }
            out.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
