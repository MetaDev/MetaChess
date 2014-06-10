package editor;

import logic.BoardLogic;
import meta.MetaConfig;
import model.ExtendedPlayerModel;
import model.ExtendedRookModel;
import model.ExtendedTileModel;


public class PlayerEditor extends Editor {
	public static void init() {
		buildPlayer();
		
	}
	private static void buildPlayer(){
		ExtendedPlayerModel player = new ExtendedPlayerModel(1,new ExtendedRookModel(1, 8),"test");
		int[] I = {0,0,0};
		ExtendedTileModel pos = BoardLogic.getTile(I, I);
		MetaConfig.getBoardModel().setPlayerPosition(player,pos);
	}
	
	
}
