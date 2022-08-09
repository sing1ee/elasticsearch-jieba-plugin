# elasticsearch-jieba-plugin

## Introduction
This is the elasticsearch-jieba-plugin for elasticsearch v7.5.2

## Difference from the original repo
1. It's for elasticsearch v7.5.2
2. Support traditional Chinese tokenization
3. Use the customized jieba-analysis, not the original one, therefore, we need to pack customized jieba-analysis first

## How to run it
0. Prepare a elasticsearch with version 7.5.2
1. Download customized jieba-analysis with this branch https://github.com/bunnyCarousell/jieba-analysis/tree/support_traditional_chinese
2. Run `mvn install` on jieba-analysis
3. Download this repo, and run `gradle pz` on this repo
4. Checkout to `elasticsearch-jieba-plugin/build/distributions` path, you'll find `elasticsearch-jieba-plugin-7.5.2.zip`
5. Make a directory, move the zip to the created directory, and unzip it
``` 
mkdir elasticsearch-jieba-plugin-7.5.2
mv elasticsearch-jieba-plugin-7.5.2.zip elasticsearch-jieba-plugin-7.5.2
cd elasticsearch-jieba-plugin-7.5.2
unzip elasticsearch-jieba-plugin-7.5.2.zip
```
6. Copy it to your elasticsearch
```
docker cp {current_dir} {docker_CONTAINER_ID}:/usr/share/elasticsearch/plugins
```
7. You'll find the `elasticsearch-jieba-plugin-7.5.2` inside the path  `/usr/share/elasticsearch` on your elasticsearch
8. Restart your elasticsearch, and you can use it

## How to tokenize
1. For `index` time, use `jieba_index` for analyzer.
Ex:
```
{
  "text": "寶可夢炎兔兒太陽眼鏡",
  "analyzer": "jieba_index"
}
```
2.  For `search` time, use `jieba_search` for analyzer.
Ex:
```
{
  "text": "寶可夢炎兔兒太陽眼鏡",
  "analyzer": "jieba_search"
}
```

## How to customize my dictionary
1. Add dictionary file with suffix `.dict` inside `elasticsearch-jieba-plugin/src/dic`
2. The schema for each row in the dictionary file will be
`term{one_blank}term_frequency`, ex:
```
炎兔兒 2
寶可夢 2
史努比 2
```
3. Terms in dictionary file can be either traditional Chinese or simplified Chinese.
4. Need to run `gradle pz` again to pack it to new zip




# ----------------------------------------------


# The following are readme from original repo: #

jieba analysis plugin for elasticsearch: ***7.3.0***, ***7.0.0***, ***6.4.0***, ***6.0.0***, ***5.4.0***, ***5.3.0***, ***5.2.2***, ***5.2.1***, ***5.2.0***, ***5.1.2***, ***5.1.1***


### 有关jieba_index和jieba_search的应用

[戳这里](about_jieba_index_jieba_search.md)

### 新分词支持

