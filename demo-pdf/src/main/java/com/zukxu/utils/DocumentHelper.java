package com.zukxu.utils;

import com.aspose.words.*;
import org.testng.Assert;

import java.io.*;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 用于处理文档和内容操作的工具类
 */
public final class DocumentHelper {

    private DocumentHelper() {
        // 防止实例化
    }

    /**
     * 创建一个段落中没有运行对象的简单文档。
     *
     * @return 没有任何文本的新文档
     * @throws Exception 创建新文档时可能抛出的异常
     */
    static Document createDocumentWithoutDummyText() throws Exception {
        Document doc = new Document();

        // 移除文档之前的所有更改
        doc.removeAllChildren();

        // 设置文档作者
        doc.getBuiltInDocumentProperties().setAuthor("Test Author");

        // 创建一个没有运行对象的段落
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.writeln();

        return doc;
    }

    /**
     * 创建一个包含文本的新文档。
     *
     * @return 包含测试文本的新文档
     * @throws Exception 创建新文档时可能抛出的异常
     */
    static Document createDocumentFillWithDummyText() throws Exception {
        Document doc = new Document();

        // 移除文档之前的所有更改
        doc.removeAllChildren();

        // 设置文档作者
        doc.getBuiltInDocumentProperties().setAuthor("Test Author");

        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.write("Page ");
        builder.insertField("PAGE", "");
        builder.write(" of ");
        builder.insertField("NUMPAGES", "");

        // 插入一个包含两行两列的新表格
        insertTable(builder);

        builder.writeln("Hello World!");

        // 文档内容续至第二页
        builder.insertBreak(BreakType.PAGE_BREAK);

        // 插入目录条目
        insertToc(builder);

        return doc;
    }

    /**
     * 在文件中查找文本。
     *
     * @param path       文件路径
     * @param expression 用于文本搜索的表达式
     * @throws IOException 读取文件时可能抛出的异常
     */
    static void findTextInFile(final String path, final String expression) throws IOException {
        BufferedReader sr = new BufferedReader(new FileReader(path));
        try {
            String line = sr.readLine();
            while (line != null) {
                if (line.isEmpty()) {
                    continue;
                }

                if (line.contains(expression)) {
                    System.out.println(line);
                    break;
                } else {
                    Assert.fail();
                }
            }
        } finally {
            sr.close();
        }
    }

    /**
     * 为报表引擎创建新的文档模板。
     *
     * @param templateText 模板文本
     * @throws Exception 创建新文档时可能抛出的异常
     */
    static Document createSimpleDocument(final String templateText) throws Exception {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.write(templateText);

        return doc;
    }

    /**
     * 创建一个包含文本框形状和一些查询的新文档。
     *
     * @param templateText 模板文本
     * @param shapeType    形状类型
     * @throws Exception 创建新文档时可能抛出的异常
     */
    static Document createTemplateDocumentWithDrawObjects(final String templateText, final int shapeType) throws Exception {
        final double shapeWidth = 431.5;
        final double shapeHeight = 431.5;

        Document doc = new Document();

        // 创建文本框形状
        Shape shape = new Shape(doc, shapeType);
        shape.setWidth(shapeWidth);
        shape.setHeight(shapeHeight);

        Paragraph paragraph = new Paragraph(doc);
        paragraph.appendChild(new Run(doc, templateText));

        // 将段落插入到文本框中
        shape.appendChild(paragraph);

        // 将文本框插入到文档中
        doc.getFirstSection().getBody().getFirstParagraph().appendChild(shape);

        return doc;
    }

    /**
     * 比较两个Word文档。
     *
     * @param filePathDoc1 第一个文档的路径
     * @param filePathDoc2 第二个文档的路径
     * @return 文档比较结果
     * @throws Exception 创建新文档时可能抛出的异常
     */
    static boolean compareDocs(final String filePathDoc1, final String filePathDoc2) throws Exception {
        Document doc1 = new Document(filePathDoc1);
        Document doc2 = new Document(filePathDoc2);

        return doc1.getText().equals(doc2.getText());

    }

