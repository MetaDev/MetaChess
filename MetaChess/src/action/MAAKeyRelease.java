package action;

import org.lwjgl.input.Keyboard;

import meta.MetaMapping;
import meta.MetaUtil;
import model.ExtendedPieceModel;

public class MAAKeyRelease implements MetaActionActivity{
	@Override
	public boolean isActive(ExtendedPieceModel model, MetaAction action) {
		String MetaInput = MetaUtil.getKeyByValue(MetaMapping.getKeyMapping(), action);
		MetaInput =  MetaInput.replaceAll("\\D+","");
		int key = Integer.parseInt(MetaInput);
		return Keyboard.isKeyDown(key);
	}
}
