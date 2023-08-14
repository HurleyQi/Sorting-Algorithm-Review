package SortReview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class SortReview {

    // final variables
    private static final int ARRAY_LENGTH = 15;
    private static final int NUMSORTS = 5;
    private static final int SELECTION = 1;
    private static final int INSERTION = 2; 
    private static final int RADIX = 3;
    private static final int QUICK = 4;
    private static final int RANDOM_MAX = 150;
    private static final int BEGINNING = 2;
    private static final int CUTOFF = 3;
    private static final int CUTOFF_CHOICE = 4;
    private static final int RANGE = 11;


    /**
     * Main method that print out one of the mid points
     * of the sorted array and interact with the user
     * @param args
     */
    public static void main(String[] args) {
        // create scanner to interact with user
        Scanner sc = new Scanner(System.in);
        System.out.println("This is a review for the sorting questions for CS 314 Exam 2." + 
        "");
        boolean run = true;
        while (run) {
            // create and get an array of random ints
            int[] data = new int[ARRAY_LENGTH];
            getRandomArray(data);
            System.out.println("Random: " + Arrays.toString(data));
            // randomly select the sort to be used
            int randomNum = 1 + (int) (Math.random() * NUMSORTS);
            String result = getSort(data, randomNum);
            System.out.println("Please identify: " + result);
            int userAnswer = getAnswer(sc);
            // compare client answer
            if (userAnswer == randomNum) {
                System.out.println("Correct");
            } else {
                System.out.println("Wrong");
                System.out.println("Answer: " + randomNum);
            }
            // ask if client wants to continue
            System.out.print("Would you like to continue. Enter Y or y: ");
            String input = sc.nextLine();
            if (!input.equals("Y") && !input.equals("y")) {
                run = false;
            }
        }

    }


    /**
     * Private method that gets the answer from the user
     * @param sc
     * @return  int from 1 - 5 representing the sort the user selected as answer
     */
    private static int getAnswer(Scanner sc) {
        System.out.println("1 for Selection Sort \n2 for Insertion Sort \n" + 
        "3 for Radix Sort \n4 for Quick Sort \n5 for Merge Sort");
        System.out.print("Please input: ");
        int userAnswer = sc.nextInt();
        sc.nextLine();
        return userAnswer;

    }

    /**
     * Private method that uses the sorting method specified and result a string representation 
     * of the mid point of the sorting method 
     * @param data
     * @param determinateNum
     * @return
     */
    private static String getSort(int[] data, int determinateNum) {
        int[] oldData = data.clone();
        String result = "";
        // if it is selection 
        if (determinateNum == SELECTION) {
            while (result.isEmpty()) {
                result = selectionSort(data);
                data = oldData;
            }
        // if it is insertion
        } else if (determinateNum == INSERTION) {
            while (result.isEmpty()) {
                result = insertionSort(data);
                data = oldData;
            }
        // if it is radix
        } else if (determinateNum == RADIX) {
            while (result.isEmpty()) {
                result = radixSort(data);
                data = oldData;
            }
        // if it is quick sort
        } else if (determinateNum == QUICK) {
            while (result.isEmpty()) {
                result = quickSort(oldData, 0, data.length - 1, "");
                data = oldData;
            }
        // remaining option, merge sort
        } else {
            while (result.isEmpty()) {
                result = mergeSort(data);
                data = oldData;
            }
        }
        return result;
    }


    /**
     * Private method that generates an array in ints in random order
     * @param data
     */
    private static void getRandomArray(int[] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] =  (int) (Math.random() * RANDOM_MAX);
        }
    }


    /**
     * Private method that generates a string representing the array 
     * as it is sorted by selectionSort
     * @param data
     * @return
     */
    private static String selectionSort(int[] data) {
        // counter used for trying to decide when to return a mid point 
        int counter = 0;
        String result = "";
        boolean obtained = false;
        // iterate throught the array to sort
        for (int i = 0; i < data.length; i++) {
            int minIndex = i;
            // innner interation to find minimum in the decreasing range
            for (int j = i + 1; j < data.length; j++) {
                if (data[j] < data[minIndex]) {
                    minIndex = j;
                }
            }
            // swap 
            int temp = data[i];
            data[i] = data[minIndex];
            data[minIndex] = temp;
            counter++;
            // decide whether it is the midpoint selected for return 
            if (counter > BEGINNING && !obtained && counter < RANGE) {
                int determinateNum = (int) (Math.random() * CUTOFF_CHOICE);
                if (determinateNum == CUTOFF) {
                    obtained = true;
                    result = Arrays.toString(data);
                }
            }
        }
        return result;
    }


    /**
     * Private method that generates a string representing the array 
     * as it is sorted by insertionSort
     * @param data
     * @return
     */
    private static String insertionSort(int[] data) {
        // used for deciding when to return
        boolean obtained = false;
        int counter = 0; 
        String result = "";
        // iterate through the array for sorting 
        for (int i = 0; i < data.length; i++) {
            counter++;
            int temp = data[i];
            int swapIndex = i;
            // swap to the left until out of bound or left value is less than curr
            while (swapIndex > 0 && temp < data[swapIndex - 1]) {
                data[swapIndex] = data[swapIndex - 1];
                data[swapIndex - 1] = temp;
                swapIndex--;
            }
            // decide whether to return or not 
            if (counter > BEGINNING && !obtained) {
                int determinateNum = (int) (Math.random() * CUTOFF_CHOICE);
                if (determinateNum == CUTOFF) {
                    obtained = true; 
                    result = Arrays.toString(data);
                }
            }
        }
        return result;
    }


    /**
     * Private method that generates a string representing the array 
     * as it is sorted by radixSort
     * @param data
     */
    private static String radixSort(int[] data) {
        // number of queues used for sorting
        final int numQ = 10;
        String result = "";
        // used to decide if returned value is obtained 
        boolean obtained = false;
        ArrayList<Queue<Integer>> queues = new ArrayList<>();
        for (int i = 0; i < numQ; i++) {
            queues.add(new LinkedList<Integer>());
        }
        // get max number of digits in the number to be 
        // used for the iterations neccessary in order to sort 
        int maxDig = maxNumDigit(data);
        for (int i = 1; i <= maxDig; i++) {
            // add to queue
            for (int j = 0; j < data.length; j++) {
                queues.get(getDigAtPlace(data[j], i)).add(data[j]);
            }
            int index = 0;
            // remove from queues for next iteration 
            for (Queue<Integer> q : queues) {
                while (!q.isEmpty()) {
                    data[index++] = q.remove();
                }
            }
            // determine if it is the returned midpoint 
            int determinateNum = (int) (Math.random() * (CUTOFF_CHOICE - 2));
            if (determinateNum == (CUTOFF - 2) && !obtained && i != maxDig) {
                result = Arrays.toString(data);
                obtained = true;
            }
        }
        return result;
    }

    /**
     * Private method that get the max digit place of a number
     * @param data
     * @return
     */
    private static int maxNumDigit(int[] data) {
        int result = 0; 
        for (int i = 0; i < data.length; i++) {
            int curr = (int) Math.log10(data[i]) + 1;
            result = Math.max(result, curr);
        }
        return result;
    }


    /**
     * Private method that gets the digit at the specified place of a number
     * @param digit
     * @param place
     * @return
     */
    private static int getDigAtPlace(int digit, int place) {
        final int digitPlace = 10;
        int result = 0;
        for (int i = 0; i < place; i++) {
            result = digit % digitPlace;
            digit /= digitPlace;
        }
        return result;
    }


    /**
     * Private method that generates a string representing the array 
     * as it is sorted by quickSort
     * @param data
     * @param start
     * @param stop
     * @return
     */
    private static String quickSort(int[] data, int start, int stop, String result) {
        if (start < stop) {
            // pivot mid point value 
            int pivotInd = (start + stop) / 2;
            swap(data, pivotInd, start);
            int pivot = data[start];
            int endOfLess = start;
            for (int i = start + 1; i <= stop; i++) {
                if (data[i] < pivot) {
                endOfLess++;
                swap(data, i, endOfLess);
                }
            }
            // determine whenre if it is the resulting mid point 
            if ((int) (Math.random() * (CUTOFF_CHOICE + 2)) == CUTOFF && result.isEmpty()) {
                result = Arrays.toString(data);
            }
            swap(data, start, endOfLess);
            result = quickSort(data, start, endOfLess, result);
            result = quickSort(data, endOfLess + 1, stop, result);
        }
        return result;
    }

    /**
     * Private method that swap two elements in the array as specified 
     * @param data
     * @param index1
     * @param index2
     */
    private static void swap(int[] data, int index1, int index2) {
        int temp = data[index1];
        data[index1] = data[index2];
        data[index2] = temp;
    }

    /**
     * private method that completes the merge sort 
     * @param data
     * @return string representing one of the midpoints of mergeSort
     */
    private static String mergeSort(int[] data) {
        String result = "";
        int[] temp = new int[data.length];
        result = sort(data, temp, 0, data.length - 1, "");
        return result;
    }

    /**
     * private method that sorts; used for merge sort
     * @param data
     * @param temp
     * @param low
     * @param high
     * @param result
     * @return returns one of the midpoints of the sorting process
     */
    private static String sort(int[] data, int[] temp, int low, int high, String result) {
        // break down the array and then rebuikd it to achieve sorting 
        if (low < high) {
            int center = (low + high) / 2;
            result = sort(data, temp, low, center, result);
            result = sort(data, temp, center + 1, high, result);
            result = merge(data, temp, low, center + 1, high, result);
        }
        return result;
    }

    /**
     * private method the merges according to the merge sort algorithm
     * @param data
     * @param temp
     * @param leftPos
     * @param rightPos
     * @param rightEnd
     * @param result
     * @return String representation of one of the midpoints of the sorting process
     */
    private static String merge(int[] data, int[] temp, int leftPos, 
                            int rightPos, int rightEnd, String result) {
        int leftEnd = rightPos - 1;
        int tempPos = leftPos;
        int numElements = rightEnd - leftPos + 1;
        while (leftPos <= leftEnd && rightPos <= rightEnd) {
            if (data[leftPos] <= data[rightPos]) {
                temp[tempPos] = data[leftPos];
                leftPos++;
            } else {
                temp[tempPos] = data[rightPos];
                rightPos++;
            }
            tempPos++;
        }
        while (leftPos <= leftEnd) {
            temp[tempPos] = data[leftPos];
            tempPos++;
            leftPos++;
        }
        while (rightPos <= rightEnd) {
            temp[tempPos] = data[rightPos];
            tempPos++;
            rightPos++;
        }
        for (int i = 0; i < numElements; i++, rightEnd--) {
            data[rightEnd] = temp[rightEnd];
        }
        if ((int) (Math.random() * 3) == 1 && result.isEmpty()) {
            result = Arrays.toString(data);
        }
        return result;
    }
}