package action;

import java.util.ArrayList;
import java.util.List;

import logic.BoardLogic;
import meta.MetaMapping;
import model.ExtendedPieceModel;
import model.ExtendedTileModel;

public class MARStraightDirectionX implements MetaActionRange{

	@Override
	public List<ExtendedTileModel> getRange(ExtendedPieceModel model, MetaAction action) {
		int[] dir = model.getDirection();
		int range = model.getDecisionRange();
		ExtendedTileModel currTile =MetaMapping.getBoardModel().getPiecePosition(model);
		List<ExtendedTileModel> list = new ArrayList<ExtendedTileModel>();
		ExtendedTileModel tempTile = currTile;
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
