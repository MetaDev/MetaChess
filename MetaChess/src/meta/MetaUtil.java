package meta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class MetaUtil {
	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
		for (Entry<T, E> entry : map.entrySet()) {
			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	public static <T, E> List<Entry<T, E>> getAllKeysByValue(Map<T, E> map,
			E value) {
		List<Entry<T, E>> list = new ArrayList<Entry<T, E>>();
		for (Entry<T, E> entry : map.entrySet()) {
			if (value.equals(entry.getValue())) {
				list.add(entry);
			}
		}
		return list;
	}

	public static <T, E> int getKeyCountByValue(Map<T, E> map, E value) {
		int count = 0;
		for (Entry<T, E> entry : map.entrySet()) {
			if (value.equals(entry.getValue())) {
				count++;
			}
		}
		return count;
	}

	public static String stripNonDigits(CharSequence input) {
		StringBuilder sb = new StringBuilder(input.length());
		for (int i = 0; i < input.length(); i++) {
			final char c = input.charAt(i);
			if (c > 47 && c < 58) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static boolean isNumeric(String string) {
		if(string!=null){
			return string.equals("1") || string.equals("2") || string.equals("3")
					|| string.equals("4") || string.equals("5")
					|| string.equals("6") || string.equals("7")
					|| string.equals("8") || string.equals("9");
		}
		return false;
	}

	public static int convertToInteger(String s) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return -1;
		}

	}

	public static String convertIntArrayToString(int[] array) {
		String result = "[";
		for (int i = 0; i < array.length; i++) {
			if (i < array.length - 1) {
				result += array[i] + ",";
			} else {
				result += array[i];
			}
		}
		return result + "]";
	}

	public static int[] parseIntArray(String array) {
		String[] items = array.replaceAll("\\[", "").replaceAll("\\]", "")
				.split(",");
		int[] results = new int[items.length];
		for (int i = 0; i < items.length; i++) {
			try {
				results[i] = Integer.parseInt(items[i]);
			} catch (NumberFormatException nfe) {
				System.out.println("conversion problem");
			}
		}
		return results;
	}
	public static int randInt(int min, int max) {
		//TODO optimisation
	    // Usually this should be a field rather than a method variable so
	    // that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
}
