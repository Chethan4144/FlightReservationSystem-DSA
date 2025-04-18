package flightreservation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PriorityQueue<T> {
    private List<T> heap;
    private Comparator<T> comparator;
    
    public PriorityQueue(Comparator<T> comparator) {
        this.heap = new ArrayList<>();
        this.comparator = comparator;
    }
    
    public void add(T element) {
        heap.add(element);
        siftUp(heap.size() - 1);
    }
    
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        
        T result = heap.get(0);
        T lastElement = heap.remove(heap.size() - 1);
        
        if (!heap.isEmpty()) {
            heap.set(0, lastElement);
            siftDown(0);
        }
        
        return result;
    }
    
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return heap.get(0);
    }
    
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    
    public int size() {
        return heap.size();
    }
    
    private void siftUp(int index) {
        T element = heap.get(index);
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            T parent = heap.get(parentIndex);
            
            if (comparator.compare(element, parent) >= 0) {
                break;
            }
            
            heap.set(index, parent);
            index = parentIndex;
        }
        heap.set(index, element);
    }
    
    private void siftDown(int index) {
        int size = heap.size();
        T element = heap.get(index);
        
        while (2 * index + 1 < size) {
            int childIndex = 2 * index + 1;
            if (childIndex + 1 < size && 
                comparator.compare(heap.get(childIndex + 1), heap.get(childIndex)) < 0) {
                childIndex++;
            }
            
            if (comparator.compare(element, heap.get(childIndex)) <= 0) {
                break;
            }
            
            heap.set(index, heap.get(childIndex));
            index = childIndex;
        }
        
        heap.set(index, element);
    }
}