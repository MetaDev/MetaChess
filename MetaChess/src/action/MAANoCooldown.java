package action;

import model.ExtendedPieceModel;

public class MAANoCooldown implements MetaActionActivity{
	@Override
	public boolean isActive(ExtendedPieceModel model, MetaAction action) {
		int cooldown = model.getCooldown(action.getName());
		return cooldown>=0;
	}
}
