package userinterface.specific;

import engine.MetaClock;
import meta.MetaConfig;
import userinterface.accesobject.PlayerStatsAccessObject;
import userinterface.generic.GUI1Tile;
import userinterface.generic.GUITile;

public class GTBool extends GUI1Tile {
	// a second possibility for the grid depending on the players turn, this
    // grid represents not your turn

    private int[][] grid1;
private PlayerStatsAccessObject paramObject;
    public GTBool(int color, GUITile container, int i, int j,PlayerStatsAccessObject paramObject) {
        super(color, container, i, j);
        // construct both grids
        this.paramObject=paramObject;
        this.transparant=true;
        grid = MetaConfig.getIcon("YOURTURN");
        grid1 = MetaConfig.getIcon("NOTYOURTURN");

    }

    @Override
    public int[][] getGrid() {
        boolean on = PlayerStatsAccessObject.intToBoolean(paramObject.getParam());
        // get fraction and side form player singleton
        if (on) {
            return grid;
        } else {
            return grid1;
        }
    }

}
