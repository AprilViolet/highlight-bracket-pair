package cn.aprilviolet.highlightbracketpair.brace;

import com.intellij.lang.Language;
import com.intellij.psi.tree.IElementType;

import java.util.HashMap;
import java.util.Map;

/**
 * Brace Token Types
 *
 * @author AprilViolet
 * @version V1.0.0
 * @date 2021.07.31 星期六 13:57
 * @since V1.0.0
 */
public class BraceTokenTypes {
    private final static Map<IElementType, String> ELEMENT_TYPE_TEXT = new HashMap<>();

    public final static IElementType DOUBLE_QUOTE = new IElementType("DOUBLE_QUOTE", Language.ANY);

    public final static String GROOVY_STRING_TOKEN = "Gstring";

    public final static String GROOVY_SINGLE_QUOTE_TOKEN = "string";

    public final static String KOTLIN_STRING_TOKEN = "REGULAR_STRING_PART";

    public final static String KOTLIN_CHAR_TOKEN = "CHARACTER_LITERAL";

    public final static String JS_STRING_TOKEN = "STRING";

    public final static String JAVA_STRING_TOKEN = "STRING_LITERAL";

    public final static String SCALA_STRING_TOKEN = "string content";

    public final static String HASKELL_STRING_TOKEN = "HaskellTokenType.STRING_LITERAL";

    static {
        ELEMENT_TYPE_TEXT.put(DOUBLE_QUOTE, "\"");
    }

    public static String getElementTypeText(IElementType type) {
        return ELEMENT_TYPE_TEXT.get(type);
    }
}
