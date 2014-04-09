package action;

import java.util.ArrayList;
import java.util.List;

import userinterface.TileGraphic;
import logic.BoardLogic;
import model.ExtendedPieceModel;
import model.MetaModel;

public class MARStraightDirectionX implements MetaActionRange{

	@Override
	public List<TileGraphic> getRange(ExtendedPieceModel model, MetaAction action) {
		int[] dir = model.getDirection();
		int range = model.getDecisionRange();
		TileGraphic currTile =MetaModel.getPiecePosition(model);
		List<TileGraphic> list = new ArrayList<TileGraphic>();
		TileGraphic tempTile = currTile;
		int i = range;
		while( tempTile!=null && i>0){
			tempTile=BoardLogic.getTileNeighbour(currTile, dir[0], dir[1], false, true, false);
			list.add(tempTile);
			i--;
		}
		if(list.isEmpty())
			return null;
		return list;
	}

}
