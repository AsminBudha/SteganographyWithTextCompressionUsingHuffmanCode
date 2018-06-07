/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganography;

import bitmanp.BitReader;
import bitmanp.BitWriter;
import huffman.HuffmanEncode;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author asmin
 */
public class SteganEncode {

    private final BufferedImage buffImage;
    private final String text;
    private int x = 0, y = 0;

    public SteganEncode(BufferedImage image, String text) {
        this.buffImage = image;
        this.text = text;
    }

    public BufferedImage embed() {
        //Generate huffman code
        HuffmanEncode huffmanEncode = new HuffmanEncode(text);
        //code map for each character
        HashMap<Character, String> huffmancode = huffmanEncode.getHuffmanCode();
        //total bits length needed to store code
        int codelen = huffmanEncode.getCodelength();
        //total bits length needed to store text compressed using huffmancode
        int textlen = huffmanEncode.getTextLength();
        //encoded huffman tree to embed into image
        BitWriter bitwirter = huffmanEncode.getBitWriter();
        
        int width = buffImage.getWidth();
        int height = buffImage.getHeight();
        /**
         * check if text and code can be embedded into image 32 bit for code
         * length 32 bit for text length codelen bits for code textlen bits for
         * text
         */

        if ((codelen + textlen + 2 * 32) > width * height) {
            System.out.println("Text cannot be embedded in image");
        } else {
            embedHuffmanCode(codelen, bitwirter);
            embedText(textlen, huffmancode);
        }

        return buffImage;

    }

    //embed the huffman code into image
    private void embedHuffmanCode(int codelen, BitWriter bitWriter) {

        //Embed the totalBitslength
        for (int i = 0; i < 32; i++) {
            boolean bit = (codelen & (1 << i)) == 0 ? false : true;
            int pixel = buffImage.getRGB(x, y);

            if (bit) {
                pixel |= 1;
            } else {
                pixel &= (~1);
            }

            buffImage.setRGB(x, y, pixel);
            x++;
            if (x >= buffImage.getWidth()) {
                x = 0;
                y++;
            }

        }
        BitReader bitReader = new BitReader(bitWriter.getBitSet());
        //Embed the huffman tree
        for (int i = 0; i < bitWriter.getSize(); i++) {

            boolean bit = bitReader.read();
            int pixel = buffImage.getRGB(x, y);

            if (bit) {
                pixel |= 1;
            } else {
                pixel &= (~1);
            }

            buffImage.setRGB(x, y, pixel);
            x++;
            if (x >= buffImage.getWidth()) {
                x = 0;
                y++;
            }

        }

    }

    private void embedText(int textLen, HashMap<Character, String> huffmancode) {
        //Embed the totalBitslength
        for (int i = 0; i < 32; i++) {
            boolean bit = (textLen & (1 << i)) == 0 ? false : true;
            int pixel = buffImage.getRGB(x, y);

            if (bit) {
                pixel |= 1;
            } else {
                pixel &= (~1);
            }

            buffImage.setRGB(x, y, pixel);
            x++;
            if (x >= buffImage.getWidth()) {
                x = 0;
                y++;
            }

        }

        //Embed the text 
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            String bits = huffmancode.get(c);
            for (int j = 0; j < bits.length(); j++) {
                char bit = bits.charAt(j);
                int pixel = buffImage.getRGB(x, y);

                if (bit == '1') {
                    pixel |= 1;
                } else {
                    pixel &= (~1);
                }

                buffImage.setRGB(x, y, pixel);
                x++;
                if (x >= buffImage.getWidth()) {
                    x = 0;
                    y++;
                }
            }

        }
    }
}
