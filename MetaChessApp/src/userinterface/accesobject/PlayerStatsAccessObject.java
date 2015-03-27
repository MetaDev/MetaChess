package userinterface.accesobject;

import engine.board.ExtendedBoardModel;
import engine.player.Player;

public abstract class PlayerStatsAccessObject {
    public abstract int getParam(ExtendedBoardModel board);
    
    protected Player getPlayer(ExtendedBoardModel board){
        return board.getInputPlayer();
    }
    public static int booleanToInt(boolean bool) {
        if (bool) {
            return 1;
        }
        return 0;
    }
    public static boolean intToBoolean(int i){
        return i==1;
    }
}
