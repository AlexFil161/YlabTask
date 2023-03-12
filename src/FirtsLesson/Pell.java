package FirtsLesson;

import java.util.Scanner;

public class Pell {
    public static void main(String[] args) throws Exception {
        try(Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите целое чило в диапазоне: 0<= n <= 30");
            int n = scanner.nextInt();
            while (n < 0 || n > 30) {
                System.out.println("Введите целое чило в диапазоне: 0<= n <= 30");
                n = scanner.nextInt();
            }
            if (n <= 1) {
                System.out.println(n);
            }
            int a = 0;
            int b = 1;
            for (int i = 2; i <= n; i++) {
                int c = 2 * b + a;
                a = b;
                b = c;
            }
            System.out.println(b);
        }
    }
}
