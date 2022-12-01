package org.elasticsearch.index.analysis;

import com.huaban.analysis.jieba.JiebaSegmenter;
import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;

/**
 * Created by zhangcheng on 2017/1/17.
 */
public class JiebaAnalyzerProvider extends AbstractIndexAnalyzerProvider<JiebaAnalyzer> {

    private JiebaAnalyzer jiebaAnalyzer;

    public JiebaAnalyzerProvider(IndexSettings indexSettings,
                                 Environment environment,
                                 String name,
                                 Settings settings,
                                 JiebaSegmenter.SegMode mode) {
        super(indexSettings, name, settings);
        if (null != mode) {
            jiebaAnalyzer = new JiebaAnalyzer(mode.name());
        } else {
            jiebaAnalyzer = new JiebaAnalyzer(settings.get("segMode", JiebaSegmenter.SegMode.SEARCH.name()));
        }

        JiebaDict.init(environment);
    }

    public static AnalyzerProvider<? extends Analyzer> getJiebaSearchAnalyzerProvider(IndexSettings indexSettings,
                                                                                      Environment environment,
                                                                                      String s,
                                                                                      Settings settings) {
        JiebaAnalyzerProvider jiebaAnalyzerProvider = new JiebaAnalyzerProvider(indexSettings,
                environment,
                s,
                settings,
                JiebaSegmenter.SegMode.SEARCH);

        return jiebaAnalyzerProvider;
    }

    public static AnalyzerProvider<? extends Analyzer> getJiebaIndexAnalyzerProvider(IndexSettings indexSettings,
                                                                                     Environment environment,
                                                                                     String s,
                                                                                     Settings settings) {
        JiebaAnalyzerProvider jiebaAnalyzerProvider = new JiebaAnalyzerProvider(indexSettings,
                environment,
                s,
                settings,
                JiebaSegmenter.SegMode.INDEX);

        return jiebaAnalyzerProvider;
    }

    @Override
    public JiebaAnalyzer get() {
        return jiebaAnalyzer;
    }
}
