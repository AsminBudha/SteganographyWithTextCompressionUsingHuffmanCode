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
public class BitWriter {
    private BitSet bitSet;
    private int index=0;
    private int size;
    public BitWriter(int size){
        bitSet=new BitSet(size);
        this.size=size;
    }

    public int getSize() {
        return size;
    }

    public BitSet getBitSet() {
        return bitSet;
    }
    
    public void write(boolean value){
        bitSet.set(index, value);
        index++;
    }
    
    public void write(byte value){
        for(int i=0;i<8;i++){
            int bit=value&(1<<i);
            bitSet.set(index, bit==0?false:true);
            index++;
        }
    }
}
