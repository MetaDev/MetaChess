package model.paramobjects;

import meta.MetaConfig;
import model.ExtendedPieceModel;

public class POSwitch extends ParamObject{

    @Override
    public int getParam(ExtendedPieceModel model) {
       return  MetaConfig.getBoardModel().getPlayer().getControlledModel().getType().ordinal();
    }

    @Override
    public void setParam(ExtendedPieceModel model) {
        MetaConfig.getBoardModel().getPlayer().switchPiece();
    }

}
