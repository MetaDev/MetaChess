package userinterface.specific;

import res.BitGrids;
import userinterface.accesobject.PlayerStatsAccessObject;
import userinterface.generic.GUI1Tile;
import userinterface.generic.GUITile;

public class GTInt8 extends GUI1Tile {

    protected PlayerStatsAccessObject paramObject;

    public GTInt8(int color, GUITile container, int i, int j,
            PlayerStatsAccessObject paramObject) {
        super(color, container, i, j);
        this.paramObject = paramObject;
        this.transparant = true;
        this.grid="true";
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

       //use param to get correct grid
        grid=BitGrids.getNumericalGrid(getParam(), BitGrids.NumericalGridBase.eight);

    }

}
