package model.paramobjects;

import meta.MetaClock;
import model.ExtendedPieceModel;

public class PONrOfTurn extends PlayerStatsAccessObject{
	//return turn relative to parent
	

    @Override
    public int getParam() {
       return MetaClock.getRelativeTileTurn(getPlayer());
    }

   

}
