package meta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
}
