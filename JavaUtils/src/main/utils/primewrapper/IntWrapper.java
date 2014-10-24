package utils.primewrapper;

public class IntWrapper {

	private int i;
	
	public IntWrapper() {
	}
	
	public IntWrapper(int i) {
		this.i = i;
	}

	public int val() {
		return this.i;
	}

	public IntWrapper val(int i) {
		this.i = i;
		return this;
	}
	
}
