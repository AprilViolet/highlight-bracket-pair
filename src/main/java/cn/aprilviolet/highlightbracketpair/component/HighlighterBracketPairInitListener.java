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
 * 打开项目时候的监听器
 *
 * @author AprilViolet
 * @version V1.0.0
 * @date 2021.07.30 星期五 17:16
 * @since V1.0.0
 */
public class HighlighterBracketPairInitListener implements ProjectManagerListener {
    /**
     * 当前插件是否更新
     */
    private boolean updated = Boolean.FALSE;

    /**
     * 插件是否更新通知
     */
    private boolean updateNotificationShown = Boolean.FALSE;

    /**
     * 打开项目时候执行
     *
     * @param project 项目
     */
    @Override
    public void projectOpened(@NotNull Project project) {
        ProjectManagerListener.super.projectOpened(project);
        // 注册监听器。这种类型的监听器在plugin.xml中配置不了?
        EditorFactory.getInstance().addEditorFactoryListener(new HighlightBracketPairEditFactoryListener(),
                ApplicationManager.getApplication());

        final HighlightBracketPairSettings settings = HighlightBracketPairSettings.getInstance();
        updated = !getPlugin().getVersion().equals(settings.getPluginVersion());
        if (updated) {
            settings.setPluginVersion(getPlugin().getVersion());
        }

        if (!updated && !updateNotificationShown) {
            String content = "HighlightBracketPair is updated to " + settings.getPluginVersion();
            NotificationGroupManager.getInstance().getNotificationGroup("HighlighterBracketPairNotification")
                    .createNotification(content, NotificationType.INFORMATION).notify(project);
            updateNotificationShown = Boolean.TRUE;
        }
    }

    /**
     * 项目关闭时候执行
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
     * Get the plugin description by plugin id
     *
     * @return plugin description
     */
    private IdeaPluginDescriptor getPlugin() {
        return PluginManagerCore.getPlugin(PluginId.getId("HighlightBracketPair"));
    }
}
