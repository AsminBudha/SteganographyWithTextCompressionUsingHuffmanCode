/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import steganography.SteganDecode;
import steganography.SteganEncode;

/**
 *
 * @author asmin
 */
public class Runnable {
    public static void main(String[] arg){
        String text="AABCDEF";
            
        File file=null;
        BufferedImage buffImage=null;
        
        file=new File("res/img.jpg");
        try {
            buffImage=ImageIO.read(file);
            SteganEncode steganEncode=new SteganEncode(buffImage, text);
            BufferedImage img=steganEncode.embed();
            
            SteganDecode steganDecode=new SteganDecode(img);
            String extractedText=steganDecode.extract();
            System.out.println(extractedText);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
}
