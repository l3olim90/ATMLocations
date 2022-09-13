package SortingTask;

import java.util.Arrays;

public class TestSort {
    public static void main(String[] args) {
        Integer[] numArr = {91, 7, 2, 38, 31, 76, 12, 15, 12, 3};
        System.gc();

        long startTime = System.nanoTime();

        Integer[] resultArr = genericSort(numArr);

        long elapsedTime = System.nanoTime() - startTime;
        System.out.println("Sorted integer array: " + Arrays.toString(resultArr));
        System.out.println("Time elapsed: " + elapsedTime + " nano seconds\n");
    }

    // State what sorting algorithm the below method implements as a comment
    public static <T extends Comparable<? super T>> T[] genericSort (T[] data) {
        // Implement your sorting algorithm below

        //Selection sort
        for (int i = 0; i < data.length-1; i++){
            int jMin = i;
            for (int j = i+1; j < data.length; j++){
                if (data[j].compareTo(data[jMin]) < 0)
                    jMin = j;
            }

            if (jMin != i){
                T temp = data[i];
                data[i] = data[jMin];
                data[jMin] = temp;
            }
        }

        return data;
    }

}
