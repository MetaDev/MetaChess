package model.paramobjects;

import meta.MetaConfig;
import model.ExtendedPieceModel;

public class POLives extends PlayerStatsAccessObject{

    @Override
    public int getParam() {
        return getPlayer().getControlledModel().getLives();
    }

  


}
