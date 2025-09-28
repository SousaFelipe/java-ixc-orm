package br.dev.fscarmo.ixcorm.api;


import java.text.Normalizer;


public abstract class Utils {

    public static class Text {
        public static String normalize(String text) {
            if (text != null && !text.isBlank()) {
                return Normalizer
                        .normalize(text, Normalizer.Form.NFD)
                        .replaceAll("[^\\p{ASCII}]", "")
                        .toLowerCase()
                        .trim();
            }
            return "";
        }
    }
}
