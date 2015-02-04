package model.paramobjects;

import meta.MetaConfig;
import model.PlayerModel;

public abstract class PlayerStatsAccessObject {
    public abstract int getParam();
  
    protected PlayerModel getPlayer(){
        return MetaConfig.getBoardModel().getInputPlayer();
    }
    protected int booleanToInt(boolean bool) {
        if (bool) {
            return 1;
        }
        return 0;
    }
}
