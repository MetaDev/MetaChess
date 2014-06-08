package model.paramobjects;

import meta.MetaMapping;

public class POView implements ParamObject{

	@Override
	public int getParam() {
		return MetaMapping.getBoardModel().getPlayer().getControlledModel().getNrOfViewTiles();
	}

	@Override
	public void setParam(int param) {
		 MetaMapping.getBoardModel().getPlayer().getControlledModel().setNrOfViewTiles(param);
	}

}
