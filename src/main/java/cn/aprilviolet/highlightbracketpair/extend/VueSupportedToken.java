package cn.aprilviolet.highlightbracketpair.extend;

import cn.aprilviolet.highlightbracketpair.util.Pair;
import com.intellij.lang.Language;
import com.intellij.psi.tree.IElementType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Customer Vue support Bracket
 *
 * @author AprilViolet
 * @version v1.0.0
 * @date 2021.09.21 16:03
 * @since v1.3.0
 */
public class VueSupportedToken extends CustomSupportedToken {
    public static final String VUE = "VUE";

    @Override
    public List<Pair<IElementType, IElementType>> addSupported(Map<Language, List<Pair<IElementType, IElementType>>> languagePairsMap) {
        Language vue = Language.findLanguageByID("Vue");
        if (vue == null) {
            return new ArrayList<>();
        }
        List<Pair<IElementType, IElementType>> vueJsPairList = languagePairsMap.get(Language.findLanguageByID("VueJS"));
        List<Pair<IElementType, IElementType>> xmlPairList = languagePairsMap.get(Language.findLanguageByID("XML"));
        List<Pair<IElementType, IElementType>> cssPairList = languagePairsMap.get(Language.findLanguageByID("CSS"));
        List<Pair<IElementType, IElementType>> vuePairList = new ArrayList<>();
        if (null != vueJsPairList) {
            vuePairList.addAll(vueJsPairList);
        }
        if (null != xmlPairList) {
            vuePairList.addAll(xmlPairList);
        }
        if (null != cssPairList) {
            vuePairList.addAll(cssPairList);
        }
        languagePairsMap.put(vue, vuePairList);
        return vuePairList;
    }

    public enum Singleton {
        /**
         * Enumeration singleton
         */
        INSTANCE;

        private final VueSupportedToken vueSupportedToken;

        Singleton() {
            vueSupportedToken = new VueSupportedToken();
        }

        public VueSupportedToken getInstance() {
            return vueSupportedToken;
        }

        public void addSupported(Language language, Map<Language, List<Pair<IElementType, IElementType>>> map) {
            if (VUE.equalsIgnoreCase(language.getID())) {
                vueSupportedToken.addSupported(map);
            }
        }

        public void removeSupported(Language language, Map<Language, List<Pair<IElementType, IElementType>>> map) {
            if (VUE.equalsIgnoreCase(language.getID())) {
                map.remove(language);
            }
        }
    }
}
