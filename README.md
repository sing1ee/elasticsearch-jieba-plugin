# elasticsearch-jieba-plugin
jieba analysis plugin for elasticsearch: ***7.7.0***, ***7.4.2***, ***7.3.0***, ***7.0.0***, ***6.4.0***, ***6.0.0***, ***5.4.0***, ***5.3.0***, ***5.2.2***, ***5.2.1***, ***5.2.0***, ***5.1.2***, ***5.1.1***

## 特点
- 支持动态添加字典，不重启ES。

### 简单的修改，即可适配不同版本的ES

[戳这里](custom_plugin_version.md)

### 支持动态添加字典，ES不需要重启

[戳这里](update_dict_online.md)

### 有关jieba_index和jieba_search的应用

[戳这里](about_jieba_index_jieba_search.md)

### 新分词支持

- [thulac分词ES插件](https://github.com/microbun/elasticsearch-thulac-plugin)， [thulac官网](http://thulac.thunlp.org/)


### 如果是ES6.4.0的版本，请使用6.4.0分支最新的代码，或者master分支最新代码，也可以下载6.4.1的release，强烈推荐升级！

#### 6.4.1的release，解决了PositionIncrement问题。详细说明见[ES分词PositionIncrement解析](https://github.com/sing1ee/kotlin-road/blob/master/ES-analysis-positionincrement.md)

### 版本对应

| 分支      | tag        | elasticsearch版本 | Release Link                                                                                  |
| ---       | ---        | ---               | ---                                                                                           |
| 7.7.0     | tag v7.7.1 | v7.7.0            | Download: [v7.7.0](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v7.7.1) |
| 7.4.2     | tag v7.4.2 | v7.4.2            | Download: [v7.4.2](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v7.4.2) |
| 7.3.0     | tag v7.3.0 | v7.3.0            | Download: [v7.3.0](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v7.3.0) |
| 7.0.0     | tag v7.0.0 | v7.0.0            | Download: [v7.0.0](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v7.0.0) |
| 6.4.0     | tag v6.4.1 | v6.4.0            | Download: [v6.4.1](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v6.4.1) |
| 6.4.0     | tag v6.4.0 | v6.4.0            | Download: [v6.4.0](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v6.4.0) |
| 6.0.0     | tag v6.0.0 | v6.0.0            | Download: [v6.0.1](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v6.0.1) |
| 5.4.0     | tag v5.4.0 | v5.4.0            | Download: [v5.4.0](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v5.4.0) |
| 5.3.0     | tag v5.3.0 | v5.3.0            | Download: [v5.3.0](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v5.3.0) |
| 5.2.2     | tag v5.2.2 | v5.2.2            | Download: [v5.2.2](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v5.2.2) |
| 5.2.1     | tag v5.2.1 | v5.2.1            | Download: [v5.2.1](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v5.2.1) |
| 5.2       | tag v5.2.0 | v5.2.0            | Download: [v5.2.0](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v5.2.0) |
| 5.1.2     | tag v5.1.2 | v5.1.2            | Download: [v5.1.2](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v5.1.2) |
| 5.1.1     | tag v5.1.1 | v5.1.1            | Download: [v5.1.1](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v5.1.1) |



### more details
- choose right version source code.
- run

```shell
git clone https://github.com/sing1ee/elasticsearch-jieba-plugin.git --recursive
./gradlew clean pz
```
- copy the zip file to plugin directory

```shell
cp build/distributions/elasticsearch-jieba-plugin-5.1.2.zip ${path.home}/plugins
```
- unzip and rm zip file

```shell
unzip elasticsearch-jieba-plugin-5.1.2.zip
rm elasticsearch-jieba-plugin-5.1.2.zip
```
- start elasticsearch

```shell
./bin/elasticsearch
```


### Custom User Dict
Just put you dict file with suffix ***.dict*** into  ${path.home}/plugins/jieba/dic. Your dict
file should like this:

```shell
小清新 3
百搭 3
显瘦 3
隨身碟 100
your_word word_freq

```


### Using stopwords
- find stopwords.txt in ${path.home}/plugins/jieba/dic.
- create folder named ***stopwords*** under ${path.home}/config

```shell
mkdir -p {path.home}/config/stopwords
```
- copy stopwords.txt into the folder just created

```shell
cp ${path.home}/plugins/jieba/dic/stopwords.txt {path.home}/config/stopwords
```
- create index:

```shell
PUT http://localhost:9200/jieba_index
```

```json
{
  "settings": {
    "analysis": {
      "filter": {
        "jieba_stop": {
          "type":        "stop",
          "stopwords_path": "stopwords/stopwords.txt"
        },
        "jieba_synonym": {
          "type":        "synonym",
          "synonyms_path": "synonyms/synonyms.txt"
        }
      },
      "analyzer": {
        "my_ana": {
          "tokenizer": "jieba_index",
          "filter": [
            "lowercase",
            "jieba_stop",
            "jieba_synonym"
          ]
        }
      }
    }
  }
}
```
- test analyzer:

```shell
PUT http://localhost:9200/jieba_index/_analyze
{
  "analyzer" : "my_ana",
  "text" : "黄河之水天上来"
}
```
Response as follow:

```json
{
    "tokens": [
        {
            "token": "黄河",
            "start_offset": 0,
            "end_offset": 2,
            "type": "word",
            "position": 0
        },
        {
            "token": "黄河之水天上来",
            "start_offset": 0,
            "end_offset": 7,
            "type": "word",
            "position": 0
        },
        {
            "token": "之水",
            "start_offset": 2,
            "end_offset": 4,
            "type": "word",
            "position": 1
        },
        {
            "token": "天上",
            "start_offset": 4,
            "end_offset": 6,
            "type": "word",
            "position": 2
        },
        {
            "token": "上来",
            "start_offset": 5,
            "end_offset": 7,
            "type": "word",
            "position": 2
        }
    ]
}
```

### NOTE
migrate from [jieba-solr](https://github.com/sing1ee/jieba-solr)

### Roadmap
I will add more analyzer support:
- stanford chinese analyzer
- fudan nlp analyzer
- ...

If you have some ideas, you should create an issue. Then, we will do it together.
