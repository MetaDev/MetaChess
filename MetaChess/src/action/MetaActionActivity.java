package action;

import logic.MetaClock;
import model.ExtendedPieceModel;

public abstract class MetaActionActivity {
	// returns wether the MetaAction is still active based on the current
	// cooldown
	// it returns how much turns the MetaAction is still active, if it returns 0
	// it is no longer active
	// this can also be based on de metaaction and a pieceextendedmodel
	public boolean getTurnsOfActivity(ExtendedPieceModel model, MetaAction metaAction) {
		// check if your turn
		// check if not action locking and model locked
		// check if cooldwon is 0
		if ((MetaClock.getTurn(model) && !model.isLocked())
				|| !metaAction.isLocking()) {
			if (model.getCooldown(metaAction) == 0) {
				return true;
			}
		}
		return false;

	}

}
