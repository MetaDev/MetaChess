package userinterface.specific;

import logic.MetaClock;
import meta.MetaMapping;
import userinterface.generic.GUI1Tile;
import userinterface.generic.GUITile;

public class GTTurn extends GUI1Tile {
	// a second possibility for the grid depending on the players turn, this
	// grid represents not your turn
	private int[][] grid1;

	public GTTurn(int color, GUITile container, int i, int j) {
		super(color, container, i, j);
		// construct both grids
		int squareColor = (color + 1) % 2;
		grid = MetaMapping.getIcon("YOURTURN");
		grid1 =  MetaMapping.getIcon("NOTYOURTURN");
		
	}

	@Override
	public int[][] getGrid() {
		boolean turn = MetaClock.getTurn(MetaMapping.getBoardModel()
				.getPlayer());
		// get fraction and side form player singleton
		if (turn) {
			return grid;
		} else {
			return grid1;
		}
	}

}
