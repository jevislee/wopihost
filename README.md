# wopihost

[![GitHub release](https://img.shields.io/github/release/ethendev/wopihost.svg)](https://github.com/ethendev/wopihost/releases)
[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/ethendev/wopihost/master/LICENSE)

**[English](https://github.com/ethendev/wopihost/blob/master/README-EN.md)**

### 项目介绍
基于 wopi 协议开发的 WopiHost , 支持 word, excel，ppt, pdf(仅支持预览)等文档的预览和编辑。

### 运行环境
需要安装 Office online 2016 才可以使用，基于jdk 1.8，spring boot开发。

### 使用方法
在 application.properties 中配置文档的存储路径
```
file.path=E:\\
```

#### 接口访问地址  
http://[owas.domain]/hosting/discovery  
打开上面的URL, 可以看到结果如下的XML文件，上面有对应的文件类型的请求路径。  
![请求路径](https://github.com/ethendev/data/blob/master/wopihost/20170418114309314.png)

word文档预览
http://[owas.domain]/wv/wordviewerframe.aspx?WOPISrc=http://[WopiHost.IP]:8080/wopi/files/test.docx&access_token=123
![word view](https://raw.githubusercontent.com/ethendev/data/master/wopihost/20170418172425910.png)

word文档编辑  
http://[owas.domain]/we/wordeditorframe.aspx?WOPISrc=http://[WopiHost.IP]:8080/wopi/files/test.docx&access_token=123
![word edit](https://raw.githubusercontent.com/ethendev/data/master/wopihost/20170418172534332.png)

excel预览  
http://[owas.domain]/x/_layouts/xlviewerinternal.aspx?ui=zh-CN&rs=zh-CN&WOPISrc=http://[WopiHost.IP]:8080/wopi/files/test.xlsx&access_token=123

excel编辑   
http://[owas.domain]/x/_layouts/xlviewerinternal.aspx?edit=1&WOPISrc=http://[WopiHost.IP]:8080/wopi/files/test.xlsx&access_token=123

ppt预览
http://[owas.domain]/p/PowerPointFrame.aspx?PowerPointView=ReadingView&WOPISrc=http://[WopiHost.IP]:8080/wopi/files/test.pptx&access_token=123

ppt编辑   
http://[owas.domain]/p/PowerPointFrame.aspx?PowerPointView=EditView&WOPISrc=http://[WopiHost.IP]:8080/wopi/files/test.pptx&access_token=123

备注：[owas.domain] 是 Office online 2016 的地址，[WopiHost.IP]是 WopiHost 服务的 IP 地址。
使用时替换成自己相应的服务地址。

### 常见问题
* word 文档编辑支持 .docx 格式，不支持 .doc 。
* pdf 仅支持预览，不支持编辑。
* 如果 wopihost 的接口没有问题，但是不能预览或者编辑文档  
  可能 wopi 和 Office Web Apps Server 之间的网络不能互通，也可能 Office Web Apps Server 配置有问题，建议检查配置或者重装后重试。
* 不能打开中文名的文件，提示 `Unable to Open the File`  
  将文件名使用 URLEncoder.encode 进行两次编码。例如：测试.xlsx, 编码为 %25E6%25B5%258B%25E8%25AF%2595.xlsx 即可正常预览、编辑。
* 因为 office 的 [BUG](https://social.msdn.microsoft.com/Forums/en-US/bb2f9118-8efd-463d-b4a2-54bb2cebf882/word-online-file-unlock-bug-office-online-server-201605?forum=os_office)，
  更新docx文件时 unlockAndRelock 和 putfile 请求中 header 中的锁不一致，因此在处理更新docx请求时，不去校验lock是否一致，否则无法保存docx更改。
  
### License
[MIT License](https://github.com/ethendev/wopihost/blob/master/LICENSE.md)