package model.paramobjects;

import model.ExtendedPieceModel;

public class POCommand extends ParamObject {

    @Override
    public int getParam(ExtendedPieceModel piece) {
        return piece
                .getCommand();
    }

    @Override
    public void setParam(ExtendedPieceModel piece) {
        piece.setCommand();
    }

}
