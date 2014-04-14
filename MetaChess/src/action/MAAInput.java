package action;

import model.ExtendedPieceModel;

import org.lwjgl.input.Keyboard;

public class MAAInput extends MetaActionActivity{
	private int key;

	public MAAInput(int key) {
		this.key = key;
	}

	@Override
	public int getTurnsOfActivity(ExtendedPieceModel model, MetaAction metaAction) {
		if (Keyboard.isKeyDown(key)){
			 return super.getTurnsOfActivity(model, metaAction);
		}
		return 0;
	}
	
}
