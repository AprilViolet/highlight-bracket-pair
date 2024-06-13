package cn.aprilviolet.highlightbracketpair.component;

import com.intellij.openapi.editor.Editor;

import java.util.HashMap;
import java.util.Map;

/**
 * EditorHighlightEditor singleton Map
 *
 * @author AprilViolet
 * @version v1.0.0
 * @date 2021.07.30 18:43
 * @since v1.0.0
 */
public class EditorHighlightEditorEntity {
    private static volatile EditorHighlightEditorEntity instance;

    private final Map<Editor, HighlightEditorCartListener> editorHighlightEditorComponentMap;

    private EditorHighlightEditorEntity() {
        this.editorHighlightEditorComponentMap = new HashMap<>(64);
    }

    public static synchronized EditorHighlightEditorEntity getInstance() {
        if (instance == null) {
            synchronized (EditorHighlightEditorEntity.class) {
                if (instance == null) {
                    instance = new EditorHighlightEditorEntity();
                }
            }
        }
        return instance;
    }

    /**
     * add HighlightEditorCartListener
     *
     * @param key   key
     * @param value value
     */
    public void putValue(Editor key, HighlightEditorCartListener value) {
        editorHighlightEditorComponentMap.put(key, value);
    }

    /**
     * remove value
     *
     * @param key key
     */
    public HighlightEditorCartListener removeValue(Editor key) {
        return editorHighlightEditorComponentMap.remove(key);
    }

    /**
     * clear HighlightEditorCartListener
     */
    public void clearValue() {
        editorHighlightEditorComponentMap.clear();
    }

    /**
     * get editorHighlightEditorComponentMap
     *
     * @return editorHighlightEditorComponentMap
     */
    public Map<Editor, HighlightEditorCartListener> getEditorHighlightMap() {
        return editorHighlightEditorComponentMap;
    }
}
