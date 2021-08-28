package cn.aprilviolet.highlightbracketpair.setting;

/**
 * The status class for configuring color settings in Idea
 *
 * @author AprilViolet
 * @version V1.0.0
 * @date 2021.07.31 11:02
 * @since V1.0.0
 */
public class HighlightBracketPairPluginState {
    /**
     * Plug-in version
     */
    private String pluginVersion = "";

    public String getPluginVersion() {
        return pluginVersion;
    }

    public void setPluginVersion(String pluginVersion) {
        this.pluginVersion = pluginVersion;
    }
}
