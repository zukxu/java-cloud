package com.zukxu.myexcel.service.imports;

import com.github.liaochong.myexcel.core.ColumnSaxExcelReader;
import com.github.liaochong.myexcel.core.DefaultExcelReader;
import com.github.liaochong.myexcel.core.SaxExcelReader;
import com.zukxu.myexcel.entity.ArtCrowd;
import com.zukxu.myexcel.entity.People;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

/**
 * 导入
 */
@Service
public class MyExcelImportService {


    /**
     * 一般导入(不支持CSV导入，不支持图片读取)
     */
    @SneakyThrows
    public void defaultImport(MultipartFile file) {
        // 方式一：全部读取后处理
        List<ArtCrowd> result = DefaultExcelReader.of(ArtCrowd.class).sheet(0) // 0代表第一个，如果为0，可省略该操作，也可sheet("名称")读取
                                                  .rowFilter(row -> row.getRowNum() > 0) // 如无需过滤，可省略该操作，0代表第一行
                                                  .beanFilter(ArtCrowd::isDance) // bean过滤
                                                  .startSheet(sheet -> System.out.println(sheet.getSheetName())) // 在开始读取sheet前执行指定操作
                                                  .read(file.getInputStream());// 可接收inputStream

        // 方式二：读取一行处理一行，可自行决定终止条件
        // readThen有两种重写接口，返回Boolean型接口允许在返回False情况下直接终止读取
        DefaultExcelReader.of(ArtCrowd.class).sheet(0) // 0代表第一个，如果为0，可省略该操作，也可sheet("名称")读取
                          .rowFilter(row -> row.getRowNum() > 0) // 如无需过滤，可省略该操作，0代表第一行
                          .beanFilter(ArtCrowd::isDance) // bean过滤
                          .readThen(file.getInputStream(),
                                    artCrowd -> {System.out.println(artCrowd.getName());});// 可接收inputStream

    }

    /**
     * sax导入，增强型过功能
     * 如需导入为Map，请将导入类设置为SaxExcelReader.of(Map.class)，结果为List<Map>，Map实际类型为LinkedHashMap，key值为Cell，value值为String类型内容值
     * 支持无注解导入，即无需@ExcelColumn指定字段对应的列，会按照全字段默认顺序进行导入
     * 如需提前获取工作簿信息，如有哪些sheet，某个sheet有多少行等，请使用SaxExcelReader.getWorkbookMetaData(file)获取元信息
     */
    @SneakyThrows
    public void saxImport(MultipartFile file) {
        // 方式一：全部读取后处理，SAX模式，避免OOM，建议大量数据使用
        List<ArtCrowd> result = SaxExcelReader.of(ArtCrowd.class)
                                              .sheet(0) // 0代表第一个，如果为0，可省略该操作，也可sheet("名称")读取，.csv文件无效
                                              .rowFilter(row -> row.getRowNum() > 0) // 如无需过滤，可省略该操作，0代表第一行
                                              .charset("GBK") // 仅.csv文件有效，设置当前文件的编码，可选，默认为UTF-8
                                              //.csvDelimiter(';') // 仅.csv文件有效，设置当前文件分割符，可选，默认为英文逗号-,
                                              .ignoreBlankRow() // 是否忽略空行，可选，默认不忽略
                                              .stopReadingOnBlankRow() // 是否遇到空行则停止读取，可选，默认为否
                                              .beanFilter(ArtCrowd::isDance) // 可选，bean过滤
                                              .read(file.getInputStream());// 可接收inputStream

        // 方式二：读取一行处理一行，可自行决定终止条件，SAX模式，避免OOM，建议大量数据使用
        // readThen有两种重写接口，返回Boolean型接口允许在返回False情况下直接终止读取
        SaxExcelReader.of(ArtCrowd.class).sheet(0) // 0代表第一个，如果为0，可省略该操作，也可sheet("名称")读取，.csv文件无效
                      .rowFilter(row -> row.getRowNum() > 0) // 如无需过滤，可省略该操作，0代表第一行
                      .charset("GBK") // 仅.csv文件有效，设置当前文件的编码，可选，默认为UTF-8
                      //.csvDelimiter(';') // 仅.csv文件有效，设置当前文件分割符，可选，默认为英文逗号-,
                      .ignoreBlankRow() // 是否忽略空行，可选，默认不忽略
                      .stopReadingOnBlankRow() // 是否遇到空行则停止读取，可选，默认为否
                      .beanFilter(ArtCrowd::isDance) // 可选，bean过滤
                      .readThen(file.getInputStream(),
                                artCrowd -> {System.out.println(artCrowd.getName());});// 可接收inputStream
    }

    /**
     * sax 多个sheet导入
     *
     * @param response
     */
    @SneakyThrows
    public void multiSheetImport(MultipartFile file, HttpServletResponse response) {
        InputStream excelFile = file.getInputStream();
        //按照sheet索引导入
        SaxExcelReader.of(People.class).sheets(0, 1).read(excelFile);
        //按照sheet名称导入
        SaxExcelReader.of(People.class).sheets("sheet名称1", "sheet名称2").read(excelFile);
    }

    /**
     * 图片导入
     * 图片导入仅DefaultExcelReader支持，属性类型为InputStream，实际赋值类型为ByteArrayInputStream。
     *
     * @param file
     * @param response
     */
    @SneakyThrows
    public void imageImport(MultipartFile file, HttpServletResponse response) {
        InputStream excelFile = file.getInputStream();
        SaxExcelReader.of(ArtCrowd.class).read(excelFile);
    }

    /**
     * 按列读取
     * 支持直接读取为Byte、Short、Integer、Long、Float、Double、String
     */
    @SneakyThrows
    public void columnReadImport(MultipartFile file, HttpServletResponse response) {
        InputStream excelFile = file.getInputStream();

        // 读取为字符串
        List<String> strings = ColumnSaxExcelReader.columnNum(0)
                                                   .rowFilter(row -> row.getRowNum() > 1)
                                                   .readAsString(excelFile);

        // 读取为Integer
        List<Integer> integers = ColumnSaxExcelReader.columnNum(1)
                                                     .rowFilter(row -> row.getRowNum() > 1)
                                                     .readAsInteger(excelFile);

        // 读取为Long
        List<Long> longs = ColumnSaxExcelReader.columnNum(2)
                                               .rowFilter(row -> row.getRowNum() > 1)
                                               .readAsLong(excelFile);
    }

}
