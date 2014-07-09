package meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logic.BoardLogic;
import model.ExtendedBoardModel;
import model.ExtendedPieceModel;
import model.ExtendedTileModel;
import model.paramobjects.PODragon;
import model.paramobjects.POMaxRange;
import model.paramobjects.POSwitch;
import model.paramobjects.POTileView;
import model.paramobjects.POTurn;
import model.paramobjects.ParamObject;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Color;

import userinterface.generic.IconLoader;
import view.renderer.BoardRenderer;
import view.renderer.GUIRenderer;

//contains and initialises all unique instances
public class MetaConfig {
	public enum PieceType {
		PAWN, ROOK, KNIGHT, BISHOP, KING, QUEEN
	}

	// free means that the gui's position is based on absolute coordinates
	public enum GUIPosition {
		LEFT, RIGHT, BOTTOM, TOP, FREE

	}

	// map String "MetaAction name, number , char" to icons for GUI and board
	// representation
	private static Map<String, int[][]> decisionsIcons = new HashMap<>();

	// mapping of range keys
	private static Map<Integer, Integer> rangeKeys = new HashMap<>();

	public static Map<Integer, Integer> getRangeKeys() {
		return rangeKeys;
	}

	// the first 2 colors are for the opposing tile and piece colors
	private static Map<Integer, Color> colors = new HashMap<>();

	// all Controls, a mapping of a key number sequence onto decision
	private static Map<PieceType, HashMap<String, String>> keyMapping = new HashMap<>();

	// board renderer
	private static BoardRenderer boardRenderer;
	// also keep extended models here

	private static ExtendedBoardModel boardModel;

	// turn cycle of 3 types of movement, only needed for pawn
	private static String[] directionTurn = new String[] { "[-1,-1]", "[0,-1]",
			"[1,-1]", "[1,0]", "[1,1]", "[0,1]", "[-1,1]", "[-1,0]" };

	// map decision name to int key
	public static Map<PieceType, HashMap<String, String>> getKeyMapping() {
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
		for (int i = 0; i < directionTurn.length; i++) {
			if (direction.equals(directionTurn[i])) {
				dir = i;
			}
		}
		if (dir != -1)
			return directionTurn[(dir + turn) % directionTurn.length];
		return direction;
	}

	public static String getDirectionWithIndex(int index) {
		return directionTurn[index];
	}

