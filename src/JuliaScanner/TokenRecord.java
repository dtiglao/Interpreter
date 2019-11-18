package JuliaScanner;

/** Rosny Colas and Darius Tiglao
 * Class: TokenRecord
 * TokenRecord is what the name implies. It is a record that stores the information for each token.
 * There are 4 class attributes: lexeme, tokenCode, tokenName, and srcLine
 */

class TokenRecord {

    private String lexeme;
    private String tokenCode;
    private String tokenName;
    private int srcLine;
    private int blockNumber;

    TokenRecord() {
        this.lexeme = "null";
        this.tokenCode = "null";
        this.tokenName = "null";
    }

    TokenRecord(String lexeme, String tokenName, int srcLine) {
        this.lexeme = lexeme;
        this.tokenName = tokenName;
        this.srcLine = srcLine;

        if (tokenName.contains("Operator")) { this.tokenCode = opCode(tokenName); }
        else if (tokenName.contains("Literal")) { this.tokenCode = litCode(tokenName); }
        else if (tokenName.contains("id")) { this.tokenCode = idCode(tokenName); }
        else if (tokenName.contains("Function")) { this.tokenCode = funcCode(tokenName); }
        else { this.tokenCode = keywordCode(lexeme); }
    }

    int blockCheck() {
        if (tokenCode.equals("1002")) return 0;
        if (tokenCode.equals("1005") || tokenCode.equals("1006") || tokenCode.equals("1007")) return 1;
        else return -1;
    }

    String getTokenCode() {
        return this.tokenCode;
    }
    int getBlockNumber() { return this.blockNumber; }
    String getLexeme() { return this.lexeme; }
    String getTokenName() { return this.tokenName; }
    int getSrcLine() { return this.srcLine; }

    void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    /** printRecord will print the attributes of each TokenRecord
     * @return String
     */
    String printRecord() {
        if (this.tokenName.length() > 7) { return srcLine + "\t" + blockNumber + "\t" +
                tokenCode + "\t" + tokenName + "\t\t" + lexeme; }
        else { return srcLine + "\t" + blockNumber + "\t" + tokenCode + "\t" + tokenName + "\t\t\t" + lexeme; }
    }

    /** idCode returns the tokenCode for all identifiers
     * @param idName
     * @return tokenCode starting with "3---"
     */
    private String idCode(String idName) {
        return "3001";
    }

    /** funcCode returns the funcCode for all functions found
     * @param functionName
     * @return tokenCode starting with "4---"
     */
    private String funcCode(String functionName) {
        return "4001";
    }

    /** keywordCode takes in a keyword and returns a code for the corresponding keyword
     * @param keywordName
     * @return tokenCode starting with "1---"
     */
    private String keywordCode(String keywordName) {
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
            case "print":
                return "1011";
        }
        return "1000";
    }

    /** opCode returns an operator code for each different operator
     * @param opName
     * @return tokenCode starting with "0---"
     */
    private String opCode(String opName) {
        switch (opName) {
            case "<Assignment_Operator>":
                return "0100";
            case "<Plus_Operator>":
                return "0101";
            case "<Minus_Operator>":
                return "0102";
            case "<Multiplication_Operator>":
                return "0103";
            case "<Division_Operator>":
                return "0104";
            case "<ReverseDivision_Operator>":
                return "0105";
            case "<Power_Operator>":
                return "0106";
            case "<Remainder_Operator>":
                return "0107";
            case "<IncBy_Operator>":
                return "0108";
            case "<DecBy_Operator>":
                return "0109";
            case "<GT_Operator>":
                return "0110";
            case "<GE_Operator>":
                return "0111";
            case "<LT_Operator>":
                return "0112";
            case "<LE_Operator>":
                return "0113";
            case "<Eq_Operator>":
                return "0114";
            case "<NotEq_Operator>":
                return "0115";
            case "<LP_Operator>":
                return "0116";
            case "<RP_Operator>":
                return "0117";
            case "<And_Operator>":
                return "0018";
            case "<Iter_Operator>":
                return "0019";
        }
        return "0000";
    }

    /** litCode takes in the name of a literal and returns a code for the corresponding literal
     * @param litName
     * @return tokenCode starting with "2---"
     */
    private String litCode(String litName) {
        switch(litName) {
            case "<Integer_Literal>":
                return "2001";
            case "<Float_Literal>":
                return "2002";
            case "<String_Literal>":
                return "2003";
        }
        return "2000";
    }
}
