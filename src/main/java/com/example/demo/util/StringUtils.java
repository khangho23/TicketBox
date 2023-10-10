package com.example.demo.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtils {
    public static String convertVietnameseToNoDiacritics(String value) {
        try {
            String temp = Normalizer.normalize(value, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
