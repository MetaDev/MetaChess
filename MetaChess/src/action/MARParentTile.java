package action;

import java.util.ArrayList;
import java.util.List;

import userinterface.TileGraphic;
import model.ExtendedPieceModel;
import model.MetaModel;

public class MARParentTile implements MetaActionRange{

	@Override
	public List<TileGraphic> getRange(ExtendedPieceModel model, MetaAction action) {
		TileGraphic currTile =MetaModel.getPiecePosition(model);
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
