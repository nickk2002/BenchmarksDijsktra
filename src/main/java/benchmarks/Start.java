package benchmarks;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(batchSize = 1,iterations = 1)
public class Start {

    Set<Edge> edges;
    int n = 5000;
    int m = n * (n - 1) / 2;
    int s = 1;
    int t = n;
    HashMap<Integer, List<Pair>> graph = new HashMap<>();

    @Setup
    public void createSpecificGraph() {
        edges = new HashSet<>();
        Random random = new Random(42);
        for (int i = 1; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                int val = random.nextInt();
                if (j == i + 1)
                    edges.add(new Edge(i, j, val));
                else
                    edges.add(new Edge(i, j, random.nextInt() + 1 + val));
            }
        }
        for (int i = 1; i <= n; i++)
            graph.put(i, new ArrayList<>());
        for (Edge edge : edges) {
            List<Pair> nodes = graph.get(edge.from);
            nodes.add(new Pair(edge.weight, edge.to));
            graph.put(edge.from, nodes);
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Heyy");
//        new Start().runTestCase();
        Main.main(args);
    }


    @Benchmark
    public int correctSol() {
        if (s == t)
            return 0;

        int[] dist = new int[n + 1];
        for (int i = 1; i <= n; i++)
            dist[i] = (int) 2e9;
        dist[s] = 0;
        Comparator<Pair> comp = Comparator.comparingInt(o -> o.cost);
        Queue<Pair> pq = new PriorityQueue<>(m, comp); // priority queue from java
        pq.add(new Pair(graph.get(s).size(), s));
        while (!pq.isEmpty()) {
            Pair el = pq.remove();
            if (el.node == t) {
                return el.cost - graph.get(el.node).size();
            }
            for (Pair edge : graph.get(el.node)) {
                int computedCost = el.cost + edge.cost + graph.get(edge.node).size();
                if (computedCost < dist[edge.node]) {
                    dist[edge.node] = computedCost;
                    pq.add(new Pair(computedCost, edge.node));
                }
            }
        }
        return -1;
    }

    @Benchmark
    public int BinaryHeapSol1() {
        if (s == t)
            return 0;
        // TODO
        Comparator<Pair> comp = Comparator.comparingInt(o -> o.cost);
        BinaryHeap<Pair> pq = new BinaryHeap<>(m, comp); // normal binary heap

        int[] dist = new int[n + 1];
        for (int i = 1; i <= n; i++)
            dist[i] = (int) 2e9;
        dist[s] = 0;
        pq.add(new Pair(graph.get(s).size(), s));
        while (!pq.isEmpty()) {
            Pair el = pq.remove();
            if (el.node == t) {
                return el.cost - graph.get(el.node).size();
            }
            for (Pair edge : graph.get(el.node)) {
                int computedCost = el.cost + edge.cost + graph.get(edge.node).size();
                if (computedCost < dist[edge.node]) {
                    dist[edge.node] = computedCost;
                    pq.add(new Pair(computedCost, edge.node));
                }
            }
        }
        return -1;
    }

    @Benchmark
    public int BinaryHeapSol2() {
        if (s == t)
            return 0;
        // TODO
        Comparator<Pair> comp = Comparator.comparingInt(o -> o.cost);
        BinaryHeap<Pair> pq = new BinaryHeap<>(n, comp, (o -> o.node)); // binary heap with replacement

        boolean[] vis = new boolean[n + 1];
        int[] dist = new int[n + 1];
        for (int i = 1; i <= n; i++)
            dist[i] = (int) 2e9;
        dist[s] = 0;
        pq.add(new Pair(graph.get(s).size(), s));
        while (!pq.isEmpty()) {
            Pair el = pq.remove();
            if (el.node == t) {
                return el.cost - graph.get(el.node).size();
            }
            if (vis[el.node])
                continue;
            vis[el.node] = true;
            for (Pair edge : graph.get(el.node)) {
                int computedCost = el.cost + edge.cost + graph.get(edge.node).size();
                if (!vis[edge.node] && computedCost < dist[edge.node]) {
                    dist[edge.node] = computedCost;
                    pq.add(new Pair(computedCost, edge.node));
                }
            }
        }
        return -1;
    }

    public static class Pair {
        public int cost;
        public int node;

        public Pair(int cost, int node) {
            this.cost = cost;
            this.node = node;
        }
    }


}
