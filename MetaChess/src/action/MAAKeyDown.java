package action;

import model.ExtendedPieceModel;

import org.lwjgl.input.Keyboard;

public class MAAKeyDown extends MetaActionActivity{
	private int key;

	public MAAKeyDown(int key) {
		this.key = key;
	}

	@Override
	public boolean getTurnsOfActivity(ExtendedPieceModel model, MetaAction metaAction) {
		if (Keyboard.isKeyDown(key)){
			 return super.getTurnsOfActivity(model, metaAction) &&  true;
		}
		return false;
	}
	
}
