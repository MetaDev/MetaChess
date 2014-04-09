package action;

import model.ExtendedPieceModel;

public interface MetaActionActivity {
	//returns wether the MetaAction is still active based on the current cooldown
	//this can also be based on de metaaction and a pieceextendedmodel
	public boolean isActive(ExtendedPieceModel model, MetaAction action);
 
}
