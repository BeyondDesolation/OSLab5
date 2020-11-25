package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        SystemCore systemCore = new SystemCore();

        systemCore.createProcess(null);
        systemCore.createProcess(null);
        systemCore.createProcess(new char[]{'a', 'b', 'c', '1', '2'});
        systemCore.createProcess(null);
        systemCore.createProcess(null);

        long start = System.currentTimeMillis();
        systemCore.start();
        long finish = System.currentTimeMillis();
        System.out.println("Время работы в мс: " + (finish - start));

        // С текущими начальными условиями получаем следующие результаты (в среднем)
        // Программный ввод-вывод: 120 mc
        // Ввод-вывод, управляемый прерываниями: 90 ms
    }
}
