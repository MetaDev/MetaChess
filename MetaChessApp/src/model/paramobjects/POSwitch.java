package model.paramobjects;

import meta.MetaConfig;
import model.ExtendedPieceModel;

public class POSwitch extends PlayerStatsAccessObject{

    @Override
    public int getParam() {
       return  getPlayer().getControlledModel().getType().ordinal();
    }

    
}
