package cn.aprilviolet.highlightbracketpair.setting;

import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
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
    private final JBPanel bracketMainPanel;

    /**
     * Open render bracket in gutter
     */
    private final JBCheckBox bracketGutterEnable = new JBCheckBox("Open render bracket in gutter");

    /**
     * Set bracket size for gutter"
     */
    private final JBTextField bracketGutterSizeText = new JBTextField();

    public HighlightBracketPairSettingComponent() {
        bracketMainPanel = new JBPanel();
        bracketMainPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.insets = JBUI.insets(10, 10, 10, 10);
        constraints.gridx = 0;

        JBLabel bracketGutterLabel = new JBLabel("Bracket size in gutter");
        JPanel bracketGutterPanel = FormBuilder.createFormBuilder()
                .addComponent(bracketGutterEnable, 1)
                .addLabeledComponent(bracketGutterLabel, bracketGutterSizeText, 1, false)
                .getPanel();
        bracketGutterPanel.setBorder(IdeBorderFactory.createTitledBorder("Bracket In Gutter"));

        constraints.gridy = 0;
        bracketMainPanel.add(bracketGutterPanel, constraints);
    }

    public JPanel getBracketMainPanel() {
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
}
