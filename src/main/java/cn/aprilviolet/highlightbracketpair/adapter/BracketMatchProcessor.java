package cn.aprilviolet.highlightbracketpair.adapter;

import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.tree.IElementType;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.intellij.codeInsight.highlighting.BraceMatchingUtil.*;

/**
 * Find left and right bracket interface
 *
 * @author AprilViolet
 * @version v1.0.0
 * @date 2021.09.21 10:42
 * @since v1.0.0
 */
public interface BracketMatchProcessor {
    Integer NON_BRACE_OFFSET = -1;

    /**
     * Find the left closest brace offset position.
     *
     * @param iterator        highlighter iterator
     * @param lparenTokenType left token type to be paired
     * @param fileText        file text
     * @param fileType        file type
     * @param isBlockCaret    is it a block caret
     * @param offset          offset
     * @return offset
     */
    default int findLeftParen(HighlighterIterator iterator, IElementType lparenTokenType, CharSequence fileText,
                              FileType fileType, boolean isBlockCaret, Integer offset) {
        int initOffset = iterator.atEnd() ? -1 : iterator.getStart();
        Deque<IElementType> braceStack = new ArrayDeque<>();
        for (; !iterator.atEnd(); iterator.retreat()) {
            final IElementType tokenType = iterator.getTokenType();
            if (isLBraceToken(iterator, fileText, fileType)) {
                if (!isBlockCaret && initOffset == iterator.getStart()) {
                    continue;
                }
                if (!braceStack.isEmpty()) {
                    IElementType topToken = braceStack.removeFirst();
                    if (!isPairBraces(tokenType, topToken, fileType)) {
                        // unmatched braces
                        break;
                    }
                } else {
                    if (tokenType == lparenTokenType) {
                        return iterator.getStart();
                    } else {
                        break;
                    }
                }
            } else if (isRBraceToken(iterator, fileText, fileType)) {
                if (initOffset == iterator.getStart()) {
                    continue;
                }
                braceStack.addFirst(iterator.getTokenType());
            }
        }

        return NON_BRACE_OFFSET;
    }

    /**
     * find the right closest brace offset position
     *
     * @param iterator        highlight iterator
     * @param rparenTokenType right token type to paired
     * @param fileText        file text
     * @param fileType        file type
     * @param isBlockCaret    is it a block caret
     * @param offset          offset
     * @return offset
     */
    default int findRightParen(HighlighterIterator iterator, IElementType rparenTokenType, CharSequence fileText,
                               FileType fileType, boolean isBlockCaret, Integer offset) {
        int initOffset = iterator.atEnd() ? -1 : iterator.getStart();
        Deque<IElementType> braceStack = new ArrayDeque<>();
        for (; !iterator.atEnd(); iterator.advance()) {
            final IElementType tokenType = iterator.getTokenType();

            if (isRBraceToken(iterator, fileText, fileType)) {
                if (!braceStack.isEmpty()) {
                    IElementType topToken = braceStack.removeFirst();
                    if (!isPairBraces(tokenType, topToken, fileType)) {
                        // unmatched braces
                        break;
                    }
                } else {
                    if (tokenType == rparenTokenType) {
                        return iterator.getStart();
                    } else {
                        break;
                    }
                }
            } else if (isLBraceToken(iterator, fileText, fileType) && (!isBlockCaret || initOffset != iterator.getStart())) {
                braceStack.addFirst(iterator.getTokenType());
            }
        }
        return NON_BRACE_OFFSET;
    }
}
