package model.paramobjects;

import meta.MetaMapping;
import meta.MetaUtil;

public class POAffectedTiles implements ParamObject{

	@Override
	public int getParam() {
		return MetaUtil.getKeyCountByValue(MetaMapping.getBoardModel().getActiveMetaActionsActor(),MetaMapping.getBoardModel().getPlayer());
	}

	@Override
	public void setParam(int param) {
		 //can't be set
	}

}
