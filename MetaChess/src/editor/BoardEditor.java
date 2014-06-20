package editor;

import meta.MetaConfig;
import model.ExtendedTileModel;


public class BoardEditor extends Editor {

	
	public static void init(){
		
			
		// draw floor tiles
		ExtendedTileModel floor = (ExtendedTileModel)MetaConfig.getBoardModel().getRootTile();
		//make manditory 8 tiles
		floor.divide(8);
		
		//now randomly change the board
		

		
		
	}

}
