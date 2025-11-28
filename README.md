# elasticsearch-jieba-plugin

Jieba 中文分词 Elasticsearch 插件

## 更新日志

### 20251128
- 更新 ES 版本到 8.11.1
- 更新 Java 版本到 17
- 更新 jieba-analysis 依赖

### 20221201
- 新增 `7.17.x` 分支，支持 ES `7.17.0`
- 新增 `8.4.1` 分支，支持 ES `8.4.1`

---

## 特点

- 支持动态添加字典，不重启 ES
- 简单修改即可适配不同版本的 ES

## 快速开始

### 环境要求

| 组件 | 版本 |
|------|------|
| Gradle | 7.6 |
| Java | 17+ (需与目标 ES 版本兼容) |
| Elasticsearch | 8.11.1 (当前默认) |

> 参考 [ES 和 JDK 版本对应关系](https://www.elastic.co/cn/support/matrix#matrix_jvm)

### 克隆项目

```shell
git clone https://github.com/sing1ee/elasticsearch-jieba-plugin.git --recursive
cd elasticsearch-jieba-plugin
```

### 构建打包

```shell
./gradlew clean pz
```

构建成功后，插件包位于：`build/distributions/elasticsearch-jieba-plugin-{version}.zip`

### 安装到 Elasticsearch

```shell
# 复制到 ES 插件目录
cp build/distributions/elasticsearch-jieba-plugin-1.0.0.zip ${ES_HOME}/plugins/

# 解压
cd ${ES_HOME}/plugins/
unzip elasticsearch-jieba-plugin-1.0.0.zip
rm elasticsearch-jieba-plugin-1.0.0.zip

# 启动 ES
cd ${ES_HOME}
./bin/elasticsearch
```
### 测试分词
```shell
# 测试 jieba_search 分词器
curl -X POST "localhost:9200/_analyze" -H 'Content-Type: application/json' -d'
{
  "analyzer": "jieba_search",
  "text": "中华人民共和国国歌"
}
'

# 测试 jieba_index 分词器
curl -X POST "localhost:9200/_analyze" -H 'Content-Type: application/json' -d'
{
  "analyzer": "jieba_index",
  "text": "中华人民共和国国歌"
}
'
```
---

## 适配不同 ES 版本（完整教程）

如果需要适配其他版本的 Elasticsearch，按以下步骤操作：

### 第一步：确定版本信息

根据目标 ES 版本，查阅 [ES 和 JDK 版本对应关系](https://www.elastic.co/cn/support/matrix#matrix_jvm)，确定需要的 Java 版本。

例如：
| ES 版本 | 推荐 Java 版本 |
|---------|----------------|
| 8.x     | 17, 21         |
| 7.17.x  | 11, 17         |
| 7.x     | 11             |

### 第二步：修改 `build.gradle`

修改以下三处：

```groovy
// 1. 插件版本（可选）
version = '1.0.0'

// 2. Java 版本
sourceCompatibility = "17"  // 改为你的 Java 版本
targetCompatibility = "17"  // 改为你的 Java 版本

// 3. ES 依赖版本
dependencies {
    implementation 'org.elasticsearch:elasticsearch:8.11.1'  // 改为目标 ES 版本
}
```

### 第三步：修改 `src/main/resources/plugin-descriptor.properties`

```properties
# 插件版本
version=1.0.0

# Java 版本
java.version=17

# ES 版本（必须与 build.gradle 中一致）
elasticsearch.version=8.11.1
```

### 第四步：确认 Java 环境

```shell
java -version
```

确保当前 Java 版本与配置一致。

### 第五步：构建

```shell
./gradlew clean pz
```

### 第六步：验证

构建成功后，检查生成的 zip 包：

```shell
ls -la build/distributions/
```

---

## 常见问题

### Gradle Wrapper 缺失

如果遇到 `ClassNotFoundException: org.gradle.wrapper.GradleWrapperMain`，执行：

```shell
# 如果已安装 Gradle
gradle wrapper --gradle-version 7.6

# 或手动下载
curl -L -o gradle/wrapper/gradle-wrapper.jar \
  https://github.com/gradle/gradle/raw/v7.6.0/gradle/wrapper/gradle-wrapper.jar
```

### Java 版本不匹配

如果遇到 `错误: 无效的源发行版`，检查：
1. 系统 Java 版本：`java -version`
2. `build.gradle` 中的 `sourceCompatibility`
3. `plugin-descriptor.properties` 中的 `java.version`

确保三者一致。

---

## 相关文档

- [支持动态添加字典，ES不需要重启](update_dict_online.md)
- [jieba_index 和 jieba_search 的应用](about_jieba_index_jieba_search.md)
- [简单修改适配不同版本 ES](custom_plugin_version.md)

---

## 版本对应

| 分支      | tag        | elasticsearch版本 | Release Link                                                                                  |
| ---       | ---        | ---               | ---                                                                                           |
| master    | -          | v8.11.1           | 从源码构建                                                                                     |
| 7.7.0     | tag v7.7.1 | v7.7.0            | Download: [v7.7.0](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v7.7.1) |
| 7.4.2     | tag v7.4.2 | v7.4.2            | Download: [v7.4.2](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v7.4.2) |
| 7.3.0     | tag v7.3.0 | v7.3.0            | Download: [v7.3.0](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v7.3.0) |
| 7.0.0     | tag v7.0.0 | v7.0.0            | Download: [v7.0.0](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v7.0.0) |
| 6.4.0     | tag v6.4.1 | v6.4.0            | Download: [v6.4.1](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v6.4.1) |
| 6.0.0     | tag v6.0.0 | v6.0.0            | Download: [v6.0.1](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v6.0.1) |
| 5.4.0     | tag v5.4.0 | v5.4.0            | Download: [v5.4.0](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v5.4.0) |
| 5.3.0     | tag v5.3.0 | v5.3.0            | Download: [v5.3.0](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v5.3.0) |
| 5.2.2     | tag v5.2.2 | v5.2.2            | Download: [v5.2.2](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v5.2.2) |
| 5.2.1     | tag v5.2.1 | v5.2.1            | Download: [v5.2.1](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v5.2.1) |
| 5.2       | tag v5.2.0 | v5.2.0            | Download: [v5.2.0](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v5.2.0) |
| 5.1.2     | tag v5.1.2 | v5.1.2            | Download: [v5.1.2](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v5.1.2) |
| 5.1.1     | tag v5.1.1 | v5.1.1            | Download: [v5.1.1](https://github.com/sing1ee/elasticsearch-jieba-plugin/releases/tag/v5.1.1) |

---

## 自定义用户词典

将 `.dict` 后缀的词典文件放入 `${ES_HOME}/plugins/jieba/dic` 目录，格式如下：

```
小清新 3
百搭 3
显瘦 3
隨身碟 100
你的词语 词频
```

---

## 使用停用词

### 1. 创建停用词目录

```shell
mkdir -p ${ES_HOME}/config/stopwords
```

### 2. 复制停用词文件

```shell
cp ${ES_HOME}/plugins/jieba/dic/stopwords.txt ${ES_HOME}/config/stopwords/
```

### 3. 创建索引时配置分析器

```shell
PUT http://localhost:9200/jieba_index
```

```json
{
  "settings": {
    "analysis": {
      "filter": {
        "jieba_stop": {
          "type": "stop",
          "stopwords_path": "stopwords/stopwords.txt"
        },
        "jieba_synonym": {
          "type": "synonym",
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

### 4. 测试分析器

```shell
POST http://localhost:9200/jieba_index/_analyze
{
  "analyzer": "my_ana",
  "text": "黄河之水天上来"
}
```

响应示例：

```json
{
  "tokens": [
    { "token": "黄河", "start_offset": 0, "end_offset": 2, "type": "word", "position": 0 },
    { "token": "黄河之水天上来", "start_offset": 0, "end_offset": 7, "type": "word", "position": 0 },
    { "token": "之水", "start_offset": 2, "end_offset": 4, "type": "word", "position": 1 },
    { "token": "天上", "start_offset": 4, "end_offset": 6, "type": "word", "position": 2 },
    { "token": "上来", "start_offset": 5, "end_offset": 7, "type": "word", "position": 2 }
  ]
}
```

---

## License

MIT License

---

## 致谢

- 迁移自 [jieba-solr](https://github.com/sing1ee/jieba-solr)
- 基于 [jieba](https://github.com/fxsjy/jieba) 中文分词
- 感谢 [huaban](https://github.com/huaban) 提供的 jieba-analysis 依赖