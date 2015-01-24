import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class KeywordConverterTest {

    @Test
    public void testConvert() throws Exception {
        String randomNumberAlphabet = generateRandomNumberAlphabet();
        System.out.println(randomNumberAlphabet);
        assertThat("英数字のみは接頭末尾に%をつけて()でくくる", KeywordConverter.convert(randomNumberAlphabet), is("(%" + randomNumberAlphabet + "%)"));
        assertThat("末尾が英数字の場合は末尾に%をつけて()でくくる", KeywordConverter.convert("あいうえお" + randomNumberAlphabet), is("(あいうえお)(" + randomNumberAlphabet + "%)"));
        assertThat("先頭が英数字の場合は先頭に%をつけて()でくくる", KeywordConverter.convert(randomNumberAlphabet + "あいうえお"), is("(%" + randomNumberAlphabet + ")(あいうえお)"));
        assertThat("予約記号の場合はエスケープして{}でくくる", KeywordConverter.convert(",&=?{}()[]-;~|$!>*%_"), is("{\\,\\&\\=\\?\\{\\}\\(\\)\\[\\]\\-\\;\\~\\|\\$\\!\\>\\*\\%\\_}"));
        assertThat("英数字以外の場合は()でくくる", KeywordConverter.convert("あいうえお"), is("(あいうえお)"));
    }

    private String generateRandomNumberAlphabet() {
        String halfNumber = "0123456789";
        String halfLowerAlpha = "abcdefghijklmnopqrstuvwxyz";
        String halfUpperAlpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String fullNumber = "０１２３４５６７８９";
        String fullLowerAlpha = "ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ";
        String fullUpperAlpha = "ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ";
        return RandomStringUtils.random(10, halfNumber
                + halfLowerAlpha
                + halfUpperAlpha
                + fullNumber
                + fullLowerAlpha
                + fullUpperAlpha);
    }

}