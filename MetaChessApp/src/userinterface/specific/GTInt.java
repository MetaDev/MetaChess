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
    public String getGrid() {
        updateRows();
        return grid;
    }

    protected void updateRows() {

        int oppositeColor = (color + 1) % 2;

        int parameterValue = getParam();
       //use param to get correct grid

    }

}