    /**
     * 向当前文档中插入运行对象。
     *
     * @param doc       当前文档
     * @param text      自定义文本
     * @param paraIndex 段落索引
     */
    static Run insertNewRun(final Document doc, final String text, final int paraIndex) {
        Paragraph para = getParagraph(doc, paraIndex);

        Run run = new Run(doc);
        run.setText(text);

        para.appendChild(run);

        return run;
    }

    /**
     * 向当前文档中插入文本。
     *
     * @param builder     当前文档构建器
     * @param textStrings 自定义文本数组
     */
    static void insertBuilderText(final DocumentBuilder builder, final String[] textStrings) {
        for (String textString : textStrings) {
            builder.writeln(textString);
        }
    }

    /**
     * 获取当前文档中指定段落的文本。
     *
     * @param doc       当前文档
     * @param paraIndex 段落编号
     */
    static String getParagraphText(final Document doc, final int paraIndex) {
        return doc.getFirstSection().getBody().getParagraphs().get(paraIndex).getText();
    }

    /**
     * 在文档中插入新表格。
     *
     * @param builder 当前文档构建器
     * @throws Exception 设置表格宽度以适应内容时可能抛出的异常
     */
    static Table insertTable(final DocumentBuilder builder) throws Exception {
        // 开始创建新表格
        Table table = builder.startTable();

        // 插入第一行第一列单元格
        builder.insertCell();
        builder.write("Date");

        // 设置宽度以适应表格内容
        table.autoFit(AutoFitBehavior.AUTO_FIT_TO_CONTENTS);

        // 插入第一行第二列单元格
        builder.insertCell();
        builder.write(" ");

        builder.endRow();

        // 插入第二行第一列单元格
        builder.insertCell();
        builder.write("Author");

        // 插入第二行第二列单元格
        builder.insertCell();
        builder.write(" ");

        builder.endRow();

        builder.endTable();

        return table;
    }

    /**
     * 在文档中插入目录条目。
     *
     * @param builder 文档构建器
     */
    static void insertToc(final DocumentBuilder builder) {
        // 创建目录条目
        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_1);

