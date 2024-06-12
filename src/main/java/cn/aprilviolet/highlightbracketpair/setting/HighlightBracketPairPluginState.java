package cn.aprilviolet.highlightbracketpair.setting;

/**
 * The status class for configuring color settings in Idea
 *
 * @author AprilViolet
 * @version v1.1.0
 * @date 2021.07.31 11:02
 * @since v1.0.0
 */
public class HighlightBracketPairPluginState {
    /**
     * Plug-in version
     */
    private String pluginVersion = "0.0.0";

    /**
     * turn on rendering brackets in gutter
     * true: open
     * false: close
     */
    private Boolean bracketGutterEnable = Boolean.TRUE;

    /**
     * customize bracket in gutter size
     */
    private String gutterBracketSize = "14";

    /**
     * turn on render bracket in xml language
     * true: open
     * false: close
     */
    private Boolean highlightXmlFlag = Boolean.TRUE;


    /**
     * turn on render bracket in vue language
     * true: open
     * false: close
     */
    private Boolean highlightVueFlag = Boolean.TRUE;

    public String getPluginVersion() {
        return pluginVersion;
    }

    public void setPluginVersion(String pluginVersion) {
        this.pluginVersion = pluginVersion;
    }

    public Boolean getBracketGutterEnable() {
        return bracketGutterEnable;
    }

    public void setBracketGutterEnable(Boolean bracketGutterEnable) {
        this.bracketGutterEnable = bracketGutterEnable;
    }

    public String getGutterBracketSize() {
        return gutterBracketSize;
    }

    public void setGutterBracketSize(String gutterBracketSize) {
        this.gutterBracketSize = gutterBracketSize;
    }

    public Boolean getHighlightXmlFlag() {
        return highlightXmlFlag;
    }

    public void setHighlightXmlFlag(Boolean highlightXmlFlag) {
        this.highlightXmlFlag = highlightXmlFlag;
    }

    public Boolean getHighlightVueFlag() {
        return highlightVueFlag;
    }

    public void setHighlightVueFlag(Boolean highlightVueFlag) {
        this.highlightVueFlag = highlightVueFlag;
    }
}
