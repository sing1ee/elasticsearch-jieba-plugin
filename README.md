# # elasticsearch-jieba-plugin
jieba analysis plugin for elasticsearch ***5.1.2***


### USAGE
0. pull code from master
1. run

    ```shell
        gradle pz
    ```
2. copy the zip file to plugin directory

    ```shell
        cp build/distributions/elasticsearch-jieba-plugin-5.1.2.zip ${path.home}/plugins
    ```
4. unzip and rm zip file

    ```shell
        unzip elasticsearch-jieba-plugin-5.1.2.zip
        rm elasticsearch-jieba-plugin-5.1.2.zip
    ```
5. start elasticsearch

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

### NOTE
migrate from [jieba-solr](https://github.com/sing1ee/jieba-solr)


