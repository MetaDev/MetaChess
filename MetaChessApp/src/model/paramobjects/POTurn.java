package model.paramobjects;

import meta.MetaConfig;
import model.ExtendedPieceModel;

public class POTurn extends PlayerStatsAccessObject {

    @Override
    public int getParam() {
        return getPlayer().getControlledModel().getTurn();
    }


}
