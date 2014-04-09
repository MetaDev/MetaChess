package action;

import model.ExtendedPieceModel;

public class MAAHalfCooldown implements MetaActionActivity{

	@Override
	public boolean isActive(ExtendedPieceModel model, MetaAction action) {
		int cooldown = model.getCooldown(action.getName());
		return cooldown> ((float) action.getCooldown())/2;
	}

}
