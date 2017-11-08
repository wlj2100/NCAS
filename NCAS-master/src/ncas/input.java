package ncas;

import java.util.Scanner;

/**
 * Created by whb on 2017/11/5.
 */
public class input {
    public static String get (String s){
        System.out.print(s);
        Scanner sc = new Scanner (System.in);
        String result = sc.nextLine();
        return result;
    }
}
