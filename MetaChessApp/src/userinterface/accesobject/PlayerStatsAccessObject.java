package userinterface.accesobject;

import meta.MetaConfig;
import engine.player.Player;

public abstract class PlayerStatsAccessObject {
    public abstract int getParam();
  
    protected Player getPlayer(){
        return MetaConfig.getBoardModel().getInputPlayer();
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
