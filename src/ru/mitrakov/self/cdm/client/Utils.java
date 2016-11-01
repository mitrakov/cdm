package ru.mitrakov.self.cdm.client;

import java.util.*;

/**
 *
 * @author Tommy
 */
public class Utils {
    public static List<Integer> fromBase64(String s) {
        String w = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        List<Integer> res = new ArrayList<>();
        for (char c : s.toCharArray())
            res.add(w.indexOf(c));
        return res;
    }
    
    public static <T> List<List<T>> grouped(Collection<T> col, int n) {
        List<List<T>> res = new ArrayList<>();
        List<T> lst = new ArrayList<>(col);
        List<T> sub = null;
        for (int i = 0; i < col.size(); i++) {
            if (i % n == 0) {
                if (sub != null) res.add(sub);
                sub = new ArrayList<>();
            }
            sub.add(lst.get(i));
        }
        if (sub != null) res.add(sub);
        return res;
    }
}
