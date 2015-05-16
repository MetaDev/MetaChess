package userinterface.specific;

import engine.board.ExtendedBoardModel;
import res.BitGrids;
import userinterface.accesobject.PlayerStatsAccessObject;
import userinterface.generic.GUI1Tile;
import userinterface.generic.GUITile;

public class GTInt8 extends GUI1Tile {

    protected PlayerStatsAccessObject paramObject;

    public GTInt8(float color, GUITile container, int i, int j,
            PlayerStatsAccessObject paramObject) {
        super(color, container, i, j);
        this.paramObject = paramObject;
        this.transparant = true;
        this.grid="true";
    }

    protected int getParam(ExtendedBoardModel board) {
        return paramObject.getParam(board);
    }

    @Override
    public String getGrid(ExtendedBoardModel board) {
        updateRows(board);
        return grid;
    }

    protected void updateRows(ExtendedBoardModel board) {

       //use param to get correct grid
        grid=BitGrids.getNumericalGrid(getParam(board), BitGrids.NumericalGridBase.eight);

    }

}