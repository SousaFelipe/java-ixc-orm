package br.dev.fscarmo.ixcorm.api;


import java.text.Normalizer;


/**
 * <p>
 * Classe de utilitários criada para concentrar métodos simples, para serem compartilhados por todas as classes da
 * biblioteca, que necessitarem.
 * </p>
 *
 * @author Felipe S. Carmo
 * @version 1.0.2
 * @since 2025-09-27
 */
public abstract class Utils {

    /**
     * <p>
     * A classe 'Text' possui métodos de formatação e mascaramento de texto.
     * </p>
     *
     * @author Felipe S. Carmo
     * @version 1.0.1
     * @since 2025-09-27
     */
    public static class Text {

        /**
         * <p>
         * Remove os caracteres especiais de um texto, fornecido pelo parâmetro <b>text.</b>
         * </p>
         *
         * @param text A <b>String</b> a ser "formatada".
         * @return Uma nova <b>String</b>, sem caracteres especiais.
         */
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
