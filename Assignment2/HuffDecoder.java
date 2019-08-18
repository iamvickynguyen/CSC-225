/* HuffDecoder.java

   Starter code for compressed file decoder. You do not have to use this
   program as a starting point if you don't want to, but your implementation
   must have the same command line interface. Do not modify the HuffFileReader
   or HuffFileWriter classes (provided separately).
   
   B. Bird - 03/19/2019
   (Vicky Nguyen/V00906571/7-5-2019)
*/

import java.io.*;


public class HuffDecoder{

    private HuffFileReader inputReader;
    private BufferedOutputStream outputFile;


    /* Basic constructor to open input and output files. */
    public HuffDecoder(String inputFilename, String outputFilename) throws FileNotFoundException {
        inputReader = new HuffFileReader(inputFilename);
        outputFile = new BufferedOutputStream(new FileOutputStream(outputFilename));
    }


    public void decode() throws IOException{
        /* This is where actual decoding should happen. */

        /* s: # of entries in the symbol table, n: # of bits in the bit stream
        
        /*
            Build HuffTree
            Running time of adding 1 node is O(1) (= the length of 1 sym.symbolBits)
            So building a HuffTree takes O(s) time.
        */
        HuffNode root = new HuffNode(null);          
        HuffFileSymbol sym = inputReader.readSymbol();
        while(sym!=null){
            addNode(root, sym);
            sym = inputReader.readSymbol();
        }

        /*
            Read and Write
            Running time of reading and writing bits in the bit stream is O(s+n) 
        */
        int bit = inputReader.readStreamBit();
        HuffNode temp = root;
        while(bit!=-1){      
            if(bit==0)
                temp = temp.left;            
            else
                temp = temp.right;        
            if (temp.characters!=null){
                for(byte word : temp.characters){
                    outputFile.write(word); 
                }temp = root;                
            }
            bit = inputReader.readStreamBit();
        }

        inputReader.close();
        outputFile.flush();      
    }


    public void addNode(HuffNode root, HuffFileSymbol sym){
        int[] bits = sym.symbolBits;
        byte[] symbol = sym.symbol;
        HuffNode temp = root;

        for(int i=0; i < bits.length-1 ; i++){
            if(bits[i]==0){
                if(temp.left==null)
                    temp.left = new HuffNode(null);
                temp = temp.left;
            }               
            else{
                if(temp.right==null)
                    temp.right = new HuffNode(null);
                temp = temp.right;
            }
        }
        HuffNode leaf = new HuffNode(symbol);
        if(bits[bits.length-1]==0)
            temp.left = leaf;
        else
            temp.right = leaf;          
    }


    public static void main(String[] args) throws IOException{
        if (args.length != 2){
            System.err.println("Usage: java HuffDecoder <input file> <output file>");
            return;
        }
        String inputFilename = args[0];
        String outputFilename = args[1];

        try {
            HuffDecoder decoder = new HuffDecoder(inputFilename, outputFilename);
            decoder.decode();
        } catch (FileNotFoundException e) {
            System.err.println("Error: "+e.getMessage());
        }
    }
}
