package action;

import model.PieceExtendedModel;

public class MAAHalfCooldown implements MetaActionActivity{

	@Override
	public boolean isActive(PieceExtendedModel model, MetaAction action) {
		int cooldown = model.getCooldown(action.getName());
		return cooldown> ((float) action.getCooldown())/2;
	}

}
