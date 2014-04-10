package action;

import model.ExtendedPieceModel;

public interface MetaActionActivity {
	//returns wether the MetaAction is still active based on the current cooldown
	//it returns how much turns the MetaAction is still active, if it returns 0 it is no longer active
	//this can also be based on de metaaction and a pieceextendedmodel
	public int getTurnsOfActivity(ExtendedPieceModel model, MetaAction action);
 
}
