package model.paramobjects;

import meta.MetaClock;
import model.ExtendedPieceModel;

public class PONrOfTurn extends ParamObject{
	//return turn relative to parent
	

    @Override
    public int getParam(ExtendedPieceModel model) {
       return MetaClock.getRelativeTileTurn();
    }

    @Override
    public void setParam(ExtendedPieceModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
