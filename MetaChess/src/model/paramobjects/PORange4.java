package model.paramobjects;

import meta.MetaConfig;
import model.ExtendedPieceModel;

public class PORange4 extends ParamObject{

    @Override
    public int getParam(ExtendedPieceModel model) {
        return model.getRange();
    }

    @Override
    public void setParam(ExtendedPieceModel model) {
        model.setRange(model.getRange()+4);
    }

	

}
