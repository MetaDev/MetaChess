package meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import logic.BoardLogic;
import model.ExtendedBoardModel;
import model.ExtendedTileModel;
import model.paramobjects.PODragon;
import model.paramobjects.POMaxRange;
import model.paramobjects.POSwitch;
import model.paramobjects.POTileView;
import model.paramobjects.POTurn;
import model.paramobjects.ParamObject;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Color;

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

//contains and initialises all unique instances
public class MetaConfig {
	public enum PieceType {
		PAWN, ROOK, KNIGHT, BISCHOP, KING, QUEEN
	}

	// free means that the gui's position is based on absolute coordinates
	public enum GUIPosition {
		LEFT, RIGHT, BOTTOM, TOP, FREE

	}

	// map String "MetaAction name, number , char" to icons for GUI and board
	// representation
	private static Map<String, int[][]> decisionsIcons = new HashMap<>();

	// Mapping MetaAction names to pieces
	private static Map<PieceType, Set<String>> pieceDecisions = new HashMap<>();
	// mapping of range keys
	private static Map<Integer, Integer> rangeKeys = new HashMap<>();

	public static Map<Integer, Integer> getRangeKeys() {
		return rangeKeys;
	}

	// the first 2 colors are for the opposing tile and piece colors
	private static Map<Integer, Color> colors = new HashMap<>();

	// all PieceRenderers
	private static Map<PieceType, PieceRenderer> pieceRenderers = new HashMap<>();
	// all Controls, a mapping of a key number sequence onto decision
	private static Map<PieceType,HashMap<String,String>> keyMapping = new HashMap<>();

	// board renderer
	private static BoardRenderer boardRenderer;
	// also keep extended models here

	private static ExtendedBoardModel boardModel;

	// turn cycle of 3 types of movement, only needed for pawn
	private static String[] orthogonalTurn = new String[] { "[0,1]", "[1,0]",
			"[0,-1]", "[-1,0]" };

	public static Map<PieceType, Set<String>> getPieceDecisions() {
		return pieceDecisions;
	}

	public static void setPieceDecisions(
			Map<PieceType, Set<String>> pieceDecisions) {
		MetaConfig.pieceDecisions = pieceDecisions;
	}
	//map decision name to int key
	public static Map<PieceType,HashMap<String,String>> getKeyMapping() {
		return keyMapping;
	}

	
	public static Map<String, int[]> getOrthogonalSet() {
		return orthogonalSet;
	}

	public static void setOrthogonalSet(Map<String, int[]> orthogonalSet) {
		MetaConfig.orthogonalSet = orthogonalSet;
	}

	public static Map<String, int[]> getDiagonalSet() {
		return diagonalSet;
	}

	public static void setDiagonalSet(Map<String, int[]> diagonalSet) {
		MetaConfig.diagonalSet = diagonalSet;
	}

	public static Map<String, int[]> getHorseSet() {
		return horseSet;
	}

	public static void setHorseSet(Map<String, int[]> horseSet) {
		MetaConfig.horseSet = horseSet;
	}

	public static String getDirectionWithTurn(String direction, int turn) {
		int dir = -1;
		for (int i = 0; i < 4; i++) {
			if (direction.equals(orthogonalTurn[i])) {
				dir = i;
			}
		}
		if (dir != -1)
			return orthogonalTurn[(dir + turn) % 4];
		return null;
	}

	// 3 maps for the directions
	private static Map<String, int[]> orthogonalSet;
	private static Map<String, int[]> diagonalSet;
	private static Map<String, int[]> horseSet;
	private static Map<String, ParamObject> specialsSet = new HashMap<>();

