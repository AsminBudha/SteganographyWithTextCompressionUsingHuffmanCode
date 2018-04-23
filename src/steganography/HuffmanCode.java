/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganography;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

/**
 *
 * @author asmin
 */
public class HuffmanCode {

    private String text;
    private HashMap<Character, String> huffmanCode = new HashMap<>();
    private int length;

    public HuffmanCode(String text) {
        this.text = text;
    }

    public void generateCode() {
        //Calculating frequencies
        HashMap<Character,Integer> freq=new HashMap<Character,Integer>();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (freq.containsKey(c)) {
                freq.replace(c, freq.get(c)+1);
            } else {
                freq.put(c, 1);
                huffmanCode.put(c, "");
            }
        }
        
        //Create own node
        TreeSet<Node> nodes=new TreeSet<>();
        for(Character c:freq.keySet()){
            nodes.add(new Node(c.toString(), freq.get(c)));
        }
        
        //Calculating huffman code for nodes
        while(nodes.size()>1){
            Node first=nodes.pollFirst();
            Node second=nodes.pollFirst();
            
            nodes.add(new Node(first.c+second.c, first.i+second.i));
            
            for(int i=0;i<first.c.length();i++){
                char c=first.c.charAt(i);
                this.huffmanCode.replace(c, "0"+this.huffmanCode.get(c));
            }
            for(int i=0;i<second.c.length();i++){
                char c=second.c.charAt(i);
                this.huffmanCode.replace(c, "1"+this.huffmanCode.get(c));
            }
        }
        
        this.length=0;
        //calculating total bits length of text to be embedded
        for(Character c:freq.keySet()){
            this.length+=(freq.get(c)*this.huffmanCode.get(c).length());
        }
    }
    
    public HashMap<Character,String> getCode(){
        return huffmanCode;
    }
    
    public int getBitsLength(){
        return length;
    }
    static class Node implements Comparable<Node> {

        String c;
        Integer i;

        public Node(String c,int i) {
            this.c = c;
            this.i=i;
        }

        @Override
        public int compareTo(Node o) {
            return this.i >= o.i ? 1 : -1;
        }

    }
    
//    public static void main(String[] arg){
//        String str="ABBBBBCCCCCCDDDDDDDD";
//        HuffmanCode huffmanCode=new HuffmanCode(str);
//        huffmanCode.generateCode();
//        
//        HashMap<Character,String> code=huffmanCode.getCode();
//        for(Character c:code.keySet()){
//            System.out.println(c+"->"+code.get(c));
//        }
//    }
   
}
