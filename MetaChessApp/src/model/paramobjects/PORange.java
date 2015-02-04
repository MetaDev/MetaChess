package model.paramobjects;

import meta.MetaConfig;
import model.ExtendedPieceModel;

public class PORange extends PlayerStatsAccessObject{

    @Override
    public int getParam() {
        return getPlayer().getRange();
    }

  

	

}
