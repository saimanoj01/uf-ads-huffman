import java.util.ArrayList;

/**
 * Created by saima_000 on 4/1/2017.
 */
public class FourCacheHeap {
    ArrayList<HuffmanEntry> data;

    public FourCacheHeap() {
        data = new ArrayList<HuffmanEntry>();
        HuffmanEntry temp = new HuffmanEntry(0);
        data.add(temp);
        data.add(temp);
        data.add(temp);
    }

    public FourCacheHeap(ArrayList<HuffmanEntry> list) {
        this();
        data.addAll(list);
        for(int i=data.size()-1;i>=3;i--) {
            heapify(i);
        }
    }

    public void add(HuffmanEntry entry) {
        data.add(entry);
        int index = data.size() - 1;
        Integer parentIndex = getParent(index);
        while(parentIndex != null && data.get(index).getFrequency() < data.get(parentIndex).getFrequency()) {
            HuffmanEntry temp = data.get(index);
            data.set(index, data.get(parentIndex));
            data.set(parentIndex, temp);
            index = parentIndex;
            parentIndex = getParent(index);
        }
    }

    public void addAll(ArrayList<HuffmanEntry> entries) {
        data.addAll(entries);
        for(int i=data.size()+1;i>=3;i--) {
            heapify(i);
        }
    }

    public HuffmanEntry remove() {
        if(data.size()-3 == 0)
            return null;
        HuffmanEntry entry = data.get(3);
        data.set(3, data.get(data.size() - 1));
        data.remove(data.size()-1);
        heapify(3);
        return entry;
    }

    public HuffmanEntry peek() {
        if(data.size()-3 == 0)
            return null;
        return data.get(3);
    }


    public void updateRoot(HuffmanEntry entry) {
        if(entry == null)
            return;
        data.set(3, entry);
        heapify(3);
    }


    public int size() {
        return data.size() - 3;
    }

    public boolean isEmpty() {
        return data.size()-3 ==  0;
    }

    private ArrayList<Integer> getChildren(Integer index) {

        if(index == null)
            return null;
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i=1;i<=4;i++) {
            int childIndex = (index - 3) * 4 + i + 3;
            if(childIndex >=3 && childIndex <= data.size()-1) {
                list.add(childIndex);
            }
        }
        return list;
    }

    private Integer getChild(Integer index, int childNo) {
        if(index == null || index < 3 || index >= data.size())
            return null;
        if(childNo <= 0 || childNo > 4)
            return null;
        int childIndex = (index - 3) * 4 + childNo + 3;
        if(childIndex < 3 || childIndex >= data.size())
            return null;
        return childIndex;
    }

    private Integer getParent(Integer index) {
        if(index == null)
            return null;
        index = (index )/4 + 2;
        if(index >= 3 && index <= data.size()-1)
            return index;
        else
            return null;
    }

    private Integer getHighPriorityNodeFromChildren(Integer index) {
        if(index == null)
            return null;
        HuffmanEntry min = new HuffmanEntry(Integer.MAX_VALUE);
        Integer min_index = Integer.MAX_VALUE;
        ArrayList<Integer> list = getChildren(index);
        for(int i=0;i<list.size();i++) {
            if(data.get(list.get(i)).getFrequency() < min.getFrequency()) {
                min = data.get(list.get(i));
                min_index = (index - 3) * 4 + i + 4;
            }
        }
        if(min_index == Integer.MAX_VALUE)
            return null;
        return min_index;
    }

    private void heapify(Integer index) {
        if(index == null || index < 3 || index >= data.size())
            return;
        Integer minIndex = min(getChild(index, 1), getChild(index, 2));
        if(minIndex != null)
            minIndex = min(minIndex,getChild(index, 3));
        if(minIndex != null)
            minIndex = min(minIndex,getChild(index, 4));
        Integer swapIndex = min(minIndex,index);
        if(swapIndex != index) {
            HuffmanEntry temp = data.get(index);
            data.set(index, data.get(swapIndex));
            data.set(swapIndex, temp);
            heapify(swapIndex);
        }
        return;
    }

    private Integer min(Integer index1, Integer index2) {
        if(index1 == null && index2 == null)
            return null;
        else if(index1 == null)
            return index2;
        else if(index2 == null)
            return index1;
        else {
            if(data.get(index1).getFrequency() <= data.get(index2).getFrequency())
                return index1;
            else
                return index2;
        }

    }
    private void heapify1(int index) {
        if(index < 3 || index > data.size()-1)
            return;
        Integer childIndex = getHighPriorityNodeFromChildren(index);
        if(childIndex == null || data.get(index).getFrequency() <= data.get(childIndex).getFrequency())
            return;
        HuffmanEntry temp = data.get(index);
        data.set(index, data.get(childIndex));
        data.set(childIndex, temp);
        heapify(childIndex);
    }

    public void print() {
        for(int i=0;i<data.size();i++)
            System.out.print(data.get(i).getFrequency() + "    ");
    }
}
