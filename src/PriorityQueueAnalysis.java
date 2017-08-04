import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by saima_000 on 4/1/2017.
 */
public class PriorityQueueAnalysis {

    public static HuffmanEntry buildTreeBinaryHeap(ArrayList<HuffmanEntry> list) {
        BinaryHeap heap = new BinaryHeap();
        heap.addAll(list);

        while(!heap.isEmpty() && heap.size() != 1) {
            HuffmanEntry huffmanEntry1 = heap.remove();
            HuffmanEntry huffmanEntry2 = heap.peek();
            HuffmanEntry newHuffmanEntry = null;
            newHuffmanEntry = new HuffmanEntry(huffmanEntry1.getFrequency() + huffmanEntry2.getFrequency());
            newHuffmanEntry.isChild = false;
            newHuffmanEntry.leftChild = huffmanEntry1;
            newHuffmanEntry.rightChild = huffmanEntry2;
            heap.updateRoot(newHuffmanEntry);
        }

        if(heap.isEmpty())
            return null;
        return heap.peek();

    }

    public static HuffmanEntry buildTreeFourCacheHeap(ArrayList<HuffmanEntry> list) {
        FourCacheHeap heap = new FourCacheHeap();
        heap.addAll(list);

        while(!heap.isEmpty() && heap.size() != 1) {
            HuffmanEntry huffmanEntry1 = heap.remove();
            HuffmanEntry huffmanEntry2 = heap.peek();
            HuffmanEntry newHuffmanEntry = null;
            newHuffmanEntry = new HuffmanEntry(huffmanEntry1.getFrequency() + huffmanEntry2.getFrequency());
            newHuffmanEntry.isChild = false;
            newHuffmanEntry.leftChild = huffmanEntry1;
            newHuffmanEntry.rightChild = huffmanEntry2;
            heap.updateRoot(newHuffmanEntry);
        }

        if(heap.isEmpty())
            return null;
        return heap.peek();

    }

    public static HuffmanPairingEntry buildTreePairingHeap(HashMap<Integer, Integer> map) {
        PairingHeap heap = new PairingHeap();
        for(int i : map.keySet()) {
            heap.add(new HuffmanPairingEntry(i, map.get(i)));
        }

        while(!heap.isEmpty() && heap.size() != 1) {
            HuffmanPairingEntry huffmanEntry1 = heap.remove();
            HuffmanPairingEntry huffmanEntry2 = heap.peek();
            HuffmanPairingEntry newHuffmanEntry = null;
            newHuffmanEntry = new HuffmanPairingEntry(huffmanEntry1.getFrequency() + huffmanEntry2.getFrequency());
            newHuffmanEntry.isChild = false;
            huffmanEntry1.leftChild = huffmanEntry1;
            huffmanEntry2.rightChild = huffmanEntry2;
            heap.updateRoot(newHuffmanEntry);
        }

        if(heap.isEmpty())
            return null;
        return heap.peek();

    }

    public static void main(String[] args) throws IOException {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        File file = new File("sample2/sample_input_large.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = bufferedReader.readLine();
        while(line != null) {
            if(line.contentEquals(""))
                break;
            int data = Integer.parseInt(line);
            if(map.containsKey(data)) {
                map.put(data, map.get(data) + 1);
            }
            else {
                map.put(data, 1);
            }
            line = bufferedReader.readLine();
        }

        ArrayList<HuffmanEntry> list = new ArrayList<HuffmanEntry>();
        for(int key : map.keySet()) {
            list.add(new HuffmanEntry(key, map.get(key)));
        }

        long startTime = System.currentTimeMillis();
        for(int i=0;i<10;i++) {
            buildTreeBinaryHeap(list);
        }
        System.out.println("Time using binary heap : " + (System.currentTimeMillis() - startTime)/10);

        startTime = System.currentTimeMillis();
        for(int i=0;i<10;i++) {
            buildTreeFourCacheHeap(list);
        }
        System.out.println("Time using Four way cache heap : " + (System.currentTimeMillis() - startTime)/10);

        startTime = System.currentTimeMillis();
        for(int i=0;i<10;i++) {
            buildTreePairingHeap(map);
        }
        System.out.println("Time using Pairing heap : " + (System.currentTimeMillis() - startTime)/10);
    }
}
