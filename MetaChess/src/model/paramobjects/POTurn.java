package model.paramobjects;

import meta.MetaConfig;
import model.ExtendedPieceModel;

public class POTurn extends ParamObject {

    @Override
    public int getParam(ExtendedPieceModel model) {
        return model.getTurn();
    }

    @Override
    public void setParam(ExtendedPieceModel model) {
        model.setTurn();
    }

}
