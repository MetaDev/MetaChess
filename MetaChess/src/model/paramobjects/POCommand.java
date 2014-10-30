package model.paramobjects;

import meta.MetaConfig;

public class POCommand implements ParamObject  {
	@Override
	public int getParam() {
		return MetaConfig.getBoardModel().getPlayer().getControlledModel()
				.getCommand();
	}

	@Override
	public void setParam(int param) {
		//only change if the key is pressed
			MetaConfig.getBoardModel().getPlayer().getControlledModel()
					.setCommand(param);
	}

}
