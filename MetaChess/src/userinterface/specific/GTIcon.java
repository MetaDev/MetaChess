package userinterface.specific;

import meta.MetaMapping;
import userinterface.generic.GUI1Tile;
import userinterface.generic.GUITile;

public class GTIcon extends GUI1Tile{
	private int[][] icon;
	public GTIcon(int color, GUITile container, int i, int j,String name) {
		super(color, container, i, j);
		icon=MetaMapping.getIcon(name);
		parseIconFromMatrix();
	}

	public void parseIconFromMatrix(){
		
		for(int i=0;i<8;i++){
			for(int j=7;j>=0;j--){
				if(elements[i][7-j]!=null){
					// the origin (0,0) of the string representation is upper right not upper left
					elements[i][7-j].setColor(icon[i][j]);
				}else{
					setColorInGrid(elements, i, 7-j, icon[i][j]);
				}
			}
		}
	}

}
