/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import bitmanp.BitReader;
import bitmanp.BitWriter;
import java.util.HashMap;
import main.Node;

/**
 *
 * @author asmin
 */
public class HuffmanDecode {
    private HashMap<String, Character> huffmanCode=new HashMap<>();
    public HashMap<String, Character> decodeCode(BitWriter bitWriter) {
        BitReader bitreader=new BitReader(bitWriter.getBitSet());
        
        readNode(bitreader,"");
        return huffmanCode;
    }

    private Node readNode(BitReader reader,String code) {
        if (reader.read()) {
            char c=(char)(reader.readByte()& 0xFF);
            huffmanCode.put(code,c);
            return new Node((byte)c, null, null,0);
        } else {
            Node leftChild = readNode(reader,code+"0");
            Node rightChild = readNode(reader,code+"1");
            return new Node((byte)0, leftChild, rightChild,0);
        }
    }

}
