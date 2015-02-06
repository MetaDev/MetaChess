package userinterface.accesobject;

import meta.MetaConfig;
import engine.piece.ExtendedPieceModel;

public class POLives extends PlayerStatsAccessObject{

    @Override
    public int getParam() {
        return getPlayer().getControlledModel().getLives();
    }

  


}
