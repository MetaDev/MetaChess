package userinterface.generic;

public class GUI1Tile extends GUITile {
	// standard tile has an 8x8 grid
	public GUI1Tile(int color, GUITile container, int i, int j) {
		super(1, 1, 8, 8, color, container, i, j);
		elements = new GUITile[8][8];
	}

	public GUI1Tile(int size, int color, GUITile container, int i, int j) {
		super(1, 1, size, size, color, container, i, j);
		elements = new GUITile[size][size];
	}

	public void setColorInGrid(GUITile[][] elements, int i, int j, int color) {
		// if there's already an element in the grid, only change it's color
		if (elements[i][j] != null) {
			elements[i][j].setColor(color);
		} else {
			elements[i][j] = new GUITileSquare(color, this, i, j);
		}

	}

	

	public GUITile[][] copyToGrid(int[][] icon) {
		GUITile[][] copy = new GUITile[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				copy[i][j] = new GUITileSquare((icon[i][j] + color) % 2, this,
						i, j);
			}
		}
		return copy;
	}

	// a GUITile always has height/width ratio of 1 (square tile)
	@Override
	public float getHeight() {
		float height = super.getHeight();
		float width = super.getWidth();
		return Math.min(height, width);
	}

	@Override
	public float getWidth() {
		return getHeight();
	}

}
