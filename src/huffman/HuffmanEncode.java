/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;
import bitmanp.BitWriter;
import main.Node;

/**
 *
 * @author asmin
 */
public class HuffmanEncode {

    private String text;
    private HashMap<Character, String> huffmanCode = new HashMap<>();
    private TreeMap<Character, Integer> freq = new TreeMap<Character, Integer>();
    private Node tree;
    private int codelength=0;
    private int textLength=0;
    private BitWriter bitWriter;

    public HuffmanEncode(String text) {
        this.text = text;
        generateHuffmanTree();
    }

    public String getText() {
        return text;
    }

    public HashMap<Character, String> getHuffmanCode() {
        System.out.println("Created Huffman Code Starts");
        for(Character c: huffmanCode.keySet()){
            System.out.println(c+"->"+huffmanCode.get(c));
        }
        System.out.println("Created Huffman Code ends");
        return huffmanCode;
    }

    public Node getTree() {
        return tree;
    }

    public int getTextLength() {
        return textLength;
    }

    public BitWriter getBitWriter() {
        return bitWriter;
    }

    public int getCodelength() {
        return codelength;
    }
    
    

    private void generateHuffmanTree() {
        //Calculating frequencies
        
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (freq.containsKey(c)) {
                freq.replace(c, freq.get(c) + 1);
            } else {
                freq.put(c, 1);
                huffmanCode.put(c, "");
            }
        }
        codelength=10 * freq.size() - 1;
        //Create own node
        TreeSet<Node> nodes = new TreeSet<>();
        for (Character c : freq.keySet()) {
            nodes.add(new Node((byte) c.charValue(), null, null, freq.get(c)));
        }

        //Calculating huffman code for nodes
        while (nodes.size() > 1) {
            Node first = nodes.pollFirst();
            Node second = nodes.pollFirst();

            Node cur = new Node(Integer.valueOf(first.Value + second.Value).byteValue(),
                    second, first, first.count + second.count);
            
            nodes.add(cur);

        }
        tree = nodes.pollFirst();
        encode();
    }
    
    private void encode(){
        bitWriter=new BitWriter(codelength);
        bitWriter=EncodeNode(tree, bitWriter, "");
        
    }

    private BitWriter EncodeNode(Node node, BitWriter writer,String code) {
        if (node.IsLeafNode()) {
            writer.write(true);
            writer.write(node.Value);
            huffmanCode.put((char)(node.Value& 0xFF), code);
            textLength+=(freq.get((char)(node.Value&0xFF))*code.length());
            return writer;
        } else {
            writer.write(false);
            writer=EncodeNode(node.LeftChild, writer,code+"0");
            writer=EncodeNode(node.RightChild, writer,code+"1");
        }
        return writer;
    }
}
