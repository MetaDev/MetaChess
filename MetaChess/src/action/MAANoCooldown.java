package action;

import model.PieceExtendedModel;

public class MAANoCooldown implements MetaActionActivity{
	@Override
	public boolean isActive(PieceExtendedModel model, MetaAction action) {
		int cooldown = model.getCooldown(action.getName());
		return cooldown>=0;
	}
}
