import java.util.regex.Pattern;

public class OtherConverter implements Converter {

    private static final Pattern PATTERN = Pattern.compile("[^0-9a-zA-Z０-９Ａ-Ｚａ-ｚ,&= ?{}\\()\\[\\]\\-\\;~|$!>*%_]+");

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }

    @Override
    public String convert(String value) {
        return '(' + value + ')';
    }
}
