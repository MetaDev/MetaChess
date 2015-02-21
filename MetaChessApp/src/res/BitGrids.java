package res;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
//this class saves all bitgrids as 64  bit hex values (strings) in a map

public class BitGrids {

    public enum NumericalGridBase {

        eight, sixtyfour
    }
    private static Map<String, String> bitGrids = new HashMap<>();

    public static int getBit(String gridName, int i, int j) {
        //invert both vert indices
        if (bitGrids.get(gridName).charAt((i * 8 + (7 - j))) == '1') {
            return 1;
        }
        return 0;
    }

    public static String getNumericalGrid(int number, NumericalGridBase base) {
        if (base == NumericalGridBase.eight) {
            number = Math.min(number, 8);
        } else {
            number = Math.min(number, 64);
        }
        return number + ":" + base.name();
    }

    public static void loadBitgrids() {
        saveHex("axis", "207e220202070200");
        saveHex("bischop", "00005EDEDE5E0000");
        saveHex("decisioncooldown", "221c080008080800");
        saveHex("decisionrange", "221c0800081c0800");
        saveHex("decisionextended", "221c0800141c0000");
        saveHex("piecebound", "0100000000000001");
        saveHex("pieceplayer", "8100000000000081");
        saveHex("dragon", "e46f1d3c76e26020");
        saveHex("extended", "000066667e7e0000");
        saveHex("harald", "007e241818247e00");
        saveHex("horizon", "040c142c2c140c04");
        saveHex("king", "2070fe7e7efe7020");
        saveHex("knight", "001e7c3818fc7e60");
        saveHex("false", "0018181818181800");
        saveHex("true", "0000007e7e000000");
        saveHex("pawn", "0000001e1e000000");
        saveHex("queen", "00106efefe6e1000");
        saveHex("range", "0018187e7e181800");
        saveHex("rook", "403e5e3e3e5e3e40");
        saveHex("runner", "8142001818004281");
        saveHex("wall", "0377730000777700");
        saveHex("turn", "0000207c7c200000");
        saveHex("teamlives", "0066661818666600");
        saveHex("lostlives", "0018180018181818");
        saveHex("takenlives", "00181800183c3c18");
        

        //save numbers
        //64 bit bitstring 0
        char[] bin = convertHexToBin("0000000000000000").toCharArray();
        //base 8, 0-8
        saveBin(0 + ":" + NumericalGridBase.eight.name(), new String(bin));
        for (int i = 0; i < 8; i++) {
            //set correct bits to 1
            for (int j = 0; j < 8; j++) {
                bin[j*8 + 7-i] = '1';
            }
            saveBin(i + 1 + ":" + NumericalGridBase.eight.name(), new String(bin));

        }
        //base 64, 1-64 
        //reset
        bin = convertHexToBin("0000000000000000").toCharArray();
        saveBin(0 + ":" + NumericalGridBase.sixtyfour.name(), new String(bin));
        for (int i = 0; i < 8; i++) {

            //set correct bits to 1
            for (int j = 0; j < 8; j++) {
                bin[7 - j + i * 8] = '1';
                saveBin((j + i * 8 + 1) + ":" + NumericalGridBase.sixtyfour.name(), new String(bin));
            }
        }
        //TODO
        //save letters
    }

    private static void saveBin(String name, String bin) {
        bitGrids.put(name, bin);
    }

    private static void saveHex(String name, String hex) {
        saveBin(name, convertHexToBin(hex));
    }

    public static String convertHexToBin(String hex) {
        return String.format("%64s", new BigInteger(hex, 16).toString(2)).replace(' ', '0');
    }

}