- [thulac分词ES插件](https://github.com/microbun/elasticsearch-thulac-plugin)， [thulac官网](http://thulac.thunlp.org/)


### 如果是ES6.4.0的版本，请使用6.4.0分支最新的代码，或者master分支最新代码，也可以下载6.4.1的release，强烈推荐升级！

#### 6.4.1的release，解决了PositionIncrement问题。详细说明见[ES分词PositionIncrement解析](https://github.com/sing1ee/kotlin-road/blob/master/ES-analysis-positionincrement.md)

### 版本对应

| 分支      | tag        | elasticsearch版本 | Release Link                                                                                  |
| ---       | ---        | ---               | ---                                                                                           |
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
gradle pz
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
GET http://localhost:9200/jieba_index/_analyze?analyzer=my_ana&text=中国的伟大时代来临了，欢迎参观北京大学PKU
```
Response as follow:

```json
{
  "tokens": [
    {
      "token": "中国",
      "start_offset": 0,
      "end_offset": 2,
      "type": "word",
      "position": 0
    },
    {
      "token": "伟大",
      "start_offset": 3,
      "end_offset": 5,
      "type": "word",
      "position": 2
    },
    {
      "token": "时代",
      "start_offset": 5,
      "end_offset": 7,
      "type": "word",
      "position": 3
    },
    {
      "token": "来临",
      "start_offset": 7,
      "end_offset": 9,
      "type": "word",
      "position": 4
    },
    {
      "token": "欢迎",
      "start_offset": 11,
      "end_offset": 13,
      "type": "word",
      "position": 7
    },
    {
      "token": "参观",
      "start_offset": 13,
      "end_offset": 15,
      "type": "word",
      "position": 8
    },
    {
      "token": "北京",
      "start_offset": 15,
      "end_offset": 17,
      "type": "word",
      "position": 9
    },
    {
      "token": "大学",
      "start_offset": 17,
      "end_offset": 19,
      "type": "word",
      "position": 10
    },
    {
      "token": "北京大",
      "start_offset": 15,
      "end_offset": 18,
      "type": "word",
      "position": 11
    },
    {
      "token": "北京大学",
      "start_offset": 15,
      "end_offset": 19,
      "type": "word",
      "position": 12
    },
    {
      "token": "北大",
      "start_offset": 15,
      "end_offset": 19,
      "type": "SYNONYM",
      "position": 12
    },
    {
      "token": "pku",
      "start_offset": 15,
      "end_offset": 19,
      "type": "SYNONYM",
      "position": 12
    },
    {
      "token": "pku",
      "start_offset": 19,
      "end_offset": 22,
      "type": "word",
      "position": 13
    },
    {
      "token": "北大",
      "start_offset": 19,
      "end_offset": 22,
      "type": "SYNONYM",
      "position": 13
    },
    {
      "token": "北京大学",
      "start_offset": 19,
      "end_offset": 22,
      "type": "SYNONYM",
      "position": 13
    }
  ]
}
```
- Pay attention to ***jieba_synonym**, same with ***jieba_stop***, the format of synoyms.txt:

```shell
北京大学,北大,pku
清华大学,清华,Tsinghua University
```
- create document

```shell
POST http://localhost:9200/jieba_index/fulltext/1
```

```json
{"content":"中国的伟大时代来临了，欢迎参观北京大学PKU"}
```

- search

```shell
POST http://localhost:9200/jieba_index/fulltext/_search
```
Request body:

```json
{
    "query" : { "match" : { "content" : "pku" }},
    "highlight" : {
        "pre_tags" : ["<tag1>", "<tag2>"],
        "post_tags" : ["</tag1>", "</tag2>"],
        "fields" : {
            "content" : {}
        }
    }
}
```
Response body:

```json
{
  "took": 3,
  "timed_out": false,
  "_shards": {
    "total": 5,
    "successful": 5,
    "failed": 0
  },
  "hits": {
    "total": 1,
    "max_score": 0.52305835,
    "hits": [
      {
        "_index": "jieba_index",
        "_type": "fulltext",
        "_id": "1",
        "_score": 0.52305835,
        "_source": {
          "content": "中国的伟大时代来临了，欢迎参观北京大学PKU"
        },
        "highlight": {
          "content": [
            "中国的伟大时代来临了，欢迎参观<tag1>北京大学</tag1><tag1>PKU</tag1>"
          ]
        }
      }
    ]
  }
}
```

- 聚合示例（aggregation）

Query:

```json
{
  "query": {
    "match": {
      "name": "lala"
    }
  },
  "_source": [
    "name"
  ],
  "aggs": {
    "dedup": {
      "terms": {
        "field": "your_agg_field"
      },
      "aggs": {
        "dedup_docs": {
          "top_hits": {
            "sort": [
              {
                "updatedAt": {
                  "order": "desc"
                }
              }
            ],
            "_source": {
              "includes": [
                "name"
                ]
            },
            "size": 2
          }
        }
      }
    },
    "facets": {
      "terms": {
        "field": "your_facet_field"
      },
      "aggs": {
        "facets_docs": {
          "top_hits": {
            "sort": [
              {
                "updatedAt": {
                  "order": "desc"
                }
              }
            ],
            "_source": {
              "includes": [
                "name"
              ]
            },
            "size": 1
          }
        }
      }
    }
  }
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
