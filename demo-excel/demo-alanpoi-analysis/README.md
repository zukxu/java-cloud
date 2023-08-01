# alanpoi-analysis

参考：官网：https://alanpoi.com/document
Github：https://github.com/alan-et/alanpoi

来源自定义Excel导出工具类

## 使用

一配置一实现一调用

### 一配置

- 在项目resources目录中新建excel-config.xml文件,
- consume中配置自己的消费类路径，继承ExcelConsumeInterface接口，
- sheet中的vo是把当前sheet序列化的对象路径，
- column中当然就是配置vo中的属性了，
- 其中name可选字段，填了就是按照这个匹配excel列名，不填就是按照offset顺序；
- 导入包含多个sheet就配置多个

### 一实现

consume类实现ExcelConsumeInterface接口，实现其中的方法

```java
public class Temp implements ExcelConsumeInterface {

    @Override
    public void error(ExcelError excelError) {

    }

    @Override
    public void validData(String workbookId, List<ExcelSheetData> sheetDataList, Map<Serializable, Object> excelParam) {

    }

    @Override
    public void end(List<ExcelSheetData> sheetDataList, Map<Serializable, Object> excelParam) {

    }

}
```

### 一调用

用户调用ExcelExportUtil类的customImportData即可，参数excelId就是excel-conifg.xml中配置的id

## 导出

### 注解模式导出

- ExcelSheet注解： 用于导入类上，可制定sheet名，列头的颜色、字体、高度、宽度
- ExcelColum注解: 用于导入类的属性上，可指定列头的名称，单元格的样式
- DateFormat注解: 用于导入类的属性上, 可以按照指定格式输出到excel,默认"yyyy/MM/dd"
- NumFormat注解: 用于导入类的属性上，可以按照指定格式输出到excel,默认"00.00"

```java

@ExcelSheet(name = "测试", backColor = AlanColor.GREEN, font = "宋体", fontSize = 25)
@Data
public class ExportVO {

    @ExcelColumn(name = "名称", width = 32, link = "${url}")
    private String name;

    @ExcelColumn(name = "值")
    private String value;

    @ExcelColumn(name = "金额")
    @NumFormat(value = "0000.00##")
    private BigDecimal amount;

    @ExcelColumn(name = "时间格式化")
    @DateFormat(value = "yyyy-MM-dd hh:mm:ss")
    private Date dateTime;

    @DateFormat
    @ExcelColumn(name = "日期格式化")
    private java.sql.Date date;

    @ExcelColumn(isExist = false)
    private String url;

}
```

### 使用

- 方式一. 直接导出到浏览器

```java
import com.zukxu.alanpoi.model.SysUser;

import java.util.ArrayList;

public interface Temp {

    //ExcelExportUtil
    export(Colletion<?> list, Class aClass, HttpServletRequest request, HttpServletResponse resp, String fileName);

}
```

- 方式二. 调用getWorkbook获取工作表，自行处理workbook

```java
public interface Temp {

    //ExcelExportUtil.
    getWorkbook(Collection<?> singleSheetData, Class<?> c);

}
```

### 高级使用

- 示例一：导出指定列（动态导出列）

```java
public class Temp {

    public void temp() {
        List<ExportVO> list = new ArrayList<>();
        for(int i = 0; i < 500; i++) {
            ExportVO exportVO = new ExportVO();
            exportVO.setName("name" + i);
            exportVO.setValue(new BigDecimal(123.11 + i * 0.09));
            exportVO.setAmount(new BigDecimal(6666.666 + i * 10));
            exportVO.setDate(new Date(132324343 + i * 100));
            exportVO.setDateTime(new java.util.Date());
            list.add(exportVO);
        }
        List<String> colList = new ArrayList<>();
        //按照顺序仅导出add的列
        colList.add("name");
        colList.add("value");
        //调用获取workbook对象；也可以直接调用exportSpecifyCol方法导出到浏览器
        Workbook workbook = ExcelExportUtil.getWorkbookSpecifyCol(list, ExportVO.class, colList);
    }

}
```

- 示例二：多sheet页签导出

```java
public class Temp {

    public void temp() {
        List<ExportVO> list = new ArrayList<>();
        List<Export2VO> list2 = new ArrayList<>();
        for(int i = 0; i < 500; i++) {
            ExportVO exportVO = new ExportVO();
            exportVO.setName("name" + i);
            exportVO.setValue(new BigDecimal(123.11 + i * 0.09));
            exportVO.setAmount(new BigDecimal(6666.666 + i * 10));
            exportVO.setDate(new Date(132324343 + i * 100));
            exportVO.setDateTime(new java.util.Date());
            list.add(exportVO);
            Export2VO export2VO = new Export2VO();
            export2VO.setName("name" + i);
            export2VO.setValue("value" + i);
            export2VO.setAmount(new BigDecimal(6666.666 + i * 10));
            export2VO.setDate(new Date(132324343 + i * 100));
            export2VO.setDateTime(new java.util.Date());
            list2.add(export2VO);
        }
        Map<Class<?>, Collection<?>> map = new HashMap<>();
        map.put(ExportVO.class, list);
        map.put(Export2VO.class, list2);
        //调用获取workbook对象；也可以直接调用exportByMultiSheet方法导出到浏览器
        Workbook workbook = ExcelExportUtil.getWorkbookByMultiSheet(map);
    }

}
```

对于大数据量的数据导出时性能不是很好，不推荐在大型项目中使用
