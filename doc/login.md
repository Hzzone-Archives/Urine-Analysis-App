# login

```
119.29.16.200:6789/auth/login
method post
记得urlencode
email=123@qq.com&password=123&remember_me=y&submit=login
成功 返回 ok http_code 200
失败返回 fail http_code 200
```

# register 

```
119.29.16.200:6789/auth/register
method post

username=xxx&email=xxx@qq.com&password=xxx&password2=xxx&submit=register
成功 返回 ok http_code 200
失败返回 fail http_code 200
```


## 默认已关闭邮箱验证，需要打开再说