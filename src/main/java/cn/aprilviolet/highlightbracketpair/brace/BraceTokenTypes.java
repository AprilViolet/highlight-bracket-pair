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
 * @date 2021.07.31 13:57
 * @since V1.0.0
 */
public class BraceTokenTypes {
    private BraceTokenTypes() {
    }

    private static final Map<IElementType, String> ELEMENT_TYPE_TEXT = new HashMap<>();

    public static final IElementType DOUBLE_QUOTE = new IElementType("DOUBLE_QUOTE", Language.ANY);

    public static final String GROOVY_STRING_TOKEN = "Gstring";

    public static final String GROOVY_SINGLE_QUOTE_TOKEN = "string";

    public static final String KOTLIN_STRING_TOKEN = "REGULAR_STRING_PART";

    public static final String KOTLIN_CHAR_TOKEN = "CHARACTER_LITERAL";

    public static final String JS_STRING_TOKEN = "STRING";

    public static final String JAVA_STRING_TOKEN = "STRING_LITERAL";

    public static final String SCALA_STRING_TOKEN = "string content";

    public static final String HASKELL_STRING_TOKEN = "HaskellTokenType.STRING_LITERAL";

    public static final IElementType TEXT_TOKEN = new IElementType("TEXT_TOKEN", Language.ANY);

    static {
        ELEMENT_TYPE_TEXT.put(DOUBLE_QUOTE, "\"");
    }

    public static String getElementTypeText(IElementType type) {
        return ELEMENT_TYPE_TEXT.get(type);
    }
}
