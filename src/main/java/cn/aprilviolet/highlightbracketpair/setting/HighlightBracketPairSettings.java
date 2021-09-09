package cn.aprilviolet.highlightbracketpair.setting;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.RoamingType;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Setting class for configuring colors in Idea
 *
 * @author AprilViolet
 * @version V1.0.0
 * @date 2021.07.31 11:02
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
     * Get the singleton instance of the plugin settings
     *
     * @return Singleton instance of plug-in settings
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
     * Load state from configuration file
     *
     * @param highlightBracketPairPluginState Configuration
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

    public Boolean getBracketGutterEnable() {
        return highlightBracketPairPluginState.getBracketGutterEnable();
    }

    public void setBracketGutterEnable(Boolean bracketGutterEnable) {
        highlightBracketPairPluginState.setBracketGutterEnable(bracketGutterEnable);
    }

    public String getGutterBracketSize() {
        return highlightBracketPairPluginState.getGutterBracketSize();
    }

    public void setGutterBracketSize(String bracketGutterEnable) {
        highlightBracketPairPluginState.setGutterBracketSize(bracketGutterEnable);
    }
}
