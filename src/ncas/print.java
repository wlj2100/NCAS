package ncas;

import java.util.List;
import java.util.Map;

public class print {
	public static void print(boolean flag) {
//		if (flag) {
//	    	System.out.println("true");
//	    } else {
//	    	System.out.println("false");
//	    }
		System.out.println(flag? "true":"false");
	}
	public static void print(int n) {
		System.out.println(n);
	}
	public static void print(int[] nums) {
		for (int num : nums) {
			System.out.format("%d, ", num);
		}
//		for (int i = 0; i < nums.length; i++) {
//			System.out.println(nums[i]);
//		}
		System.out.println();
	}
	public static void print(int[][] nums) {
		for (int[] sub : nums) {
			for (int num : sub) {
				System.out.format("%d, ", num);
			}
			System.out.println();
		}
	}
	public static void print(char[][] board) {
		// TODO Auto-generated method stub
		for (char[] sub : board) {
			for (char chr : sub) {
				System.out.format("%s, ", chr);
			}
			System.out.println();
		}
	}
	public static void print(int index, int[] nums) {
		if (nums != null && index < nums.length) {
			System.out.println(nums[index]);
		} else {
			System.out.format("index: %d is out of range%n", index);
		}
	}
	public static void print(long l) {
		System.out.println(l);
	}
	public static void print(double d) {
		System.out.println(d);
	}
	public static void print(char chr) {
		System.out.println(chr);
	}
	public static void print(char[] chrs) {
		for (char chr : chrs) {
			System.out.print(chr);
		}
		System.out.println();
	}
	
	public static void print(String s) {
		System.out.println(s);
	}
	public static void print(String[] words) {
		for (String word : words) {
			System.out.println(word);
		}
	}
	public static void print(List<?> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString());
		}
//		System.out.println();
	}
	public static void print(Map.Entry<?, ?> entry) {
		System.out.print(entry.getKey().toString());
		System.out.print(" : ");
		System.out.println(entry.getValue().toString());
	}
	public static void print(Map<?, ?> map) {
		for (Map.Entry<?, ?> entry : map.entrySet()) {
			System.out.print(entry.getKey().toString());
			System.out.print(" : ");
			System.out.println(entry.getValue().toString());
		}
	}
	public static void print(Object[] objs) {
		for (Object obj : objs) print.print(obj.toString());
	}
}
