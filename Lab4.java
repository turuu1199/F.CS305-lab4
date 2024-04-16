import java.util.*;

enum TokenType {
    KEYWORD, ID, SEMICOLON, ASSIGN, OPERATION, NUM, STR
}

class Token {
    TokenType typ;
    String val;

    Token(TokenType typ, String val) {
        this.typ = typ;
        this.val = val;
    }
}

class Variable {
    Token keyword;
    Token identifier;

    Variable(Token keyword, Token identifier) {
        this.keyword = keyword;
        this.identifier = identifier;
    }

    public String toString() {
        return keyword.val + " " + identifier.val;
    }
}

public class Lab4 {
    static int counter = 0;
    static String line;

    static Token get_next_token() {
        while (counter < line.length() && Character.isWhitespace(line.charAt(counter))) {
            counter++;
        }

        if (counter >= line.length()) {
            return new Token(null, null);
        }

        char ch = line.charAt(counter);
        if (Character.isDigit(ch)) {
            StringBuilder tok = new StringBuilder();
            while (counter < line.length() && Character.isDigit(line.charAt(counter))) {
                tok.append(line.charAt(counter));
                counter++;
            }
            return new Token(TokenType.NUM, tok.toString());
        } else if (Character.isLetter(ch)) {
            StringBuilder tok = new StringBuilder();
            while (counter < line.length()
                    && (Character.isLetterOrDigit(line.charAt(counter)) || line.charAt(counter) == '_')) {
                tok.append(line.charAt(counter));
                counter++;
            }
            TokenType type = (List.of("print", "int", "while", "if", "elif", "else").contains(tok.toString()))
                    ? TokenType.KEYWORD
                    : TokenType.ID;
            return new Token(type, tok.toString());
        } else {
            switch (ch) {
                case '=':
                    counter++;
                    return new Token(TokenType.ASSIGN, "=");
                case '+':
                    counter++;
                    return new Token(TokenType.OPERATION, "+");
                case '-':
                    counter++;
                    return new Token(TokenType.OPERATION, "-");
                case ';':
                    counter++;
                    return new Token(TokenType.SEMICOLON, ";");
                case '"':
                case '\'':
                    char delimiter = ch;
                    StringBuilder tok = new StringBuilder();
                    counter++;
                    while (counter < line.length() && line.charAt(counter) != delimiter) {
                        tok.append(line.charAt(counter));
                        counter++;
                    }
                    counter++;
                    return new Token(TokenType.STR, tok.toString());
                default:
                    System.out.println("Invalid character encountered");
                    System.exit(1);
            }
        }
        return null;
    }

    static Token match(TokenType check_tok_typ) {
        Token cur_token = get_next_token();
        if (cur_token.typ != check_tok_typ) {
            System.out.println(
                    check_tok_typ + " baih estoi " + ", gewch baihgui bna eswel buruu baina -> " + cur_token.typ);
            System.exit(1);
        }
        return cur_token;
    }

    public static void main(String[] args) {
        line = "int x;";
        counter = 0;
        Token key1 = match(TokenType.KEYWORD);
        Token id1 = match(TokenType.ID);
        match(TokenType.SEMICOLON);
        System.out.println("Declaration detected");

        Variable v = new Variable(key1, id1);
        System.out.println(v.toString());

        // Token match taarahgui bgaa case semicolon hasaw
        System.out.println("*****************************************\n");
        line = "INT x;";
        System.out.println(line);
        counter = 0;
        Token key2 = match(TokenType.KEYWORD);
        Token id2 = match(TokenType.ID);
        match(TokenType.SEMICOLON);
        System.out.println("Declaration detected");

        Variable v1 = new Variable(key2, id2);
        System.out.println(v1.toString());
    }
}
