# Tiny Short URL

一种基于对称Hash算法的轻量级、生产级的短地址服务。

## 示例

* 创建短URL，普通文本格式输入、输出

```shell script
curl --location --request POST 'http://localhost:8080/shorturl' \
--header 'Content-Type: text/plain' \
--data-raw 'https://haokan.baidu.com/tab/yingshi'
```

```txt
http://localhost:8080/shorturl/swyUksT
```

* 创建短URL，JSON格式输入、输出

```shell script
curl --location --request POST 'http://localhost:8080/shorturl' \
--header 'Content-Type: application/json' \
--data-raw '{
    "raw_url":"https://baike.baidu.com/item/%E7%9F%AD%E5%9C%B0%E5%9D%80/2760921?fr=aladdin"
}'
```

```json
{
    "status": "ok",
    "url": "http://localhost:8080/shorturl/swyUksS"
}
```

