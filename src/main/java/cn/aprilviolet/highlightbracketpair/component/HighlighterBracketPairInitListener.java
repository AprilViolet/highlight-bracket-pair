package cn.aprilviolet.highlightbracketpair.component;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import org.jetbrains.annotations.NotNull;

/**
 * Listener when closing the project.
 *
 * @author AprilViolet
 * @version v1.1.0
 * @date 2021.07.30 17:16
 * @since v1.0.0
 */
public class HighlighterBracketPairInitListener implements ProjectManagerListener {
    /**
     * Execute when the project is closed.
     *
     * @param project 项目
     */
    @Override
    public void projectClosed(@NotNull Project project) {
        ProjectManagerListener.super.projectClosed(project);
        EditorHighlightEditorEntity editorHighlightEditorEntity = EditorHighlightEditorEntity.getInstance();
        for (HighlightEditorCartListener editorComponent : editorHighlightEditorEntity.getEditorHighlightMap().values()) {
            editorComponent.dispose();
        }
        editorHighlightEditorEntity.clearValue();
    }
}
