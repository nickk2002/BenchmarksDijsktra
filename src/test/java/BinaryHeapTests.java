import benchmarks.BinaryHeap;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BinaryHeapTests {

    static class Pair {
        public int cost;
        public int node;

        public Pair(int cost, int node) {
            this.cost = cost;
            this.node = node;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pair)) return false;
            Pair pair = (Pair) o;
            return cost == pair.cost && node == pair.node;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "cost=" + cost +
                    ", node=" + node +
                    '}';
        }

        @Override
        public int hashCode() {
            return Objects.hash(cost, node);
        }
    }

    @Test
    public void test() {
        Comparator<Pair> comp = Comparator.comparingInt(o -> o.cost);
        BinaryHeap<Pair> pq = new BinaryHeap<>(12, comp, (o -> o.node));
        pq.add(new Pair(1, 10));
        pq.add(new Pair(2, 11));
        pq.add(new Pair(10, 12));
        pq.add(new Pair(3, 12));

        assertEquals(pq.remove(), new Pair(1, 10));
        assertEquals(pq.remove(), new Pair(2, 11));
        assertEquals(pq.remove(), new Pair(3, 12));
        assertTrue(pq.isEmpty());

    }

    @Test
    public void test_2() {
        Comparator<Pair> comp = Comparator.comparingInt(o -> o.cost);
        BinaryHeap<Pair> pq = new BinaryHeap<>(100, comp, (o -> o.node));
        for (int i = 1; i <= 100; i++) {
            pq.add(new Pair(i + 5, i));

            pq.add(new Pair(i, i));
            pq.add(new Pair(i - 1, i));
            pq.add(new Pair(i + 2, i));

        }
        for (int i = 1; i <= 100; i++) {
            Pair p = pq.remove();
            assertEquals(new Pair(i + 2, i), p);
        }

    }

    @Test
    public void add() {
        assertEquals(1 + 1, 2);
    }
}
