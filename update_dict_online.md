### 在线更新字典

#### 需求

- 在不重启ES的情况下更新字典

#### 实现思路

- 定时扫描`<es_install_dir>/plugins/jieba/dic`目录，发现以`.dict`为后缀的文件，则按照如下处理
    - 先判断是否为新增加的字典文件，如果否，则结束。如果是，则：
    - load字典文件的内容
- 扫描间隔为60s

#### 使用方法

- 受限准备好自定义字典：your_custom.dict
- 注意名字没有和当前的字典文件重复。可以加入时间戳：your_custom_20200608.dict
- 内容格式如下

```shell script
天上来 100
```

#### 示例

- 获取插件源码：

```shell script
git clone https://github.com/sing1ee/elasticsearch-jieba-plugin.git --recursive
```

- 插件打包

```shell script
./gradlew clean pz
```

打包后的插件在目录 `build/distributions`

- 安装插件

```shell script
cp build/distributions/elasticsearch-jieba-plugin-7.7.0.zip <es_install_dir>/plugins
cd <es_install_dir>/plugins
unzip elasticsearch-jieba-plugin-7.7.0.zip
rm elasticsearch-jieba-plugin-7.7.0.zip
```

- 启动ES

```shell script
./bin/elasticsearch
```

- 创建索引

```shell script
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

- 查看分词

```shell script
POST http://localhost:9200/jieba_index/_analyze
{
  "analyzer" : "my_ana",
  "text" : "黄河之水天上来"
}
```

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

- 添加新的词典，准备新的文件：`test.dict`

```shell script
天上来 100
```

- 将`test.dict`拷贝到`<es_install_dir>/plugins/jieba/dic`，注意ES不需要重启，等待60s后，看到ES有如下的日志：

```shell script
[2020-06-06T22:30:16,486][INFO ][stdout                   ] [cheng] start to load new dict
[2020-06-06T22:30:16,487][INFO ][stdout                   ] [cheng] initialize user dictionary:/Users/cheng/Downloads/elasticsearch-7.7.0/plugins/jieba/dic
[2020-06-06T22:30:16,487][INFO ][stdout                   ] [cheng] already loaded: /Users/cheng/Downloads/elasticsearch-7.7.0/plugins/jieba/dic/test2.dict
[2020-06-06T22:30:16,488][INFO ][stdout                   ] [cheng] already loaded: /Users/cheng/Downloads/elasticsearch-7.7.0/plugins/jieba/dic/sougou.dict
[2020-06-06T22:30:16,490][INFO ][stdout                   ] [cheng] user dict /Users/cheng/Downloads/elasticsearch-7.7.0/plugins/jieba/dic/test.dict load finished, tot words:4, time elapsed:1ms
[2020-06-06T22:30:16,491][INFO ][stdout                   ] [cheng] already loaded: /Users/cheng/Downloads/elasticsearch-7.7.0/plugins/jieba/dic/user.dict
```

- 重新查询分词

```shell script
POST http://localhost:9200/jieba_index/_analyze
{
  "analyzer" : "my_ana",
  "text" : "黄河之水天上来"
}
```

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
            "token": "天上来",
            "start_offset": 4,
            "end_offset": 7,
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

- 注意看到分词结果`天上来`。