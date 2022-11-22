package benchmarks;

import java.util.Comparator;
import java.util.function.Function;

public class BinaryHeap<T> {
    private Function<T, Integer> func = null;
    private final Object[] data;
    private int currentIndex = 0;
    private final Comparator<T> comparator;
    private int[] locationOfNode = null;

    public BinaryHeap(int size, Comparator<T> comp) {
        data = new Object[size + 1];
        comparator = comp;
    }

    public BinaryHeap(int size, Comparator<T> comp, Function<T, Integer> function) {
        data = new Object[size + 1];
        locationOfNode = new int[size + 1];
        comparator = comp;
        func = function;
    }

    private void upHeap(int heapNode, Object x) {
        // while node is smaller than parent
        while (heapNode != 1) {
            int parent = heapNode / 2;
            Object e = data[parent];
            if (comparator.compare((T) x, (T) e) >= 0)
                break;
            locationOfNode[func.apply((T) e)] = heapNode;
            data[heapNode] = data[parent];
            // swap node with parent
            heapNode = parent;
        }
        data[heapNode] = x;
        locationOfNode[func.apply((T) x)] = heapNode;
    }
    private void upHeapNormal(int heapNode, Object x) {
        // while node is smaller than parent
        while (heapNode != 1) {
            int parent = heapNode / 2;
            Object e = data[parent];
            if (comparator.compare((T) x, (T) e) >= 0)
                break;
            data[heapNode] = data[parent];
            // swap node with parent
            heapNode = parent;
        }
        data[heapNode] = x;
    }

    private void addNormal(T object) {
        ++currentIndex;
        upHeapNormal(currentIndex, object);
    }

    private void addWithReplacement(T object) {
        int nodePos = func.apply(object);
        if (locationOfNode[nodePos] > 0) {
            // up heap or down heap
            int heapNode = locationOfNode[nodePos];
            data[heapNode] = object;
            if (heapNode > 1 && smaller(heapNode, heapNode / 2)) {
                upHeap(heapNode, object);
            } else {
                // or do down heap
                minHeap(heapNode);
            }
        } else {
            ++currentIndex;
            int heapNode = currentIndex;
            // do up heap algorithm
            upHeap(heapNode, object);
        }
    }

    public void add(T object) {
        if (func == null) {
            addNormal(object);
        } else {
            addWithReplacement(object);
        }
    }

    private void minHeap(int node) {
        while (true) {
            int leftChild = node * 2;
            int smallest = node;
            int rightChild = node * 2 + 1;
            if (leftChild <= currentIndex && smaller(leftChild, smallest))
                smallest = leftChild;
            if (rightChild <= currentIndex && smaller(rightChild, smallest))
                smallest = rightChild;
            if (smallest != node) {
                swap(smallest, node);
                node = smallest;
            } else {
                return;
            }
        }
    }

    public boolean isEmpty() {
        return currentIndex == 0;
    }

    public T remove() {
        T initial = (T) data[1]; // initial first element
        if (currentIndex == 1) {
            currentIndex--;
            return initial;
        }
        // [1,currentIndex]
        swap(1, currentIndex);  // swap last element with first element
        // [1,currentIndex - 1]
        currentIndex--; // decrease number of elements
        minHeap(1);
        return initial;
    }


    boolean smaller(int node1, int node2) {
        return comparator.compare((T) data[node1], (T) data[node2]) < 0;
    }

    private void swap(int index1, int index2) {
        Object temp = data[index1];
        data[index1] = data[index2];
        data[index2] = temp;
        if (func != null) {
            locationOfNode[func.apply((T) data[index1])] = index1;
            locationOfNode[func.apply((T) data[index2])] = index2;
        }

    }
}
