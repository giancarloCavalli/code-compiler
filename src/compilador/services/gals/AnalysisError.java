package compilador.services.gals;

public class AnalysisError extends Exception {
	private static final long serialVersionUID = 1L;
	
	private int position;

	public AnalysisError(String msg, int position) {
		super(msg);
		this.position = position;
	}

	public AnalysisError(String msg) {
		super(msg);
		this.position = -1;
	}

	public int getPosition() {
		return position;
	}

	public String toString() {
		return super.toString() + ", @ " + position;
	}
}
