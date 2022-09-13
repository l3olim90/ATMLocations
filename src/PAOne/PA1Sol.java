package PAOne;

import java.util.Scanner;
import java.util.Stack;

public class PA1Sol {
    public static void main(String[] args) {
        double[] arr = {3.1, 5.2, 10.4, 5.2, 3.1};
        System.out.println(getLargestArea(arr, 2, arr.length));
        double[] arr1 = {6, 2, 5, 4, 5, 1, 6};
        System.out.println(getLargestArea(arr1, 3, arr1.length));
        double[] arr2 = {6, 2, 5, 4, 5, 1, 6, 4, 6 ,7, 8, 2, 10, 18, 19, 6, 7, 19, 10, 18, 17, 16, 15, 19, 18, 17, 5, 4, 60, 24, 9, 3, 2,10, 3, 2, 17};
        System.out.println(getLargestArea(arr2, 1, arr2.length));
        double[] arr3 = {100};
        System.out.println(getLargestArea(arr3, 50, arr3.length));
        double[] arr4 = {};
        System.out.println(getLargestArea(arr4, 1, arr4.length));
        double[] arr5 = new double[60];
        for (int i = 0; i < 30; i++){
            arr5[2*i] = 30 - i;
            arr5[2*i + 1] = 30 - i;
        }
        System.out.println(getLargestArea(arr5, 2, arr5.length));

        /*
        //Scanner solution:
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        double width = in.nextDouble();
        double[] arr_in = new double[n];
        for (int i = 0; i < n; i++){
            arr_in[i] = in.nextDouble();
        }
        System.out.println(getLargestArea(arr_in, width, arr_in.length));
         */
    }

    /*
    How algorithm works for {3.1, 5.2, 10.4, 5.2, 3.1}
    Index = 0: Push index 0 into stack
    Index = 1: Push index 1 into stack as 3.1 <= 5.2
    Index = 2: Push index 2 into stack as 5.2 <= 10.4
    Index = 3: 10.4 > 5.2, hence,
        Pop index 2 from stack (Now stack only has 0, 1)
        Area = 10.4 * (3 - 1 - 1)(2) = 20.8 (Assume this is the largest possible)
    Index = 3: Push index 3 into stack as 5.2 <= 5.2 (Now stack has 0, 1, 3)
    Index = 4: 5.2 > 3.1, hence,
        Pop index 3 from stack (Now stack only has 0, 1)
        Area = 5.2 * (4 - 1 - 1)(2) = 20.8 == 20.8 -> Not largest
    Index = 4: 5.2 > 3.1, hence,
        Pop index 1 from stack (Now stack only has 0)
        Area = 5.2 * (4 - 0 - 1)(2) = 31.2 (Assume this is the largest possible)
    Index = 4: Push index 4 into stack as 3.1 <= 3.1 (Now stack has 0, 4)

    First While Loop done, Index = 5

    Pop 4 from stack, Area = 3.1*(5 - 0 - 1) = 12.4 < 31.2 -> Not largest
    Pop 0 from stack, Area = 3.1*5 = 15.3 < 31.2 -> Not largest
     */
    public static double getLargestArea(double[] arr, double width, int size){
        //Stack is to insert the indexes of array to keep track of index of heights to use for maximal area
        Stack<Integer> stack = new Stack<>();
        double maxArea = 0; int stackTop; double areaWTop;

        int index = 0;
        while (index < size){
            // If there is nothing in the stack or a larger array value is observed in the array, push index into stack
            if (stack.empty() || arr[stack.peek()] <= arr[index])
                stack.push(index++);
            // This only runs if arr[stackTop] > arr[index]
            else{
                stackTop = stack.peek();
                stack.pop();

                //If the stack is empty after pop, this indicates that the max area involves only one rect / building
                if (stack.isEmpty())
                    areaWTop = arr[stackTop] * index * width;
                //If the stack is NOT empty after pop, this indicates that there could be other "subset rect" to consider
                else
                    areaWTop = arr[stackTop] * (index - stack.peek() - 1) * width;

                if (maxArea < areaWTop) maxArea = areaWTop;
            }
        }

        //This will run if there are remaining indexes (rectangles) within the stack
        while (!stack.isEmpty()){
            stackTop = stack.peek();
            stack.pop();
            //Apply the same check as the initial while loop
            //This loop and check is to see if a collection of rectangles with smaller height can actually exceed the max
            if (stack.isEmpty())
                areaWTop = arr[stackTop] * index * width;
            else
                areaWTop = arr[stackTop] * (index - stack.peek() - 1) * width;

            if (maxArea < areaWTop) maxArea = areaWTop;
        }
        return maxArea;
    }
}
