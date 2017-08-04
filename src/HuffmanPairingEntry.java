/**
 * Created by saima_000 on 4/1/2017.
 */
public class HuffmanPairingEntry {
    public boolean isChild;
    public HuffmanPairingEntry leftChild, rightChild, leftSibling, rightSibling, child;
    public int data, frequency;

    public HuffmanPairingEntry(int data, int frequency) {
        this.data = data;
        this.frequency = frequency;
    }

    public HuffmanPairingEntry(int frequency) {
        this.frequency = frequency;
    }

    public int getFrequency() {
        return frequency;
    }

    public int data() {
        return this.data;
    }
}
