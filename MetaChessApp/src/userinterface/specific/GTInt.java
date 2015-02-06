package userinterface.specific;

import userinterface.accesobject.PlayerStatsAccessObject;
import userinterface.generic.GUI1Tile;
import userinterface.generic.GUITile;

public class GTInt extends GUI1Tile {

    protected PlayerStatsAccessObject paramObject;

    public GTInt(int color, GUITile container, int i, int j,
            PlayerStatsAccessObject paramObject) {
        super(color, container, i, j);
        this.paramObject = paramObject;
        this.transparant = true;

    }

    protected int getParam() {
        return paramObject.getParam();
    }

    @Override
    public int[][] getGrid() {
        updateRows();
        return grid;
    }

    protected void updateRows() {

        int oppositeColor = (color + 1) % 2;

        int parameterValue = getParam();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                parameterValue--;
                //draw the number in the color
                if (parameterValue >= 0) {
                    setColorInGrid(i, j, color);
                }
                //draw the background in opposite color
                else{
                    setColorInGrid(i, j, oppositeColor);
                }
            }
          
        }

    }

}
