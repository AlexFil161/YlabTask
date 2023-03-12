package FirtsLesson;

import java.sql.SQLOutput;
import java.util.Random;
import java.util.Scanner;

public class Guess {
    public static void main(String[] args) throws Exception {
        int number = new Random().nextInt(99) + 1;
        int maxAttempts = 10;
        System.out.println("Я загад число от 1 до 99. У тебя " + maxAttempts + " попыток угадать.");
        try (Scanner scanner = new Scanner(System.in)) {
            for (int count = 1; count <= maxAttempts; count++) {
                if (scanner.nextInt() > number) {
                    System.out.println("Моё число меньше! Осталось " + (maxAttempts - count) + " попыток.");
                } else if (scanner.nextInt() < number) {
                    System.out.println("Моё число больше! Осталось " + (maxAttempts - count) + " попыток.");
                } else if (scanner.nextInt() == number) {
                    System.out.println("Ты угадал с " + count + " попытки!");
                    System.exit(0);
                } else if (count == maxAttempts){
                    System.out.println("Ты не угадал.");
                }
            }
            System.out.println("Ты не угадал.");
        }
    }
}
