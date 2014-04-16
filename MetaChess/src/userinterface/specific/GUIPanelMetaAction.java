package userinterface.specific;

import meta.MetaMapping;
import model.paramobjects.ParamObjectsAcces;
import userinterface.generic.GUITile;

public class GUIPanelMetaAction extends GUITile{
	
	public GUIPanelMetaAction( int color,
			GUITile container, int i, int j) {
		super(4,4,4, 4, color, container, i, j);
		//tileview on lowest row
		addElement(new GTMetaActionCooldownTurnsActive((color+1)%2,this,1,0,MetaMapping.getMetaAction("TILEVIEW")));
		addElement(new GTMetaActionIcon((color+1)%2,this,0,0,"TILEVIEW"));
		addElement(new GTMetaActionNumber((color+1)%2,this,2,0,ParamObjectsAcces.getPOView()));
		//range on new row
		addElement(new GTMetaActionIcon((color+1)%2,this,0,1,"MOVRANGE"));
		addElement(new GTMetaActionNumber((color+1)%2,this,1,1,ParamObjectsAcces.getPOMovement()));
	}
	//dynamically add MetaActions to elements
	//use a queue to show only the most recently used and acivated
	
}
