package ac;

import java.util.Comparator;

public class EncoderNode {

	 int _symbol; 
	double _freq;
	 EncoderNode _left; 
	 EncoderNode _right;
	 int _height;
	 String _code;
	
	public EncoderNode(int symbol, double freq){
		_symbol = symbol;
		_freq = freq;
		_left = null;
		_right = null; 
		_height = 0;
		_code = "";
	}
	

	public int get_symbol() {
		return _symbol;
	}

//	public void set_symbol(int _symbol) {
//		this._symbol = _symbol;
//	}

	public double get_freq() {
		return _freq;
	}

	public void set_freq(double _freq) {
		this._freq = _freq;
	}
	
	public void set_Left(EncoderNode left){
		_left = left;
	}
	
	public void set_right(EncoderNode right){
		_right = right;
	}
	
	public EncoderNode get_left(){
		return _left;
	}
	
	public EncoderNode get_right(){
		return _right;
	}
	
	public int get_height(){
		return _height;
	}
	
	public void set_height(int i){
		_height = i;
	}
	
	public void add_code(String s){
		_code = _code +s;
	}
	
	public String get_code(){
		return _code;
	}
	
	public int get_code_length(){
		return _code.length();
	}

	
	

	public static  Comparator<EncoderNode> sorted = new Comparator<EncoderNode>(){
		@Override
		public int compare(EncoderNode x, EncoderNode y) {
			int comp = Double.compare(x.get_freq(), y.get_freq());
			if(comp != 0){
				return comp;
			}
			return x.get_height() - y.get_height();
		}};
		
		public static  Comparator<EncoderNode> code_sorted = new Comparator<EncoderNode>(){
			@Override
			public int compare(EncoderNode x, EncoderNode y) {
				int comp = x.get_code_length() - y.get_code_length();
				if(comp !=0){
					return comp;
				}
				return x.get_symbol() - y.get_symbol();
			}};
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
}
