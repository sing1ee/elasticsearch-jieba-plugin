# elasticsearch-jieba-plugin
jieba analysis plugin for elasticsearch ***5.1.2***


### usage
1. run

    ```shell
        gradle buildPluginZip
    ```
2. create a directory ${path.home}/plugins/jieba
3. copy the zip file to plugin directory

    ```shell
        cp build/distributions/elasticsearch-jieba-plugin-5.1.2.zip ${path.home}/plugins/jieba
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

