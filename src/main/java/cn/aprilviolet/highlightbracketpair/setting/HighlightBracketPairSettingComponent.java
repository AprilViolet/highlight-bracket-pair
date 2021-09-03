package cn.aprilviolet.highlightbracketpair.setting;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

/**
 * HighlightBracketPair setting component
 *
 * @author AprilViolet
 * @version v1.0.0
 * @date 2021.08.30 23:25
 * @since v1.0.0
 */
public class HighlightBracketPairSettingComponent {
    private final JPanel bracketMainPanel;
    private final JBCheckBox bracketGutterEnable = new JBCheckBox("Open render bracket in gutter");

    public HighlightBracketPairSettingComponent() {
        bracketMainPanel = FormBuilder.createFormBuilder()
                .addComponent(bracketGutterEnable, 1).addComponentFillVertically(new JPanel(), 0)
                .getPanel();
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
}