        builder.writeln("Heading 1");

        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_2);

        builder.writeln("Heading 1.1");

        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_4);

        builder.writeln("Heading 1.1.1.1");

        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_5);

        builder.writeln("Heading 1.1.1.1.1");

        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_9);

        builder.writeln("Heading 1.1.1.1.1.1.1.1.1");
    }

    /**
     * 获取当前文档中指定节的文本。
     *
     * @param doc      当前文档
     * @param secIndex 节编号
     * @return 当前文档指定节的文本
     */
    static String getSectionText(final Document doc, final int secIndex) {
        return doc.getSections().get(secIndex).getText();
    }

    /**
     * 获取当前文档中指定段落。
     *
     * @param doc       当前文档
     * @param paraIndex 段落编号
     * @return 当前文档指定段落
     */
    static Paragraph getParagraph(final Document doc, final int paraIndex) {
        return doc.getFirstSection().getBody().getParagraphs().get(paraIndex);
    }

    /**
     * 从输入流中获取字节数组。
     *
     * @param inputStream 包含测试图像的输入流
     * @return 字节数组
     * @throws IOException 读取输入流时可能抛出的异常
     */
    static byte[] getBytesFromStream(final InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        int len;

        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[bufferSize];

        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    /**
     * 创建用于测试的特定日期。
     *
     * @return 特定日期
     */
    static Date createDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        return cal.getTime();
    }

    /**
     * 创建用于测试的特定日期。
     *
     * @return 特定日期
     */
    static Date createDate(int year, int month, int day, int hours, int minuts, int seconds) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, hours, minuts, seconds);
        return cal.getTime();
    }

    /**
     * 创建用于测试的不带时间的日期。
     *
     * @return 不带时间的特定日期
     */
    static Date getDateWithoutTimeUsingFormat(Date date)
    throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy");
        return formatter.parse(formatter.format(date));
    }

    /**
     * 根据系统配置获取日期。
     *
     * @return 不带时间的特定日期
     */
    static LocalDate getLocalDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    static ArrayList<String> directoryGetFiles(final String dirname, final String filenamePattern) {
        File dirFile = new File(dirname);
        Pattern re = Pattern.compile(filenamePattern.replace("*", ".*").replace("?", ".?"));
        ArrayList<String> dirFiles = new ArrayList<>();
        for (File file : Objects.requireNonNull(dirFile.listFiles())) {
            if (file.isDirectory()) {
                dirFiles.addAll(directoryGetFiles(file.getPath(), filenamePattern));
            } else {
                if (re.matcher(file.getName()).matches()) {
                    dirFiles.add(file.getPath());
                }
            }
        }
        return dirFiles;
    }

    /**
     * 实用函数，用于创建连接、命令，执行命令并将结果返回到DataTable中。
     */
    static ResultSet executeDataTable(final String commandText) throws Exception {
        // 加载驱动
        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

        // 打开数据库连接
        //String connString = "jdbc:ucanaccess://" + getDatabaseDir() + "Northwind.accdb";
        String connString = "jdbc:ucanaccess://" + "getDatabaseDir()" + "Northwind.accdb";

        // 来自维基百科：Sun驱动在字符编码和Microsoft Access数据库方面存在已知问题
        // Microsoft Access可能使用一种无法被驱动正确翻译的编码，导致字符串中，例如重音字符被问号替换
        //
        // 在这种情况下，我必须将字符集设置为CP1252，以便欧洲字符能正确显示在数据值中
        java.util.Properties props = new java.util.Properties();
        props.put("charSet", "Cp1252");
        props.put("UID", "Admin");

        // 无DSN数据库连接
        java.sql.Connection conn = java.sql.DriverManager.getConnection(connString, props);

        // 创建并执行命令
        java.sql.Statement statement = conn.createStatement();
        return statement.executeQuery(commandText);
    }

    /**
     * 将文档保存到流中，立即重新打开并返回新打开的版本
     * <p>
     * 用于测试文档功能在保存/加载后是否得以保留
     *
     * @param doc 我们希望重新打开的文档
     */
    static Document saveOpen(Document doc) throws Exception {
        ByteArrayOutputStream docStream = new ByteArrayOutputStream();
        try {
            doc.save(docStream, new OoxmlSaveOptions(SaveFormat.DOCX));
            return new Document(new ByteArrayInputStream(docStream.toByteArray()));
        } finally {
            if (docStream != null) docStream.close();
        }
    }

    /**
     * 获取段落集合中列表项的数量。
     *
     * @param paragraphs 段落集合
     * @return 列表项的数量
     */
    static int getListItemCount(NodeCollection paragraphs) {
        int listItemCount = 0;

        for (Paragraph para : (Iterable<Paragraph>) paragraphs) {
            if (para.getListFormat().isListItem()) {
                listItemCount++;
            }
        }

        return listItemCount;
    }

    /**
     * 获取段落集合中指定列表级别编号的数量。
     *
     * @param paragraphs      段落集合
     * @param listLevelNumber 列表级别编号
     * @return 指定列表级别编号的数量
     */
    static int getListLevelNumberCount(NodeCollection paragraphs, int listLevelNumber) {
        int listLevelNumberCount = 0;

        for (Paragraph para : (Iterable<Paragraph>) paragraphs) {
            if (para.getListFormat().getListLevelNumber() == listLevelNumber) {
                listLevelNumberCount++;
            }
        }

        return listLevelNumberCount;
    }

    /**
     * 获取字段集合中指定类型字段的数量。
     *
     * @param fields    字段集合
     * @param fieldType 字段类型
     * @return 指定类型字段的数量
     */
    static int getFieldsCount(FieldCollection fields, int fieldType) {
        int fieldsCount = 0;

        for (Field field : fields) {
            if (field.getType() == fieldType) {
                fieldsCount++;
            }
        }

        return fieldsCount;
    }

    /**
     * 获取字段集合中指定类型的字段。
     *
     * @param fields    字段集合
     * @param fieldType 字段类型
     * @return 指定类型的字段，如果未找到则返回null
     */
    static Object getField(FieldCollection fields, int fieldType) {
        for (Field field : fields) {
            if (field.getType() == fieldType)
                return field;
        }

        return null;
    }
}