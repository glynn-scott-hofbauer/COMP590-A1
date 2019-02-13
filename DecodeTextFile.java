package app;

import java.awt.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.text.html.HTMLDocument.Iterator;


import ac.Node;
import ac.TreeBuilder;
import ac.compressionTest;
import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;
import io.OutputStreamBitSink;

public class DecodeTextFile {

	
	public static void main(String [] args) throws InsufficientBitsLeftException, IOException{
		
		String input_file_name = "compressed (1).dat";
		String output_file_name = "uncompressed.txt";
		FileInputStream fis = new FileInputStream(input_file_name);
		InputStreamBitSource bit_source = new InputStreamBitSource(fis);
		
		int[] sym_lengths = new int[256];
		
		Integer[] symbols = new Integer[256];
		
		ArrayList<Node> sorted_Nodes = new ArrayList<Node>();
		for (int i=0; i<256; i++) {
			Node new_Node = new Node(i, bit_source.next(8));
			sorted_Nodes.add(new_Node);
		}
		
		

		Collections.sort(sorted_Nodes, Node.sorted);
		
		
		
		int num_syms = bit_source.next(32);
		System.out.println(num_syms);
	
		TreeBuilder tree = new TreeBuilder(sorted_Nodes, num_syms);
		tree.buildTree();
		Node root = tree.getRoot();
		Node current = root;
		
		FileOutputStream fos = new FileOutputStream(output_file_name);
		int i = 0;
		while(i < num_syms){
			int bit = bit_source.next(1);
			if(bit == 0){
				current = current.left();
				if(current.getSymbol() !=-1){
					fos.write((char) current.getSymbol());
					i++;
					current = root;
				}
			}else{
				current = current.right();
				if(current.getSymbol() !=-1){
					fos.write((char) current.getSymbol());
					i++;
					current = root;
					}
				}
			
		}
		System.out.println("Decoding Done");
		
		
	
		fis.close();
		
		
		
		
		
		
	}
	
	public static void printList(ArrayList sorted_Nodes){
	
	java.util.Iterator<Node> iter = sorted_Nodes.iterator();
	while(iter.hasNext()){
		Node node = iter.next();
		System.out.println("Symbol: "+ node.getSymbol() + " "+ "Length: "+ node.getLength());
	}
	}
	
	
	
	
}
