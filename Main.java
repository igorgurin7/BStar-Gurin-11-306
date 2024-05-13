package BStar;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Генерация массива случайных 10000 чисел
        int[] array = generateRandomArray(10000);

        // Создание B* дерева
        BStarTree tree = new BStarTree(3); // Здесь 3 - это минимальная степень дерева t

        // Замер времени и количества операций для каждой вставки числа в дерево
        long totalTimeInsert = 0;
        long totalOperationsInsert = 0;
        for (int i = 0; i < array.length; i++) {
            long startTime = System.nanoTime();
            tree.insert(array[i]);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            totalTimeInsert += duration;
            totalOperationsInsert++;
        }

        // Выбор случайных 100 элементов и поиск их в B* дереве с замером времени и количества операций
        long totalTimeSearch = 0;
        long totalOperationsSearch = 0;
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int randomIndex = random.nextInt(array.length);
            int keyToSearch = array[randomIndex];
            long startTime = System.nanoTime();
            boolean found = tree.search(keyToSearch);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            totalTimeSearch += duration;
            totalOperationsSearch++;
        }

        // Выбор случайных 1000 элементов и удаление их из B* дерева с замером времени и количества операций
        long totalTimeDelete = 0;
        long totalOperationsDelete = 0;
        for (int i = 0; i < 1000; i++) {
            int randomIndex = random.nextInt(array.length);
            int keyToDelete = array[randomIndex];
            long startTime = System.nanoTime();
            tree.delete(keyToDelete);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            totalTimeDelete += duration;
            totalOperationsDelete++;
        }

        // Расчёт среднего времени вставки, удаления и поиска данных, выраженного в количестве операций и времени работы
        double averageTimeInsert = (double) totalTimeInsert / totalOperationsInsert;
        System.out.println("Среднее время вставки: " + averageTimeInsert);

        double averageTimeSearch = (double) totalTimeSearch / totalOperationsSearch;
        System.out.println("Среднее время поиска: " + averageTimeSearch);

        double averageTimeDeletion = (double) totalTimeDelete / totalOperationsDelete;
        System.out.println("Среднее время удаления: " + averageTimeDeletion);
    }

    // Метод для генерации массива случайных чисел
    private static int[] generateRandomArray(int n) {
        int[] array = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }
}
