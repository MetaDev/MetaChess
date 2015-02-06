package userinterface.accesobject;

import meta.MetaConfig;
import engine.piece.ExtendedPieceModel;

public class POSwitch extends PlayerStatsAccessObject{

    @Override
    public int getParam() {
       return  getPlayer().getControlledModel().getType().ordinal();
    }

    
}
