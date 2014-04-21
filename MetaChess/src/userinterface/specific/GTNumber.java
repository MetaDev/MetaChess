package userinterface.specific;

import model.paramobjects.ParamObject;
import userinterface.generic.GUI1Tile;
import userinterface.generic.GUITile;

public class GTNumber extends GUI1Tile {
	private ParamObject paramObject;
	private int oldParam;

	public GTNumber(int color, GUITile container, int i, int j,
			ParamObject paramObject) {
		super(color, container, i, j);
		this.paramObject = paramObject;
		oldParam = paramObject.getParam();
		updateParam();
	}

	@Override
	public int[][] getGrid() {
		if (oldParam != paramObject.getParam()) {
			oldParam = paramObject.getParam();
			updateParam();
		}
		return grid;
	}

	private void updateParam() {
		reset();
		int oppositeColor =(color+1)%2;
		int column3 = Math.min(oldParam - 24, 8);
		int column2 = Math.min(oldParam - 16, 8);
		int column1 = Math.min(oldParam - 8, 8);
		int column0 = Math.min(oldParam, 8);
		// check if the columns have to be drawn
		if (column0 > 0) {
			for (int i = 0; i < column0; i++) {
				setColorInGrid(1, i, oppositeColor);
			}
			if (column1 > 0) {
				for (int i = 0; i < column1; i++) {
					setColorInGrid(3,  i, oppositeColor);
				}
				if (column2 > 0) {
					for (int i = 0; i < column2; i++) {
						setColorInGrid(5, i, oppositeColor);
					}
					if (column3 > 0) {
						for (int i = 0; i < column3; i++) {
							setColorInGrid(7, i, oppositeColor);
						}
					}
				}
			}
		}
	}


}
