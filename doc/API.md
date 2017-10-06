# 软件开发实践接口清单 

``` json

http://127.0.0.1:5000/getallinformation
请求方式：GET
需要登陆 
返回类型 
{"name": "123", "friends": ["admin@qq.com"], "group_info": [[6, "handsome"], [7, "hand"]], "email": "123@qq.com"}
```

添加好友接口 

```json
/addfriend/<email>
请求方式：GET
需要登陆 
此时的email为您需要添加的用户的email
会向目标用户推送一条消息 
格式为 
content = {'type': 'add_friend', 'from': current_user.email} 
返回值 'ok',200
```

确认好友接口 

```json
/confirm/<email>
请求方式：GET
需要登陆 
此时的email为上一条content中的from值 
此时向该email用户推送一条信息
格式为
content = {'type': 'add_friend_success', 'from': current_user.email}
返回值 'ok',200
```

推送信息接口 

```json
/pushtext

表单数据 cname=目标邮箱&content=everything you want to say
请求方式：POST
需要登陆 
向目标用户推送一条消息
格式为
content = {'type': 'text', 'content': 
你说的话, 'from': 从哪里来（发送用户邮箱账号}
返回值： 'ok',200
```

推送图片接口

``` json
/pushimg
表单数据 cname=目标邮箱&content=everything you want to say
请求方式：POST
需要登陆 
向目标用户推送一条消息
格式为
content = {'type': 'text', 'content': 
你说的话base64编码后的结果, 'from': 从哪里来（发送用户邮箱账号}
返回值： 'ok',200
```

创建群组接口

```json
/creategroup/<groupname>
请求方式： GET
需要登陆 
返回值 'ok',200
```

申请加群接口 

```json
/addtogroup/<groupname>
请求方式： GET
需要登陆 
此时向群管理员推送一条消息
格式为 
content = {'type': 'applygroup', 'content': groupname, 'from': 申请加群的人的email}
考虑到一个人可能有多个群，所以content为groupname

返回值 'ok',200
```

确认加群接口 

```json
/confirmgroup/<groupname>?email=xxx
请求方式: GET 
需要登陆 
email为加群人email
groupname为申请加入的email

返回值 'ok',200
```


群成员接口 （& 群信息接口）

```json
/getgroupinfo/<groupname>
请求方式：GET
不需要登录 
返回值示例
{"groupadmin": "123@qq.com", "groupcreate_time": "2017-05-30 07:10:50", "groupname": "handsome", "groupmember": ["123@qq.com"]}
```

群信息推送接口

```json
/pushgrouptext
请求方式：POST
需要登录
cname=groupname&content="everything you want to say"
pushmsg示例
cname=handsome&content=hi xyf
{"from_group": "handsome", "from": "123@qq.com", "content": "hi xyf", "type": "grouptext"}

```

群图片推送接口

```json
还没写完，跟上面的差不多
```

历史信息接口 

```json
/historymessage?email=xxx@qq.com
这里email为你要获取的目标用户。
我要获取与你的聊天记录，应填你的email
请求方式:GET
需要登录
返回值 json_encode()

[{"from": "1234@qq.com", "time": "2017-06-04 20:48:04", "type": "text", "content": "\u5b54\u65fb\u660a"}, {"from": "123@qq.com", "time": "2017-06-04 20:48:08", "type": "text", "content": "123"}, {"from": "123@qq.com", "time": "2017-06-04 20:53:28", "type": "text", "content": "\u903c\u738bhzz"}, {"from": "123@qq.com", "time": "2017-06-04 21:02:18", "type": "text", "content": "1234"}]
·
```

删除好友

```json
/deletefriend?email=xxx@qq.com
请求方式： GET
需要登录
返回值 'ok',200
or 'please not hack me',200
```

退出群组

```json
/quitgroup?groupname=xxx
请求方式 :GET
需要登录
注意：群主不能通过这种方式退出，但前端不需要做校验，我后端做了验证的。
返回值 'ok',200
```

踢出成员 

```json
/kick?email=xxx@qq.com&groupname=xxx
请求方式 :GET
需要登录
注意:群主麻烦不要t自己，我后端做了校验的。
返回值 'ok',200
```

解散群

```json
/dissolvegroup?groupname=xxx
请求方式：GET
需要登录 
注意，不要瞎几把解散，只有群主可以解散，我还是做了校验
返回值 'ok',200
```

上传文件接口 

```json
/sendfile
请求方式： POST
需要登录
参数格式 Multipart/form-data
去查一查怎么发送这样的数据 
file 文件内容 
email 对方email
返回值 file
comet： 返回结果示例 

\u8f6f\u4ef6\u5f00\u53d1\u5b9e\u8df5\u63a5\u53e3\u6e05\u5355 .pdf 
这个是原文件名 
http://119.29.16.200:1999/upload/b2d779fe9e25da14fd2797fc5b930964.pdf
这个是之后的下载链接

注意，这两个是连在一起的，因为要加一个原文件名的话我后端要修改太多地方了，所以只能前端处理了。（逃，如果你需要分隔符的话跟我说一声。

{"content": "\u8f6f\u4ef6\u5f00\u53d1\u5b9e\u8df5\u63a5\u53e3\u6e05\u5355 .pdfhttp://119.29.16.200:1999/upload/b2d779fe9e25da14fd2797fc5b930964.pdf", "time": "2017-06-05 15:15:42.914519", "from_group": "", "from": "1234@qq.com", "type": "file"}

```
