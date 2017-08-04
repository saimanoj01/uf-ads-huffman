/**
 * Created by saima_000 on 4/1/2017.
 */
public class HuffmanEntry {
    public boolean isChild;
    public HuffmanEntry leftChild;
    public HuffmanEntry rightChild;
    public int data, frequency;

    public HuffmanEntry(int data, int frequency) {
        this.data = data;
        this.frequency = frequency;
    }

    public HuffmanEntry(int frequency) {
        this.frequency = frequency;
    }

    public int getFrequency() {
        return frequency;
    }
}
