package meta;

import java.util.HashMap;
import java.util.Map;

import res.BitGrids;
import view.renderer.BoardRenderer;
import view.renderer.GUIRenderer;

//contains and initialises all unique instances
public class MetaConfig {

   
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

  

    // guirenderers
    private static GUIRenderer guiRenderer;



    // init all necessary constants
    public static void initConstants() {

        //init singletons
        boardRenderer = new BoardRenderer();
        guiRenderer = new GUIRenderer();

        // load icons
       BitGrids.loadBitgrids();
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
