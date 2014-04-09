package meta;

import editor.MetaAcionKeyAndPieceMappingEditor;
import graphic.PieceGraphic;
import graphic.TileGraphic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logic.BoardLogic;
import model.ExtendedModel;
import model.MetaModel;
import model.PlayerExtendedModel;

import org.lwjgl.util.Color;

import action.MetaAction;
import view.extended.BischopRenderer;
import view.extended.BoardRenderer;
import view.extended.GUIRenderer;
import view.extended.KingRenderer;
import view.extended.KnightRenderer;
import view.extended.PawnRenderer;
import view.extended.PieceRenderer;
import view.extended.QueenRenderer;
import view.extended.RookRenderer;
import control.Controller;

//contains and initialises all unique instances
public class MetaMapping {
	public enum PieceRendererType {
		PAWN, ROOK, KNIGHT, BISCHOP, KING, QUEEN
	}

	// hierarchical enum for control types
	// AI is handled in the metaloop differently
	public enum ControllerType {
		INPUTPAWN(ControllerGroup.INPUT), INPUTROOK(ControllerGroup.INPUT), INPUTKNIGHT(
				ControllerGroup.INPUT), INPUTBISCHOP(ControllerGroup.INPUT), INPUTKING(
				ControllerGroup.INPUT), INPUTQUEEN(ControllerGroup.INPUT), RANDOMAIPAWN(
				ControllerGroup.RANDOMAI), RANDOMAIROOK(
				ControllerGroup.RANDOMAI);
		private ControllerGroup group;

		private ControllerType(ControllerGroup group) {
			this.group = group;
		}

		public boolean isInGroup(ControllerGroup group) {
			return this.group == group;
		}

		public enum ControllerGroup {
			INPUT, RANDOMAI;
		}
	}
	//free means that the gui's position is based on absolute coordinates
	public enum GUIPosition {
		LEFT, RIGHT, BOTTOM, TOP, FREE

	}

	public enum ActionType {
		LEFT, RIGHT, UP, DOWN, 
		RANGE1, RANGEPLUS1, RANGEPLUS2, RANGEPLUS4, RANGEPLUS8, 
		RANGEMIN1, RANGEMIN2, RANGEMIN4, RANGEMIN8, 
		UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT, 
		DOWNRIGHT12, DOWNRIGHT21, 
		DOWNLEFT21, DOWNLEFT12, 
		UPRIGHT12, UPRIGHT21, 
		UPLEFT12, UPLEFT21,
		TILEVIEWUP,TILEVIEWDOWN,
		PENETRATELFTILE,NPENETRATELFTILE
	}

	// map metaAction name to metaAction
	private static Map<String, MetaAction> metaActions = new HashMap<>();

	// Mapping MetaAction names to pieces
	private static Map<ControllerType, List<String>> pieceActions = new HashMap<>();

	// the first 2 colors are for the opposing tile and piece colors
	private static Map<Integer, Color> colors = new HashMap<>();

	// all PieceRenderers
	private static Map<PieceRendererType, PieceRenderer> pieceRenderers = new HashMap<>();
	// all Controls
	private static Map<ControllerType, Controller> allControllers = new HashMap<>();

	// key mapping for MetaActions
	private static Map<String, MetaAction> keyToAction = new HashMap<>();
	// board renderer
	private static BoardRenderer boardRenderer;

	// guirenderer
	private static GUIRenderer guiRenderer;

	private static int tileSize = 8 * 64;

	

	
	public static Map<String, MetaAction> getKeyMapping() {
		return keyToAction;
	}

	

	public static int getTileSize() {
		return tileSize;
	}

	public static List<String> getPieceMetaActions(ControllerType type) {
		return pieceActions.get(type);
	}

	public static MetaAction getMetaActionFromKey(String key) {
		return keyToAction.get(key);
	}

	public static void bindMetaActionToInput(String key, MetaAction action) {
		keyToAction.put(key, action);
	}

	// init all necessary constants
	public static void initConstants() {
		// tile entering mapping
		BoardLogic.init();
		// map colors
		setColor(0, new Color(Color.BLACK));
		setColor(1, new Color(Color.WHITE));
		// renderers
		pieceRenderers.put(PieceRendererType.PAWN, new PawnRenderer());
		pieceRenderers.put(PieceRendererType.ROOK, new RookRenderer());
		pieceRenderers.put(PieceRendererType.BISCHOP, new BischopRenderer());
		pieceRenderers.put(PieceRendererType.KING, new KingRenderer());
		pieceRenderers.put(PieceRendererType.KNIGHT, new KnightRenderer());
		pieceRenderers.put(PieceRendererType.QUEEN, new QueenRenderer());

		// Control to metaActionEnum mapping init
		for (ControllerType type : ControllerType.values()) {
			pieceActions.put(type, new ArrayList<String>());
		}
		// Map MetaActions
		MetaAcionKeyAndPieceMappingEditor.init();
		boardRenderer = new BoardRenderer();
		guiRenderer = new GUIRenderer();
		TileGraphic floor = new TileGraphic(0, 0, 1, tileSize, 0, 0, 0, null);
		MetaModel.setBoardModel(new ExtendedModel(floor, null));

		PieceGraphic graphic = new PieceGraphic(0, (TileGraphic) MetaModel
				.getBoardModel().getGraphic(), PieceRendererType.PAWN);
		MetaModel.setPlayer(new PlayerExtendedModel(graphic,
				ControllerType.INPUTROOK, 8, 8, 1));
	}

	public static void addPieceAction(ControllerType controller, String action) {
		pieceActions.get(controller).add(action);
	}

	public static Color getColor(int c) {
		return colors.get(c);
	}

	public static void setColor(int c, Color color) {
		colors.put(c, color);
	}

	public static PieceRenderer getPieceRenderer(PieceRendererType piece) {
		return pieceRenderers.get(piece);
	}

	public static BoardRenderer getBoardRenderer() {
		return boardRenderer;
	}

	public static void setBoardRenderer(BoardRenderer boardRenderer) {
		MetaMapping.boardRenderer = boardRenderer;
	}

	public static GUIRenderer getGuiRenderer() {
		return guiRenderer;
	}

	public static void setGuiRenderer(GUIRenderer guiRenderer) {
		MetaMapping.guiRenderer = guiRenderer;
	}

	public static Controller getController(ControllerType controller) {
		return allControllers.get(controller);
	}

	public static void addMetaAction(String name, MetaAction action) {
		metaActions.put(name, action);
	}

	public static void removeMetaAction(String name) {
		metaActions.remove(name);
	}

	public static MetaAction getMetaAction(String name) {
		return metaActions.get(name);
	}

	public static Map<String, MetaAction> getAllMetaActions() {
		return metaActions;
	}

}
