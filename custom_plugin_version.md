### 适配不通版本ES的jieba分词插件

#### 修改文件
- 根目录下的：`build.gradle`
    - 主要修改 `version`和依赖ES的版本，例如为6.3.0的ES打包，这里都改成6.3.0
- `src/main/resources`目录下的：`plugin-descriptor.properties`
    - 主要修改 `version` 和 `elasticsearch.version` 分别表示插件的版本和支持ES的版本，同样，如果是支持6.3.0的插件，都改为6.3.0就可以。
- 执行打包命令
```shell script
./gradlews pz
```
- 打包好的插件在目录： `./build/distributions`