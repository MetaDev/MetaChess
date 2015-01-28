package model.paramobjects;

import meta.MetaConfig;
import model.ExtendedPieceModel;

public class POTileView extends ParamObject {

    @Override
    public int getParam(ExtendedPieceModel model) {
        return model.getNrOfViewTiles();
    }

    @Override
    public void setParam(ExtendedPieceModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
