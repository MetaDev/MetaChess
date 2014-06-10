package userinterface.specific;

import meta.MetaConfig;
import model.paramobjects.ParamObjectsAcces;
import userinterface.generic.GUITile;

public class GUIPanelMetaAction extends GUITile{
	
	public GUIPanelMetaAction( int color,
			GUITile container, int i, int j) {
		super(container.getColumns(),container.getRows(),container.getColumns(), container.getRows(), color, container, i, j);
		//tileview on lowest row
		addElement(new GTMetaActionCooldownTurnsActive((color+1)%2,this,1,0,"TILEVIEW"));
		addElement(new GTIcon((color+1)%2,this,0,0,"TILEVIEW"));
		addElement(new GTNumber((color+1)%2,this,2,0,ParamObjectsAcces.getPOView()));
		//range on new row
		addElement(new GTIcon((color+1)%2,this,0,2,"MOVRANGE"));
		addElement(new GTNumber((color+1)%2,this,1,2,ParamObjectsAcces.getPOMovement()));
	}
	//dynamically add MetaActions to elements
	//use a queue to show only the most recently used and acivated
	
}
