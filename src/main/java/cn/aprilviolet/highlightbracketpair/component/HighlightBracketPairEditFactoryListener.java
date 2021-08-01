package cn.aprilviolet.highlightbracketpair.component;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import org.jetbrains.annotations.NotNull;

/**
 * 编辑器构造时候的监听器：监听比如编辑面板的打开关闭等
 *
 * 高亮括号对应用组件，负责一些初始化操作，比如注册编辑器事件监听器，检查插件更新等
 *
 * @author AprilViolet
 * @version V1.0.0
 * @date 2021.07.30 星期五 17:16
 * @since V1.0.0
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
