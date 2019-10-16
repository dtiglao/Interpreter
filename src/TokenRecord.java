public class TokenRecord {

    public String lexeme;
    public String tokenCode;
    public String tokenName;
    public int srcLine;

    public TokenRecord(String lexeme, String tokenCode, String tokenName, int srcLine) {
        this.lexeme = lexeme;
        this.tokenCode = tokenCode;
        this.tokenName = tokenName;
        this.srcLine = srcLine;
    }

    public void printRecord() {
        System.out.println(tokenCode + " " + tokenName + ": " + lexeme);
    }
}
