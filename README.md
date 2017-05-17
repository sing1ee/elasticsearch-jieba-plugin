# elasticsearch-jieba-plugin
jieba analysis plugin for elasticsearch: ***5.4.0***, ***5.3.0***, ***5.2.2***, ***5.2.1***, ***5.2.0***, ***5.1.2***, ***5.1.1***


### 版本对应

| 分支      | tag        | elasticsearch版本 | Release Link                                                                                  |
| ---       | ---        | ---               | ---                                                                                           |
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
