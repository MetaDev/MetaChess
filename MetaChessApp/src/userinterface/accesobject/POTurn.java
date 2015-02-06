package userinterface.accesobject;

import meta.MetaConfig;
import engine.piece.ExtendedPieceModel;

public class POTurn extends PlayerStatsAccessObject {

    @Override
    public int getParam() {
        return booleanToInt(getPlayer().hasTurn());
    }


}
