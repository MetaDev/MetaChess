package userinterface.generic;

//class handels GUI and GUI elements
public class GUITile {

    // a grid

    protected GUITile[][] elements;
    // number of blocks it takes in the container
    protected int rowsInContainer;
    protected int columnsInContainer;
	// number of rows and columns it has itself, could be accessed through the
    // size of elements
    protected int rows;
    protected int columns;

    protected int color;
    protected GUITile container;
    protected int i;
    protected int j;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public GUITile getContainer() {
        return container;
    }

    public void setContainer(GUITile container) {
        this.container = container;
    }

    // absolute position and size
    protected float x;
    protected float y;
    protected float height;
    protected float width;

	// get position relative to container
    // position is based on position in container if it has any
    public float getX() {
        if (container != null) {
            return container.getX() + i * (container.getWidth() / container.columns);
        } else {
            return x;
        }

    }

    public float getY() {
        if (container != null) {
            return container.getY() + j * (container.getHeight() / container.rows);
        } else {
            return y;
        }
    }

	// get size relative to container
    public float getHeight() {
        if (container != null) {
            return rowsInContainer * (container.getHeight() / container.rows);
        }
        return height;
    }

    public float getWidth() {
        if (container != null) {
            return columnsInContainer
                    * (container.getWidth() / container.columns);
        }
        return width;
    }

    public GUITile(int columnsInContainer, int rowsInContainer, int columns,
            int rows, int color, GUITile container, int i, int j) {
        this.rowsInContainer = rowsInContainer;
        this.columnsInContainer = columnsInContainer;
        this.rows = rows;
        this.columns = columns;
        this.color = color;
        this.container = container;
        this.i = i;
        this.j = j;
    }

    // root container
    public GUITile(int columns, int rows, int color, GUITile container, int i,
            int j) {
        this.rowsInContainer = 1;
        this.columnsInContainer = 1;
        this.rows = rows;
        this.columns = columns;
        this.color = color;
        this.container = container;
        this.i = i;
        this.j = j;
    }

    public GUITile[][] getElements() {
        return elements;
    }

    private void addElement(GUITile[][] elements, GUITile element) {
        if (elements != null) {
            element.setContainer(this);
            elements[element.getI()][element.getJ()] = element;
        }
    }

    public void addElement(GUITile element) {
        if (elements == null) {
            elements = new GUITile[columns][rows];
        }
        addElement(elements, element);
    }

    public void clearTile() {
        elements = null;
    }

	// only in GUITiles the position and size can be set absolutely
    // and only if it has no parent
    public void setX(float x) {
        if (container == null) {
            this.x = x;
        }
    }

    public void setY(float y) {
        if (container == null) {
            this.y = y;
        }
    }

    public void setHeight(float height) {
        if (container == null) {
            this.height = height;
        }
    }

    public void setWidth(float width) {
        if (container == null) {
            this.width = width;
        }
    }

    // check whether GUIElements are overlapping
    public boolean isOverlapping() {
        return false;
    }

    public void reset() {
        elements = null;
    }

    public void refresh() {
        if (elements != null) {
            for (int i = 0; i < elements.length; i++) {
                for (int j = 0; j < elements[0].length; j++) {
                    if (elements[i][j] != null) {
                        elements[i][j].refresh();
                    }
                }
            }
        }
    }

}
