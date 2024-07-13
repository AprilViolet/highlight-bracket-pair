package cn.aprilviolet.highlightbracketpair.adapter;

import cn.aprilviolet.highlightbracketpair.brace.BraceTokenTypes;
import cn.aprilviolet.highlightbracketpair.constant.Constant;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.xml.XmlTokenType;

/**
 * xml bracket enhanced adaptation
 *
 * @author AprilViolet
 * @version v1.0.0
 * @date 2021.09.21 14:34
 * @since v1.0.0
 */
public class XmlBracketMatchProcessor implements BracketMatchProcessor {
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
    @Override
    public int findLeftParen(HighlighterIterator iterator, IElementType lparenTokenType, CharSequence fileText, FileType fileType, Boolean isBlockCaret, Integer offset) {
        if (iterator.getTokenType().equals(XmlTokenType.XML_DATA_CHARACTERS)) {
            int leftParen = BracketMatchProcessorHolder.findLeftParen(fileText, offset);
            if (leftParen != Constant.NON_OFFSET && lparenTokenType == BraceTokenTypes.TEXT_TOKEN) {
                return leftParen;
            }
            return Constant.NON_OFFSET;
        }
        return findLeftParenDefault(iterator, lparenTokenType, fileText, fileType, isBlockCaret);
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
    @Override
    public int findRightParen(HighlighterIterator iterator, IElementType rparenTokenType, CharSequence fileText, FileType fileType, Boolean isBlockCaret, Integer offset) {
        if (iterator.getTokenType().equals(XmlTokenType.XML_DATA_CHARACTERS)) {
            int rightParen = BracketMatchProcessorHolder.findRightParen(fileText, offset);
            if (rightParen != Constant.NON_OFFSET && rparenTokenType == BraceTokenTypes.TEXT_TOKEN) {
                return rightParen;
            }
            return Constant.NON_OFFSET;
        }
        return findRighParenDefault(iterator, rparenTokenType, fileText, fileType, isBlockCaret);
    }
}
