package action;

import model.ExtendedPieceModel;

public class MAAHalfCooldownInput extends MAAInput {

	public MAAHalfCooldownInput(int key) {
		super(key);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getTurnsOfActivity(ExtendedPieceModel model,
			MetaAction metaAction) {
		int isActive = super.getTurnsOfActivity(model, metaAction);
		int actionCooldown = metaAction.getCooldown();
		// if the correct key is pressed and the MetaAction is not cooling down
		// it becomes active
		// with half the cooldown as time active
		return ( actionCooldown / 2) * isActive;

	}

}
