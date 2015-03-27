package userinterface.specific;

import engine.MetaClock;
import engine.board.ExtendedBoardModel;
import meta.MetaConfig;
import userinterface.accesobject.PlayerStatsAccessObject;
import userinterface.generic.GUI1Tile;
import userinterface.generic.GUITile;

public class GTBool extends GUI1Tile {
	// a second possibility for the grid depending on the players turn, this
    // grid represents not your turn

    private String grid1;
private PlayerStatsAccessObject paramObject;
    public GTBool(float color, GUITile container, int i, int j,PlayerStatsAccessObject paramObject) {
        super(color, container, i, j);
        // construct both grids
        this.paramObject=paramObject;
        this.transparant=true;
        grid = "true";
        grid1 = "false";

    }

    @Override
    public String getGrid(ExtendedBoardModel board) {
        boolean on = PlayerStatsAccessObject.intToBoolean(paramObject.getParam(board));
        // get fraction and side form player singleton
        if (on) {
            return grid;
        } else {
            return grid1;
        }
    }

}
