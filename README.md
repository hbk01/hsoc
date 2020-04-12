# Overview

hsoc 既可作为服务端，亦可作为客户端，用做局域网收发消息

# Usage

```bash
usage: hsoc [-a <ADDRESS>] [-c] [-f <FILE> | -m <MESSAGE>] [-h]  [-p <PORT>]
server be receiver, client be sender.
 -a,--address <ADDRESS>   connection this ip address. (client mode only)
 -c,--client              run in client mode.
 -f,--file <FILE>         in client mode, send the file to server.
                          in server mode, receive message and save into file.
 -h,--help                show help of the program.
 -m,--msg <MESSAGE>       send the msg to server. (client mode only)
 -p,--port <PORT>         in server mode, receive message this port.
                          in client mode, send message to this port.
```

一些例子

```bash
# 向 192.168.1.123 发送 "hello world"
hsoc -ca 192.168.1.123 -m "hello world"

# 向 192.168.1.123 发送文件
hsoc -ca 192.168.1.123 -f "/home/hbk/send.txt"

# 接收消息并打印出来
hsoc

# 接受消息并将其存到文件里
hsoc -f "/home/hbk/received.txt"
```

# Libraries

+ 命令行解析器 [commons-cli-1.4.jar](http://commons.apache.org/proper/commons-cli/download_cli.cgi)