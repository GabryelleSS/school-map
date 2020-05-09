package estruturadedadosado2;

import java.util.Random;
import java.util.Scanner;

public class EstruturaDeDadosADO2 {
    public static final Random RANDOM = new Random();

    public static void main(String[] args) {
        char[] array = new char[3];
        
        requestCharacter(array);
        
        quicksort(array, 0, array.length - 1);
        
        System.out.println("Array ordenado");
        for(int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }
    
    private static void requestCharacter(char[] array) {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        
        while(i < array.length) {
            System.out.println("Informe um caracter:");
            array[i] = scanner.next().charAt(0);
            i++;
        }
    }

    private static void quicksort(char[] array, int start, int end) {
        if (end > start) {
            int index = partition(array, start, end);
            quicksort(array, start, index - 1);
            quicksort(array, index + 1, end);
        }
    }

    private static void swap(char[] array, int i, int j) {
        char tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private static int partition(char[] array, int start, int end) {
        int index = start + RANDOM.nextInt(end - start + 1);
        char pivot = array[index];

        swap(array, index, end);
        for (int i = index = start; i < end; ++i) {
            if (array[i] <= pivot) {
                swap(array, index++, i);
            }
        }

        swap(array, index, end);
        return (index);
    }
}
