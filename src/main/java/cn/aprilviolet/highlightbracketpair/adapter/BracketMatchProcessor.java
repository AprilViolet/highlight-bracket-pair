package cn.aprilviolet.highlightbracketpair.adapter;

import cn.aprilviolet.highlightbracketpair.constant.Constant;
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
    default int findLeftParen(HighlighterIterator iterator, IElementType lparenTokenType, CharSequence fileText, FileType fileType, Boolean isBlockCaret, Integer offset) {
        return findLeftParenDefault(iterator, lparenTokenType, fileText, fileType, isBlockCaret);
    }

    /**
     * Find the left closest brace offset position.
     *
     * @param iterator        iterator
     * @param rparenTokenType lparenTokenType
     * @param fileText        fileText
     * @param fileType        fileType
     * @param isBlockCaret    isBlockCaret
     * @param offset          offset
     * @return left offset position
     */
    default int findRightParen(HighlighterIterator iterator, IElementType rparenTokenType, CharSequence fileText, FileType fileType, Boolean isBlockCaret, Integer offset) {
        return findRighParenDefault(iterator, rparenTokenType, fileText, fileType, isBlockCaret);
    }

    /**
     * Find the left closest brace offset position.
     *
     * @param iterator        iterator
     * @param lparenTokenType lparenTokenType
     * @param fileText        fileText
     * @param fileType        fileType
     * @param isBlockCaret    isBlockCaret
     * @return left offset position
     */
    default int findLeftParenDefault(HighlighterIterator iterator, IElementType lparenTokenType, CharSequence fileText, FileType fileType, Boolean isBlockCaret) {
        int initOffset = iterator.atEnd() ? -1 : iterator.getStart();
        Deque<IElementType> braceStack = new ArrayDeque<>();
        for (; !iterator.atEnd(); iterator.retreat()) {
            IElementType tokenType = iterator.getTokenType();
            if (isLBraceToken(iterator, fileText, fileType)) {
                if (!isBlockCaret && initOffset == iterator.getStart()) {
                } else if (!braceStack.isEmpty()) {
                    if (!isPairBraces(tokenType, braceStack.removeFirst(), fileType)) {
                        break;
                    }
                } else {
                    if (tokenType == lparenTokenType) {
                        return iterator.getStart();
                    }
                    break;
                }
            } else if (isRBraceToken(iterator, fileText, fileType) && initOffset != iterator.getStart()) {
                braceStack.addFirst(iterator.getTokenType());
            }
        }
        return Constant.NON_OFFSET;
    }

    /**
     * Find the left closest brace offset position.
     *
     * @param iterator        iterator
     * @param rparenTokenType lparenTokenType
     * @param fileText        fileText
     * @param fileType        fileType
     * @param isBlockCaret    isBlockCaret
     * @return left offset position
     */
    default int findRighParenDefault(HighlighterIterator iterator, IElementType rparenTokenType, CharSequence fileText, FileType fileType, Boolean isBlockCaret) {
        int initOffset = iterator.atEnd() ? -1 : iterator.getStart();
        Deque<IElementType> braceStack = new ArrayDeque<>();
        for (; !iterator.atEnd(); iterator.advance()) {
            IElementType tokenType = iterator.getTokenType();
            if (isRBraceToken(iterator, fileText, fileType)) {
                if (!braceStack.isEmpty()) {
                    if (!isPairBraces(tokenType, braceStack.removeFirst(), fileType)) {
                        break;
                    }
                } else {
                    if (tokenType == rparenTokenType) {
                        return iterator.getStart();
                    }
                    break;
                }
            } else if (isLBraceToken(iterator, fileText, fileType) && !(isBlockCaret && initOffset == iterator.getStart())) {
                braceStack.addFirst(iterator.getTokenType());
            }
        }
        return Constant.NON_OFFSET;
    }
}
