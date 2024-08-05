package cn.aprilviolet.highlightbracketpair.setting;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.PlainSyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.xml.XmlTokenType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

import static cn.aprilviolet.highlightbracketpair.brace.BraceTokenTypes.DOUBLE_QUOTE;


/**
 * Color settings for different bracket pair shape.
 *
 * @author AprilViolet
 * @version v1.0.0
 * @date 2021.07.31 11:02
 * @since v1.0.0
 */
public class HighlightBracketPairSettingsPage implements ColorSettingsPage {
    public static final TextAttributesKey BRACE_ATTR = TextAttributesKey.createTextAttributesKey("BRACE_ATTR");

    public static final TextAttributesKey BRACKET_ATTR = TextAttributesKey.createTextAttributesKey("BRACKET_ATTR");

    public static final TextAttributesKey PARENTHESIS_ATTR = TextAttributesKey.createTextAttributesKey("PARENTHESIS_ATTR");

    public static final TextAttributesKey DOUBLE_QUOTE_ATTR = TextAttributesKey.createTextAttributesKey("DOUBLE_QUOTE_ATTR");

    public static final TextAttributesKey CUSP_BRACKETS_ATTR = TextAttributesKey.createTextAttributesKey("CUSP_BRACKETS_ATTR");

    public static final TextAttributesKey BRACE_ATTR_GUTTER = TextAttributesKey.createTextAttributesKey("BRACE_ATTR_GUTTER");

    private static final AttributesDescriptor[] ATTRIBUTES_DESCRIPTION = {
            new AttributesDescriptor("Brace", BRACE_ATTR),
            new AttributesDescriptor("Bracket", BRACKET_ATTR),
            new AttributesDescriptor("Parenthesis", PARENTHESIS_ATTR),
            new AttributesDescriptor("DoubleQuote", DOUBLE_QUOTE_ATTR),
            new AttributesDescriptor("CuspBracket", CUSP_BRACKETS_ATTR),
            new AttributesDescriptor("BraceInGutter", BRACE_ATTR_GUTTER)
    };

    private static final Map<IElementType, TextAttributesKey> ELEMENT_TYPE_ATTRIBUTE = new HashMap<>();

    private static final Map<String, TextAttributesKey> CONTENT_ATTRIBUTE = new HashMap<>();

    private static final Map<String, TextAttributesKey> TAGS = new HashMap<>();

    static {
        // ELEMENT_TYPE_ATTRIBUTE
        ELEMENT_TYPE_ATTRIBUTE.put(XmlTokenType.XML_START_TAG_START, CUSP_BRACKETS_ATTR);
        ELEMENT_TYPE_ATTRIBUTE.put(XmlTokenType.XML_ATTRIBUTE_VALUE_START_DELIMITER, DOUBLE_QUOTE_ATTR);
        ELEMENT_TYPE_ATTRIBUTE.put(DOUBLE_QUOTE, DOUBLE_QUOTE_ATTR);
        // CONTENT_ATTRIBUTE
        CONTENT_ATTRIBUTE.put("{", BRACE_ATTR);
        CONTENT_ATTRIBUTE.put("[", BRACKET_ATTR);
        CONTENT_ATTRIBUTE.put("(", PARENTHESIS_ATTR);
        CONTENT_ATTRIBUTE.put("<", CUSP_BRACKETS_ATTR);
        CONTENT_ATTRIBUTE.put("|", BRACE_ATTR_GUTTER);
        // TAGS
        TAGS.put("Brace", BRACE_ATTR);
        TAGS.put("Bracket", BRACKET_ATTR);
        TAGS.put("Parenthesis", PARENTHESIS_ATTR);
        TAGS.put("DoubleQuote", DOUBLE_QUOTE_ATTR);
        TAGS.put("CuspBracket", CUSP_BRACKETS_ATTR);
        TAGS.put("BraceInGutter", BRACE_ATTR_GUTTER);
    }

    public static TextAttributesKey getTextAttributesKeyByToken(IElementType type) {
        return ELEMENT_TYPE_ATTRIBUTE.get(type);
    }

    public static TextAttributesKey getTextAttributesKeyByText(String content) {
        return CONTENT_ATTRIBUTE.get(content);
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return null;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new PlainSyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
        return """
                <Brace>{</Brace>...<Brace>}</Brace>\

                <Parenthesis>(</Parenthesis>...<Parenthesis>)</Parenthesis>\

                <Bracket>[</Bracket>...<Bracket>]</Bracket>\

                <CuspBracket><</CuspBracket>...<CuspBracket>></CuspBracket>\

                <DoubleQuote>"</DoubleQuote>...<DoubleQuote>"</DoubleQuote>\

                <BraceInGutter>|</BraceInGutter>...<BraceInGutter>|</BraceInGutter>""";
    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return TAGS;
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return ATTRIBUTES_DESCRIPTION;
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return new ColorDescriptor[0];
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "HighlightBracketPair";
    }
}
