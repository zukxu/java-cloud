# MyExcel

https://github.com/liaochong/myexcel

MyExcel是一个集导入、导出、加密Excel等多项功能的工具包。  
导入：提供简便的API，低内存读取Excel内容，并转化为List<Bean>或者List<Map>。

## 优势

- 可生成任意复杂表格：本工具使用迭代单元格方式进行Excel绘制，可生成任意复杂度Excel，提供多种宽度策略；
- 零学习成本：使用Html作为模板，学习成本几乎为零，无需关心POI本身任何操作；
- 支持常用背景色、边框、字体等样式设置：具 体参见菜单样式支持部分；
- 支持.xls、.xlsx、.csv：支持生成.xls、.xlsx后缀的Excel以及.csv文件；
- 支持公式导出：支持Excel模板中设置公式，降低服务端的计算量；
- 支持低内存SXSSF模式：支持低内存的SXSSF模式，可利用极低的内存生成.xlsx；
- 支持生产者消费者模式导出：支持生产者消费者模式导出，无需一次性获取所有数据，分批获取数据配合SXSSF模式实现真正意义上海量数据导出；
- 支持多种模板引擎：已内置Freemarker、Groovy、Beetl、Thymeleaf等常用模板引擎Excel构建器
  （详情参见文档 [Getting started](https://github.com/liaochong/myexcel/wiki/Excel%E6%A8%A1%E6%9D%BF%E6%9E%84%E5%BB%BA)）  
  推荐使用Beetl模板引擎 [Beetl文档](http://ibeetl.com/guide/#beetl)；
- 提供默认Excel构建器，直接输出简单Excel：无需编写任何Html，已内置默认模板，可直接根据POJO数据列表输出；
- 支持一次生成多sheet：以table作为sheet单元，支持一份Excel文档中多sheet导出；
- 支持Excel容量设定：支持设定Excel容量，到达容量后自动新建Excel，可构建成zip压缩包导出；

## wiki

https://github.com/liaochong/myexcel.wiki.git

## API

https://github.com/liaochong/myexcel/wiki/Operation-API