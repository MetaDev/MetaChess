package model.paramobjects;

import meta.MetaConfig;
import model.ExtendedPieceModel;

public class POHorizon extends ParamObject {

    @Override
    public int getParam(ExtendedPieceModel model) {
        return booleanToInt(model.getViewing());
    }

    @Override
    public void setParam(ExtendedPieceModel model) {
        model.setViewing();
    }

}
