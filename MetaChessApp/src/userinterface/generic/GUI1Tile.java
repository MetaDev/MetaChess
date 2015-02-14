package userinterface.generic;

public class GUI1Tile extends GUITile {
	// standard tile has an 8x8 grid
	protected String grid;
	public GUI1Tile(int color, GUITile container, int i, int j) {
		super(1, 1, 1, 1, color, container, i, j);
	}
	public GUI1Tile(int color, GUITile container, int i, int j,String grid) {
		super(1, 1, 1, 1, color, container, i, j);
		this.grid = grid;
	}
	
	

	public void setColorInGrid(int[][] elements, int i, int j, int color) {
		//column and row are switched
		elements[i][j]=(color+elements[i][j])%2;
	}
	
	public String getGrid(){
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
