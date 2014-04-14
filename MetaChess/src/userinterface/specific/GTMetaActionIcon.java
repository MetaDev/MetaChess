package userinterface.specific;

import meta.MetaMapping;
import userinterface.generic.GUI1Tile;
import userinterface.generic.GUITile;
import action.MetaAction;

public class GTMetaActionIcon extends GUI1Tile{
	private int[][] icon;
	public GTMetaActionIcon(int color, GUITile container, int i, int j,MetaAction metaAction) {
		super(color, container, i, j);
		icon=MetaMapping.getMetaActionIcon(metaAction.getName());
		setIcon();
	}

	public void setIcon(){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(elements[i][j]!=null){
					elements[i][j].setColor(icon[i][j]);
				}else{
					setColorInGrid(elements, i, j, icon[i][j]);
				}
			}
		}
	}

}
