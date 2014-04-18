package action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import logic.BoardLogic;
import meta.MetaMapping;
import meta.MetaMapping.ControllerType;
import model.ExtendedPieceModel;
import model.ExtendedTileModel;

public class MARStraightDirectionX implements MetaActionRange{
	//this will become the main range implementation
	@Override
	public Set<ExtendedTileModel> getRange(ExtendedPieceModel model, MetaAction action) {
		Set<ExtendedTileModel> list = new HashSet<ExtendedTileModel>();
		List<int[]> directions = new ArrayList<>();
		int[] dir = model.getDirection();
		int range = model.getMovementRange();
		//no direction
		if(dir[0]==0 && dir[1]==0){
			//no range, local decision
			if(range==1){
				return list;
			}
			//ranged, undirected decision, unidirected
			else{
				//depends upon controllertype
				ControllerType type = model.getControllerType();
				
				if(type==ControllerType.INPUTROOK || type==ControllerType.INPUTQUEEN || type==ControllerType.INPUTKING ){
					//directions should be saved as constants for each controllertype
					directions.add(new int[]{1,0});
					directions.add(new int[]{0,1});
					directions.add(new int[]{-1,0});
					directions.add(new int[]{0,-1});
				}
				if(type==ControllerType.INPUTBISCHOP || type==ControllerType.INPUTQUEEN || type==ControllerType.INPUTKING){
					directions.add(new int[]{1,1});
					directions.add(new int[]{-1,1});
					directions.add(new int[]{-1,-1});
					directions.add(new int[]{1,-1});
				}
				if(type==ControllerType.INPUTPAWN){
					//implement turn first as decision
				}
				if (type==ControllerType.INPUTKNIGHT){
					directions.add(new int[]{1,2});
					directions.add(new int[]{-1,2});
					directions.add(new int[]{-1,-2});
					directions.add(new int[]{1,-2});
					//...
				}
				
			}
		}
		//directed decision
		else{
			//depends upon controllertype
			directions.add(dir);
		}
		//iterate all directions and save all tiles on path
		ExtendedTileModel currTile =MetaMapping.getBoardModel().getPiecePosition(model);
		for(int[] direction: directions){
			
			ExtendedTileModel tempTile = currTile;
			int i = range;
			while( tempTile!=null && i>0){
				tempTile=BoardLogic.getTileNeighbour(currTile, direction[0], direction[1], false, true, false);
				list.add(tempTile);
				i--;
			}
		}
		
		return list;
	}

}
