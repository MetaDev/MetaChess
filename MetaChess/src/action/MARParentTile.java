package action;

import graphic.PieceGraphic;
import graphic.TileGraphic;

import java.util.ArrayList;
import java.util.List;

import model.PieceExtendedModel;

public class MARParentTile implements MetaActionRange{

	@Override
	public List<TileGraphic> getRange(PieceExtendedModel model, MetaAction action) {
		TileGraphic currTile =((PieceGraphic) model.getGraphic()).getTile();
		TileGraphic parent = currTile.getParent();
		if(parent!=null){
			return convert2DArrayToList(parent.getChildren());
		}
		//no parent, shouldn't happen
		else{
			return null;
		}
		
	}
	private List<TileGraphic> convert2DArrayToList(TileGraphic[][] arr){
		List<TileGraphic> list = new ArrayList<>();
		for(int i=0; i<arr.length;i++) {
			for (int j=0; j<arr.length;j++){
				list.add(arr[i][j]);
			}
		}
		return list;
	}

}
