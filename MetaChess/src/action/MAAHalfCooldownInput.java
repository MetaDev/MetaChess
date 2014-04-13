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
		int actionCooldown = metaAction.getCooldown();
		//while there's still half the cooldown left, the MetaAction stays active
		if(remainingCooldown>actionCooldown/2){
			return actionCooldown/2-remainingCooldown;
		}
		//if the correct key is pressed and the MetaAction is not cooling down it becomes active
		//with half the cooldown left
		if(remainingCooldown==0){
			return actionCooldown/2*isActive;
		}
		//and for all else the MetaAction isn't active
		return 0;
	}

}
