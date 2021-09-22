package cn.aprilviolet.highlightbracketpair.highlighter;

import cn.aprilviolet.highlightbracketpair.adapter.BracketMatchProcessorHolder;
import cn.aprilviolet.highlightbracketpair.brace.Brace;
import cn.aprilviolet.highlightbracketpair.brace.BracePair;
import cn.aprilviolet.highlightbracketpair.brace.BraceTokenTypes;
import cn.aprilviolet.highlightbracketpair.extend.XmlSupportedToken;
import cn.aprilviolet.highlightbracketpair.setting.HighlightBracketPairSettingsPage;
import cn.aprilviolet.highlightbracketpair.util.Pair;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.ex.MarkupModelEx;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.xml.XmlTokenType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static cn.aprilviolet.highlightbracketpair.brace.BraceTokenTypes.DOUBLE_QUOTE;

/**
 * Brace highlighter abstract class.
 *
 * @author AprilViolet
 * @version V1.0.0
 * @date 2021.07.31 14:50
 * @since V1.0.0
 */
public abstract class AbstractBracketHighlighter {
    public static final Integer NON_OFFSET = -1;

    public static final Integer HIGHLIGHT_LAYER_WEIGHT = 100;

    public static final BracePair EMPTY_BRACE_PAIR = new BracePair.BracePairBuilder()
            .leftOffset(NON_OFFSET).rightOffset(NON_OFFSET).build();

    protected Editor editor;

    protected Project project;

    protected Document document;

    protected FileType fileType;

    protected CharSequence fileText;

    protected PsiFile psiFile;

    protected MarkupModelEx markupModelEx;

    public static final String LEFT_BRACKET = "{";

    public static final String RIGHT_BRACKET = "}";

    public static final String BRACE_ATTR_GUTTER = "|";

