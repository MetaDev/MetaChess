package model.paramobjects;

import meta.MetaConfig;
import meta.MetaUtil;

public class POAffectedTiles implements ParamObject{

	@Override
	public int getParam() {
		return MetaUtil.getKeyCountByValue(MetaConfig.getBoardModel().getActiveMetaActionsActor(),MetaConfig.getBoardModel().getPlayer().getControlledModel());
	}

	@Override
	public void setParam(int param) {
		 //can't be set
	}

}
