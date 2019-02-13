
	private Node _right;
	private int _length;
	private boolean _full;
	private String _code;
	public Node(int symbol, int length){
		_symbol = symbol;
		_left = null;
		_right = null;
		_length = length;
		if(symbol != -1){
			_full = true;
		}else{
			_full = false;
		}
		set_code("");
	}
	
	public int getSymbol(){
		return _symbol;
	}
	public int getLength(){
		return _length;
	}
	
	public void setSymbol(int i){
		_symbol = i;
	}
	
	public void setRight(Node right){
		if(right != null){
			_right = right;
		}
		
	}
	public void setLeft(Node left){
		if(left != null){
			_left = left;
		}
	}
	
	public void setFull(boolean x){
		_full = x;
	}
	
	public Node left(){
		return _left;
	}
	
	public Node right(){
		return _right;
	}
	
	public boolean isInternal(){
		if(this.getSymbol()<0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isFull(){
		if(this.getSymbol() == -1){
			if(this.left() != null && this.right() != null &&this.left().isFull() 
					&& this.right().isFull()){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}
	
	public boolean hasLeft(){
//		System.out.println("Has Left Check: "+ this.getLeft().getSymbol());
		if(_left == null || _left.getSymbol()<0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean hasRight(){
//		System.out.println("Has Left Check: "+ this.getRight().getSymbol());
		if(_right == null || _right.getSymbol()<0){
			return true;
		}else{
			return false;
		}
	}
	
	public String get_code() {
		return _code;
	}
	
	public int get_code_length(){
		return _code.length();
	}

	public void set_code(String _code) {
		this._code = _code;
	}

	public static Comparator<Node> sorted = new Comparator<Node>(){
	public int compare(Node left, Node right){

		
		int comp = left.getLength() - right.getLength();
		if(comp != 0){
			return comp;
		}
		return left.getSymbol() - right.getSymbol();
	}};

}
