package userinterface.specific;

import model.paramobjects.ParamObject;
import userinterface.generic.GUI1Tile;
import userinterface.generic.GUITile;

public class GTMetaActionNumber extends GUI1Tile {
	private ParamObject paramObject;
	private int oldParam;

	public GTMetaActionNumber(int color, GUITile container, int i, int j,
			ParamObject paramObject) {
		super(color, container, i, j);
		this.paramObject = paramObject;
		oldParam = paramObject.getParam();
		updateParam();
	}

	@Override
	public GUITile[][] getElements() {
		if (oldParam != paramObject.getParam()) {
			oldParam = paramObject.getParam();
			updateParam();
		}
		return elements;
	}

	private void updateParam() {
		reset();
		int oppositeColor = (color + 1) % 2;
		int column3 = oldParam - 24;
		int column2 = oldParam - 16;
		int column1 = oldParam - 8;
		int column0 = oldParam;
		if (column3 > 0) {
			for (int i = 0; i < column3; i++) {
				setColorInGrid(elements, 6, i, oppositeColor);
			}
		}
		if (column2 > 0) {
			for (int i = 0; i < column2; i++) {
				setColorInGrid(elements, 4, i, oppositeColor);
			}
		}
		if (column1 > 0) {
			for (int i = 0; i < column1; i++) {
				setColorInGrid(elements, 2, i, oppositeColor);
			}
		}
		if (column0 > 0) {
			for (int i = 0; i < column0; i++) {
				setColorInGrid(elements, 0, i, oppositeColor);
			}
		}
	}
	private void reset(){
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j<=6 ;j+=2 ){
				setColorInGrid(elements, j,i, color);
			}
		}
		
	}

}
