package res;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
//this class saves all bitgrids as 64  bit hex values (strings) in a map

public class BitGrids {
    public enum NumericalGridBase{
        eight,sixtyfour
    }
    private static Map<String, String> bitGrids = new HashMap<>();

    public static int getBit(String gridName, int i, int j) {
        if(bitGrids.get(gridName).charAt(i*8+j)=='1'){
            return 1;
        }
        return 0;
    }
    public static String getNumericalGrid(int number, NumericalGridBase  base){
        return number+":"+base;
    }
    public static void loadBitgrids() {
        bitGrids.put("bischop", convertHexToBin("00005EDEDE5E0000"));
        
    }
    public static String convertHexToBin(String hex){
        return String.format("%64s",new BigInteger(hex, 16).toString(2)).replace(' ', '0');
    }
   

}