	// for the range decision, check if mapping is
	public static int isNumber(String s) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return -1;
		}
		// only got here if we didn't return false
	}

	// method that converts direction array to key-string
	//we save string and array, because conversion everytime is less cost-effective
	private static Map<String, int[]> mapDirections(List<int[]> directions) {
		Map<String, int[]> map = new HashMap<String, int[]>();
		for (int[] dir : directions) {
			map.put(MetaUtil.convertIntArrayToString(dir), dir);
		}
		return map;
	}

	// method thar return direction array for any movement
	public static int[] getDirectionArray(String direction) {
		if (orthogonalSet.containsKey(direction)) {
			return orthogonalSet.get(direction);
		} else if (diagonalSet.containsKey(direction)) {
			return diagonalSet.get(direction);
		} else if (horseSet.containsKey(direction)) {
			return horseSet.get(direction);
		}
		return null;
	}

	// multiplayer or not
	public static boolean multiPlayer = false;

	public static int[][] getIcon(String name) {
		return decisionsIcons.get(name);
	}

	public static void setIcon(String name, int[][] icon) {
		decisionsIcons.put(name, icon);
	}

	public static ExtendedBoardModel getBoardModel() {
		return boardModel;
	}

	public static void setBoardModel(ExtendedBoardModel boardModel) {
		MetaConfig.boardModel = boardModel;
	}

	// guirenderers
	private static GUIRenderer guiRenderer;

	private static int tileSize = 8 * 64;

	public static int getTileSize() {
		return tileSize;
	}

	public static Set<String> getPieceMetaActions(PieceType type) {
		return pieceDecisions.get(type);
	}
