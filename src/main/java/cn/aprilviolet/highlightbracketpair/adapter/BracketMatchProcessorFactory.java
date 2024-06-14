package cn.aprilviolet.highlightbracketpair.adapter;

import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.openapi.fileTypes.FileType;

import java.util.HashMap;
import java.util.Map;

/**
 * Find different BracketMatchProcessor according to different types
 *
 * @author AprilViolet
 * @version v1.0.0
 * @date 2021.09.21 14:23
 * @since v1.0.0
 */
public class BracketMatchProcessorFactory {
    Map<String, BracketMatchProcessor> braceMatchingMapProcessor = new HashMap<>();

    private BracketMatchProcessorFactory() {
        braceMatchingMapProcessor.put("Default", new BracketMatchProcessor() {
        });
        init();
    }

    public void addBraceMatching(FileType fileType, BracketMatchProcessor braceMatching) {
        braceMatchingMapProcessor.put(fileType.getName(), braceMatching);
    }

    public BracketMatchProcessor getProcessor(FileType fileType) {
        BracketMatchProcessor braceMatching = braceMatchingMapProcessor.get(fileType.getName());
        return braceMatching == null ? braceMatchingMapProcessor.get("Default") : braceMatching;
    }

    private void init() {
        braceMatchingMapProcessor.put(XmlFileType.INSTANCE.getName(), new XmlBracketMatchProcessor());
        braceMatchingMapProcessor.put("Vue.js", new XmlBracketMatchProcessor());
    }

    public static BracketMatchProcessorFactory getInstance() {
        return BracketMatchProcessorFactory.SingletonInstance.INSTANCE.getInstance();
    }

    private enum SingletonInstance {
        /**
         * Enumeration singleton
         */
        INSTANCE;

        private final BracketMatchProcessorFactory bracketMatchProcessorFactory;

        SingletonInstance() {
            bracketMatchProcessorFactory = new BracketMatchProcessorFactory();
        }

        BracketMatchProcessorFactory getInstance() {
            return bracketMatchProcessorFactory;
        }
    }
}
