package com.yemyat.practice.coding;

import java.util.Arrays;
import java.util.Random;

class CustomItem implements Comparable {
    Integer data;
    CustomItem(Integer data) {
        this.data = data;
    }

    public int compareTo(Object o) {
        CustomItem incoming = (CustomItem) o;
        if (this.data.intValue() < incoming.data.intValue()) return -1;
        else if (this.data.intValue() > incoming.data.intValue()) return 1;
        else return 0;
    }
}

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
    static CustomItem[] getRandomCustomItemArray(int size) {
        Random randomGen = new Random();
        CustomItem[] data = new CustomItem[size];
        for (int i=0; i<data.length; i++) {
            data[i] = new CustomItem(randomGen.nextInt(100));
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

class QuickSort<K extends Comparable<K>> extends Sort<K> {
    void sort(K[] input) {
        sort(input, 0, input.length-1);
    }
    private int partition(K[] input, int start, int end) {
        int pivot=start, left=start+1, right=end;
        while (true) {
            while (left < right && input[right].compareTo(input[pivot]) > 0) {
                right--;
            }
            while (left < right && input[left].compareTo(input[pivot]) <= 0) {
                left++;
            }
            if (left == right) break;
            swap(input, left, right);
        }
        if (input[left].compareTo(input[pivot]) > 0) return pivot;
        swap(input, left, pivot);
        return left;
    }
    private void sort(K[] input, int start, int end) {
        if (start >= end) return;
        int pivotPoint = partition(input, start, end);
        sort(input, start, pivotPoint-1);
        sort(input, pivotPoint+1, end);
    }
}

class MergeSort<K extends Comparable<K>> extends Sort<K> {
    void sort(K[] input) {
        if (input.length < 1) return;
        K[] temp = Arrays.copyOf(input, input.length);
        for (int i=0; i<temp.length; i++) temp[i] = null;
        sort(input, temp, 0, input.length-1);
    }

    private void sort(K[] input, K[] temp, int start, int end) {
        if (start == end) return;
        int mid = start + ((end-start)/2);
        sort(input, temp, start, mid);
        sort(input, temp, mid+1, end);
        twoWayMerge(input, temp, start, mid, end);
    }
    private void twoWayMerge(K[] input, K[] temp, int start, int mid, int end) {
        for (int i=start; i<=end; i++) {
            temp[i] = input[i];
        }
        int i=start, j=mid+1, k=start;
        while (i <= mid && j <= end) {
            if (temp[i].compareTo(temp[j]) <= 0) {
                input[k] = temp[i];
                i++;
            } else {
                input[k] = temp[j];
                j++;
            }
            k++;
        }
        while (i <= mid) {
            input[k] = temp[i];
            k++; i++;
        }
        while (j <= end) {
            input[k] = temp[j];
            k++; j++;
        }
    }
}

public class Sorting {
    public static void main(String[] args) {
        Sort[] sortStrategies = new Sort[] {
                new SelectionSort(),
                new InsertionSort(),
                new QuickSort(),
                new MergeSort()
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

            // CustomItem type
            CustomItem[] dataCustom = Utility.getRandomCustomItemArray(10);
            CustomItem[] originalCustom = Arrays.copyOf(dataCustom, dataCustom.length);
            sorter.sort(dataCustom);
            assert Utility.verifySorted(originalCustom, dataCustom);


        }
    }
}