	public static int getIndexfromDirection(String direction) {
		int dir = -1;
		for (int i = 0; i < directionTurn.length; i++) {
			if (direction.equals(directionTurn[i])) {
				dir = i;
			}
		}
		return dir;
	}
	public static int getIndexfromDirection(int[] direction) {
		return getIndexfromDirection(MetaUtil.convertIntArrayToString(direction));	
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
	// we save string and array, because conversion everytime is less
	// cost-effective
	private static Map<String, int[]> mapDirections(List<int[]> directions) {
		Map<String, int[]> map = new HashMap<String, int[]>();
		for (int[] dir : directions) {
			map.put(MetaUtil.convertIntArrayToString(dir), dir);
		}
		return map;
	}

	// method thar return direction array for any movement
	public static int[] getDirectionArray(String direction,
			ExtendedPieceModel piece) {
		if(piece.getType()==PieceType.PAWN){
			direction = MetaConfig.getDirectionWithTurn(direction, piece.getTurn());
		}
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

	private static void setKeyMappingForAll(Map<String, String> mapping) {
		for (PieceType type : PieceType.values()) {
			setKeyMappingForPiece(type, mapping);
		}

	}

	private static void setKeyMappingForPiece(PieceType type,
			Map<String, String> mapping) {
		if (keyMapping.get(type) == null) {
			keyMapping.put(type, new HashMap<String, String>());
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
		diagonalList.add(new int[] { 1, 1 });
		diagonalList.add(new int[] { 1, -1 });
		diagonalList.add(new int[] { -1, -1 });
		diagonalList.add(new int[] { -1, 1 });
		diagonalSet = mapDirections(diagonalList);
		List<int[]> horseList = new ArrayList<int[]>();
		horseList.add(new int[] { 1, 2 });
		horseList.add(new int[] { 1, -2 });
		horseList.add(new int[] { -1, -2 });
		horseList.add(new int[] { -1, 2 });
		horseList.add(new int[] { 2, 1 });
		horseList.add(new int[] { 2, -1 });
		horseList.add(new int[] { -2, -1 });
		horseList.add(new int[] { -2, 1 });
		horseSet = mapDirections(horseList);
		// fill specialset with
		specialsSet.put("HORIZON", new POTileView());
		specialsSet.put("ROGUE", new POMaxRange());
		specialsSet.put("DRAGON", new PODragon());
		specialsSet.put("TURN", new POTurn());
		specialsSet.put("SWITCH", new POSwitch());
		//TODO rogue for bischop and command for king
		// map to initial keymapping

		// special decisions
		Map<String, String> map = new HashMap<>();
		map.put(Keyboard.KEY_SPACE + "", "HORIZON");
		setKeyMappingForPiece(PieceType.ROOK, map);
		map = new HashMap<>();
		map.put(Keyboard.KEY_SPACE + "", "ROGUE");
		setKeyMappingForPiece(PieceType.BISHOP, map);
		map = new HashMap<>();
		map.put(Keyboard.KEY_SPACE + "", "DRAGON");
		setKeyMappingForPiece(PieceType.KNIGHT, map);
		map = new HashMap<>();
		map.put(Keyboard.KEY_SPACE + "", "TURN");
		setKeyMappingForPiece(PieceType.PAWN, map);
//		map = new HashMap<>();
//		map.put(Keyboard.KEY_SPACE + "", "RANGED");
//		setKeyMappingForPiece(PieceType.KING, map);
		// range
		map = new HashMap<>();
		map.put(Keyboard.KEY_NUMPAD1 + "", "1");
		map.put(Keyboard.KEY_NUMPAD2 + "", "2");
		map.put(Keyboard.KEY_NUMPAD3 + "", "3");
		map.put(Keyboard.KEY_NUMPAD4 + "", "4");
		map.put(Keyboard.KEY_NUMPAD5 + "", "5");
		map.put(Keyboard.KEY_NUMPAD0 + "", "SWITCH");
		// set above decision for all piece types
		setKeyMappingForAll(map);

		// movement
		map = new HashMap<>();
		map.put(Keyboard.KEY_Z + "", "[0,1]");
		setKeyMappingForPiece(PieceType.PAWN, map);
		map = new HashMap<>();
		map.put(Keyboard.KEY_D + "", "[1,0]");
		map.put(Keyboard.KEY_Z + "", "[0,1]");
		map.put(Keyboard.KEY_Q + "", "[-1,0]");
		map.put(Keyboard.KEY_S + "", "[0,-1]");
		setKeyMappingForPiece(PieceType.KING, map);
		setKeyMappingForPiece(PieceType.ROOK, map);
		setKeyMappingForPiece(PieceType.QUEEN, map);
		map = new HashMap<>();
		map.put(Keyboard.KEY_E + "", "[1,1]");
		map.put(Keyboard.KEY_A + "", "[-1,1]");
		map.put(Keyboard.KEY_W + "", "[-1,-1]");
		map.put(Keyboard.KEY_C + "", "[1,-1]");
		setKeyMappingForPiece(PieceType.KING, map);
		setKeyMappingForPiece(PieceType.BISHOP, map);
		setKeyMappingForPiece(PieceType.QUEEN, map);
		map = new HashMap<>();
		map.put(Keyboard.KEY_Z + "", "[-1,2]");
		map.put(Keyboard.KEY_Q + "", "[-2,1]");
		map.put(Keyboard.KEY_R + "", "[1,2]");
		map.put(Keyboard.KEY_G + "", "[2,1]");
		map.put(Keyboard.KEY_F + "", "[2,-1]");
		map.put(Keyboard.KEY_C + "", "[1,-2]");
		map.put(Keyboard.KEY_S + "", "[-2,-1]");
		map.put(Keyboard.KEY_X + "", "[-1,-2]");
		setKeyMappingForPiece(PieceType.KNIGHT, map);

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
