/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitmanp;

import java.util.BitSet;

/**
 *
 * @author asmin
 */
public class BitReader {
    private BitSet bitSet;
    private int index=0;
    public BitReader(BitSet bitSet){
        this.bitSet=bitSet;
    }
    public int getSize(){
        return bitSet.size();
    }
    public boolean read(){
        boolean bit=bitSet.get(index);
        index++;
        return bit;
    }
    
    public byte readByte(){
        byte b=0;
        for(int i=0;i<8;i++){
            boolean bit=read();
            b=(byte)(b|((bit?1:0)<<i));
            
        }
        return b;
    }
}
