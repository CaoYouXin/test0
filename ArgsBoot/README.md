##命令行启动项目

###包含如下特性：
1. 命令行启动程序<br/>
2. 命令模式调用子模块（松耦合）<br/>
例：ArgsBoot.main("fs", "open", "-quick", "-p", "xx.jpg");<br/>
这里"fs"是模块的名字，"open"是模块的命令，"-quick"是open命令的配置（cfg），"-p"是分隔符，"xx.jpg"是open命令的参数。<br/>
3. C/S架构，处理系统命令加载和重新加载的过程，远程的哦<br/>

