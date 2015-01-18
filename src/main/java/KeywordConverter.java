import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * OracleTextでキーワードに対する部分一致検索を行う場合のキーワードコンバーター
 * @auther uggds
 */
public class KeywordConverter {

    /** 半角英数字 */
    private static final String NUMBER_ALPHABET = "[0-9a-zA-Z０-９Ａ-Ｚａ-ｚ]+";
    /** OracleText予約語 */
    private static final String RESERVED_WORD = "[,&=?{}\\()\\[\\]\\-\\;~|$!>*%_]+";
    /** 半角英数字と予約語以外 */
    private static final String OTHER_WORD = "[^0-9a-zA-Z０-９Ａ-Ｚａ-ｚ,&= ?{}\\()\\[\\]\\-\\;~|$!>*%_]+";

    public static String convert(String keyword) {
        StringBuilder convertedKeyword = new StringBuilder();
        Pattern p = Pattern.compile("(" + NUMBER_ALPHABET + " | "
                + RESERVED_WORD + " | "
                + OTHER_WORD + ")", Pattern.COMMENTS);
        Matcher m = p.matcher(keyword);
        while (m.find()) {

            String chunk = m.group();

            if (chunk.matches(RESERVED_WORD)) {
                convertedKeyword.append("{").append(escapeReservedWord(chunk)).append("}");
            } else if (0 == m.start() && keyword.length() == m.end() && chunk.matches(NUMBER_ALPHABET)) {
                // 末端文字列が英数記号の場合
                convertedKeyword.append("(%").append(chunk).append("%)");
            } else if (0 == m.start() && chunk.matches(NUMBER_ALPHABET)) {
                // 先頭文字列が英数記号の場合
                convertedKeyword.append("(%").append(chunk).append(")");
            } else if (keyword.length() == m.end() && chunk.matches(NUMBER_ALPHABET)) {
                // 末端文字列が英数記号の場合
                convertedKeyword.append("(").append(chunk).append("%)");
            } else {
                convertedKeyword.append("(").append(chunk).append(")");
            }

        }
        return convertedKeyword.toString();
    }

    private static String escapeReservedWord(String chunk) {
        // 文字列が予約語の場合
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chunk.length(); i++) {
            sb.append("\\").append(chunk.charAt(i));
        }
        return sb.toString();
    }
}
