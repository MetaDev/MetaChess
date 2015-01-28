package userinterface.generic;

public class GUI1Tile extends GUITile {
	// standard tile has an 8x8 grid
	protected int[][] grid;
	public GUI1Tile(int color, GUITile container, int i, int j) {
		super(1, 1, 1, 1, color, container, i, j);
		grid = new int[8][8];
	}
	public GUI1Tile(int color, GUITile container, int i, int j,int[][] grid) {
		super(1, 1, 1, 1, color, container, i, j);
		this.grid = grid;
	}
	protected void clear() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j <8; j ++) {
				setColorInGrid(i, j, color);
			}
		}

	}
	

	public void setColorInGrid(int[][] elements, int i, int j, int color) {
		//column and row are switched
		elements[i][j]=(color+elements[i][j])%2;
	}
	public void setColorInGrid( int i, int j, int color) {
		grid[i][j]=color;
	}

	public int[][] getGrid(){
		return grid;
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
