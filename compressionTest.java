package ac;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;

public class compressionTest {
	//THIS CLASS TESTS THE ENTROPY FOR BITS/SYMBOL FOR COMPRESSED (1).DAT
	
		
	public static void main(String [] args) throws InsufficientBitsLeftException, IOException{
		String input_file_name = "compressed (1).dat";
		String output_file_name = "uncompressed.txt";
		FileInputStream fis = new FileInputStream(input_file_name);
		InputStreamBitSource bit_source = new InputStreamBitSource(fis);
		
		int[] sym_lengths = new int[256];
		Integer[] symbols = new Integer[256];
		
		int[] symbol_counts = new int[256];
		int num_symbols = 0;
		
		ArrayList<Node> sorted_Nodes = new ArrayList<Node>();
		for (int i=0; i<256; i++) {
			Node new_Node = new Node(i, bit_source.next(8));
			sorted_Nodes.add(new_Node);
		}
		
		int num_syms = bit_source.next(32);
		
		
//		Collections.sort(sorted_Nodes, Node.sorted);
		
		String inputfile_name = "uncompressed.txt";
		
		FileInputStream fiss = new FileInputStream(inputfile_name);
		
		while(fiss.available() >0){
			int symbol = fiss.read();
		
			symbol_counts[symbol]++;
			num_symbols++;
		}
		fiss.close();
		
		
		ArrayList<EncoderNode> huff_nodes = new ArrayList<EncoderNode>();
		for(int i = 0; i < 256; i++){
			huff_nodes.add(new EncoderNode( i,(double) symbol_counts[i]/num_symbols));	
		}
//		Collections.sort(huff_nodes, EncoderNode.sorted);
		
//		System.out.println(huff_nodes.size());
//		
//		java.util.Iterator<EncoderNode> iterr = huff_nodes.iterator();
//		while(iterr.hasNext()){
//			EncoderNode node = iterr.next();
//			System.out.println("Symbol: "+ node._symbol + " "+ "Freq: "+ node.get_freq());
//		
//		}
//		System.out.println("------------------------");
//		java.util.Iterator<Node> iter = sorted_Nodes.iterator();
//		while(iter.hasNext()){
//			Node node = iter.next();
//			System.out.println("Symbol: "+ node.getSymbol() + " "+ "length: "+ node.getLength());
//		
//		}

		
		System.out.println("Bits/Symbol: "+ calc_realEntorpy(huff_nodes, sorted_Nodes ));
	}
	
	public static double calc_realEntorpy(ArrayList<EncoderNode> list_freq, ArrayList<Node> list_count){
		double sum  = 0;
		for(int i=0; i < 256;i++){	
			if(list_freq.get(i)._freq != 0){
			int length = list_count.get(i).getLength();
			System.out.println(list_freq.get(i).get_freq()+ " * " + list_count.get(i).getLength());
			sum = sum + ( list_freq.get(i).get_freq()*length);
			}
		}
		return sum;
		
	}
	
	public static double log2(double n){
	    return (Math.log(n) / Math.log(2));
	}
	
}
