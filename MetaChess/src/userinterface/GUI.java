package userinterface;


//class handels GUI and GUI elements
public class GUI{
	// the raster
	private GUIElement[][] elements;
	// about main view
	// left, right, center -1,1,0
	private float x;
	private float y;
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	private int rows;
	private int columns;
	private float height;
	private float width;
	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public GUI ( int rows, int columns) {
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
