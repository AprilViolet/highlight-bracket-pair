package cn.aprilviolet.highlightbracketpair.brace;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.tree.IElementType;

/**
 * Brace
 *
 * @author AprilViolet
 * @version V1.0.0
 * @date 2021.07.31 星期六 13:57
 * @since V1.0.0
 */
public class Brace {
    private final IElementType elementType;

    private final int offset;

    private final String text;

    public Brace(IElementType elementType, HighlighterIterator iterator) {
        this.elementType = elementType;
        this.offset = iterator.getStart();

        Document document = iterator.getDocument();
        this.text = document.getText(new TextRange(iterator.getStart(), iterator.getEnd()));
    }

    public Brace(IElementType elementType, String text, int offset) {
        this.elementType = elementType;
        this.offset = offset;
        this.text = text;
    }

    public IElementType getElementType() {
        return elementType;
    }

    public String getText() {
        return text;
    }

    public int getOffset() {
        return offset;
    }
}
