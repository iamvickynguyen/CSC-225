/* HuffEncoder.java

   Starter code for compressed file encoder. You do not have to use this
   program as a starting point if you don't want to, but your implementation
   must have the same command line interface. Do not modify the HuffFileReader
   or HuffFileWriter classes (provided separately).
   
   B. Bird - 03/19/2019
   (Vicky Nguyen/V00906571/7-5-2019)
*/

import java.io.*;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;

public class HuffEncoder{

    private BufferedInputStream inputFile;
    private HuffFileWriter outputWriter;

    public HuffEncoder(String inputFilename, String outputFilename) throws FileNotFoundException {
        inputFile = new BufferedInputStream(new FileInputStream(inputFilename));
        outputWriter = new HuffFileWriter(outputFilename);
    }


    public void encode() throws IOException{
        
        //Reading the entire file into a list
        LinkedList<Byte> input_bytes = new LinkedList<Byte>();
        for(int nextByte = inputFile.read(); nextByte != -1; nextByte = inputFile.read()){
            input_bytes.add((byte)nextByte);
        }

        HashMap<Byte, Integer> frequencyMap = CreateFrequencyMap(input_bytes); //Create Map={character : frequency} 
        PriorityQueue<HuffNode> pq = makeHuffNode(frequencyMap);    //Make HuffNodes
        HuffNode root = BuildHuffTree(pq);//Build HuffTree

        //Make a HuffCode table
        HashMap<Byte, int[]> table = new HashMap<Byte, int[]>();
        BuildTable(root, "", table);

        writeSymbolTable(table); //Write the symbol table to the output file      
        writeBinaryContent(input_bytes, table); //Write binary content to output file

    }
    //Make HuffNodes
    public PriorityQueue<HuffNode> makeHuffNode(HashMap<Byte, Integer> frequencyMap){
        PriorityQueue<HuffNode> pq = new PriorityQueue<HuffNode>(new HuffComparator());
        for(Map.Entry<Byte, Integer> item : frequencyMap.entrySet())
            pq.add(new HuffNode(item.getKey(), item.getValue()));
        return pq;
    }

    //Create Map={character : frequency}
    public  HashMap<Byte, Integer> CreateFrequencyMap(LinkedList<Byte> input_bytes){
        HashMap<Byte, Integer> frequencyMap = new HashMap<Byte, Integer>();
        for(Byte byte_key : input_bytes){
            Integer byte_frequency = frequencyMap.get(byte_key);
            if(byte_frequency==null)
                frequencyMap.put(byte_key, 1);
            else
                frequencyMap.put(byte_key, (byte_frequency+1));
        }
        return frequencyMap;
    }
    
    //Merge to HuffTree
    public HuffNode BuildHuffTree(PriorityQueue<HuffNode> pq){
        HuffNode root = null;
        while(pq.size()>1){
            HuffNode x = pq.poll();
            HuffNode y = pq.poll();
            HuffNode tempRoot = new HuffNode();

            root = tempRoot; 
            tempRoot.frequency = x.frequency + y.frequency;
            tempRoot.left = x;
            tempRoot.right = y;

            pq.add(tempRoot);
        }
        return root;
    }   

    //Reference: https://www.techiedelight.com/huffman-coding/
    public void BuildTable(HuffNode root, String code, HashMap<Byte, int[]> table){
        if(root==null) return;
        if ((root.left==null) && (root.right==null))
            table.put(root.character, stringToInt(code));
        
        BuildTable(root.left, code + "0", table);
        BuildTable(root.right, code + "1", table);
    }

    //Write the symbol table to the output file  
    public void writeSymbolTable(HashMap<Byte, int[]> table){
        for(Map.Entry<Byte, int[]> symbol : table.entrySet()){
            HuffFileSymbol sym = createHuffSymbol(symbol.getKey(), symbol.getValue());
            outputWriter.writeSymbol(sym);
        }       
        outputWriter.finalizeSymbols();
    }
     
    //Create HuffFileSymbol
    public HuffFileSymbol createHuffSymbol (Byte key, int[] value){
        byte[] symbolContents = new byte[1];   
        symbolContents[0] = key;
        return new HuffFileSymbol(symbolContents, value);
    }

    //Write binary content to output file
    public void writeBinaryContent( LinkedList<Byte> input_bytes, HashMap<Byte, int[]> table){
        for(byte b : input_bytes){
            for(int bit : table.get(b))
                outputWriter.writeStreamBit(bit);
        } 
        outputWriter.close();
    }

    //String to int[]   
    public int[] stringToInt (String s){
        int[] arr = new int[s.length()];
        for (int i = 0; i < s.length() ; i++){
            arr[i] = Character.getNumericValue(s.charAt(i));
        }
        return arr;
    }

    public static void main(String[] args) throws IOException{
        if (args.length != 2){
            System.err.println("Usage: java HuffEncoder <input file> <output file>");
            return;
        }
        String inputFilename = args[0];
        String outputFilename = args[1];

        try{
            HuffEncoder encoder = new HuffEncoder(inputFilename, outputFilename);
            encoder.encode();
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: "+e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException: "+e.getMessage());
        }

    }
}

