import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

interface Converter {
    Pattern getPattern();

    String convert(String value);
}

/**
 * OracleTextで部分一致検索を行う場合のキーワードコンバーター
 * @author uggds
 */
public class KeywordConverter {

    /**
     * 文字種と文字の位置に応じたコンバーターリスト
     */
    private static final List<Converter> CONVERTER_LIST = Arrays.asList(
            new ReservedWordConverter(),
            new AllNumberAndAlphabetConverter(),
            new StartWithNumberAndAlphabetConverter(),
            new EndWithNumberAndAlphabetConverter(),
            new OtherConverter()
    );

    public static String convert(String keyword) {
        int position = 0;
        StringBuilder sb = new StringBuilder(keyword.length() + 10);
        while (position < keyword.length()) {
            for (Converter converter : CONVERTER_LIST) {
                final Matcher matcher = converter.getPattern()
                        .matcher(keyword);
                if (matcher.find(position) && matcher.start() == position) {
                    position = matcher.end();
                    sb.append(converter.convert(matcher.group()));
                    break;
                }
            }
        }
        return sb.toString();
    }
}

