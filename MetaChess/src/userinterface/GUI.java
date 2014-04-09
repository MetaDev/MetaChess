package userinterface;


//class handels GUI and GUI elements
public class GUI extends Graphic{
	// the raster
	private GUIElement[][] elements;
	// about main view
	// left, right, center -1,1,0

	private int rows;
	private int columns;

	public GUI ( int rows, int columns) {
		super(0,0,0,0,0,0);
		elements = new GUIElement[rows][columns];
		this.rows = rows;
		this.columns = columns;
	}

	public GUIElement[][] getElements() {
		return elements;
	}


	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public void addElement(int j, int i, GUIElement el) {
		elements[i][j] = el;
	}

	// check whether GUIElements are overlapping
	public boolean isOverlapping() {
		return false;
	}

}
