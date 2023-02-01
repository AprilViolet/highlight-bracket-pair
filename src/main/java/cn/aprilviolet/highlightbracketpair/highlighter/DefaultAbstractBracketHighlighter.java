package cn.aprilviolet.highlightbracketpair.highlighter;

import cn.aprilviolet.highlightbracketpair.extend.VueSupportedToken;
import cn.aprilviolet.highlightbracketpair.extend.XmlSupportedToken;
import cn.aprilviolet.highlightbracketpair.util.Pair;
import com.intellij.lang.BracePair;
import com.intellij.lang.Language;
import com.intellij.lang.LanguageBraceMatching;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.tree.IElementType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Default Brace Highlighter to highlight all supported brace pair.
 *
 * @author AprilViolet
 * @version v1.0.0
 * @date 2021.07.31 14:52
 * @since v1.0.0
 */
public class DefaultAbstractBracketHighlighter extends AbstractBracketHighlighter {
    /**
     * Default registered languages brace
     */
    protected static final Map<Language, List<Pair<IElementType, IElementType>>> LANGUAGE_BRACE_PAIRS = new HashMap<>();

    /*
     * Get all the registered languages brace pairs and cache it.
     */
    static {
        refresh();
    }

    /**
     * Constructor.
     *
     * @param editor editor
     */
    public DefaultAbstractBracketHighlighter(Editor editor) {
        super(editor);
    }

    /**
     * Get all cached supported brace token pair.
     *
     * @return all supported brace pair token
     */
    @Override
    public List<Pair<IElementType, IElementType>> getSupportedBraceToken() {
        Language language = this.psiFile.getLanguage();
        List<Pair<IElementType, IElementType>> braceList = LANGUAGE_BRACE_PAIRS.get(language);
        return braceList == null ? customSupportedBraceToken(language) : braceList;
    }

    /**
     * Query Pair from custom BraceToken
     *
     * @param language language type
     * @return Pair
     */
    private List<Pair<IElementType, IElementType>> customSupportedBraceToken(Language language) {
        refresh();
        XmlSupportedToken.Singleton.INSTANCE.addSupported(LANGUAGE_BRACE_PAIRS);
        VueSupportedToken.Singleton.INSTANCE.addSupported(LANGUAGE_BRACE_PAIRS);
        List<Pair<IElementType, IElementType>> braceList = LANGUAGE_BRACE_PAIRS.get(language);
        return braceList == null ? new ArrayList<>() : braceList;
    }

    /**
     * Initialize the registered language Pair
     */
    private static void refresh () {
        Collection<Language> languageList = Language.getRegisteredLanguages();
        for (Language language : languageList) {
            PairedBraceMatcher pairedBraceMatcher = LanguageBraceMatching.INSTANCE.forLanguage(language);
            if (pairedBraceMatcher != null) {
                BracePair[] bracePairs = pairedBraceMatcher.getPairs();
                List<Pair<IElementType, IElementType>> braceList = new LinkedList<>();
                for (BracePair bracePair : bracePairs) {
                    Pair<IElementType, IElementType> braceEntry =
                            new Pair<>(bracePair.getLeftBraceType(), bracePair.getRightBraceType());
                    braceList.add(braceEntry);
                }
                LANGUAGE_BRACE_PAIRS.put(language, braceList);
            }
        }
    }
}
