package cn.aprilviolet.highlightbracketpair.component;


import cn.aprilviolet.highlightbracketpair.setting.HighlightBracketPairSettings;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.ProjectActivity;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * idea2023.1 and later new method to execute method for project open.
 *
 * @author AprilViolet
 * @version v1.0.0
 * @date 2023-05-22 21:46
 * @since v1.4.0
 */
public class HighlightBracketPairNotificationStartupActivity implements ProjectActivity {
    /**
     * Update notification flag.
     */
    private boolean updateNotificationShown = Boolean.TRUE;

    @Nullable
    @Override
    public Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
        try {
            final HighlightBracketPairSettings settings = HighlightBracketPairSettings.getInstance();
            if (!getPlugin().getVersion().equals(settings.getPluginVersion())) {
                settings.setPluginVersion(getPlugin().getVersion());
                if (updateNotificationShown) {
                    String content = """
                            Thank you for downloading HighlightBracketPair v"""
                            + settings.getPluginVersion() +
                            """
                            <br/>
                            If you run into any problem, <b><a href="https://github.com/AprilViolet/highlight-bracket-pair">feel free to raise a issue</a>.</b>
                            """;
                    NotificationGroupManager.getInstance().getNotificationGroup("HighlighterBracketPairNotification")
                            .createNotification(content, NotificationType.INFORMATION).notify(project);
                }
            }
        } finally {
            updateNotificationShown = Boolean.FALSE;
        }
        return null;
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
