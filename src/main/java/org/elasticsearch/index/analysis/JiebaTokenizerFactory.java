package org.elasticsearch.index.analysis;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.WordDictionary;
import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;

public class JiebaTokenizerFactory extends AbstractTokenizerFactory {

  private String segMode;


  public JiebaTokenizerFactory(IndexSettings indexSettings, Environment env, Settings settings) {
    super(indexSettings, settings);
    WordDictionary.getInstance().init(env.pluginsFile().resolve("jieba/dic"));
  }

  @Override
  public Tokenizer create() {
    return new JiebaTokenizer(segMode);
  }

  public String getSegMode() {
    return segMode;
  }

  public void setSegMode(String segMode) {
    this.segMode = segMode;
  }

  public static TokenizerFactory getJiebaSearchTokenizerFactory(IndexSettings indexSettings,
                                                                Environment environment,
                                                                String s,
                                                                Settings settings) {
    JiebaTokenizerFactory jiebaTokenizerFactory = new JiebaTokenizerFactory(indexSettings,
        environment,
        settings);
    jiebaTokenizerFactory.setSegMode(JiebaSegmenter.SegMode.SEARCH.name());
    return jiebaTokenizerFactory;
  }

  public static TokenizerFactory getJiebaIndexTokenizerFactory(IndexSettings indexSettings,
                                                               Environment environment,
                                                               String s,
                                                               Settings settings) {
    JiebaTokenizerFactory jiebaTokenizerFactory = new JiebaTokenizerFactory(indexSettings,
        environment,
        settings);
    jiebaTokenizerFactory.setSegMode(JiebaSegmenter.SegMode.INDEX.name());
    return jiebaTokenizerFactory;
  }
}
