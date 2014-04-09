package editor;

import userinterface.TileGraphic;
import model.MetaModel;


public class BoardEditor extends Editor {

	
	public static void init(){
		//make this equal to initial windowsize or else->problem
		
		// draw floor tiles
		TileGraphic floor = (TileGraphic)MetaModel.getBoardModel().getRootTile();
		floor.divide(8);
		floor.getChildren()[0][1].divide(2);
		floor.getChildren()[2][4].divide(8);
		floor.getChildren()[3][4].divide(8);
		floor.getChildren()[2][6].divide(8);
		floor.getChildren()[6][2].divide(4);
		floor.getChildren()[0][0].divide(2);
		floor.getChildren()[0][0].getChildren()[0][0].divide(2);
		
		
	}

}
