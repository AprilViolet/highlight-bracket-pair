package cn.aprilviolet.highlightbracketpair.adapter;

import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.tree.IElementType;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import static cn.aprilviolet.highlightbracketpair.brace.BraceTokenTypes.*;


/**
 * Find difference BracketMatchProcessor
 *
 * @author AprilViolet
 * @version v1.0.0
 * @date 2021.09.21 15:33
 * @since v1.0.0
 */
public class BracketMatchProcessorHolder {
    private BracketMatchProcessorHolder() {
    }

    /**
     * default return value for no brace offset
     */
    public static final int NON_BRACE_OFFSET = -1;

    protected static final Set<String> STRING_TOKEN_SET = new HashSet<>();

    public static final BracketMatchProcessorFactory BRACKET_MATCHING_FACTORY = BracketMatchProcessorFactory.getInstance();

    private static final char L_BRACE_ATTR = '{';
    private static final char R_BRACE_ATTR = '}';

    private static final char L_BRACKET_ATTR = '[';
    private static final char R_BRACKET_ATTR = ']';

    private static final char L_PARENTHESIS_ATTR = '(';
    private static final char R_PARENTHESIS_ATTR = ')';

    private static final char L_CUSP_BRACKETS_ATTR = '<';
    private static final char R_CUSP_BRACKETS_ATTR = '>';


    static {
        STRING_TOKEN_SET.add(GROOVY_STRING_TOKEN);
        STRING_TOKEN_SET.add(GROOVY_SINGLE_QUOTE_TOKEN);
        STRING_TOKEN_SET.add(KOTLIN_STRING_TOKEN);
        STRING_TOKEN_SET.add(KOTLIN_CHAR_TOKEN);
        STRING_TOKEN_SET.add(JS_STRING_TOKEN);
        STRING_TOKEN_SET.add(JAVA_STRING_TOKEN);
        STRING_TOKEN_SET.add(SCALA_STRING_TOKEN);
        STRING_TOKEN_SET.add(HASKELL_STRING_TOKEN);
    }

    /**
     * Find the left closest brace offset position.
     *
     * @param iterator        highlighter iterator
     * @param lparenTokenType left token type to be paired
     * @param fileText        file text
     * @param fileType        file type
     * @return offset
     */
    public static int findLeftBraceOffset(HighlighterIterator iterator, IElementType lparenTokenType, CharSequence fileText, FileType fileType, boolean isBlockCaret, Integer offset) {
        return BRACKET_MATCHING_FACTORY.getProcessor(fileType).findLeftParen(iterator, lparenTokenType, fileText, fileType, isBlockCaret, offset);
    }

    /**
     * find the right closest brace offset position
     *
     * @param iterator        highlight iterator
     * @param rparenTokenType right token type to paired
     * @param fileText        file text
     * @param fileType        file type
     * @return offset
     */
    public static int findRightBraceOffset(HighlighterIterator iterator, IElementType rparenTokenType, CharSequence fileText,
                                           FileType fileType, boolean isBlockCaret, Integer offset) {

        return BRACKET_MATCHING_FACTORY.getProcessor(fileType).findRightParen(iterator, rparenTokenType, fileText, fileType, isBlockCaret, offset);
    }

    /**
     * check is the current token type is string token.
     *
     * @param tokenType token type
     * @return is string token
     */
    public static boolean isStringToken(IElementType tokenType) {
        String elementName = tokenType.toString();
        return STRING_TOKEN_SET.contains(elementName);
    }

    /**
     * Find left string symbol matches
     *
     * @param fileText Character text
     * @param offset   offset
     * @return offset
     */
    public static int findLeftParen(CharSequence fileText, int offset) {
        if (offset > fileText.length() || offset < 0) {
            return NON_BRACE_OFFSET;
        }
        Deque<Integer> indexDeque = new ArrayDeque<>();
        for (int i = offset - 1; i > -1; i--) {
            char character = fileText.charAt(i);
            if (isLeftBrace(character)) {
                if (!indexDeque.isEmpty()) {
                    indexDeque.removeFirst();
                    continue;
                }
                return i;
            } else if (isRightBrace(character)) {
                indexDeque.addFirst(i);
            }
        }
        return NON_BRACE_OFFSET;
    }

    /**
     * Find right string symbol matches
     *
     * @param fileText Character text
     * @param offset   offset
     * @return offset
     */
    public static int findRightParen(CharSequence fileText, int offset) {
        if (offset > fileText.length() || offset < 0) {
            return NON_BRACE_OFFSET;
        }
        Deque<Integer> index = new ArrayDeque<>();
        for (int i = offset; i < fileText.length(); i++) {
            char character = fileText.charAt(i);
            if (isRightBrace(character)) {
                if (!index.isEmpty()) {
                    index.removeFirst();
                    continue;
                }
                return i;
            } else if (isLeftBrace(character)) {
                index.addFirst(i);
            }
        }
        return NON_BRACE_OFFSET;
    }

    /**
     * Determine whether it is left bracket character
     *
     * @param character character
     * @return true:is left character false:otherwise
     */
    private static boolean isLeftBrace(char character) {
        return character == L_BRACE_ATTR || character == L_BRACKET_ATTR || character == L_CUSP_BRACKETS_ATTR || character == L_PARENTHESIS_ATTR;
    }

    /**
     * Determine whether it is right bracket character
     *
     * @param character character
     * @return true:is right character false:otherwise
     */
    private static boolean isRightBrace(char character) {
        return character == R_BRACE_ATTR || character == R_BRACKET_ATTR || character == R_CUSP_BRACKETS_ATTR || character == R_PARENTHESIS_ATTR;
    }
}
