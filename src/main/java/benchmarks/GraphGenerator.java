package benchmarks;

import java.util.*;


public class GraphGenerator {
    public static Set<Edge> generateGraph(int vertices, int edges, int maxCost, int start, int finish, int seed) {
        if (edges > vertices * (vertices - 1) / 2) {
            System.err.println("Too many edges (or too few nodes)!");
            System.err.println("With " + vertices + " nodes, at most " + (vertices * (vertices - 1) / 2) + " edges are possible.");
            return null;
        }
        if (start < 1 || start > vertices) {
            System.err.println("The starting vertex is invalid!");
            System.err.println("It must be between 1 and n (inclusive).");
            return null;
        }
        if (finish < 1 || finish > vertices) {
            System.err.println("The exit vertex is invalid!");
            System.err.println("It must be between 1 and n (inclusive).");
            return null;
        }
        Random random = new Random(seed);

        int[] edgeIndices = new int[vertices * (vertices - 1) / 2];
        for (int i = 0; i < edgeIndices.length; i++) {
            edgeIndices[i] = i;
        }
        shuffleArray(edgeIndices, random);

        Set<Edge> edgesSet = new HashSet<>();
        for (int i = 0; i < edges; i++) {
            int row = (int) Math.floor((1 + Math.sqrt(1 + 8 * edgeIndices[i])) / 2.); // Inverse of i = n*(n-1)/2
            int col = edgeIndices[i] - row * (row - 1) / 2;
            int from, to, cost;
            from = vertices - row;
            to = vertices - col;
            cost = random.nextInt(maxCost);
//            System.out.printf("%d %d %d\n", vertices - row, vertices - col, cost);
            edgesSet.add(new Edge(from, to, cost));
        }
        return edgesSet;
    }

    // Implementing Fisher–Yates shuffle
    static void shuffleArray(int[] arr, Random random) {
        for (int i = arr.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            // Simple swap
            int a = arr[index];
            arr[index] = arr[i];
            arr[i] = a;
        }
    }
}