private static void setKeyMappingForAll(Map<String,String> mapping){
	for(PieceType type: PieceType.values()){
		setKeyMappingForPiece(type,mapping);
	}
	
}
private static void setKeyMappingForPiece(PieceType type,Map<String,String> mapping){
	if(keyMapping.get(type)==null){
		keyMapping.put(type, new HashMap<String,String>());
	}
	keyMapping.get(type).putAll(mapping);
	
}
	// init all necessary constants
	public static void initConstants() {
		// tile entering mapping
		BoardLogic.init();
		// map colors
		setColor(0, new Color(Color.BLACK));
		setColor(1, new Color(Color.WHITE));
		// renderers
		pieceRenderers.put(PieceType.PAWN, new PawnRenderer());
		pieceRenderers.put(PieceType.ROOK, new RookRenderer());
		pieceRenderers.put(PieceType.BISCHOP, new BischopRenderer());
		pieceRenderers.put(PieceType.KING, new KingRenderer());
		pieceRenderers.put(PieceType.KNIGHT, new KnightRenderer());
		pieceRenderers.put(PieceType.QUEEN, new QueenRenderer());

		// Map MetaActions
		boardRenderer = new BoardRenderer();
		guiRenderer = new GUIRenderer();
		ExtendedTileModel floor = new ExtendedTileModel(1, tileSize);
		MetaConfig.setBoardModel(new ExtendedBoardModel(floor));

		// draw MetaAction icons
		IconLoader.loadIcons();

		// map all possible directions
		List<int[]> orthogonalList = new ArrayList<int[]>();
		orthogonalList.add(new int[] { 0, 1 });
		orthogonalList.add(new int[] { 0, -1 });
		orthogonalList.add(new int[] { 1, 0 });
		orthogonalList.add(new int[] { -1, 0 });
		orthogonalSet = mapDirections(orthogonalList);
		List<int[]> diagonalList = new ArrayList<int[]>();
		orthogonalList.add(new int[] { 1, 1 });
		orthogonalList.add(new int[] { 1, -1 });
		orthogonalList.add(new int[] { -1, -1 });
		orthogonalList.add(new int[] { -1, 1 });
		diagonalSet = mapDirections(diagonalList);
		List<int[]> horseList = new ArrayList<int[]>();
		orthogonalList.add(new int[] { 1, 2 });
		orthogonalList.add(new int[] { 1, -2 });
		orthogonalList.add(new int[] { -1, -2 });
		orthogonalList.add(new int[] { -1, 2 });
		orthogonalList.add(new int[] { 2, 1 });
		orthogonalList.add(new int[] { 2, -1 });
		orthogonalList.add(new int[] { -2, -1 });
		orthogonalList.add(new int[] { -2, 1 });
		horseSet = mapDirections(horseList);
		//fill specialset with
		specialsSet.put("TILEVIEW", new POTileView());
		specialsSet.put("MAXRANGE", new POMaxRange());
		specialsSet.put("DRAGON", new PODragon());
		specialsSet.put("TURN", new POTurn());
		specialsSet.put("SWITCH", new POSwitch());
		// TODO
		//  map to initial keymapping
		
		//special decisions
		Map<String,String> map = new HashMap<>();
		map.put(Keyboard.KEY_SPACE+"","TILEVIEW");
		setKeyMappingForPiece(PieceType.ROOK,map);
		map = new HashMap<>();
		map.put(Keyboard.KEY_SPACE+"","MAXRANGE");
		setKeyMappingForPiece(PieceType.BISCHOP,map);
		map = new HashMap<>();
		map.put(Keyboard.KEY_SPACE+"","DRAGON");
		setKeyMappingForPiece(PieceType.KNIGHT,map);
		map = new HashMap<>();
		map.put(Keyboard.KEY_SPACE+"","TURN");
		setKeyMappingForPiece(PieceType.PAWN,map);
		map = new HashMap<>();
		map.put(Keyboard.KEY_SPACE+"","RANGEDTILEVIEW");
		map.put(Keyboard.KEY_NUMPAD0+"","RANGEDMAXRANGE");
		setKeyMappingForPiece(PieceType.KING,map);
		//range
		map = new HashMap<>();
		map.put(Keyboard.KEY_NUMPAD1+"","1");
		map.put(Keyboard.KEY_NUMPAD2+"","2");
		map.put(Keyboard.KEY_NUMPAD3+"","3");
		map.put(Keyboard.KEY_NUMPAD4+"","4");
		map.put(Keyboard.KEY_NUMPAD5+"","5");
		setKeyMappingForAll(map);
		//TODO, complete
		//movement
		map = new HashMap<>();
		map.put(Keyboard.KEY_Z+"","[0,1]");
		setKeyMappingForPiece(PieceType.PAWN,map);
		map = new HashMap<>();
		map.put(Keyboard.KEY_D+"","[1,0]");
		map.put(Keyboard.KEY_Z+"","[0,1]");
		map.put(Keyboard.KEY_Q+"","[-1,0]");
		map.put(Keyboard.KEY_S+"","[0,-1]");
		setKeyMappingForPiece(PieceType.KING,map);
		setKeyMappingForPiece(PieceType.ROOK,map);
		setKeyMappingForPiece(PieceType.QUEEN,map);
		
				
		
		//todo, this can be deleted, because redundant
		// config pieces with available decisions

		// pawn
		Set<String> pawnSet = new HashSet<String>();
		// pawn can only go up
		pawnSet.add("[0,1]");
		// pawn can change turn, but not active on main board
		pawnSet.add("TURN");
		pieceDecisions.put(PieceType.PAWN, pawnSet);

		// Bischop
		Set<String> bischopSet = new HashSet<String>();
		bischopSet.addAll(diagonalSet.keySet());

		bischopSet.add("MAXRANGE");
		pieceDecisions.put(PieceType.BISCHOP, bischopSet);

		// Rook
		Set<String> rookSet = new HashSet<String>();

		rookSet.addAll(orthogonalSet.keySet());

		rookSet.add("TILEVIEW");
		pieceDecisions.put(PieceType.ROOK, rookSet);

		// Knight
		Set<String> knightSet = new HashSet<String>();

		knightSet.addAll(horseSet.keySet());

		knightSet.add("DRAGON");
		pieceDecisions.put(PieceType.KNIGHT, knightSet);

		// Queen
		Set<String> queenSet = new HashSet<String>();

		queenSet.addAll(diagonalSet.keySet());
		queenSet.addAll(orthogonalSet.keySet());
		pieceDecisions.put(PieceType.QUEEN, queenSet);

		// King
		Set<String> kingSet = new HashSet<String>();

		kingSet.addAll(diagonalSet.keySet());
		kingSet.addAll(orthogonalSet.keySet());
		kingSet.add("RANGEDMAXRANGE");
		kingSet.add("RANGEDTILEVIEW");

		pieceDecisions.put(PieceType.KING, kingSet);

	}

	public static Map<String, ParamObject> getSpecialsSet() {
		return specialsSet;
	}

	public static void setSpecialsSet(Map<String, ParamObject> specialsSet) {
		MetaConfig.specialsSet = specialsSet;
	}

	public static Color getColor(int c) {
		return colors.get(c);
	}

	public static void setColor(int c, Color color) {
		colors.put(c, color);
	}

	public static PieceRenderer getPieceRenderer(PieceType piece) {
		return pieceRenderers.get(piece);
	}

	public static BoardRenderer getBoardRenderer() {
		return boardRenderer;
	}

	public static void setBoardRenderer(BoardRenderer boardRenderer) {
		MetaConfig.boardRenderer = boardRenderer;
	}

	public static GUIRenderer getGuiRenderer() {
		return guiRenderer;
	}

	public static void setGuiRenderer(GUIRenderer guiRenderer) {
		MetaConfig.guiRenderer = guiRenderer;
	}

}
