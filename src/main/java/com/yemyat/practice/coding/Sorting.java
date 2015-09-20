package com.yemyat.practice.coding;

import java.util.Arrays;
import java.util.Random;

class Utility {
    static Integer[] getRandomIntegerArray(int size) {
        Random randomGen = new Random();
        Integer[] data = new Integer[size];
        for (int i=0; i<data.length; i++) {
            data[i] = randomGen.nextInt(100);
        }
        return data;
    }
    static Character[] getRandomCharacterArray(int size) {
        Random randomGen = new Random();
        Character[] data = new Character[size];
        for (int i=0; i<data.length; i++) {
            data[i] = (char) (randomGen.nextInt(123 - 97) + 97);
        }
        return data;
    }
    static <K> void printArray(K[] input) {
        for (int i=0; i<input.length; i++) {
            System.out.print(input[i] + ((i == input.length-1)? "\n": " "));
        }
    }
    static <K> boolean verifySorted(K[] original, K[] sorted) {
        Arrays.sort(original);
        if (original.length != sorted.length) return false;
        for (int i=0; i<original.length; i++) {
            if (!original[i].equals(sorted[i])) return false;
        }
        return true;
    }
}

abstract class Sort<K  extends Comparable<K>> {
    void swap(K[] input, int i, int j) {
        if (!input[i].equals(input[j])) {
            K temp = input[i];
            input[i] = input[j];
            input[j] = temp;
        }
    }
    abstract void sort(K[] input);
}

class SelectionSort<K extends Comparable<K>> extends Sort<K> {
    void sort(K[] input) {
        for (int i=0; i<(input.length-1); i++) {
            int nextMinIndex = i;
            for (int j=i+1; j<input.length; j++) {
                if (input[j].compareTo(input[nextMinIndex]) < 0) nextMinIndex = j;
            }
            swap(input, i, nextMinIndex);
        }
    }
}

class InsertionSort<K extends Comparable<K>> extends Sort<K> {
    void shiftAndInsert(K[] input, int target, int position) {
        K targetItem = input[target];
        for (int i=target; i>position; i--) {
            input[i] = input[i-1];
        }
        input[position] = targetItem;
    }
    @Override
    void sort(K[] input) {
        for (int i=1; i<input.length; i++) {
            for (int j=0; j<i; j++) {
                if (input[i].compareTo(input[j]) < 0) {
                    shiftAndInsert(input, i, j);
                }
            }
        }
    }
}

public class Sorting {
    public static void main(String[] args) {
        Sort[] sortStrategies = new Sort[] {
                new SelectionSort(),
                new InsertionSort()
        };

        for (Sort sorter: sortStrategies) {

            // Integer type
            Integer[] dataIntegers = Utility.getRandomIntegerArray(10);
            Integer[] originalIntegers = Arrays.copyOf(dataIntegers, dataIntegers.length);
            sorter.sort(dataIntegers);
            assert Utility.verifySorted(originalIntegers, dataIntegers);

            // Character type
            Character[] dataChars = Utility.getRandomCharacterArray(10);
            Character[] originalChars = Arrays.copyOf(dataChars, dataChars.length);
            sorter.sort(dataChars);
            assert Utility.verifySorted(originalChars, dataChars);

        }
    }
}