    protected AbstractBracketHighlighter(Editor editor) {
        this.editor = editor;
        this.project = this.editor.getProject();
        this.document = this.editor.getDocument();
        this.psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);
        if (psiFile == null) {
            return;
        }
        this.fileType = psiFile.getFileType();
        this.fileText = this.editor.getDocument().getImmutableCharSequence();
        this.markupModelEx = (MarkupModelEx) this.editor.getMarkupModel();
    }

    /**
     * find the brace positions
     *
     * @param offset offset
     * @return contain left and right BracePair
     */
    public BracePair findClosetBracePair(int offset) {
        BracePair braceTokenBracePair = this.findCloseBracePairInBraceTokens(offset);
        BracePair stringSymbolBracePair = this.findCloseBracePairInStringSymbols(offset);

        int leftBraceTokenOffset = braceTokenBracePair.getLeftBrace().getOffset();
        int rightBraceTokenOffset = braceTokenBracePair.getRightBrace().getOffset();
        int leftSymbolBraceOffset = stringSymbolBracePair.getLeftBrace().getOffset();
        int rightSymbolBraceOffset = stringSymbolBracePair.getRightBrace().getOffset();

        if ((offset - leftBraceTokenOffset > offset - leftSymbolBraceOffset)
                && (offset - rightBraceTokenOffset < offset - rightSymbolBraceOffset)) {
            return stringSymbolBracePair;
        } else {
            return braceTokenBracePair;
        }
    }

    /**
     * highlight pair
     *
     * @param bracePair BracePair
     * @return Pair<RangeHighlighter, RangeHighlighter>
     */
    public Pair<RangeHighlighter, RangeHighlighter> highlightPair(BracePair bracePair) {
        final Brace leftBrace = bracePair.getLeftBrace();
        final Brace rightBrace = bracePair.getRightBrace();
        final int leftBraceOffset = leftBrace.getOffset();
        final int rightBraceOffset = rightBrace.getOffset();
        final String leftBraceText = leftBrace.getText();
        final String rightBraceText = rightBrace.getText();

        if (leftBraceOffset == NON_OFFSET || rightBraceOffset == NON_OFFSET) {
            return null;
        }
        // try to get the text attr by element type
        TextAttributesKey textAttributesKey =
                HighlightBracketPairSettingsPage.getTextAttributesKeyByToken(leftBrace.getElementType());
        // if not found, get the text attr by brace text
        if (textAttributesKey == null) {
            textAttributesKey = HighlightBracketPairSettingsPage.getTextAttributesKeyByText(leftBraceText);
        }
        final TextAttributes textAttributes = editor.getColorsScheme().getAttributes(textAttributesKey);

        RangeHighlighter leftHighlighter = markupModelEx.addRangeHighlighter(leftBraceOffset,
                leftBraceOffset + leftBraceText.length(), HighlighterLayer.SELECTION + HIGHLIGHT_LAYER_WEIGHT,
                textAttributes, HighlighterTargetArea.EXACT_RANGE);
        RangeHighlighter rightHighlighter = markupModelEx.addRangeHighlighter(rightBraceOffset,
                rightBraceOffset + rightBraceText.length(), HighlighterLayer.SELECTION + HIGHLIGHT_LAYER_WEIGHT,
                textAttributes, HighlighterTargetArea.EXACT_RANGE);
        return new Pair<>(leftHighlighter, rightHighlighter);
    }

    /**
     * Render braces in gutter
     *
     * @param bracePair BracePair
     * @return List<RangeHighlighter>
     */
    public Pair<RangeHighlighter, RangeHighlighter> renderBracesInGutter(BracePair bracePair, Integer gutterBracketSize) {
        final Brace leftBrace = bracePair.getLeftBrace();
        final Brace rightBrace = bracePair.getRightBrace();
        final int leftBraceOffset = leftBrace.getOffset();
        final int rightBraceOffset = rightBrace.getOffset();
        final String leftBraceText = leftBrace.getText();
        final String rightBraceText = rightBrace.getText();

        if (leftBraceOffset == NON_OFFSET || rightBraceOffset == NON_OFFSET) {
            return null;
        }
        if (!LEFT_BRACKET.equals(leftBraceText) || !RIGHT_BRACKET.equals(rightBraceText)) {
            return null;
        }

        // try to get the text attr by element type
        TextAttributesKey textAttributesKey =
                HighlightBracketPairSettingsPage.getTextAttributesKeyByToken(leftBrace.getElementType());
        // if not found, get the text attr by brace text
        if (textAttributesKey == null) {
            textAttributesKey = HighlightBracketPairSettingsPage.getTextAttributesKeyByText(BRACE_ATTR_GUTTER);
        }
        final TextAttributes textAttributes = editor.getColorsScheme().getAttributes(textAttributesKey);

        int openBraceLine = document.getLineNumber(leftBraceOffset);
        RangeHighlighter openBraceHighlighter = renderBraceInGutter(openBraceLine, leftBraceText,
                textAttributes, gutterBracketSize);
        int closeBraceLine = document.getLineNumber(rightBraceOffset);
        RangeHighlighter closeBraceHighlighter = renderBraceInGutter(closeBraceLine, rightBraceText,
                textAttributes, gutterBracketSize);

        return new Pair<>(openBraceHighlighter, closeBraceHighlighter);
    }

    /**
     * Render braces in gutter
     *
     * @param braceLine brace line
     * @param braceText brace text
     * @param textAttributes text
     * @return RangeHighlighter
     */
    public RangeHighlighter renderBraceInGutter(int braceLine, String braceText,
                                                TextAttributes textAttributes, Integer gutterBracketSize) {
        RangeHighlighter braceHighlighter = editor.getMarkupModel()
                .addLineHighlighter(braceLine, HighlighterLayer.SELECTION, null);

        GutterIconRenderer braceGutterIconRenderer = new GutterIconRenderer() {
            @NotNull
            @Override
            public Icon getIcon() {
                return new Icon() {
                    @Override
                    public void paintIcon(Component c, Graphics g, int x, int y) {
                        if (braceText.toCharArray().length < 1) {
                            return;
                        }

                        g.setColor(textAttributes.getForegroundColor());
                        g.setFont(new Font("JetBrains Mono", Font.BOLD, gutterBracketSize));
                        g.drawChars(braceText.toCharArray(), 0, braceText.length(), 0, 0);
                    }

                    @Override
                    public int getIconWidth() {
                        return 1;
                    }

                    @Override
                    public int getIconHeight() {
                        return 1;
                    }
                };
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }

            @Override
            public int hashCode() {
                return 1;
            }
        };

        braceHighlighter.setGutterIconRenderer(braceGutterIconRenderer);
        braceHighlighter.setGreedyToRight(true);
        return braceHighlighter;
    }

    /**
     * find close brace pair
     *
     * @param offset offset
     * @return BracePair
     */
    @SuppressWarnings("RedundantCast")
    private BracePair findCloseBracePairInBraceTokens(int offset) {
        EditorHighlighter editorHighlighter = ((EditorEx) editor).getHighlighter();
        boolean isBlockCaret = this.isBlockCaret();
        List<Pair<IElementType, IElementType>> braceTokens = this.getSupportedBraceToken();
        for (Pair<IElementType, IElementType> braceTokenPair : braceTokens) {
            HighlighterIterator leftTraverseIterator = editorHighlighter.createIterator(offset);
            HighlighterIterator rightTraverseIterator = editorHighlighter.createIterator(offset);

            int leftBraceOffset = BracketMatchProcessorHolder.findLeftBraceOffset(leftTraverseIterator,
                    braceTokenPair.getLeft(), this.fileText, this.fileType, isBlockCaret, offset);
            int rightBraceOffset = BracketMatchProcessorHolder.findRightBraceOffset(rightTraverseIterator,
                    braceTokenPair.getRight(), this.fileText, this.fileType, isBlockCaret, offset);

            if (leftBraceOffset != NON_OFFSET && rightBraceOffset != NON_OFFSET) {
                if (braceTokenPair.getRight().equals(XmlTokenType.XML_TAG_END)) {
                    HighlighterIterator leftIterator = editorHighlighter.createIterator(leftBraceOffset);
                    HighlighterIterator rightIterator = editorHighlighter.createIterator(rightBraceOffset);
                    String leftText = XmlSupportedToken.getLeftPartOnlyName(leftBraceOffset, leftIterator);
                    String rightText = XmlSupportedToken.getRightPart(rightBraceOffset + 1, rightIterator, leftBraceOffset);
                    return new BracePair.BracePairBuilder().leftType(braceTokenPair.getLeft())
                            .rightType(braceTokenPair.getRight()).leftText(leftText).rightText(rightText)
                            .leftOffset(leftBraceOffset).rightOffset(rightBraceOffset - rightText.length() + 1).build();
                }

                if (braceTokenPair.getRight().equals(XmlTokenType.XML_EMPTY_ELEMENT_END)) {
                    HighlighterIterator leftIterator = editorHighlighter.createIterator(leftBraceOffset);
                    String leftText = XmlSupportedToken.getLeftPartOnlyName(leftBraceOffset, leftIterator);
                    String rightText = document.getText(new TextRange(rightTraverseIterator.getStart(),
                            rightTraverseIterator.getEnd()));
                    return new BracePair.BracePairBuilder().leftType(braceTokenPair.getLeft())
                            .rightType(braceTokenPair.getRight()).leftText(leftText).rightText(rightText)
                            .leftOffset(leftBraceOffset).rightOffset(rightTraverseIterator.getStart()).build();
                }
                if (braceTokenPair.getRight().equals(BraceTokenTypes.TEXT_TOKEN)) {
                    String leftText = document.getText(new TextRange(leftBraceOffset, leftBraceOffset + 1));
                    String rightText = document.getText(new TextRange(rightBraceOffset, rightBraceOffset + 1));
                    return new BracePair.BracePairBuilder().leftType(braceTokenPair.getLeft())
                            .rightType(braceTokenPair.getRight()).leftText(leftText).rightText(rightText)
                            .leftOffset(leftBraceOffset).rightOffset(rightBraceOffset).build();
                }
                return new BracePair.BracePairBuilder().leftType(braceTokenPair.getLeft()).rightType(braceTokenPair.getRight())
                                .leftIterator(leftTraverseIterator).rightIterator(rightTraverseIterator).build();
            }
        }
        return EMPTY_BRACE_PAIR;
    }

    /**
     * find close brace pair
     *
     * @param offset offset
     * @return BracePair
     */
    @SuppressWarnings("RedundantCast")
    private BracePair findCloseBracePairInStringSymbols(int offset) {
        if (offset < 0 || this.fileText == null || this.fileText.length() == 0) {
            return EMPTY_BRACE_PAIR;
        }
        EditorHighlighter editorHighlighter = ((EditorEx) editor).getHighlighter();
        HighlighterIterator iterator = editorHighlighter.createIterator(offset);
        IElementType type = iterator.getTokenType();
        boolean isBlockCaret = this.isBlockCaret();
        if (!BracketMatchProcessorHolder.isStringToken(type)) {
            return EMPTY_BRACE_PAIR;
        }

        int leftOffset = iterator.getStart();
        int rightOffset = iterator.getEnd() - 1;
        if (!isBlockCaret && leftOffset == offset) {
            return EMPTY_BRACE_PAIR;
        }
        return new BracePair.BracePairBuilder().leftType(DOUBLE_QUOTE).rightType(DOUBLE_QUOTE)
                .leftOffset(leftOffset).rightOffset(rightOffset).build();
    }

    /**
     * Get languages default register brace
     *
     * @return languages register brace
     */
    protected abstract List<Pair<IElementType, IElementType>> getSupportedBraceToken();

    /**
     * clear the high lighter
     *
     * @param highlighterList highlighterList
     */
    public void eraseHighlight(List<RangeHighlighter> highlighterList) {
        for (RangeHighlighter highlighter : highlighterList) {
            this.markupModelEx.removeHighlighter(highlighter);
        }
    }

    public boolean isBlockCaret() {
        return this.editor.getSettings().isBlockCursor();
    }
}
