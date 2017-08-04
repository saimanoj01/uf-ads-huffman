import java.io.*;
import java.util.HashMap;

/**
 * Created by saima_000 on 4/4/2017.
 */
public class encoder {
    HuffmanPairingEntry root;
    HashMap<Integer,String> codes;
    File inputFile;
    BufferedOutputStream bout = null;
    FileOutputStream fout = null;

    byte[] bytes = new byte[1024];
    int bytePtr = 0;
    byte temp = 0;
    int tempPtr = 0;

    public encoder(HashMap<Integer, Integer> map, File file) {
        this.root = constructTree(map);
        this.codes = new HashMap<Integer, String>();
        this.inputFile = file;
    }

    public void encodeData() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
        String line = bufferedReader.readLine();

        this.fout = new FileOutputStream(new File("./encoded.bin"));
        this.bout = new BufferedOutputStream(fout);

        while(line != null) {
            if (line.contentEquals(""))
                break;
            int data = Integer.parseInt(line);
            writeEncodedDataToFile(codes.get(data));
            line = bufferedReader.readLine();
        }

        if(bytePtr != 0) {
            bout.write(bytes, 0, bytePtr);
        }

        bout.close();
        fout.close();
        bufferedReader.close();
    }

    private void writeEncodedDataToFile(String data) throws IOException {
        for(int i=0;i<data.length();i++) {
            if(data.charAt(i) == '1')
                temp = (byte)(temp | 1);
            tempPtr++;
            if(tempPtr == 8) {
                bytes[bytePtr++] = temp;
                if(bytePtr == 1024) {
                    bout.write(bytes);
                    bytePtr = 0;
                }
                tempPtr = 0;
                temp = 0;
            }
            else {
                temp = (byte) (temp << 1);
            }
        }
    }

    public void generateCodes() {
        generateCodes(root, new StringBuilder());
//        for(int i : codes.keySet()) {
//            System.out.println(i + " : " + codes.get(i));
//        }
    }

    public void writeCodeTable() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File("./code_table.txt"));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        int count = 0;
        for(int i : codes.keySet()) {
            bufferedOutputStream.write((Integer.toString(i) + " " + codes.get(i)).getBytes());
            if(count != codes.size()-1)
                bufferedOutputStream.write("\n".getBytes());
            count++;
        }
        bufferedOutputStream.close();
        fileOutputStream.close();
    }

    private void generateCodes(HuffmanPairingEntry root, StringBuilder code) {
        if(root.leftChild == null && root.rightChild == null) {
            codes.put(root.data, code.toString());
            return;
        }
        code.append("0");
        generateCodes(root.leftChild, code);
        code.deleteCharAt(code.length()-1);
        code.append("1");
        generateCodes(root.rightChild, code);
        code.deleteCharAt(code.length()-1);
    }
    private HuffmanPairingEntry constructTree(HashMap<Integer, Integer> map) {
        PairingHeap heap = new PairingHeap();
        for(int i : map.keySet()) {
            heap.add(new HuffmanPairingEntry(i, map.get(i)));
        }

        while(!heap.isEmpty() && heap.size() != 1) {
            HuffmanPairingEntry huffmanEntry1 = heap.remove();
            HuffmanPairingEntry huffmanEntry2 = heap.peek();
            HuffmanPairingEntry newHuffmanEntry = new HuffmanPairingEntry(huffmanEntry1.getFrequency() + huffmanEntry2.getFrequency());
            newHuffmanEntry.isChild = false;
            newHuffmanEntry.leftChild = huffmanEntry1;
            newHuffmanEntry.rightChild = huffmanEntry2;
            heap.updateRoot(newHuffmanEntry);
        }
        if(heap.isEmpty())
            return null;
        return heap.peek();
    }

    public static HashMap<Integer, Integer> fillFrequencyTable(File file) throws IOException {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
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
        return map;
    }
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        String filePath = args[0];
        File file = new File(filePath);
        if(!file.exists()) {
            throw new FileNotFoundException("File not found at location : " + filePath + ". Please give the absolute path to the file");
        }
        HashMap<Integer, Integer> map = fillFrequencyTable(file);
        encoder encoder = new encoder(map, file);
        encoder.generateCodes();
        encoder.encodeData();
        encoder.writeCodeTable();
        System.out.println("Encoder takes : " + (System.currentTimeMillis() - startTime));
    }
}
