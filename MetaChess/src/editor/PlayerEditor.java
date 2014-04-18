package editor;

import logic.BoardLogic;
import meta.MetaMapping;
import meta.MetaMapping.ControllerType;
import meta.MetaMapping.PieceRendererType;
import model.ExtendedPlayerModel;
import model.ExtendedTileModel;


public class PlayerEditor extends Editor {
	public static void init() {
		buildPlayer();
		
	}
	private static void buildPlayer(){
		ExtendedPlayerModel player = new ExtendedPlayerModel(PieceRendererType.ROOK,1,ControllerType.INPUTROOK,8,8,8,4,"test");
		int[] I = {0,0,0};
		ExtendedTileModel pos = BoardLogic.getTile(I, I);
		MetaMapping.getBoardModel().setPlayerPosition(player,pos);
	}
	
	
}
