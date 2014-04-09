package editor;

import graphic.PieceGraphic;
import graphic.TileGraphic;
import logic.BoardLogic;
import logic.MetaClock;
import meta.MetaMapping.ControllerType;
import meta.MetaMapping.PieceRendererType;
import model.MetaModel;

//implement as singleton, there is only one player
//Such that the player can be accesed and all it's corresponding data to show in GUI
public class PlayerEditor extends Editor {
	public static void init() {
		buildPlayer(PieceRendererType.ROOK, ControllerType.INPUTROOK);
		
	}
	private static void buildPlayer(PieceRendererType renderType,ControllerType controlType){
		int[] I = {0,0,0};
		TileGraphic pos = BoardLogic.getTile(I, I);
		((PieceGraphic)MetaModel.getPlayer().getGraphic()).setTile(pos);
		((PieceGraphic)MetaModel.getPlayer().getGraphic()).setPiece(renderType);
		MetaModel.getPlayer().setControllerType(controlType);
	}
	
	
}
