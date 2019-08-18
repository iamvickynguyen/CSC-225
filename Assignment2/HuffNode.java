//Vicky Nguyen/V00906571/7-5-2019

import java.util.Comparator;

public class HuffNode{
    byte[] characters;
    byte character;
    int frequency;
    HuffNode left;
    HuffNode right;

    public HuffNode(byte[] characters){
        this.characters = characters;
    }

    public HuffNode(){
    }

    public HuffNode(byte character, int frequency){
        this.character = character;
        this.frequency = frequency;
    }       
}

class HuffComparator implements Comparator<HuffNode>{
    public int compare(HuffNode x, HuffNode y){
        return x.frequency - y.frequency;
    }
}