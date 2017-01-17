package org.elasticsearch.plugin.analysis.jieba;

import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.index.analysis.AnalyzerProvider;
import org.elasticsearch.index.analysis.JiebaAnalyzerProvider;
import org.elasticsearch.index.analysis.JiebaTokenizerFactory;
import org.elasticsearch.index.analysis.TokenizerFactory;
import org.elasticsearch.indices.analysis.AnalysisModule;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangcheng on 2017/1/17.
 */
public class AnalysisJiebaPlugin extends Plugin implements AnalysisPlugin {

  public static String PLUGIN_NAME = "analysis-jieba";

  @Override
  public Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> getTokenizers() {
    Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> extra = new HashMap<>();


    extra.put("jieba_search", JiebaTokenizerFactory::getJiebaSearchTokenizerFactory);
    extra.put("jieba_index", JiebaTokenizerFactory::getJiebaIndexTokenizerFactory);

    return extra;
  }

  @Override
  public Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
    Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> extra = new HashMap<>();

    extra.put("jieba_search", JiebaAnalyzerProvider::getJiebaSearchAnalyzerProvider);
    extra.put("jieba_index", JiebaAnalyzerProvider::getJiebaIndexAnalyzerProvider);

    return extra;
  }
}
