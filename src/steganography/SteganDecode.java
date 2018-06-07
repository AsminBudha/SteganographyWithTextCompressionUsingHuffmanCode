/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganography;

import bitmanp.BitWriter;
import huffman.HuffmanDecode;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author asmin
 */
public class SteganDecode {

    private BufferedImage buffImage;
    private int x = 0, y = 0;

    public SteganDecode(BufferedImage buffImage) {
        this.buffImage = buffImage;
    }

    public String extract() {

        HashMap<String, Character> code = extractHuffmanCode();
        String extractedText=extractText(code);
        return extractedText;

    }

    private String extractText(HashMap<String, Character> huffmanCode) {

        int length = 0;
        //Embed the totalBitslength
        for (int i = 0; i < 32; i++) {
            int pixel = buffImage.getRGB(x, y);
            boolean bit = (pixel & 1) == 0 ? false : true;
            if (bit) {
                length |= (1 << i);
            } else {
                length &= (~(1 << i));
            }

            x++;
            if (x >= buffImage.getWidth()) {
                x = 0;
                y++;
            }

        }
        //Extract Text
        String key = "";
        String extractedText = "";
        for (int i = 0; i < length; i++) {
            int pixel = buffImage.getRGB(x, y);
            boolean bit = (pixel & 1) == 0 ? false : true;
            
            if (bit) {
                key+="1";
            } else {
                key+="0";
            }

            if (huffmanCode.containsKey(key)) {
                extractedText += huffmanCode.get(key);
                
                key = "";

            }

            x++;
            if (x >= buffImage.getWidth()) {
                x = 0;
                y++;
            }
        }
        return extractedText;
    }

    private HashMap<String, Character> extractHuffmanCode() {
        int codelen = 0;

        //Embed the totalBitslength
        for (int i = 0; i < 32; i++) {
            int pixel = buffImage.getRGB(x, y);
            boolean bit = (pixel & 1) == 0 ? false : true;
            if (bit) {
                codelen |= (1 << i);
            } else {
                codelen &= (~(1 << i));
            }

            x++;
            if (x >= buffImage.getWidth()) {
                x = 0;
                y++;
            }

        }
        //Extract Text
        BitWriter bitWriter = new BitWriter(codelen);

        for (int i = 0; i < codelen; i++) {
            int pixel = buffImage.getRGB(x, y);
            boolean bit = (pixel & 1) == 0 ? false : true;
            
            bitWriter.write(bit);

            x++;
            if (x >= buffImage.getWidth()) {
                x = 0;
                y++;
            }
        }
        
        HuffmanDecode huffmanDecode = new HuffmanDecode();
        return huffmanDecode.decodeCode(bitWriter);
    }
}
