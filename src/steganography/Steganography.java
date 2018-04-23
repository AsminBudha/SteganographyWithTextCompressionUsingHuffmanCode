/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganography;

import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author asmin
 */
public class Steganography {

    private BufferedImage buffImage;
    private String text;
    private int totalBitsLength;
    private HashMap<Character, String> code;
    private HashMap<Integer,Character> corresText=new HashMap<>();

    public Steganography(BufferedImage image, String text,
            int totalBitsLength, HashMap<Character, String> code) {
        this.buffImage = image;
        this.text = text;
        this.totalBitsLength = totalBitsLength;
        this.code = code;
        for(Character c:code.keySet()){
            Integer key=Integer.valueOf(code.get(c), 2);
//            System.out.println(code.get(c)+"->"+key);
            corresText.put(key, c);
        }
    }

    public BufferedImage embed() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
       
        int width = buffImage.getWidth();
        int height = buffImage.getHeight();

        int x = 0, y = 0;
        //Embed the totalBitslength
        for (int i = 0; i < 32; i++) {
            boolean bit = (totalBitsLength & (1 << i)) == 0 ? false : true;
            int pixel = buffImage.getRGB(x, y);

            if (bit) {
                pixel |= 1;
            } else {
                pixel &= (~1);
            }

            buffImage.setRGB(x, y, pixel);
            x++;
            if (x >= width) {
                x = 0;
                y++;
            }

        }

        //Embed the text 
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            String bits = code.get(c);
            for (int j = 0; j < bits.length(); j++) {
                char bit=bits.charAt(j);
                int pixel = buffImage.getRGB(x, y);

                if (bit=='1') {
                    pixel |= 1;
                } else {
                    pixel &= (~1);
                }

                buffImage.setRGB(x, y, pixel);
                x++;
                if (x >= width) {
                    x = 0;
                    y++;
                }
            }

        }

        return buffImage;

    }

    public String extract(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        int length = 0;
        int x = 0, y = 0;
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
            if (x >= width) {
                x = 0;
                y++;
            }

        }
        //Extract Text
        int key=0;
        int pos=0;
        String extractedText="";
        for(int i=0;i<length;i++){
            int pixel = buffImage.getRGB(x, y);
            boolean bit = (pixel & 1) == 0 ? false : true;
            key=(key<<1);
            if (bit) {
                
                key |= (1);
            } else {
                key &= (~(1));
            }
            
            if(corresText.containsKey(key)){
                extractedText+=corresText.get(key);
                pos=-1;
                key=0;
                                                                                                                                                                                                                                                                                                                                                                                                                                
            }

            pos++;
            x++;
            if (x >= width) {
                x = 0;
                y++;
            }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
        }
        return extractedText;

    }
}
