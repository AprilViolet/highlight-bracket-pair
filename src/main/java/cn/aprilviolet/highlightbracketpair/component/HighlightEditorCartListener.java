package cn.aprilviolet.highlightbracketpair.component;

import cn.aprilviolet.highlightbracketpair.brace.BracePair;
import cn.aprilviolet.highlightbracketpair.highlighter.AbstractBraceHighlighter;
import cn.aprilviolet.highlightbracketpair.highlighter.BraceHighlighterFactory;
import cn.aprilviolet.highlightbracketpair.util.Pair;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.editor.event.CaretListener;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Highlight editor component to highlight the most left brace and most right brace when the current caret is change.
 *
 * @author AprilViolet
 * @version V1.0.0
 * @date 2021.07.30 星期五 17:16
 * @since V1.0.0
 */
public class HighlightEditorCartListener implements CaretListener {
    private final Editor editor;

    private final List<RangeHighlighter> highlighterList = new ArrayList<>();

    private final ExtraHighlightTrigger extraHighlightTrigger;

    public HighlightEditorCartListener(Editor editor) {
        this.editor = editor;
        this.extraHighlightTrigger = new ExtraHighlightTrigger(this);
        this.editor.getContentComponent().addKeyListener(this.extraHighlightTrigger);
        editor.getCaretModel().addCaretListener(this);
    }

    public void dispose() {
        editor.getCaretModel().removeCaretListener(this);
        editor.getContentComponent().removeKeyListener(this.extraHighlightTrigger);
    }


    /**
     * Called when the caret position has changed.
     *
     * @param event CaretEvent
     */
    @Override
    public void caretPositionChanged(@NotNull CaretEvent event) {
        Editor editor = event.getEditor();
        highlightEditorCurrentPair(editor);
    }

    @Override
    public void caretAdded(@NotNull CaretEvent event) {
        // ignore the event
    }

    @Override
    public void caretRemoved(@NotNull CaretEvent event) {
        // ignore the event
    }

    /**
     * Highlight the current pair.
     *
     * @param editor editor
     */
    public void highlightEditorCurrentPair(Editor editor) {
        int offset = editor.getCaretModel().getOffset();
        AbstractBraceHighlighter highlighter = BraceHighlighterFactory.getBraceHighlighterInstance(editor);
        if (highlighter == null) {
            return;
        }
        // clear the high lighter
        highlighter.eraseHighlight(highlighterList);

        // find the brace positions
        BracePair bracePair = highlighter.findClosetBracePair(offset);

        // high light the brace
        Pair<RangeHighlighter, RangeHighlighter> highlighterEntry = highlighter.highlightPair(bracePair);

        // record the high lighter
        if (highlighterEntry != null) {
            highlighterList.add(highlighterEntry.getLeft());
            highlighterList.add(highlighterEntry.getRight());
        }
    }

    public Editor getEditor() {
        return editor;
    }

    private static class ExtraHighlightTrigger extends KeyAdapter {
        private static final char VIM_INSERT_KEY = 'i';

        private final Editor editor;
        private final HighlightEditorCartListener highlightEditorCartListener;

        public ExtraHighlightTrigger(HighlightEditorCartListener component) {
            this.editor = component.getEditor();
            this.highlightEditorCartListener = component;
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // compatible the vim insert mode
            // will trigger the highlight from normal mode to insert at caret mode
            if (e.getKeyChar() != VIM_INSERT_KEY || this.editor.getSettings().isBlockCursor()) {
                return;
            }
            this.highlightEditorCartListener.highlightEditorCurrentPair(this.editor);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() != KeyEvent.VK_ESCAPE) {
                return;
            }
            this.highlightEditorCartListener.highlightEditorCurrentPair(this.editor);
        }
    }
}
