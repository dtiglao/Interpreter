/** Rosny Colas and Darius Tiglao
 * Class: TokenRecord
 * TokenRecord is what the name implies. It is a record that stores the information for each token.
 * There are 4 class attributes: lexeme, tokenCode, tokenName, and srcLine
 */

public class TokenRecord {

    public String lexeme;
    public String tokenCode;
    public String tokenName;
    public int srcLine;

    public TokenRecord(String lexeme, String tokenName, int srcLine) {
        this.lexeme = lexeme;
        this.tokenName = tokenName;
        this.srcLine = srcLine;

        if (tokenName.contains("Op")) { this.tokenCode = opCode(tokenName); }
        else if (tokenName.contains("Lit")) { this.tokenCode = litCode(tokenName); }
        else if (tokenName.contains("Identifier")) { this.tokenCode = idCode(tokenName); }
        else if (tokenName.contains("Function")) { this.tokenCode = funcCode(tokenName); }
        else { this.tokenCode = keywordCode(lexeme); }
    }

    /** printRecord will print the attributes of each TokenRecord
     * @return String
     */
    public String printRecord() {
        if (this.tokenName.length() > 7) { return tokenCode + "\t" + tokenName + "\t\t" + lexeme; }
        else { return tokenCode + "\t" + tokenName + "\t\t\t" + lexeme; }
    }

    /** idCode returns the tokenCode for all identifiers
     * @param idName
     * @return tokenCode starting with "3---"
     */
    public String idCode(String idName) {
        return "3001";
    }

    /** funcCode returns the funcCode for all functions found
     * @param functionName
     * @return tokenCode starting with "4---"
     */
    public String funcCode(String functionName) {
        return "4001";
    }

    /** keywordCode takes in a keyword and returns a code for the corresponding keyword
     * @param keywordName
     * @return tokenCode starting with "1---"
     */
    public String keywordCode(String keywordName) {
        switch(keywordName) {
            case "begin":
                return "1001";
            case "end":
                return "1002";
            case "function":
                return "1003";
            case "and":
                return "1004";
            case "for":
                return "1005";
            case "while":
                return "1006";
            case "if":
                return "1007";
            case "elseif":
                return "1008";
            case "else":
                return "1009";
            case "then":
                return "1010";
        }
        return "1000";
    }

    /** opCode returns an operator code for each different operator
     * @param opName
     * @return tokenCode starting with "0---"
     */
    public String opCode(String opName) {
        switch (opName) {
            case "Assign_Op":
                return "0100";
            case "Plus_Op":
                return "0101";
            case "Minus_Op":
                return "0102";
            case "Mult_Op":
                return "0103";
            case "Div_Op":
                return "0104";
            case "RevDiv_Op":
                return "0105";
            case "Power_Op":
                return "0106";
            case "Rem_Op":
                return "0107";
            case "IncBy_Op":
                return "0108";
            case "DecBy_Op":
                return "0109";
            case "GT_Op":
                return "0110";
            case "GE_Op":
                return "0111";
            case "LT_Op":
                return "0112";
            case "LE_Op":
                return "0113";
            case "Eq_Op":
                return "0114";
            case "NotEq_Op":
                return "0115";
            case "LP_Op":
                return "0116";
            case "RP_Op":
                return "0117";
            case "And_Op":
                return "0018";
        }
        return "0000";
    }

    /** litCode takes in the name of a literal and returns a code for the corresponding literal
     * @param litName
     * @return tokenCode starting with "2---"
     */
    public String litCode(String litName) {
        switch(litName) {
            case "Int_Lit":
                return "2001";
            case "Float_Lit":
                return "2002";
            case "Str_Lit":
                return "2003";
        }
        return "2000";
    }
}
