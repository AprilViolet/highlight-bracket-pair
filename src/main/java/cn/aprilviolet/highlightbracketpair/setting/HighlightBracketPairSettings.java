package cn.aprilviolet.highlightbracketpair.setting;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.RoamingType;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Idea中配置颜色的设置类
 *
 * @author AprilViolet
 * @version V1.0.0
 * @date 2021.07.31 星期六 11:02
 * @since V1.0.0
 */
@State(
        name = "HighlightBracketPairSettings",
        storages = {
                @Storage(value = "highlight-bracket-pair-plugin.xml", roamingType = RoamingType.DISABLED)
        }
)
public class HighlightBracketPairSettings implements PersistentStateComponent<HighlightBracketPairPluginState> {
    private HighlightBracketPairPluginState highlightBracketPairPluginState = new HighlightBracketPairPluginState();

    /**
     * 获取插件设置的单例实例
     *
     * @return 插件设置的单例实例
     */
    public static HighlightBracketPairSettings getInstance() {
        return ApplicationManager.getApplication().getService(HighlightBracketPairSettings.class);
    }

    /**
     * Get the state.
     *
     * @return state
     */
    @Nullable
    @Override
    public HighlightBracketPairPluginState getState() {
        return highlightBracketPairPluginState;
    }

    /**
     * 从配置文件中加载状态
     *
     * @param highlightBracketPairPluginState 配置
     */
    @Override
    public void loadState(@NotNull HighlightBracketPairPluginState highlightBracketPairPluginState) {
        this.highlightBracketPairPluginState = highlightBracketPairPluginState;
    }

    public String getPluginVersion() {
        return highlightBracketPairPluginState.getPluginVersion();
    }

    public void setPluginVersion(String pluginVersion) {
        highlightBracketPairPluginState.setPluginVersion(pluginVersion);
    }
}
