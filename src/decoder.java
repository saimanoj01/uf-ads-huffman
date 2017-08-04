import java.io.*;
import java.util.HashMap;

/**
 * Created by saima_000 on 4/5/2017.
 */
public class decoder {
    public class DecoderNode {
        int data;
        public DecoderNode leftChild, rightChild;

        public DecoderNode(int data) {
            this.data = data;
        }

        public DecoderNode() {

        }

        public void setData(int data) {
            this.data = data;
        }

        public int getData() {
            return this.data;
        }
    }

    File encodedFile, codeTableFile;
    DecoderNode root;
    FileInputStream fin;
    FileOutputStream fout;
    BufferedInputStream bin;
    BufferedOutputStream bout;

    byte[] bytes = new byte[1024];
    int bytePtr = 0;
    int bufferLength = 1024;
    byte tempByte = 0;
    int tempByteMask = 1<<7;
    boolean flag = false;

    public decoder(File encodedFile, File codeTableFile) {
        this.encodedFile = encodedFile;
        this.codeTableFile = codeTableFile;
    }

    public HashMap<Integer, String> readCodeTableFile() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(codeTableFile));
        String line = bufferedReader.readLine();
        HashMap<Integer, String> codes = new HashMap<Integer, String>();
        while(line != null) {
            if(line.contentEquals(""))
                break;
            String[] data = line.split(" ");
            codes.put(Integer.parseInt(data[0]), data[1]);
            //System.out.println(data[0] + " " + data[1]);
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        return codes;
    }

    public void buildHuffmanTree(HashMap<Integer, String> codes) {
        DecoderNode temp = null;
        for(int i : codes.keySet()) {
            String code = codes.get(i);
            temp = root;
            for(int j=0;j<code.length();j++) {
                if(root == null) {
                    root = new DecoderNode();
                    temp = root;
                }
                if(code.charAt(j) == '0') {
                    if(temp.leftChild == null)
                        temp.leftChild = new DecoderNode();
                    temp = temp.leftChild;
                }
                else {
                    if(temp.rightChild == null)
                        temp.rightChild = new DecoderNode();
                    temp = temp.rightChild;
                }
                if(j == code.length() - 1) {
                    temp.setData(i);
                }
            }
        }
    }

    public void decodeData() throws IOException {
        fin = new FileInputStream(encodedFile);
        bin = new BufferedInputStream(fin);
        fout = new FileOutputStream(new File("decoded.txt"));
        bout = new BufferedOutputStream(fout);
        DecoderNode temp = root;
        bufferLength = bin.read(bytes);
        tempByte = bytes[bytePtr++];
        while (true) {
            char ch = getNextChar();
            if(ch == '2')
                break;
            else if(ch == '0') {
                temp = temp.leftChild;
            }
            else {
                temp = temp.rightChild;
            }

            if(temp.leftChild == null && temp.rightChild == null) {
                bout.write(Integer.toString(temp.data).getBytes());
                bout.write("\n".getBytes());
                temp = root;
            }
        }
        bout.close();
        fout.close();
        bin.close();
        fin.close();
    }

    private char getNextChar() throws IOException {
        if(bufferLength == -1 && flag)
            return '2';
        int bit = tempByte & tempByteMask;
        tempByteMask = tempByteMask >>> 1;
        if(tempByteMask == 0) {
            if(bufferLength == -1)
                flag = true;
            tempByteMask = 1 << 7;
            tempByte = bytes[bytePtr++];
            if(bytePtr == bufferLength) {
                bufferLength = bin.read(bytes);
                bytePtr = 0;
            }
        }
        if(bit == 0)
            return '0';
        else {
            return '1';
        }
    }

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        if(args.length != 2) {
            throw new IllegalArgumentException("Invalid arguments. Pass the encoded file and code table file as arguments");
        }
        decoder decoder = new decoder(new File(args[0]), new File(args[1]));
        HashMap<Integer, String> codes = decoder.readCodeTableFile();
        decoder.buildHuffmanTree(codes);
        decoder.decodeData();
        System.out.println("Decoder takes : " + (System.currentTimeMillis() - startTime));
    }
}
