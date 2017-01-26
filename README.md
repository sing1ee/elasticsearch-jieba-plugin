# # elasticsearch-jieba-plugin
jieba analysis plugin for elasticsearch ***5.1.2***


### USAGE
- pull code from master
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
        }
      },
      "analyzer": {
        "my_ana": {
          "tokenizer": "jieba_index",
          "filter": [
            "lowercase",
            "jieba_stop"
          ]
        }
      }
    }
  }
}
```
- test analyzer:

```shell
GET http://localhost:9200/jieba_index/_analyze?analyzer=my_ana&text=中国的伟大时代来临了HAHA
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
      "token": "haha",
      "start_offset": 10,
      "end_offset": 14,
      "type": "word",
      "position": 6
    }
  ]
}
```

### NOTE
migrate from [jieba-solr](https://github.com/sing1ee/jieba-solr)


