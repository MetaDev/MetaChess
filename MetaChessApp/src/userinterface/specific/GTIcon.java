package userinterface.specific;

import meta.MetaConfig;
import userinterface.generic.GUI1Tile;
import userinterface.generic.GUITile;

public class GTIcon extends GUI1Tile{
	public GTIcon(int color, GUITile container, int i, int j,String name) {
		super(color, container, i, j);
		grid=MetaConfig.getIcon(name);
                
	}


}
