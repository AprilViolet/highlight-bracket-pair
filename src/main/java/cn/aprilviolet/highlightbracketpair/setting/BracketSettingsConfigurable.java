package cn.aprilviolet.highlightbracketpair.setting;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.util.NlsContexts;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * HighlightBracketPair setting configuration
 *
 * @author AprilViolet
 * @version v1.0.0
 * @date 2021.08.30 23:21
 * @since v1.0.0
 */
public class BracketSettingsConfigurable implements Configurable {
    private HighlightBracketPairSettingComponent settingComponent;

    private final HighlightBracketPairSettings bracketPairSettings = HighlightBracketPairSettings.getInstance();

    /**
     * Returns the visible name of the configurable component.
     * Note, that this method must return the display name
     * that is equal to the display name declared in XML
     * to avoid unexpected errors.
     *
     * @return the visible name of the configurable component
     */
    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "HighlightBracketPair";
    }

    /**
     * Creates new Swing form that enables user to configure the settings.
     * Usually this method is called on the EDT, so it should not take a long time.
     * <p>
     * Also this place is designed to allocate resources (subscriptions/listeners etc.)
     *
     * @return new Swing form to show, or {@code null} if it cannot be created
     * @see #disposeUIResources
     */
    @Override
    public @Nullable JComponent createComponent() {
        settingComponent = new HighlightBracketPairSettingComponent();
        return settingComponent.getBracketMainPanel();
    }

    /**
     * Indicates whether the Swing form was modified or not.
     * This method is called very often, so it should not take a long time.
     *
     * @return {@code true} if the settings were modified, {@code false} otherwise
     */
    @Override
    public boolean isModified() {
        return !settingComponent.getBracketGutterEnable().equals(bracketPairSettings.getBracketGutterEnable())
                || !settingComponent.getBracketGutterSizeText().equals(bracketPairSettings.getGutterBracketSize())
                || !settingComponent.getHighlightVueFlag().equals(bracketPairSettings.getHighlightVueFlag())
                || !settingComponent.getHighlightXmlFlag().equals(bracketPairSettings.getHighlightXmlFlag());
    }

    /**
     * Stores the settings from the Swing form to the configurable component.
     * This method is called on EDT upon user's request.
     *
     */
    @Override
    public void apply() {
        bracketPairSettings.setBracketGutterEnable(settingComponent.getBracketGutterEnable());
        if (StringUtils.isNumeric(settingComponent.getBracketGutterSizeText())) {
            bracketPairSettings.setGutterBracketSize(settingComponent.getBracketGutterSizeText());
        }
        bracketPairSettings.setHighlightVueFlag(settingComponent.getHighlightVueFlag());
        bracketPairSettings.setHighlightXmlFlag(settingComponent.getHighlightXmlFlag());
    }

    /**
     * Loads the settings from the configurable component to the Swing form.
     * This method is called on EDT immediately after the form creation or later upon user's request.
     */
    @Override
    public void reset() {
        settingComponent.setBracketGutterEnable(bracketPairSettings.getBracketGutterEnable());
        settingComponent.setBracketGutterSizeText(bracketPairSettings.getGutterBracketSize());
        settingComponent.setHighlightVueFlag(bracketPairSettings.getHighlightVueFlag());
        settingComponent.setHighlightXmlFlag(bracketPairSettings.getHighlightXmlFlag());
    }
}
