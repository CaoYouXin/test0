###命令行启动项目

包含如下特性：<br/>
1. 命令行启动程序<br/>
2. （可当做服务外包出去）命令模式调用子模块（Facade）<br/>
例：ArgsBoot.main("fs", "open", "-quick", "-p", "xx.jpg");
这里"fs"是模块的名字，"open"是模块的命令，"-quick"是open命令的配置（cfg），"-p"是分隔符，"xx.jpg"是open命令的参数。