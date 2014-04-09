package action;

import graphic.PieceGraphic;
import graphic.TileGraphic;

import java.util.ArrayList;
import java.util.List;

import logic.BoardLogic;
import model.PieceExtendedModel;

public class MARStraightDirectionX implements MetaActionRange{

	@Override
	public List<TileGraphic> getRange(PieceExtendedModel model, MetaAction action) {
		int[] dir = model.getDirection();
		int range = model.getDecisionRange();
		TileGraphic currTile =((PieceGraphic) model.getGraphic()).getTile();
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
