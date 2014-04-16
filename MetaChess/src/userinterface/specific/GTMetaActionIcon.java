package userinterface.specific;

import meta.MetaMapping;
import userinterface.generic.GUI1Tile;
import userinterface.generic.GUITile;

public class GTMetaActionIcon extends GUI1Tile{
	private int[][] icon;
	public GTMetaActionIcon(int color, GUITile container, int i, int j,String name) {
		super(color, container, i, j);
		icon=MetaMapping.getMetaActionIcon(name);
		parseIconFromMatrix();
	}

	public void parseIconFromMatrix(){
		
		for(int i=0;i<8;i++){
			for(int j=7;j>=0;j--){
				if(elements[i][7-j]!=null){
					//visually the strings representing the rows of icon int[][] are actually columns
					//also the origin of the string representation is upper right not upper left
					elements[i][7-j].setColor(icon[j][i]);
				}else{
					setColorInGrid(elements, i, 7-j, icon[j][i]);
				}
			}
		}
	}

}
