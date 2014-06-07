package userinterface.specific;

import model.paramobjects.ParamObjectsAcces;
import userinterface.generic.GUITile;

public class GUIPanelStats extends GUITile{
	public GUIPanelStats( int color,
			GUITile container, int i, int j) {
		super(container.getColumns(),container.getRows(),container.getColumns(), container.getRows(), color, container, i, j);
		
		//show lives
		addElement(new GTIcon(color,this,0,5,"LIVES"));
		addElement(new GTNumber(color,this,1,5,ParamObjectsAcces.getPoLives()));
		//show maxrange
		
		//show maxdecision range
		//show your turn
		addElement(new GTTurn((color+1)%2,this,0,0));
		addElement(new GTNumber((color+1)%2,this,1,0,ParamObjectsAcces.getPoNrOfTurn()));
		//show turn on tile 1-8
		//show max affected tiles
		//show affected tiles
	}
}
