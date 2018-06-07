/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author asmin
 */
public class Node implements Comparable<Node>{

    public Byte Value;
    public Node LeftChild;
    public Node RightChild;
    public int count;
    public Node(Byte value, Node leftChild, Node rightChild,int count) {
        Value = value;
        LeftChild = leftChild;
        RightChild = rightChild;
        this.count=count;
    }

    public boolean IsLeafNode() {

        return LeftChild == null;

    }

    @Override
    public int compareTo(Node o) {
        return this.count > o.count ? 1 : -1;
    }
}
