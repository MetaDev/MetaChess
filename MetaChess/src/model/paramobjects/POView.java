package model.paramobjects;

import meta.MetaMapping;

public class POView implements ParamObject{

	@Override
	public int getParam() {
		return MetaMapping.getBoardModel().getPlayer().getNrOfViewTiles();
	}

	@Override
	public void setParam(int param) {
		 MetaMapping.getBoardModel().getPlayer().setNrOfViewTiles(param);
	}

}
