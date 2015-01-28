package model.paramobjects;

import model.ExtendedPieceModel;

public class PODragon extends ParamObject {

    @Override
    public int getParam(ExtendedPieceModel piece) {
       return booleanToInt(piece.isDragon());
    }

    @Override
    public void setParam(ExtendedPieceModel piece) {
        piece.setDragon();
    }

}
