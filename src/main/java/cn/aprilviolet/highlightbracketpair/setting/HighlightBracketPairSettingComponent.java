package cn.aprilviolet.highlightbracketpair.setting;

import com.intellij.openapi.ui.DialogPanel;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;

/**
 * HighlightBracketPair setting component
 *
 * @author AprilViolet
 * @version v1.0.0
 * @date 2021.08.30 23:25
 * @since v1.0.0
 */
public class HighlightBracketPairSettingComponent {
    /**
     * setting main panel
     */
    private final DialogPanel bracketMainPanel = new DialogPanel();

    /**
     * Open render bracket in gutter
     */
    private final JBCheckBox bracketGutterEnable = new JBCheckBox("Open render bracket in gutter");

    /**
     * Set bracket size for gutter
     */
    private final JBTextField bracketGutterSizeText = new JBTextField(5);

    /**
     * Open render bracket in gutter
     */
    private final JBCheckBox highlightXmlBox = new JBCheckBox("Open render in Xml");

    /**
     * Open render bracket in gutter
     */
    private final JBCheckBox highlightVueBox = new JBCheckBox("Open render in Vue");

    public HighlightBracketPairSettingComponent() {
        bracketMainPanel.setLayout(new GridBagLayout());
        JBLabel bracketGutterLabel = new JBLabel("Bracket size in gutter");
        JPanel bracketPanel = FormBuilder.createFormBuilder()
                .addComponent(bracketGutterEnable, 0)
                .addLabeledComponent(bracketGutterLabel, bracketGutterSizeText, 1, false)
                .getPanel();
        bracketPanel.setBorder(IdeBorderFactory.createTitledBorder("Bracket In Gutter"));

        bracketMainPanel.add(bracketPanel, new GridBagConstraints(0, 0, 1, 1, 10, 10,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, JBUI.insets(0, 0 , 0 ,0), 0, 0));

        JPanel optionsSettingPanel = FormBuilder.createFormBuilder()
                .addComponent(highlightXmlBox, 0)
                .addComponent(highlightVueBox, 0)
                .getPanel();
        optionsSettingPanel.setBorder(IdeBorderFactory.createTitledBorder("Options Setting"));

        bracketMainPanel.add(optionsSettingPanel, new GridBagConstraints(0, 2, 1, 1, 10, 10,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, JBUI.insets(0, 0 , 0 ,0), 0, 0));
    }

    public DialogPanel getBracketMainPanel() {
        return bracketMainPanel;
    }

    public Boolean getBracketGutterEnable() {
        return bracketGutterEnable.isSelected();
    }

    public void setBracketGutterEnable(boolean newStatus) {
        bracketGutterEnable.setSelected(newStatus);
    }

    public String getBracketGutterSizeText() {
        return bracketGutterSizeText.getText();
    }

    public void setBracketGutterSizeText(String bracketGutterSize) {
        bracketGutterSizeText.setText(bracketGutterSize);
    }

    public Boolean getHighlightXmlFlag() {
        return highlightXmlBox.isSelected();
    }

    public void setHighlightXmlFlag(boolean newStatus) {
        highlightXmlBox.setSelected(newStatus);
    }

    public Boolean getHighlightVueFlag() {
        return highlightVueBox.isSelected();
    }

    public void setHighlightVueFlag(boolean newStatus) {
        highlightVueBox.setSelected(newStatus);
    }
}
