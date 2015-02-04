package meta;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.DecisionModel;
import model.ExtendedBishopModel;
import model.ExtendedBoardModel;
import model.ExtendedKingModel;
import model.ExtendedKnightModel;
import model.ExtendedPawnModel;
import model.ExtendedQueenModel;
import model.ExtendedRookModel;
import model.ExtendedTileModel;
import model.MovementDecisionModel;
import model.SpecialDecisionModel;
import model.paramobjects.POExtendeSpecial;
import model.paramobjects.PORange;
import org.lwjgl.glfw.GLFW;

import userinterface.init.IconLoader;
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
    public static int DECIDE = 1;
    public static int REGRET = -1;

    public static boolean hasRegret(int param) {
        return param < 0;
    }
    

    // board renderer
    private static BoardRenderer boardRenderer;
    // also keep extended models here

    private static ExtendedBoardModel boardModel;

    // turn cycle of 3 types of movement, only needed for pawn
    private static String[] directionTurn = new String[]{"[-1,-1]", "[0,-1]",
        "[1,-1]", "[1,0]", "[1,1]", "[0,1]", "[-1,1]", "[-1,0]"};

    public static int[] getDirectionWithTurn(String direction, int turn) {
        int dir = getIndexfromDirection(direction);

        if (dir != -1) {
            return MetaUtil.parseIntArray(directionTurn[(dir + turn) % directionTurn.length]);
        }
        return MetaUtil.parseIntArray(direction);
    }

    public static int[] getDirectionWithIndex(int index) {
        return MetaUtil.parseIntArray(directionTurn[index]);
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

    private static Set<DecisionModel> decisionModels = new HashSet<>();

    // for the range decision, check if mapping is
    public static int isNumber(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
        // only got here if we didn't return false
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

    // init all necessary constants
    public static void initConstants() {

        //init singletons
        boardRenderer = new BoardRenderer();
        guiRenderer = new GUIRenderer();
        ExtendedTileModel floor = new ExtendedTileModel(1, tileSize);
        MetaConfig.setBoardModel(new ExtendedBoardModel(floor));

        // load icons
        IconLoader.loadIcons();
        //set keys
        

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
