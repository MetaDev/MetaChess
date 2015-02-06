package userinterface.accesobject;

import meta.MetaConfig;
import engine.piece.ExtendedPieceModel;

public class PORange extends PlayerStatsAccessObject{

    @Override
    public int getParam() {
        return getPlayer().getRange();
    }

  

	

}
