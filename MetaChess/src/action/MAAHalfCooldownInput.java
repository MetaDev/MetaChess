package action;

import model.ExtendedPieceModel;

public class MAAHalfCooldownInput extends MAAInput{

	public MAAHalfCooldownInput(int key) {
		super(key);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getTurnsOfActivity(ExtendedPieceModel model, MetaAction metaAction) {
		int isActive = super.getTurnsOfActivity(model, metaAction);
		int remainingCooldown = model.getCooldown(metaAction);
		return (remainingCooldown/2)*isActive;
	}

}
