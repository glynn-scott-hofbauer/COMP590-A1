package app;

import java.awt.List;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import ac.EncoderNode;
import ac.Node;
import ac.TreeBuilder;
import io.BitSink;
import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;
import io.OutputStreamBitSink;


public class EncodeTextFile {
	
	private static ArrayList<Node> final_nodes = new ArrayList<Node>();
	private static HashMap<Integer, String> map = new HashMap<Integer, String>();
	private static Node[] array = new Node[256];
	public static void main(String [] args) throws InsufficientBitsLeftException, IOException{
	
		String input_file_name = "uncompressed.txt";
		String output_file_name = "new.txt";
		FileInputStream fis = new FileInputStream(input_file_name);
		
		int[] symbol_counts = new int[256];
		int num_symbols = 0;
	
		while(fis.available() >0){
			int symbol = fis.read();
			symbol_counts[symbol]++;
			num_symbols++;
		}
		fis.close();
		
		ArrayList<EncoderNode> huff_nodes = new ArrayList<EncoderNode>();
		ArrayList<EncoderNode> list = new ArrayList<EncoderNode>();
		
		for(int i = 0; i < 256; i++){
			huff_nodes.add(new EncoderNode( i,(double) symbol_counts[i]/num_symbols));	
		}
		
		
//		printFreq(huff_nodes);
		//build huffman tree

		Collections.sort(huff_nodes, EncoderNode.sorted);
		System.out.println("Bits per Symbol: " + calc_Entropy(huff_nodes));
		
		ArrayList<EncoderNode> clone =  (ArrayList<EncoderNode>) huff_nodes.clone();
		
		for(int i = 0; i< 256; i++){
			list.add(huff_nodes.get(i));
		}
		
		
		// building the huffman tree 
		while(huff_nodes.size() >1){
		//get two smallest
		EncoderNode x = huff_nodes.get(0);
		EncoderNode y = huff_nodes.get(1);
		//remove two smallest
		huff_nodes.remove(x);
		huff_nodes.remove(y);
		//create new node with combined freqencies 
		EncoderNode new_node = new EncoderNode(-1, x.get_freq()+y.get_freq());
		//set height of new node of max of the two plus 1
		new_node.set_height(Math.max(x.get_height(), y.get_height())+1);	
		
		//setting children 
		new_node.set_Left(x);
		new_node.set_right(y);
		huff_nodes.add(new_node);
		//resorting
		Collections.sort(huff_nodes, EncoderNode.sorted);
		}
		
		
		printCode(huff_nodes.get(0), "");
		
		
		
		System.out.println();
		
//		print_canonical_lengths(final_nodes);
		
		final_nodes.sort(Node.sorted);
		
		TreeBuilder tree = new TreeBuilder(final_nodes, num_symbols);
		tree.buildTree();
		Node tree_root = tree.getRoot();
		
		printCanonical(tree_root,"");
		
		
        
		
		FileOutputStream fos = new FileOutputStream(output_file_name);
		OutputStreamBitSink bit_sink = new OutputStreamBitSink(fos);
//        System.out.println(map.get(32));
        //System.out.println("Map size: " + map.size());
        for(int i= 0; i<map.size(); i++){
        	bit_sink.write(map.get(i).length(), 8);
        }
        
        System.out.println("Compressed Bits/Symbol" + calc_realEntorpy(clone));
        
        bit_sink.write(num_symbols, 32);
        
        fis = new FileInputStream(input_file_name);
        
        while(fis.available() > 0){
        	
	        int x = fis.read();
	        bit_sink.write(map.get(x));
        }
        
        bit_sink.padToWord();

		// Close files.
		fis.close();
		fos.close();
       
		
	
		
		
		
		System.out.println("Encoding is Done");
	
	
	}
	
	public static double calc_Entropy(ArrayList _nodes){
		Iterator<EncoderNode> iter = _nodes.iterator();
		double sum = 0;
		while(iter.hasNext()){
			EncoderNode node = iter.next();
			double freq = node.get_freq();
			if(freq != 0 ){
			System.out.println(freq * log2(1/freq));
			sum = sum + freq * log2(freq);
			}
		}
	
		return -sum;
		
	}
	public static double log2(double n){
	    return (Math.log(n) / Math.log(2));
	}
	
	public static double calc_realEntorpy(ArrayList<EncoderNode> list){
		double sum  = 0;
		for(int i=0; i < map.size();i++){	
			int length = map.get(i).length();
			sum = sum + ( list.get(i).get_freq()* log2(length));
		}
		return sum;
		
	}
	
	
	
	
	
	
	public static void printCode(EncoderNode root, String s) { 
		
        if(root.get_left() == null && root.get_right() == null){
        	root.add_code(s);
        	final_nodes.add(new Node(root.get_symbol(), s.length()));
        }
		if(root.get_left() != null){
			printCode(root.get_left(), s + "0"); 
		}
		if(root.get_right() != null){

			printCode(root.get_right(), s + "1"); 
		}
    }
	public static void printCanonical(Node root, String s) { 
		
        if(root.left() == null && root.right() == null){
        	root.set_code(s);
        	map.put(root.getSymbol(), s);

        }
		if(root.left() != null){
			printCanonical(root.left(), s + "0"); 
		}
		if(root.right() != null){

			printCanonical(root.right(), s + "1"); 
		}
		
		
		
    }
	
	
	public static void printFreq(ArrayList huff_nodes){
		
		Iterator<EncoderNode> iter = huff_nodes.iterator();
		while(iter.hasNext()){
			EncoderNode node = iter.next();
			System.out.println("Symbol: "+ node.get_symbol() + " "+ "Code : "+ node.get_freq());
		}
		
	}
	
	public static void print_codeLengths(ArrayList huff_nodes){
		
		Iterator<EncoderNode> iter = huff_nodes.iterator();
		while(iter.hasNext()){
			EncoderNode node = iter.next();
			System.out.println("Symbol: "+ node.get_symbol() + " "+ "CodeLength : "+ node.get_code_length());
		}
	}
		
	public static void print_canonical_lengths(ArrayList nodes){
		
		Iterator<Node> iter = nodes.iterator();
		while(iter.hasNext()){
			Node node = iter.next();
			System.out.println("Symbol: "+ node.getSymbol() + " "+ "CodeLength : "+ node.getLength());
	}
		
		
	
	
		
	}
	

	
	
	
	
	
	

}
