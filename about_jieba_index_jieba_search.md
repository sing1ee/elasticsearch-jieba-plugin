### 结巴两种分词的说明: jieba_index, jieba_search

#### 有关搜索引擎的概念
实现一个搜索引擎，分词主要是两个环节使用：

- 查询分词
- 索引分词

通常，索引分词倾向于分出更多的可能，这样可以在查询的环节进行控制，召回更多的结果，使用中就是:`jieba_index`。
查询的分词，更在意词之间的语义关系，比如顺序、比如距离等，所以查询的分词希望找到一个合适的切分，不是找到更多的结果，这时候用`jieba_search`。


#### 示例

新建POST请求: ***http://localhost:9200/_analyze***

请求body：

```json
{
  "analyzer" : "jieba_index",
  "text" : "美国伊拉克"
}
```

##### jieba_index

当`analyzer`为`jieba_index时候`，结果为:

```json
{
    "tokens": [
        {
            "token": "美国",
            "start_offset": 0,
            "end_offset": 2,
            "type": "word",
            "position": 0
        },
        {
            "token": "伊拉",
            "start_offset": 2,
            "end_offset": 4,
            "type": "word",
            "position": 1
        },
        {
            "token": "伊拉克",
            "start_offset": 2,
            "end_offset": 5,
            "type": "word",
            "position": 1
        },
        {
            "token": "拉克",
            "start_offset": 3,
            "end_offset": 5,
            "type": "word",
            "position": 1
        }
    ]
}
```

##### jieba_search

当`analyzer`为`jieba_search`：

结果为:

```json
{
    "tokens": [
        {
            "token": "美国",
            "start_offset": 0,
            "end_offset": 2,
            "type": "word",
            "position": 0
        },
        {
            "token": "伊拉克",
            "start_offset": 2,
            "end_offset": 5,
            "type": "word",
            "position": 1
        }
    ]
}
```
如上两个例子，index和search的position是不同的，search更倾向于完整、顺序的切分。

在实际应用中，如果某些场景，对position敏感，建议采用`jieba_search`。


