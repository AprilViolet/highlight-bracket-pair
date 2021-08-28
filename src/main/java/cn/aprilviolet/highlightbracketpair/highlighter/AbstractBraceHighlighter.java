package cn.aprilviolet.highlightbracketpair.highlighter;

import cn.aprilviolet.highlightbracketpair.adapter.BraceMatchingUtilAdapter;
import cn.aprilviolet.highlightbracketpair.brace.Brace;
import cn.aprilviolet.highlightbracketpair.brace.BracePair;
import cn.aprilviolet.highlightbracketpair.setting.HighlightBracketPairSettingsPage;
import cn.aprilviolet.highlightbracketpair.util.Pair;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.ex.MarkupModelEx;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;

import java.util.LinkedList;
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
abstract public class AbstractBraceHighlighter {
    public final static int NON_OFFSET = -1;

    public final static int HIGHLIGHT_LAYER_WEIGHT = 100;

    public final static BracePair EMPTY_BRACE_PAIR = new BracePair.BracePairBuilder()
            .leftOffset(NON_OFFSET).rightOffset(NON_OFFSET).build();

    protected Editor editor;

    protected Project project;

    protected Document document;

    protected FileType fileType;

    protected CharSequence fileText;

    protected PsiFile psiFile;

    protected MarkupModelEx markupModelEx;

    public AbstractBraceHighlighter(Editor editor) {
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
        BracePair braceTokenBracePair = this.findClosetBracePairInBraceTokens(offset);
        BracePair stringSymbolBracePair = this.findClosetBracePairInStringSymbols(offset);

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

    public BracePair findClosetBracePairInBraceTokens(int offset) {
        EditorHighlighter editorHighlighter = ((EditorEx) editor).getHighlighter();
        boolean isBlockCaret = this.isBlockCaret();
        List<Pair<IElementType, IElementType>> braceTokens = this.getSupportedBraceToken();
        for (Pair<IElementType, IElementType> braceToken : braceTokens) {
            HighlighterIterator leftTraverseIterator = editorHighlighter.createIterator(offset);
            HighlighterIterator rightTraverseIterator = editorHighlighter.createIterator(offset);

            int leftBraceOffset = BraceMatchingUtilAdapter.findLeftBraceOffset(leftTraverseIterator,
                    braceToken.getLeft(), this.fileText, this.fileType, isBlockCaret);
            int rightBraceOffset = BraceMatchingUtilAdapter.findRightBraceOffset(rightTraverseIterator,
                    braceToken.getRight(), this.fileText, this.fileType, isBlockCaret);

            if (leftBraceOffset != NON_OFFSET && rightBraceOffset != NON_OFFSET) {
                return new BracePair.BracePairBuilder().leftType(braceToken.getLeft()).rightType(braceToken.getRight()).
                        leftIterator(leftTraverseIterator).rightIterator(rightTraverseIterator).build();
            }
        }
        return EMPTY_BRACE_PAIR;
    }

    public BracePair findClosetBracePairInStringSymbols(int offset) {
        if (offset < 0 || this.fileText == null || this.fileText.length() == 0) {
            return EMPTY_BRACE_PAIR;
        }
        EditorHighlighter editorHighlighter = ((EditorEx) editor).getHighlighter();
        HighlighterIterator iterator = editorHighlighter.createIterator(offset);
        IElementType type = iterator.getTokenType();
        boolean isBlockCaret = this.isBlockCaret();
        if (!BraceMatchingUtilAdapter.isStringToken(type)) {
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

    public List<Pair<IElementType, IElementType>> getSupportedBraceToken() {
        return new LinkedList<>();
    }

    public void eraseHighlight(List<RangeHighlighter> highlighterList) {
        for (RangeHighlighter highlighter : highlighterList) {
            this.markupModelEx.removeHighlighter(highlighter);
        }
    }

    public boolean isBlockCaret() {
        return this.editor.getSettings().isBlockCursor();
    }
}
