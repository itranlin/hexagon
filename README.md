## USE

### 环境准备:

1. JDK 17
2. Maven

### 主程序

1. 依赖
   ```xml
   <dependency>
       <groupId>com.itranlin.hexagon</groupId>
       <artifactId>hexagon-adapter-springboot3-starter</artifactId>
      <version>1.0.0</version>
   </dependency>
   ```
2. 启动 jvm 参数:
   --add-opens java.base/java.lang=ALL-UNNAMED

### 插件管理服务

1. 依赖

```xml

<dependency>
    <groupId>com.itranlin.hexagon</groupId>
    <artifactId>hexagon-plugin-manager</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 插件

1. 依赖
   ```xml
   <dependency>
      <groupId>com.itranlin.hexagon</groupId>
      <artifactId>hexagon-plugin-depend</artifactId>
      <version>1.0.0</version>
   </dependency>
   ```

2. 配置插件Build

   ```xml
   <plugin>
      <groupId>com.itranlin.hexagon</groupId>
      <artifactId>hexagon-plugin-builder-maven-plugin</artifactId>
      <version>1.0.0</version>
      <executions>
         <execution>
            <goals>
               <goal>builder</goal>
            </goals>
         </execution>
      </executions>
   </plugin>
   ```

## 核心 API

```java
public interface HexagonAppContext {
    /**
     * 获取当前所有的插件 id
     */
    List<String> getAllPluginId();

    /**
     * 预加载, 只读取元信息.
     */
    Plugin preInstall(File file);

    /**
     * 安装插件
     */
    Plugin install(File file) throws Throwable;

    /**
     * 卸载插件
     */
    void uninstall(String pluginId) throws Exception;

    /**
     * 获取多个扩展点的插件实例
     */
    <P> List<P> get(String tag);

    /**
     * 获取单个插件实例.
     */
    <P> Optional<P> get(String tag, String pluginId);
}
```

## 插件核心配置

springboot 配置项(application.yml支持):

```yaml
com:
  itranlin:
    hexagon:
      master:
        path: 'plugins'
        auto-delete: true
        work-dir: "plugins_work"
        url-replace: true
        base-package: "com.itranlin.hexagon"
```

## 插件配置

1. plugin-meta.properties
   ```properties
   # 插件 boot class
   plugin.boot.class=com.itranlin.open.hexagon.example.empty.Boot
   # code 名 不能为空
   plugin.domain=example.plugin.empty
   # 描述
   plugin.desc=this a plugin a empty demo
   # 版本
   plugin.version=1.0.0
   # 扩展
   plugin.ext=null
   ```

2. extension.properties 扩展点映射

    ```properties
    com.itranlin.hexagonexample.extend.DemoService=com.itranlin.hexagonexample.plugin.DemoServiceImpl
    ```

