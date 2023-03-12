package SecondLesson.StatsAccumulator;

public class StatsAccumulatorImpl implements StatsAccumulator {
    private int max = Integer.MIN_VALUE;
    private int min = Integer.MAX_VALUE;

    private int count = 0;

    private Double sum = 0.0;

    @Override
    public void add(int value) {
        if (value < min) {
            this.min = value;
        }
        if (value > max) {
            this.max = value;
        }

        count++;

        sum += value;
    }

    @Override
    public int getMin() {
        if (count == 0) {
            System.out.println("Добавьте хотя бы 1 число!");
        }
        return min;
    }

    @Override
    public int getMax() {
        if (count == 0) {
            System.out.println("Добавьте хотя бы 1 число!");
        }
        return max;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Double getAvg() {
        if (count == 0) {
            return 0.0;
        }
        return sum / count;
    }
}
