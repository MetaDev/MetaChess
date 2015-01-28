package model.paramobjects;

import model.ExtendedPieceModel;

public class PORogue extends ParamObject {

    @Override
    public int getParam(ExtendedPieceModel model) {
        return booleanToInt(model.isRogue());
    }

    @Override
    public void setParam(ExtendedPieceModel model) {
        model.setRogue();
    }

}
