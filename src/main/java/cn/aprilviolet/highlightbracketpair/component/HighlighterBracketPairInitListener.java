package cn.aprilviolet.highlightbracketpair.component;

import cn.aprilviolet.highlightbracketpair.setting.HighlightBracketPairSettings;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import org.jetbrains.annotations.NotNull;

/**
 * Listener when opening the project.
 *
 * @author AprilViolet
 * @version V1.0.0
 * @date 2021.07.30 17:16
 * @since V1.0.0
 */
public class HighlighterBracketPairInitListener implements ProjectManagerListener {
    /**
     * Update notification flag.
     */
    private boolean updateNotificationShown = Boolean.TRUE;

    /**
     * Run when the project is opened.
     *
     * @param project project
     */
    @Override
    public void projectOpened(@NotNull Project project) {
        ProjectManagerListener.super.projectOpened(project);
        // Register the listener
        EditorFactory.getInstance().addEditorFactoryListener(new HighlightBracketPairEditFactoryListener(),
                ApplicationManager.getApplication());

        final HighlightBracketPairSettings settings = HighlightBracketPairSettings.getInstance();
        if (!getPlugin().getVersion().equals(settings.getPluginVersion())) {
            settings.setPluginVersion(getPlugin().getVersion());
            if (updateNotificationShown) {
                String content = "❤Thank you for downloading HighlightBracketPair v" + settings.getPluginVersion()
                        + "<br/>🐞If you run into any problem, " +
                        "<b><a href=\"https://github.com/AprilViolet\">feel free to raise a issue</a>.</b>";
                NotificationGroupManager.getInstance().getNotificationGroup("HighlighterBracketPairNotification")
                        .createNotification(content, NotificationType.INFORMATION).notify(project);
                updateNotificationShown = Boolean.FALSE;
            }
        }
    }

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

    /**
     * Get the plugin description by plugin id.
     *
     * @return plugin description
     */
    private IdeaPluginDescriptor getPlugin() {
        return PluginManagerCore.getPlugin(PluginId.getId("HighlightBracketPair"));
    }
}
