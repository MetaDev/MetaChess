package userinterface.specific;


import userinterface.accesobject.POLives;
import userinterface.generic.GUITile;

public class GUISideStatus extends GUITile{
	public GUISideStatus( int color,
			GUITile container, int i, int j) {
		super(container.getColumns(),container.getRows(),container.getColumns(), container.getRows(), color, container, i, j);
		
		//show lives
		addElement(new GTType(color, this, 0, 5));
		addElement(new GTInt64(color,this,1,5,new POLives()));
		//show maxrange
		
		//show maxdecision range
		//show your turn
		//addElement(new GTBool((color+1)%2,this,0,0));
		//show turn on tile 1-8
		//show max affected tiles
		//show affected tiles
	}
}
