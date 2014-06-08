package meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logic.BoardLogic;
import model.ExtendedBoardModel;
import model.ExtendedTileModel;

import org.lwjgl.input.Controller;
import org.lwjgl.util.Color;

import decision.Decision;
import userinterface.specific.IconLoader;
import view.extended.BischopRenderer;
import view.extended.BoardRenderer;
import view.extended.GUIRenderer;
import view.extended.KingRenderer;
import view.extended.KnightRenderer;
import view.extended.PawnRenderer;
import view.extended.PieceRenderer;
import view.extended.QueenRenderer;
import view.extended.RookRenderer;
import editor.MetaAcionKeyAndPieceMappingEditor;

//contains and initialises all unique instances
public class MetaMapping {
	public enum PieceRendererType {
		PAWN, ROOK, KNIGHT, BISCHOP, KING, QUEEN
	}

	// hierarchical enum for control types
	//no AI so this becomes pawn, king, rook,...
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

	// free means that the gui's position is based on absolute coordinates
	public enum GUIPosition {
		LEFT, RIGHT, BOTTOM, TOP, FREE

	}

	public enum ActionType {
		LEFT, RIGHT, UP, DOWN, RANGE1, RANGEPLUS1, RANGEPLUS2, RANGEPLUS4, RANGEPLUS8, RANGEMIN1, RANGEMIN2, RANGEMIN4, RANGEMIN8, UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT, DOWNRIGHT12, DOWNRIGHT21, DOWNLEFT21, DOWNLEFT12,
		UPRIGHT12, UPRIGHT21, UPLEFT12, UPLEFT21, TILEVIEWUP, TILEVIEWDOWN, PENETRATELFTILE, NPENETRATELFTILE, DECISIONDIRECTIONUP,DECISIONDIRECTIONNONE,NODIRECTION,TURN
	}

	// map metaAction name to metaAction, save all existing MetaActions
	private static Map<String, Decision> metaActions = new HashMap<>();

	// map String "MetaAction name, number , char" to icons for GUI and board representation
	private static Map<String, int[][]> metaActionsIcons = new HashMap<>();

	// Mapping MetaAction names to pieces
	private static Map<ControllerType, List<String>> pieceActions = new HashMap<>();

	// the first 2 colors are for the opposing tile and piece colors
	private static Map<Integer, Color> colors = new HashMap<>();

	// all PieceRenderers
	private static Map<PieceRendererType, PieceRenderer> pieceRenderers = new HashMap<>();
	// all Controls
	private static Map<ControllerType, Controller> allControllers = new HashMap<>();

	// board renderer
	private static BoardRenderer boardRenderer;
	// also keep extended models here

	private static ExtendedBoardModel boardModel;

	//turn cycle of 3 types of movement, only needed for pawn
	private static String[] orthogonalTurn = new String[]{"UP","RIGHT","DOWN","LEFT"};
	
	public static String getDirectionWithTurn(int direction, int turn){
		return orthogonalTurn[(direction+turn)%4];
	}
	
	
	//map ActionTypes corresponding with directions to an int[]
	private static Map<String, int[]> actionDirections= new HashMap<>();
	
	//map metaActions to string input
	private static Map<Integer, Decision> keyBinding=new HashMap<>();
	
	public static void setKeyBinding(int key, Decision decision){
		keyBinding.put(key, decision);
	}
	public static Decision getKeyBinding(int key){
		return keyBinding.get(key);
	}
	public static int[][] getIcon(String name) {
		return metaActionsIcons.get(name);
	}
	public static void setIcon(String name,int[][] icon){
		metaActionsIcons.put(name,icon);
	}

	public static ExtendedBoardModel getBoardModel() {
		return boardModel;
	}

	public static void setBoardModel(ExtendedBoardModel boardModel) {
		MetaMapping.boardModel = boardModel;
	}

	// guirenderers
	private static GUIRenderer guiRenderer;

	private static int tileSize = 8 * 64;

	public static int getTileSize() {
		return tileSize;
	}

	public static List<String> getPieceMetaActions(ControllerType type) {
		return pieceActions.get(type);
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
		ExtendedTileModel floor = new ExtendedTileModel( 1, tileSize);
		MetaMapping.setBoardModel(new ExtendedBoardModel(floor));
		//draw MetaAction icons
		IconLoader.loadIcons();
		//fill the directions of movement 
		actionDirections.put("UP", new int[]{0,1});
		actionDirections.put("DOWN", new int[]{0,-1});
		actionDirections.put("RIGHT", new int[]{1,0});
		actionDirections.put("LEFT", new int[]{-1,0});

	}
	public static int[] getActionDirection(String action){
		return actionDirections.get(action);
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

	public static void addDecision(String name, Decision action) {
		metaActions.put(name, action);
	}

	public static void removeDecision(String name) {
		metaActions.remove(name);
	}

	public static Decision getDecision(String name) {
		return metaActions.get(name);
	}

	public static Map<String, Decision> getAllDecisions() {
		return metaActions;
	}

}
