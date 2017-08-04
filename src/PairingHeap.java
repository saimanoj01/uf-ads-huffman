import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by saima_000 on 4/1/2017.
 */
public class PairingHeap {
    HuffmanPairingEntry root;
    int size;

    public void add(HuffmanPairingEntry entry) {
        size++;
        if(root == null) {
            root = entry;
            return;
        }
        root = meld(root, entry);
    }

    public void addAll(ArrayList<HuffmanPairingEntry> entries) {
        for(HuffmanPairingEntry entry : entries) {
            add(entry);
        }
    }

    public HuffmanPairingEntry remove() {
        if(root == null)
            return null;
        size--;

        HuffmanPairingEntry huffmanPairingEntry = root;
        HuffmanPairingEntry childHuffmanPairingEntry = huffmanPairingEntry.child;
        if(childHuffmanPairingEntry == null) {
            root = null;
            return huffmanPairingEntry;
        }
        Stack<HuffmanPairingEntry> stack = new Stack<HuffmanPairingEntry>();
        HuffmanPairingEntry temp = childHuffmanPairingEntry;
        temp.leftSibling = null;
        while(temp != null) {
            if(temp.rightSibling == null) {
                temp.leftSibling = null;
                stack.push(temp);
                temp = temp.rightSibling;
            }
            else {
                HuffmanPairingEntry huffmanPairingEntry2 = temp.rightSibling;
                HuffmanPairingEntry temp3 = huffmanPairingEntry2.rightSibling;
                huffmanPairingEntry2.leftSibling = null;
                huffmanPairingEntry2.rightSibling = null;
                huffmanPairingEntry2.leftSibling = null;
                huffmanPairingEntry2.rightSibling = null;
                stack.push(meld(temp, huffmanPairingEntry2));
                temp = temp3;
            }
        }

        while(stack.size() != 1 && !stack.isEmpty()) {
            HuffmanPairingEntry entry1 = stack.pop();
            HuffmanPairingEntry entry2 = stack.pop();
            stack.push(meld(entry1, entry2));
        }

        root = stack.pop();
        return huffmanPairingEntry;
    }

    public HuffmanPairingEntry peek() {
        return root;
    }

    private HuffmanPairingEntry getMinChild(HuffmanPairingEntry parent) {
        HuffmanPairingEntry child = parent.child;
        if(child == null)
            return null;
        HuffmanPairingEntry min = child;
        while(child != null) {
            if(child.getFrequency() < min.getFrequency()) {
                min = child;
            }
            child = child.rightSibling;
        }
        return min;
    }


    public void updateRoot(HuffmanPairingEntry HuffmanPairingEntry) {
        /*
        if(HuffmanPairingEntry == null)
            return;
        if(root == null) {
            root = HuffmanPairingEntry;
            return;
        }
        root.entry.setFrequency(HuffmanPairingEntry.entry.getFrequency());
        root.entry.setData(HuffmanPairingEntry.entry.getData());
        root.leftChild = HuffmanPairingEntry.leftChild;
        root.rightChild = HuffmanPairingEntry.rightChild;

        HuffmanPairingEntry hEntry = root;
        while(true) {
            HuffmanPairingEntry child = getMinChild(hEntry);
            if(child == null || hEntry.getFrequency() <= child.getFrequency())
                break;
            swap(hEntry, child);
            hEntry = child;
        }
        */

        remove();
        add(HuffmanPairingEntry);
    }

    /*
    private void swap(HuffmanPairingEntry entry1, HuffmanPairingEntry entry2) {
        // Swap data
        int temp = entry1.getFrequency();
        entry1.entry.setFrequency(entry2.getFrequency());
        entry2.entry.setFrequency(temp);

        // Swap frequency
        temp = entry1.entry.getData();
        entry1.entry.setData(entry2.entry.getData());
        entry2.entry.setData(temp);

        // Swap huffman left child pointer
        HuffmanPairingEntry tempPtr = entry1.leftChild;
        entry1.leftChild = entry2.leftChild;
        entry2.leftChild = tempPtr;

        // Swap huffman right child pointer
        tempPtr = entry1.rightChild;
        entry1.rightChild = entry2.rightChild;
        entry2.rightChild = tempPtr;
    }

    */

    public int size() {
        return size;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public void print() {
        print(root,1);
    }

    private void print(HuffmanPairingEntry temp, int index) {
        while(temp != null) {
            System.out.print("Level- "+(index)+" -- ");
            System.out.print(temp.getFrequency()+" ");
            HuffmanPairingEntry siblings = temp.rightSibling;
            while(siblings != null) {
                print(siblings,index);
                siblings = siblings.rightChild;
            }
            temp = temp.child;
            index++;
        }
        System.out.println();
    }

    private HuffmanPairingEntry meld(HuffmanPairingEntry heap1, HuffmanPairingEntry heap2) {
        if(heap1 == null && heap2 == null)
            return null;
        else if(heap1 == null)
            return heap2;
        else if(heap2 == null)
            return heap1;

        if(heap1.getFrequency() >= heap2.getFrequency()) {
            heap1.leftSibling = heap2;
            heap1.rightSibling = heap2.child;
            if(heap2.child != null) {
                HuffmanPairingEntry entry2Child = heap2.child;
                entry2Child.leftSibling = heap1;
            }
            heap2.child = heap1;
            return heap2;
        }
        else {
            heap2.leftSibling = heap1;
            heap2.rightSibling = heap1.child;
            if(heap1.child != null) {
                HuffmanPairingEntry entry1Child = heap1.child;
                entry1Child.leftSibling = heap2;
            }
            heap1.child = heap2;
            return heap1;
        }
    }
}
