package model.paramobjects;

import meta.MetaConfig;

public class POTileView implements ParamObject{

	@Override
	public int getParam() {
		return  MetaConfig.getBoardModel().getPlayer().getControlledModel().getNrOfViewTiles();
	}

	@Override
	public void setParam(int param) {
		 MetaConfig.getBoardModel().getPlayer().getControlledModel().setViewing(param);
	}

}
