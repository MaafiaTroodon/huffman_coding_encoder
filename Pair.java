public class Pair implements Comparable<Pair> {
    private final char value;
    private final double prob;

    public Pair(char value, double prob) {
        this.value = value;
        this.prob = prob;
    }

    public char getValue() {
        return value;
    }

    public double getProb() {
        return prob;
    }

    @Override
    public int compareTo(Pair other) {
        return Double.compare(this.prob, other.prob);
    }

    @Override
    public String toString() {
        return value + ": " + prob;
    }
}
