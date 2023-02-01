package cn.aprilviolet.highlightbracketpair.component;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import org.jetbrains.annotations.NotNull;

/**
 * Listener when the editor is constructed: listen for the opening and closing of the editing panel, etc.
 * The highlighted brackets are responsible for some initialization operations for application components,
 * such as registering editor event listeners, checking plug-in updates, etc.
 *
 * @author AprilViolet
 * @version v1.0.0
 * @date 2021.07.30 17:16
 * @since v1.0.0
 */
public class HighlightBracketPairEditFactoryListener implements EditorFactoryListener {
    /**
     * Invoked when the editor is created, and establish the relationship
     * between the {@link Editor} editor and {@link HighlightEditorCartListener} component.
     *
     * @param editorFactoryEvent editor factory event.
     */
    @Override
    public void editorCreated(@NotNull EditorFactoryEvent editorFactoryEvent) {
        Editor editor = editorFactoryEvent.getEditor();
        if (editor.getProject() == null) {
            return;
        }
        HighlightEditorCartListener highlightEditorCartListener = new HighlightEditorCartListener(editor);
        EditorHighlightEditorEntity editorHighlightEditorEntity = EditorHighlightEditorEntity.getInstance();
        editorHighlightEditorEntity.putValue(editor, highlightEditorCartListener);
    }

    /**
     * Invoked when the editor is released, and dissolve the relationship
     * between the {@link Editor} editor and {@link HighlightEditorCartListener} component,
     * and dispose the component.
     *
     * @param editorFactoryEvent editorFactoryEvent
     */
    @Override
    public void editorReleased(@NotNull EditorFactoryEvent editorFactoryEvent) {
        EditorHighlightEditorEntity editorHighlightEditorEntity = EditorHighlightEditorEntity.getInstance();
        HighlightEditorCartListener editorComponent = editorHighlightEditorEntity.removeValue(editorFactoryEvent.getEditor());
        if (editorComponent == null) {
            return;
        }
        editorComponent.dispose();
    }
}
