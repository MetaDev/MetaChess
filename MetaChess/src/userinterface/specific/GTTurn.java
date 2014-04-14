package userinterface.specific;

import logic.MetaClock;
import meta.MetaMapping;
import userinterface.generic.GUI1Tile;
import userinterface.generic.GUITile;
import userinterface.generic.GUITileSquare;

public class GTTurn extends GUI1Tile {
	// a second possibility for the grid depending on the players turn, this
	// grid represents not your turn
	private GUITile[][] elements1;

	public GTTurn(int color, GUITile container, int i, int j) {
		super(color, container, i, j);
		// construct both grids
		int squareColor = (color + 1) % 2;
		elements = new GUITileSquare[columns][rows];
		elements1 = new GUITileSquare[columns][rows];
		setColorInGrid(elements, 3, 3, squareColor);
		setColorInGrid(elements, 4,4, squareColor);
		setColorInGrid(elements, 4, 3, squareColor);
		setColorInGrid(elements, 3, 4, squareColor);
		
		setColorInGrid(elements1, 3, 3, squareColor);
		setColorInGrid(elements1, 4,4, squareColor);
		setColorInGrid(elements1, 4, 3, squareColor);
		setColorInGrid(elements1, 3, 4, squareColor);
		
		setColorInGrid(elements1, 1, 3, squareColor);
		setColorInGrid(elements1, 1,4, squareColor);
		setColorInGrid(elements1, 2, 3, squareColor);
		setColorInGrid(elements1, 2, 4, squareColor);
		setColorInGrid(elements1, 5, 3, squareColor);
		setColorInGrid(elements1, 5,4, squareColor);
		setColorInGrid(elements1, 6, 3, squareColor);
		setColorInGrid(elements1, 6, 4, squareColor);
		
		setColorInGrid(elements, 3, 6, squareColor);
		setColorInGrid(elements, 4,6, squareColor);
		setColorInGrid(elements, 4, 5, squareColor);
		setColorInGrid(elements, 3, 5, squareColor);
		setColorInGrid(elements, 3, 1, squareColor);
		setColorInGrid(elements, 4,1, squareColor);
		setColorInGrid(elements, 4, 2, squareColor);
		setColorInGrid(elements, 3, 2, squareColor);
		
		// elements[3][3] = elements1[3][3] = new GUITileSquare(squareColor,
		
//
//		elements1[1][4] = new GUITileSquare(squareColor, this, 1, 4);
//		elements1[1][3] = new GUITileSquare(squareColor, this, 1, 3);
//		elements1[2][4] = new GUITileSquare(squareColor, this, 2, 4);
//		elements1[2][3] = new GUITileSquare(squareColor, this, 2, 3);
//		elements1[5][4] = new GUITileSquare(squareColor, this, 5, 4);
//		elements1[5][3] = new GUITileSquare(squareColor, this, 5, 3);
//		elements1[6][4] = new GUITileSquare(squareColor, this, 6, 4);
//		elements1[6][3] = new GUITileSquare(squareColor, this, 6, 3);
//
//		elements[3][6] = new GUITileSquare(squareColor, this, 3, 6);
//		elements[4][6] = new GUITileSquare(squareColor, this, 4, 6);
//		elements[3][5] = new GUITileSquare(squareColor, this, 3, 5);
//		elements[4][5] = new GUITileSquare(squareColor, this, 4, 5);
//		elements[3][2] = new GUITileSquare(squareColor, this, 3, 2);
//		elements[3][1] = new GUITileSquare(squareColor, this, 3, 1);
//		elements[4][2] = new GUITileSquare(squareColor, this, 4, 2);
//		elements[4][1] = new GUITileSquare(squareColor, this, 4, 1);

	}

	@Override
	public GUITile[][] getElements() {
		boolean turn = MetaClock.getTurn(MetaMapping.getBoardModel()
				.getPlayer());
		// get fraction and side form player singleton
		if (turn) {
			return elements;
		} else {
			return elements1;
		}
	}

}
