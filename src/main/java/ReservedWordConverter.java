import java.util.regex.Pattern;

public class ReservedWordConverter implements Converter {

    private static final Pattern PATTERN = Pattern.compile("[,&=?{}\\()\\[\\]\\-\\;~|$!>*%_]+");

    private static String escapeReservedWord(String chunk) {
        // 文字列が予約語の場合はエスケープする
        StringBuilder sb = new StringBuilder();

        for (char c : chunk.toCharArray()) {
            if (c == '$') {
                sb.append("\\$");
            } else {
                sb.append("\\")
                        .append(c);
            }
        }
        return sb.toString();
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }

    @Override
    public String convert(String value) {
        return '{' + escapeReservedWord(value) + '}';
    }

}
