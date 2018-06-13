1. 导入 idea

2. 找到 `LxDemoApplication.java` 类，右键 `Run LxDemoApplication`

3. 保证本地浏览器有2个（别用 IE），因为需要测试不同 session

4. **测试 demo1，用 `ConcurrentHashMap` 写的**

   打开浏览器1，输入地址 http://localhost:8080/demo/upload/1

   可以看到浏览器返回的内容，格式如下：

   ```json
   {
   	cacheSize: 1,
   	session: "019A455416CC77E31ABF527356C328D9",
   	time: 1
   }
   ```

   刷新页面，模拟上传文件，当文件个数达到8个时，格式如下：

   ```json
   {
       cacheSize: 1,
       session: "019A455416CC77E31ABF527356C328D9",
       time: 8,
       message: "当前上传文件个数已经到达8个！"
   }
   ```

   关闭浏览器1。

   打开浏览器2，输入地址 http://localhost:8080/demo/upload/1

   看到返回的 json 里 cacheSize 变成 2 了。

   同样刷新页面，模拟上传文件，测试。

   **这个 size 会一直变大，所以很不友好，而且同一用户上传8次之后，需要注销登录一次或者关闭一次浏览器才可以再次上传。因为，不注销用户，或者不关闭浏览器，session 不变嘛。**

5. **测试 demo2，用Cache 来写的**

   打开浏览器1，输入地址 http://localhost:8080/demo/upload/2

   同样刷新浏览器，模拟上传文件，当文件达到8次之后。

   立马使用浏览器2，输入地址 http://localhost:8080/demo/upload/2

   看到返回的 json 里 cacheSize 变成 2 了。

   **当你间隔1分钟之后，再去测试浏览器1，你会发现 cacheSize 变回1了。因为这才是缓存，自动情空超时的废弃的数据。此时，用户不需要注销，又可以愉快的传文件了，当然，缓存过期时间你可以根据自己来修改，比如说半小时之类的。建议采用 demo2的做法。**