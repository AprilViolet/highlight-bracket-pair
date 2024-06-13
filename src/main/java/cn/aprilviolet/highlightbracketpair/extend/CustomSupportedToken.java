package cn.aprilviolet.highlightbracketpair.extend;

import cn.aprilviolet.highlightbracketpair.util.Pair;
import com.intellij.lang.Language;
import com.intellij.psi.tree.IElementType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Customer support bracket
 *
 * @author AprilViolet
 * @version v1.0.0
 * @date 2021.09.21 16:02
 * @since v1.3.0
 */
public class CustomSupportedToken {
    /**
     * Added support for customization
     *
     * @param languagePairsMap languagePairsMap
     * @return List<Pair<IElementType, IElementType>>
     */
    public List<Pair<IElementType, IElementType>> addSupported(Map<Language, List<Pair<IElementType, IElementType>>> languagePairsMap) {
        return new ArrayList<>();
    }
}
