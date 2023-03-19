package SecondLesson.Sequences;

public class SequencesImpl implements SequenceGenerator{

    @Override
    public void firstSequence(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.print(i * 2 + ", ");
        }
        System.out.println();
    }

    @Override
    public void secondSequence(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.print((i * 2) - 1 + ", ");
        }
        System.out.println();
    }

    @Override
    public void thirdSequence(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.print(i * i + ", ");
        }
        System.out.println();
    }

    @Override
    public void fourthSequence(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.print((int)Math.pow(i, 3) + ", ");
        }
        System.out.println();
    }

    @Override
    public void fifthSequence(int n) {
        for (int i = 1; i <= n; i++) {
            if (i % 2 != 0) {
                System.out.print(1 + ", ");
            } else {
                System.out.print(-1 + ", ");
            }
        }
        System.out.println();
    }

    @Override
    public void sixthSequence(int n) {
        for (int i = 1; i <= n; i++) {
            if (i % 2 != 0) {
                System.out.print(i + ", ");
            } else {
                System.out.print(i * -1 + ", ");
            }
        }
        System.out.println();
    }

    @Override
    public void seventhSequence(int n) {
        for (int i = 1; i <= n; i++) {
            if (i % 2 != 0) {
                System.out.print(i * i + ", ");
            } else {
                System.out.print(i * -i + ", ");
            }
        }
        System.out.println();
    }

    @Override
    public void eighthSequence(int n) {
        int count = 1;
        for (int i = 1; i <= n; i++) {
            if (i % 2 != 0) {
                System.out.print(count + ", ");
                count++;
            } else {
                System.out.print(0 + ", ");
            }
        }
        System.out.println();
    }

    @Override
    public void ninthSequence(int n) {
        int result = 1;
        for (int i = 1; i <= n ; i++) {
            result = result * i;
            System.out.print(result + ", ");
        }
        System.out.println();
    }

    @Override
    public void tenthSequence(int n) {
        int[] array = new int[n];
        array[0] = 1;
        array[1] = 1;
        for (int i = 2; i < array.length ; i++) {
            array[i] = array[i - 1] + array[i - 2];
        }
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ",  ");
        }
    }
}