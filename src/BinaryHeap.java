import java.util.ArrayList;

/**
 * Created by saima_000 on 3/31/2017.
 */
public class BinaryHeap {
    private ArrayList<HuffmanEntry> data;

    public BinaryHeap(ArrayList<HuffmanEntry> list) {
        this();
        data.addAll(list);
        for(int i=data.size()-1;i>=0;i--) {
            heapify(i);
        }
    }

    public BinaryHeap() {
        data = new ArrayList<HuffmanEntry>();
    }

    public void add(HuffmanEntry HuffmanEntry) {
        data.add(HuffmanEntry);
        int index = data.size() - 1;
        Integer parentIndex = parent(index);
        while(parentIndex != null && data.get(index).getFrequency() < data.get(parentIndex).getFrequency()) {
            HuffmanEntry temp = data.get(index);
            data.set(index, data.get(parentIndex));
            data.set(parentIndex, temp);
            index = parentIndex;
            parentIndex = parent(index);
        }
    }

    public void addAll(ArrayList<HuffmanEntry> entries) {
        data.addAll(entries);
        for(int i=data.size()-1;i>=0;i--) {
            heapify(i);
        }
    }

    public HuffmanEntry remove() {
        if(data.size() == 0)
            return null;
        HuffmanEntry HuffmanEntry = data.get(0);
        data.set(0, data.get(size() - 1));
        data.remove(data.size()-1);
        heapify(0);
        return HuffmanEntry;
    }


    public HuffmanEntry peek() {
        if(data.size() == 0)
            return null;
        return data.get(0);
    }

    public void updateRoot(HuffmanEntry HuffmanEntry) {
        if(HuffmanEntry == null)
            return;
        data.set(0, HuffmanEntry);
        heapify(0);
    }

    public int size() {
        return data.size();
    }


    public boolean isEmpty() {
        return data.isEmpty();
    }

    private Integer leftChild(int index) {
        int leftChildIndex = 2*index + 1;
        if(leftChildIndex >= size())
            return null;
        return leftChildIndex;
    }

    private Integer rightChild(int index) {
        int rightChildIndex = 2*index + 2;
        if(rightChildIndex >= size())
            return null;
        return rightChildIndex;
    }

    private Integer parent(int index) {
        int parentIndex = (index-1)/2;
        if(parentIndex < 0)
            return null;
        return parentIndex;
    }

    private Integer getHighPriorityIndex(Integer index1, Integer index2) {
        HuffmanEntry element1 = null, element2 = null;
        if(index1 != null)
            element1 = data.get(index1);
        if(index2 != null)
            element2 = data.get(index2);
        if(element1 != null && element2 != null) {
            if(element1.getFrequency() <= element2.getFrequency())
                return index1;
            else
                return index2;
        }
        else if(element1 != null)
            return index1;
        else
            return null;
    }

    private void heapify(int index) {
        if(index >= size() || index < 0)
            return;
        Integer childIndex = getHighPriorityIndex(leftChild(index), rightChild(index));
        if(childIndex == null || data.get(childIndex).getFrequency() >= data.get(index).getFrequency())
            return;
        HuffmanEntry temp = data.get(index);
        data.set(index, data.get(childIndex));
        data.set(childIndex, temp);
        heapify(childIndex);
    }

    public void print() {
        for(HuffmanEntry HuffmanEntry : data)
            System.out.print(HuffmanEntry.getFrequency() + "   ");
    }
}
