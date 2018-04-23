/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganography;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author asmin
 */
public class Runnable {
    public static void main(String[] arg){
        String text="ABBBBBCCCCCCDDDDDDDD";
//        System.out.println(3<<2);
        HuffmanCode huffmanCode=new HuffmanCode(text);
        huffmanCode.generateCode();
        int totalBitsLength=huffmanCode.getBitsLength();
        HashMap<Character,String> code=huffmanCode.getCode();
        
//        for(Character c:code.keySet()){
//            System.out.println(c+"->"+code.get(c));
//        }

        
        File file=null;
        BufferedImage buffImage=null;
        
        file=new File("res/img.jpg");
        try {
            buffImage=ImageIO.read(file);
            
            Steganography steganography=new Steganography(buffImage, text
                    , totalBitsLength,code);
            BufferedImage img=steganography.embed();
            String extractedText=steganography.extract(img);
            System.out.println(extractedText);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(Runnable.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
