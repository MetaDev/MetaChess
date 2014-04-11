package action;

import java.util.ArrayList;
import java.util.List;

import meta.MetaMapping;
import model.ExtendedPieceModel;
import model.ExtendedTileModel;

public class MARParentTile implements MetaActionRange{

	@Override
	public List<ExtendedTileModel> getRange(ExtendedPieceModel model, MetaAction action) {
		ExtendedTileModel currTile =MetaMapping.getBoardModel().getPiecePosition(model);
		ExtendedTileModel parent = currTile.getParent();
		if(parent!=null){
			return convert2DArrayToList(parent.getChildren());
		}
		//no parent, shouldn't happen
		else{
			return null;
		}
		
	}
	private List<ExtendedTileModel> convert2DArrayToList(ExtendedTileModel[][] arr){
		List<ExtendedTileModel> list = new ArrayList<>();
		for(int i=0; i<arr.length;i++) {
			for (int j=0; j<arr.length;j++){
				list.add(arr[i][j]);
			}
		}
		return list;
	}

}
