/*
package com.zukxu.example;

import com.aspose.words.*;
import com.aspose.words.Shape;
import com.zukxu.utils.DocumentHelper;
import org.apache.commons.collections4.IterableUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.*;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

*/
/**
 * 该类包含一系列用于测试图表相关功能的测试用例。
 *//*

@Test
public class ExChartsTest {
    */
/**
     * 测试如何插入图表并设置图表标题。
     *
     * @throws Exception 执行过程中可能抛出的异常
     *//*

    @Test
    public void chartTitle() throws Exception {
        // 开始：设置图表标题相关代码示例
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // 使用文档构建器插入一个条形图形状，并获取其图表对象
        Shape chartShape = builder.insertChart(ChartType.BAR, 400.0, 300.0);
        Chart chart = chartShape.getChart();

        // 使用 "Title" 属性为图表设置标题，标题将显示在图表区域的顶部中心位置
        ChartTitle title = chart.getTitle();
        title.setText("My Chart");
        // 原代码此处可能存在问题，ChartTitle 可能没有 getFont 方法
        // title.getFont().setSize(15.0);
        // title.getFont().setColor(Color.BLUE);

        // 将 "Show" 属性设置为 "true" 以使标题可见
        title.setShow(true);

        // 将 "Overlay" 属性设置为 "true"，允许其他图表元素与标题重叠，为其他元素腾出更多空间
        title.setOverlay(true);

        // 将文档保存到指定路径
        doc.save("files/" + "Charts.ChartTitle.docx");
        // 结束：设置图表标题相关代码示例

        doc = new Document("files/" + "Charts.ChartTitle.docx");
        chartShape = (Shape) doc.getChild(NodeType.SHAPE, 0, true);

        Assert.assertEquals(ShapeType.NON_PRIMITIVE, chartShape.getShapeType());
        Assert.assertTrue(chartShape.hasChart());

        title = chartShape.getChart().getTitle();

        Assert.assertEquals("My Chart", title.getText());
        Assert.assertTrue(title.getOverlay());
        Assert.assertTrue(title.getShow());
    }

    */
/**
     * 测试如何启用并配置图表系列的数据标签。
     *
     * @throws Exception 执行过程中可能抛出的异常
     *//*

    @Test
    public void dataLabelNumberFormat() throws Exception {
        // 开始：设置数据标签数字格式相关代码示例
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // 插入一个折线图，然后清除其演示数据系列以获得一个干净的图表，接着设置标题
        Shape shape = builder.insertChart(ChartType.LINE, 500.0, 300.0);
        Chart chart = shape.getChart();
        chart.getSeries().clear();
        chart.getTitle().setText("Monthly sales report");

        // 插入一个自定义图表系列，将月份作为 X 轴的类别，相应的十进制数值作为 Y 轴的值
        ChartSeries series = chart.getSeries().add("Revenue",
                                                   new String[]{"January", "February", "March"},
                                                   new double[]{25.611d, 21.439d, 33.750d});

        // 启用数据标签，然后为数据标签中显示的值应用自定义数字格式
        // 此格式将显示的十进制值视为数百万美元
        series.hasDataLabels(true);
        ChartDataLabelCollection dataLabels = series.getDataLabels();
        dataLabels.setShowValue(true);
        dataLabels.getNumberFormat().setFormatCode("\"US$\" #,##0.000\"M\"");
        dataLabels.getFont().setSize(12.0);

        // 将文档保存到指定路径
        doc.save("files/" + "Charts.DataLabelNumberFormat.docx");
        // 结束：设置数据标签数字格式相关代码示例

        doc = new Document("files/" + "Charts.DataLabelNumberFormat.docx");
        series = ((Shape) doc.getChild(NodeType.SHAPE, 0, true)).getChart().getSeries().get(0);

        Assert.assertTrue(series.hasDataLabels());
        Assert.assertTrue(series.getDataLabels().getShowValue());
        Assert.assertEquals("\"US$\" #,##0.000\"M\"", series.getDataLabels().getNumberFormat().getFormatCode());
    }

    */
/**
     * 测试如何插入图表并修改其坐标轴的外观。
     *
     * @throws Exception 执行过程中可能抛出的异常
     *//*

    @Test
    public void axisProperties() throws Exception {
        // 开始：设置坐标轴属性相关代码示例
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 500.0, 300.0);
        Chart chart = shape.getChart();

        // 清除图表的演示数据系列以获得一个干净的图表
        chart.getSeries().clear();

        // 插入一个图表系列，将类别作为 X 轴的值，相应的数值作为 Y 轴的值
        chart.getSeries().add("Aspose Test Series",
                              new String[]{"Word", "PDF", "Excel", "GoogleDocs", "Note"},
                              new double[]{640.0, 320.0, 280.0, 120.0, 150.0});

        // 图表坐标轴有各种选项可以改变其外观，例如方向、主/次单位刻度和刻度线
        ChartAxis xAxis = chart.getAxisX();
        xAxis.setCategoryType(AxisCategoryType.CATEGORY);
        xAxis.setCrosses(AxisCrosses.MINIMUM);
        xAxis.setReverseOrder(false);
        xAxis.setMajorTickMark(AxisTickMark.INSIDE);
        xAxis.setMinorTickMark(AxisTickMark.CROSS);
        xAxis.setMajorUnit(10.0d);
        xAxis.setMinorUnit(15.0d);
        xAxis.setTickLabelOffset(50);
        xAxis.setTickLabelPosition(AxisTickLabelPosition.LOW);
        xAxis.setTickLabelSpacingIsAuto(false);
        xAxis.setTickMarkSpacing(1);

        Assert.assertEquals(doc, xAxis.getDocument());

        ChartAxis yAxis = chart.getAxisY();
        yAxis.setCategoryType(AxisCategoryType.AUTOMATIC);
        yAxis.setCrosses(AxisCrosses.MAXIMUM);
        yAxis.setReverseOrder(true);
        yAxis.setMajorTickMark(AxisTickMark.INSIDE);
        yAxis.setMinorTickMark(AxisTickMark.CROSS);
        yAxis.setMajorUnit(100.0d);
        yAxis.setMinorUnit(20.0d);
        yAxis.setTickLabelPosition(AxisTickLabelPosition.NEXT_TO_AXIS);
        yAxis.setTickLabelAlignment(ParagraphAlignment.CENTER);
        yAxis.setTickLabelSpacing(1);

        // 柱状图没有 Z 轴
        Assert.assertNull(chart.getAxisZ());

        // 将文档保存到指定路径
        doc.save("files/" + "Charts.AxisProperties.docx");
        // 结束：设置坐标轴属性相关代码示例

        doc = new Document("files/" + "Charts.AxisProperties.docx");
        chart = ((Shape) doc.getChild(NodeType.SHAPE, 0, true)).getChart();

        Assert.assertEquals(AxisCategoryType.CATEGORY, chart.getAxisX().getCategoryType());
        Assert.assertEquals(AxisCrosses.MINIMUM, chart.getAxisX().getCrosses());
        Assert.assertFalse(chart.getAxisX().getReverseOrder());
        Assert.assertEquals(AxisTickMark.INSIDE, chart.getAxisX().getMajorTickMark());
        Assert.assertEquals(AxisTickMark.CROSS, chart.getAxisX().getMinorTickMark());
        Assert.assertEquals(1.0d, chart.getAxisX().getMajorUnit());
        Assert.assertEquals(0.5d, chart.getAxisX().getMinorUnit());
        Assert.assertEquals(50, chart.getAxisX().getTickLabelOffset());
        Assert.assertEquals(AxisTickLabelPosition.LOW, chart.getAxisX().getTickLabelPosition());
        Assert.assertFalse(chart.getAxisX().getTickLabelSpacingIsAuto());
        Assert.assertEquals(1, chart.getAxisX().getTickMarkSpacing());

        Assert.assertEquals(AxisCategoryType.CATEGORY, chart.getAxisY().getCategoryType());
        Assert.assertEquals(AxisCrosses.MAXIMUM, chart.getAxisY().getCrosses());
        Assert.assertTrue(chart.getAxisY().getReverseOrder());
        Assert.assertEquals(AxisTickMark.INSIDE, chart.getAxisY().getMajorTickMark());
        Assert.assertEquals(AxisTickMark.CROSS, chart.getAxisY().getMinorTickMark());
        Assert.assertEquals(100.0d, chart.getAxisY().getMajorUnit());
        Assert.assertEquals(20.0d, chart.getAxisY().getMinorUnit());
        Assert.assertEquals(AxisTickLabelPosition.NEXT_TO_AXIS, chart.getAxisY().getTickLabelPosition());
        Assert.assertEquals(ParagraphAlignment.CENTER, chart.getAxisY().getTickLabelAlignment());
        Assert.assertEquals(1, chart.getAxisY().getTickLabelSpacing());
    }

    */
/**
     * 测试如何处理图表的坐标轴集合。
     *
     * @throws Exception 执行过程中可能抛出的异常
     *//*

    @Test
    public void axisCollection() throws Exception {
        // 开始：处理坐标轴集合相关代码示例
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 500.0, 300.0);
        Chart chart = shape.getChart();

        // 隐藏主 Y 轴和次 Y 轴上的主网格线
        //for (ChartAxis axis : chart.getAxisX()) {
        //    if (axis.getType() == ChartAxisType.VALUE)
        //        axis.hasMajorGridlines(false);
        //}

        // 将文档保存到指定路径
        doc.save("files/" + "Charts.AxisCollection.docx");
        // 结束：处理坐标轴集合相关代码示例
    }

    */
/**
     * 测试如何插入包含日期/时间值的图表。
     *
     * @throws Exception 执行过程中可能抛出的异常
     *//*

    @Test
    public void dateTimeValues() throws Exception {
        // 开始：插入包含日期/时间值的图表相关代码示例
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.LINE, 500.0, 300.0);
        Chart chart = shape.getChart();

        // 清除图表的演示数据系列以获得一个干净的图表
        chart.getSeries().clear();

        // 插入一个自定义系列，包含 X 轴的日期/时间值和 Y 轴的相应十进制值
        chart.getSeries().add("Aspose Test Series",
                              new Date[]
                                      {
                                              DocumentHelper.createDate(2017, 11, 6),
                                              DocumentHelper.createDate(2017, 11, 9),
                                              DocumentHelper.createDate(2017, 11, 15),
                                              DocumentHelper.createDate(2017, 11, 21),
                                              DocumentHelper.createDate(2017, 11, 25),
                                              DocumentHelper.createDate(2017, 11, 29)
                                      },
                              new double[]{1.2, 0.3, 2.1, 2.9, 4.2, 5.3});

        // 设置 X 轴的下限和上限
        ChartAxis xAxis = chart.getAxisX();
        Date datetimeMin = DocumentHelper.createDate(2017, 11, 5);
        xAxis.getScaling().setMinimum(new AxisBound(datetimeMin));
        Date datetimeMax = DocumentHelper.createDate(2017, 12, 3);
        xAxis.getScaling().setMaximum(new AxisBound(datetimeMax));

        // 将 X 轴的主单位设置为一周，次单位设置为一天
        xAxis.setBaseTimeUnit(AxisTimeUnit.DAYS);
        xAxis.setMajorUnit(7.0d);
        xAxis.setMajorTickMark(AxisTickMark.CROSS);
        xAxis.setMinorUnit(1.0d);
        xAxis.setMinorTickMark(AxisTickMark.OUTSIDE);
        xAxis.hasMajorGridlines(true);
        xAxis.hasMinorGridlines(true);

        // 定义 Y 轴的十进制值属性
        ChartAxis yAxis = chart.getAxisY();
        yAxis.getTickLabels().setPosition(AxisTickLabelPosition.HIGH);
        yAxis.setMajorUnit(100.0d);
        yAxis.setMinorUnit(50.0d);
        yAxis.getDisplayUnit().setUnit(AxisBuiltInUnit.HUNDREDS);
        yAxis.getScaling().setMinimum(new AxisBound(100.0));
        yAxis.getScaling().setMaximum(new AxisBound(700.0));
        yAxis.hasMajorGridlines(true);
        yAxis.hasMinorGridlines(true);

        // 将文档保存到指定路径
        doc.save("files/" + "Charts.DateTimeValues.docx");
        // 结束：插入包含日期/时间值的图表相关代码示例

        doc = new Document("files/" + "Charts.DateTimeValues.docx");
        chart = ((Shape) doc.getChild(NodeType.SHAPE, 0, true)).getChart();

        Assert.assertEquals(datetimeMin, chart.getAxisX().getScaling().getMinimum().getValueAsDate());
        Assert.assertEquals(datetimeMax, chart.getAxisX().getScaling().getMaximum().getValueAsDate());
        Assert.assertEquals(AxisTimeUnit.DAYS, chart.getAxisX().getBaseTimeUnit());
        Assert.assertEquals(7.0d, chart.getAxisX().getMajorUnit());
        Assert.assertEquals(1.0d, chart.getAxisX().getMinorUnit());
        Assert.assertEquals(AxisTickMark.CROSS, chart.getAxisX().getMajorTickMark());
        Assert.assertEquals(AxisTickMark.OUTSIDE, chart.getAxisX().getMinorTickMark());
        Assert.assertEquals(true, chart.getAxisX().hasMajorGridlines());
        Assert.assertEquals(true, chart.getAxisX().hasMinorGridlines());

        Assert.assertEquals(AxisTickLabelPosition.HIGH, chart.getAxisY().getTickLabels().getPosition());
        Assert.assertEquals(100.0d, chart.getAxisY().getMajorUnit());
        Assert.assertEquals(50.0d, chart.getAxisY().getMinorUnit());
        Assert.assertEquals(AxisBuiltInUnit.HUNDREDS, chart.getAxisY().getDisplayUnit().getUnit());
        Assert.assertEquals(new AxisBound(100.0), chart.getAxisY().getScaling().getMinimum());
        Assert.assertEquals(new AxisBound(700.0), chart.getAxisY().getScaling().getMaximum());
        Assert.assertEquals(true, chart.getAxisY().hasMajorGridlines());
        Assert.assertEquals(true, chart.getAxisY().hasMinorGridlines());
    }

    */
/**
     * 测试如何隐藏图表坐标轴。
     *
     * @throws Exception 执行过程中可能抛出的异常
     *//*

    @Test
    public void hideChartAxis() throws Exception {
        // 开始：隐藏图表坐标轴相关代码示例
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.LINE, 500.0, 300.0);
        Chart chart = shape.getChart();

        // 清除图表的演示数据系列以获得一个干净的图表
        chart.getSeries().clear();

        // 插入一个自定义系列，将类别作为 X 轴的值，相应的十进制值作为 Y 轴的值
        chart.getSeries().add("AW Series 1",
                              new String[]{"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"},
                              new double[]{1.2, 0.3, 2.1, 2.9, 4.2});

        // 隐藏图表坐标轴以简化图表外观
        chart.getAxisX().setHidden(true);
        chart.getAxisY().setHidden(true);

        // 将文档保存到指定路径
        doc.save("files/" + "Charts.HideChartAxis.docx");
        // 结束：隐藏图表坐标轴相关代码示例

        doc = new Document("files/" + "Charts.HideChartAxis.docx");
        chart = ((Shape) doc.getChild(NodeType.SHAPE, 0, true)).getChart();

        Assert.assertEquals(chart.getAxisX().getHidden(), true);
        Assert.assertEquals(chart.getAxisY().getHidden(), true);
    }

    */
/**
     * 测试如何为图表坐标轴设置数字格式。
     *
     * @throws Exception 执行过程中可能抛出的异常
     *//*

    @Test
    public void setNumberFormatToChartAxis() throws Exception {
        // 开始：为图表坐标轴设置数字格式相关代码示例
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 500.0, 300.0);
        Chart chart = shape.getChart();

        // 清除图表的演示数据系列以获得一个干净的图表
        chart.getSeries().clear();

        // 向图表中添加一个自定义系列，包含 X 轴的类别和 Y 轴的大数值
        chart.getSeries().add("Aspose Test Series",
                              new String[]{"Word", "PDF", "Excel", "GoogleDocs", "Note"},
                              new double[]{1900000.0, 850000.0, 2100000.0, 600000.0, 1500000.0});

        // 将 Y 轴刻度标签的数字格式设置为不使用逗号分组数字
        chart.getAxisY().getNumberFormat().setFormatCode("#,##0");

        // 此标志可以覆盖上述值，并从源单元格中提取数字格式
        Assert.assertFalse(chart.getAxisY().getNumberFormat().isLinkedToSource());

        // 将文档保存到指定路径
        doc.save("files/" + "Charts.SetNumberFormatToChartAxis.docx");
        // 结束：为图表坐标轴设置数字格式相关代码示例

        doc = new Document("files/" + "Charts.SetNumberFormatToChartAxis.docx");
        chart = ((Shape) doc.getChild(NodeType.SHAPE, 0, true)).getChart();

        Assert.assertEquals("#,##0", chart.getAxisY().getNumberFormat().getFormatCode());
    }

    */
/**
     * 测试不同类型图表的显示和转换。
     *
     * @param chartType 图表类型
     * @throws Exception 执行过程中可能抛出的异常
     *//*

    @Test(dataProvider = "testDisplayChartsWithConversionDataProvider")
    public void testDisplayChartsWithConversion(int chartType) throws Exception {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(chartType, 500.0, 300.0);
        Chart chart = shape.getChart();
        chart.getSeries().clear();

        chart.getSeries().add("Aspose Test Series",
                              new String[]{"Word", "PDF", "Excel", "GoogleDocs", "Note"},
                              new double[]{1900000.0, 850000.0, 2100000.0, 600000.0, 1500000.0});

        // 将文档保存为 DOCX 和 PDF 格式
        doc.save("files/" + "Charts.TestDisplayChartsWithConversion.docx");
        doc.save("files/" + "Charts.TestDisplayChartsWithConversion.pdf");
    }

    */
/**
     * 为 testDisplayChartsWithConversion 测试方法提供数据。
     *
     * @return 包含不同图表类型的二维对象数组
     * @throws Exception 执行过程中可能抛出的异常
     *//*

    // JAVA-添加的测试方法数据提供者
    @DataProvider(name = "testDisplayChartsWithConversionDataProvider")
    public static Object[][] testDisplayChartsWithConversionDataProvider() throws Exception {
        return new Object[][]
                {
                        {ChartType.COLUMN},
                        {ChartType.LINE},
                        {ChartType.PIE},
                        {ChartType.BAR},
                        {ChartType.AREA},
                        };
    }

    */
/**
     * 测试 3D 曲面图。
     *
     * @throws Exception 执行过程中可能抛出的异常
     *//*

    @Test
    public void surface3DChart() throws Exception {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.SURFACE_3_D, 500.0, 300.0);
        Chart chart = shape.getChart();
        chart.getSeries().clear();

        chart.getSeries().add("Aspose Test Series 1",
                              new String[]{"Word", "PDF", "Excel", "GoogleDocs", "Note"},
                              new double[]{1900000.0, 850000.0, 2100000.0, 600000.0, 1500000.0});

        chart.getSeries().add("Aspose Test Series 2",
                              new String[]{"Word", "PDF", "Excel", "GoogleDocs", "Note"},
                              new double[]{900000.0, 50000.0, 1100000.0, 400000.0, 2500000.0});

        chart.getSeries().add("Aspose Test Series 3",
                              new String[]{"Word", "PDF", "Excel", "GoogleDocs", "Note"},
                              new double[]{500000.0, 820000.0, 1500000.0, 400000.0, 100000.0});

        // 将文档保存为 DOCX 和 PDF 格式
        doc.save("files/" + "Charts.SurfaceChart.docx");
        doc.save("files/" + "Charts.SurfaceChart.pdf");
    }

    */
/**
     * 测试气泡图的数据标签。
     *
     * @throws Exception 执行过程中可能抛出的异常
     *//*

    @Test
    public void dataLabelsBubbleChart() throws Exception {
        // 开始：处理气泡图数据标签相关代码示例
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Chart chart = builder.insertChart(ChartType.BUBBLE, 500.0, 300.0).getChart();

        // 清除图表的演示数据系列以获得一个干净的图表
        chart.getSeries().clear();

        // 插入一个自定义系列，包含每个气泡的 X/Y 坐标和直径
        ChartSeries series = chart.getSeries().add("Aspose Test Series",
                                                   new double[]{2.9, 3.5, 1.1, 4.0, 4.0},
                                                   new double[]{1.9, 8.5, 2.1, 6.0, 1.5},
                                                   new double[]{9.0, 4.5, 2.5, 8.0, 5.0});

        // 启用数据标签，然后修改其外观
        series.hasDataLabels(true);
        ChartDataLabelCollection dataLabels = series.getDataLabels();
        dataLabels.setShowBubbleSize(true);
        dataLabels.setShowCategoryName(true);
        dataLabels.setShowSeriesName(true);
        dataLabels.setSeparator(" & ");

        // 将文档保存到指定路径
        doc.save("files/" + "Charts.DataLabelsBubbleChart.docx");
        // 结束：处理气泡图数据标签相关代码示例

        doc = new Document("files/" + "Charts.DataLabelsBubbleChart.docx");
        dataLabels = ((Shape) doc.getChild(NodeType.SHAPE, 0, true)).getChart().getSeries().get(0).getDataLabels();

        Assert.assertTrue(dataLabels.getShowBubbleSize());
        Assert.assertTrue(dataLabels.getShowCategoryName());
        Assert.assertTrue(dataLabels.getShowSeriesName());
        Assert.assertEquals(" & ", dataLabels.getSeparator());
    }

    */
/**
     * 测试饼图的数据标签。
     *
     * @throws Exception 执行过程中可能抛出的异常
     *//*

    @Test
    public void dataLabelsPieChart() throws Exception {
        // 开始：处理饼图数据标签相关代码示例
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Chart chart = builder.insertChart(ChartType.PIE, 500.0, 300.0).getChart();

        // 清除图表的演示数据系列以获得一个干净的图表
        chart.getSeries().clear();

        // 插入一个自定义图表系列，包含每个扇形的类别名称及其频率表
        ChartSeries series = chart.getSeries().add("Aspose Test Series",
                                                   new String[]{"Word", "PDF", "Excel"},
                                                   new double[]{2.7, 3.2, 0.8});

        // 启用数据标签，使其同时显示每个扇形的百分比和频率，并修改其外观
        series.hasDataLabels(true);
        ChartDataLabelCollection dataLabels = series.getDataLabels();
        dataLabels.setShowLeaderLines(true);
        dataLabels.setShowLegendKey(true);
        dataLabels.setShowPercentage(true);
        dataLabels.setShowValue(true);
        dataLabels.setSeparator("; ");

        // 将文档保存到指定路径
        doc.save("files/" + "Charts.DataLabelsPieChart.docx");
        // 结束：处理饼图数据标签相关代码示例

        doc = new Document("files/" + "Charts.DataLabelsPieChart.docx");
        dataLabels = ((Shape) doc.getChild(NodeType.SHAPE, 0, true)).getChart().getSeries().get(0).getDataLabels();

        Assert.assertTrue(dataLabels.getShowLeaderLines());
        Assert.assertTrue(dataLabels.getShowLegendKey());
        Assert.assertTrue(dataLabels.getShowPercentage());
        Assert.assertTrue(dataLabels.getShowValue());
        Assert.assertEquals("; ", dataLabels.getSeparator());
    }

    */
/**
     * 测试如何为折线图的数据点应用标签。
     *
     * @throws Exception 执行过程中可能抛出的异常
     *//*

    // 开始：为折线图数据点应用标签相关代码示例
    @Test // 跳过此测试
    public void dataLabels() throws Exception {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape chartShape = builder.insertChart(ChartType.LINE, 400.0, 300.0);
        Chart chart = chartShape.getChart();

        Assert.assertEquals(3, chart.getSeries().getCount());
        Assert.assertEquals("Series 1", chart.getSeries().get(0).getName());
        Assert.assertEquals("Series 2", chart.getSeries().get(1).getName());
        Assert.assertEquals("Series 3", chart.getSeries().get(2).getName());

        // 为图表中的每个系列应用数据标签
        // 这些标签将显示在图表中的每个数据点旁边，并显示其值
        for (ChartSeries series : chart.getSeries()) {
            applyDataLabels(series, 4, "000.0", ", ");
            Assert.assertEquals(series.getDataLabels().getCount(), 4);
        }

        // 更改系列中每个数据标签的分隔符字符串
        Iterator<ChartDataLabel> enumerator = chart.getSeries().get(0).getDataLabels().iterator();
        while (enumerator.hasNext()) {
            Assert.assertEquals(enumerator.next().getSeparator(), ", ");
            enumerator.next().setSeparator(" & ");
        }
        ChartDataLabel dataLabel = chart.getSeries().get(1).getDataLabels().get(2);
        //dataLabel.getFormat().getFill().setColor(Color.RED);

        // For a cleaner looking graph, we can remove data labels individually.
        dataLabel.clearFormat();

        // We can also strip an entire series of its data labels at once.
        chart.getSeries().get(2).getDataLabels().clearFormat();

        doc.save("file/" + "Charts.DataLabels.docx");
    }
    private static void applyDataLabels(ChartSeries series, int labelsCount, String numberFormat, String separator) {
        series.hasDataLabels(true);
        series.setExplosion(40);

        for (int i = 0; i < labelsCount; i++) {
            Assert.assertFalse(series.getDataLabels().get(i).isVisible());

            series.getDataLabels().get(i).setShowCategoryName(true);
            series.getDataLabels().get(i).setShowSeriesName(true);
            series.getDataLabels().get(i).setShowValue(true);
            series.getDataLabels().get(i).setShowLeaderLines(true);
            series.getDataLabels().get(i).setShowLegendKey(true);
            series.getDataLabels().get(i).setShowPercentage(false);
            Assert.assertFalse(series.getDataLabels().get(i).isHidden());
            Assert.assertFalse(series.getDataLabels().get(i).getShowDataLabelsRange());

            series.getDataLabels().get(i).getNumberFormat().setFormatCode(numberFormat);
            series.getDataLabels().get(i).setSeparator(separator);

            Assert.assertFalse(series.getDataLabels().get(i).getShowDataLabelsRange());
            Assert.assertTrue(series.getDataLabels().get(i).isVisible());
            Assert.assertFalse(series.getDataLabels().get(i).isHidden());
        }
    }
    //ExEnd

    //ExStart
    //ExFor:ChartSeries.Smooth
    //ExFor:ChartSeries.InvertIfNegative
    //ExFor:ChartDataPoint
    //ExFor:ChartDataPoint.Format
    //ExFor:ChartDataPoint.ClearFormat
    //ExFor:ChartDataPoint.Index
    //ExFor:ChartDataPointCollection
    //ExFor:ChartDataPointCollection.ClearFormat
    //ExFor:ChartDataPointCollection.Count
    //ExFor:ChartDataPointCollection.GetEnumerator
    //ExFor:ChartDataPointCollection.Item(Int32)
    //ExFor:ChartMarker
    //ExFor:ChartMarker.Size
    //ExFor:ChartMarker.Symbol
    //ExFor:IChartDataPoint
    //ExFor:IChartDataPoint.InvertIfNegative
    //ExFor:ChartDataPoint.InvertIfNegative
    //ExFor:IChartDataPoint.Marker
    //ExFor:MarkerSymbol
    //ExSummary:Shows how to work with data points on a line chart.
    @Test//ExSkip
    public void chartDataPoint() throws Exception {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.LINE, 500.0, 350.0);
        Chart chart = shape.getChart();

        Assert.assertEquals(3, chart.getSeries().getCount());
        Assert.assertEquals("Series 1", chart.getSeries().get(0).getName());
        Assert.assertEquals("Series 2", chart.getSeries().get(1).getName());
        Assert.assertEquals("Series 3", chart.getSeries().get(2).getName());

        // Emphasize the chart's data points by making them appear as diamond shapes.
        for (ChartSeries series : chart.getSeries())
            applyDataPoints(series, 4, MarkerSymbol.DIAMOND, 15);

        // Smooth out the line that represents the first data series.
        chart.getSeries().get(0).setSmooth(true);

        // Verify that data points for the first series will not invert their colors if the value is negative.
        Iterator<ChartDataPoint> enumerator = chart.getSeries().get(0).getDataPoints().iterator();
        while (enumerator.hasNext()) {
            Assert.assertFalse(enumerator.next().getInvertIfNegative());
        }

        ChartDataPoint dataPoint = chart.getSeries().get(1).getDataPoints().get(2);
        dataPoint.getFormat().getFill().setColor(Color.RED);

        // For a cleaner looking graph, we can clear format individually.
        dataPoint.clearFormat();

        // We can also strip an entire series of data points at once.
        chart.getSeries().get(2).getDataPoints().clearFormat();

        doc.save(getArtifactsDir() + "Charts.ChartDataPoint.docx");
    }

    /// <summary>
    /// Applies a number of data points to a series.
    /// </summary>
    private static void applyDataPoints(ChartSeries series, int dataPointsCount, int markerSymbol, int dataPointSize) {
        for (int i = 0; i < dataPointsCount; i++) {
            ChartDataPoint point = series.getDataPoints().get(i);
            point.getMarker().setSymbol(markerSymbol);
            point.getMarker().setSize(dataPointSize);

            Assert.assertEquals(point.getIndex(), i);
        }
    }
    //ExEnd

    @Test
    public void pieChartExplosion() throws Exception {
        //ExStart
        //ExFor:IChartDataPoint.Explosion
        //ExFor:ChartDataPoint.Explosion
        //ExSummary:Shows how to move the slices of a pie chart away from the center.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.PIE, 500.0, 350.0);
        Chart chart = shape.getChart();

        Assert.assertEquals(1, chart.getSeries().getCount());
        Assert.assertEquals("Sales", chart.getSeries().get(0).getName());

        // "Slices" of a pie chart may be moved away from the center by a distance via the respective data point's Explosion attribute.
        // Add a data point to the first portion of the pie chart and move it away from the center by 10 points.
        // Aspose.Words create data points automatically if them does not exist.
        ChartDataPoint dataPoint = chart.getSeries().get(0).getDataPoints().get(0);
        dataPoint.setExplosion(10);

        // Displace the second portion by a greater distance.
        dataPoint = chart.getSeries().get(0).getDataPoints().get(1);
        dataPoint.setExplosion(40);

        doc.save(getArtifactsDir() + "Charts.PieChartExplosion.docx");
        //ExEnd

        doc = new Document(getArtifactsDir() + "Charts.PieChartExplosion.docx");
        ChartSeries series = ((Shape) doc.getChild(NodeType.SHAPE, 0, true)).getChart().getSeries().get(0);

        Assert.assertEquals(10, series.getDataPoints().get(0).getExplosion());
        Assert.assertEquals(40, series.getDataPoints().get(1).getExplosion());
    }

    @Test
    public void bubble3D() throws Exception {
        //ExStart
        //ExFor:ChartDataLabel.ShowBubbleSize
        //ExFor:ChartDataLabel.Font
        //ExFor:IChartDataPoint.Bubble3D
        //ExFor:ChartSeries.Bubble3D
        //ExSummary:Shows how to use 3D effects with bubble charts.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.BUBBLE_3_D, 500.0, 350.0);
        Chart chart = shape.getChart();

        Assert.assertEquals(1, chart.getSeries().getCount());
        Assert.assertEquals("Y-Values", chart.getSeries().get(0).getName());
        Assert.assertTrue(chart.getSeries().get(0).getBubble3D());

        // Apply a data label to each bubble that displays its diameter.
        for (int i = 0; i < 3; i++) {
            chart.getSeries().get(0).hasDataLabels(true);
            ChartDataLabel cdl = chart.getSeries().get(0).getDataLabels().get(i);
            chart.getSeries().get(0).getDataLabels().get(i).getFont().setSize(12.0);
            cdl.setShowBubbleSize(true);
        }

        doc.save(getArtifactsDir() + "Charts.Bubble3D.docx");
        //ExEnd

        doc = new Document(getArtifactsDir() + "Charts.Bubble3D.docx");
        ChartSeries series = ((Shape) doc.getChild(NodeType.SHAPE, 0, true)).getChart().getSeries().get(0);

        for (int i = 0; i < 3; i++) {
            Assert.assertTrue(series.getDataLabels().get(i).getShowBubbleSize());
            Assert.assertTrue(series.getDataLabels().get(i).getShowBubbleSize());
        }
    }

    //ExStart
    //ExFor:ChartAxis.Type
    //ExFor:ChartAxisType
    //ExFor:ChartType
    //ExFor:Chart.Series
    //ExFor:ChartSeriesCollection.Add(String,DateTime[],Double[])
    //ExFor:ChartSeriesCollection.Add(String,Double[],Double[])
    //ExFor:ChartSeriesCollection.Add(String,Double[],Double[],Double[])
    //ExFor:ChartSeriesCollection.Add(String,String[],Double[])
    //ExSummary:Shows how to create an appropriate type of chart series for a graph type.
    @Test //ExSkip
    public void chartSeriesCollection() throws Exception {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // There are several ways of populating a chart's series collection.
        // Different series schemas are intended for different chart types.
        // 1 -  Column chart with columns grouped and banded along the X-axis by category:
        Chart chart = appendChart(builder, ChartType.COLUMN, 500.0, 300.0);

        String[] categories = {"Category 1", "Category 2", "Category 3"};

        // Insert two series of decimal values containing a value for each respective category.
        // This column chart will have three groups, each with two columns.
        chart.getSeries().add("Series 1", categories, new double[]{76.6, 82.1, 91.6});
        chart.getSeries().add("Series 2", categories, new double[]{64.2, 79.5, 94.0});

        // Categories are distributed along the X-axis, and values are distributed along the Y-axis.
        Assert.assertEquals(ChartAxisType.CATEGORY, chart.getAxisX().getType());
        Assert.assertEquals(ChartAxisType.VALUE, chart.getAxisY().getType());

        // 2 -  Area chart with dates distributed along the X-axis:
        chart = appendChart(builder, ChartType.AREA, 500.0, 300.0);

        Date[] dates = {DocumentHelper.createDate(2014, 3, 31),
                        DocumentHelper.createDate(2017, 1, 23),
                        DocumentHelper.createDate(2017, 6, 18),
                        DocumentHelper.createDate(2019, 11, 22),
                        DocumentHelper.createDate(2020, 9, 7)
        };

        // Insert a series with a decimal value for each respective date.
        // The dates will be distributed along a linear X-axis,
        // and the values added to this series will create data points.
        chart.getSeries().add("Series 1", dates, new double[]{15.8, 21.5, 22.9, 28.7, 33.1});

        Assert.assertEquals(ChartAxisType.CATEGORY, chart.getAxisX().getType());
        Assert.assertEquals(ChartAxisType.VALUE, chart.getAxisY().getType());

        // 3 -  2D scatter plot:
        chart = appendChart(builder, ChartType.SCATTER, 500.0, 300.0);

        // Each series will need two decimal arrays of equal length.
        // The first array contains X-values, and the second contains corresponding Y-values
        // of data points on the chart's graph.
        chart.getSeries().add("Series 1",
                              new double[]{3.1, 3.5, 6.3, 4.1, 2.2, 8.3, 1.2, 3.6},
                              new double[]{3.1, 6.3, 4.6, 0.9, 8.5, 4.2, 2.3, 9.9});
        chart.getSeries().add("Series 2",
                              new double[]{2.6, 7.3, 4.5, 6.6, 2.1, 9.3, 0.7, 3.3},
                              new double[]{7.1, 6.6, 3.5, 7.8, 7.7, 9.5, 1.3, 4.6});

        Assert.assertEquals(ChartAxisType.VALUE, chart.getAxisX().getType());
        Assert.assertEquals(ChartAxisType.VALUE, chart.getAxisY().getType());

        // 4 -  Bubble chart:
        chart = appendChart(builder, ChartType.BUBBLE, 500.0, 300.0);

        // Each series will need three decimal arrays of equal length.
        // The first array contains X-values, the second contains corresponding Y-values,
        // and the third contains diameters for each of the graph's data points.
        chart.getSeries().add("Series 1",
                              new double[]{1.1, 5.0, 9.8},
                              new double[]{1.2, 4.9, 9.9},
                              new double[]{2.0, 4.0, 8.0});

        doc.save(getArtifactsDir() + "Charts.ChartSeriesCollection.docx");
    }

    /// <summary>
    /// Insert a chart using a document builder of a specified ChartType, width and height, and remove its demo data.
    /// </summary>
    private static Chart appendChart(DocumentBuilder builder, */
/*ChartType*//*
int chartType, double width, double height) throws Exception {
        Shape chartShape = builder.insertChart(chartType, width, height);
        Chart chart = chartShape.getChart();
        chart.getSeries().clear();
        Assert.assertEquals(0, chart.getSeries().getCount()); //ExSkip

        return chart;
    }
    //ExEnd

    @Test
    public void chartSeriesCollectionModify() throws Exception {
        //ExStart
        //ExFor:ChartSeriesCollection
        //ExFor:ChartSeriesCollection.Clear
        //ExFor:ChartSeriesCollection.Count
        //ExFor:ChartSeriesCollection.GetEnumerator
        //ExFor:ChartSeriesCollection.Item(Int32)
        //ExFor:ChartSeriesCollection.RemoveAt(Int32)
        //ExSummary:Shows how to add and remove series data in a chart.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert a column chart that will contain three series of demo data by default.
        Shape chartShape = builder.insertChart(ChartType.COLUMN, 400.0, 300.0);
        Chart chart = chartShape.getChart();

        // Each series has four decimal values: one for each of the four categories.
        // Four clusters of three columns will represent this data.
        ChartSeriesCollection chartData = chart.getSeries();

        Assert.assertEquals(3, chartData.getCount());

        // Print the name of every series in the chart.
        Iterator<ChartSeries> enumerator = chart.getSeries().iterator();
        while (enumerator.hasNext()) {
            System.out.println(enumerator.next().getName());
        }

        // These are the names of the categories in the chart.
        String[] categories = {"Category 1", "Category 2", "Category 3", "Category 4"};

        // We can add a series with new values for existing categories.
        // This chart will now contain four clusters of four columns.
        chart.getSeries().add("Series 4", categories, new double[]{4.4, 7.0, 3.5, 2.1});
        Assert.assertEquals(4, chartData.getCount()); //ExSkip
        Assert.assertEquals("Series 4", chartData.get(3).getName()); //ExSkip

        // A chart series can also be removed by index, like this.
        // This will remove one of the three demo series that came with the chart.
        chartData.removeAt(2);

        Assert.assertFalse(IterableUtils.matchesAny(chartData, s -> s.getName() == "Series 3"));
        Assert.assertEquals(3, chartData.getCount()); //ExSkip
        Assert.assertEquals("Series 4", chartData.get(2).getName()); //ExSkip

        // We can also clear all the chart's data at once with this method.
        // When creating a new chart, this is the way to wipe all the demo data
        // before we can begin working on a blank chart.
        chartData.clear();
        Assert.assertEquals(0, chartData.getCount()); //ExSkip

        //ExEnd
    }

    @Test
    public void axisScaling() throws Exception {
        //ExStart
        //ExFor:AxisScaleType
        //ExFor:AxisScaling
        //ExFor:AxisScaling.LogBase
        //ExFor:AxisScaling.Type
        //ExSummary:Shows how to apply logarithmic scaling to a chart axis.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape chartShape = builder.insertChart(ChartType.SCATTER, 450.0, 300.0);
        Chart chart = chartShape.getChart();

        // Clear the chart's demo data series to start with a clean chart.
        chart.getSeries().clear();

        // Insert a series with X/Y coordinates for five points.
        chart.getSeries().add("Series 1",
                              new double[]{1.0, 2.0, 3.0, 4.0, 5.0},
                              new double[]{1.0, 20.0, 400.0, 8000.0, 160000.0});

        // The scaling of the X-axis is linear by default,
        // displaying evenly incrementing values that cover our X-value range (0, 1, 2, 3...).
        // A linear axis is not ideal for our Y-values
        // since the points with the smaller Y-values will be harder to read.
        // A logarithmic scaling with a base of 20 (1, 20, 400, 8000...)
        // will spread the plotted points, allowing us to read their values on the chart more easily.
        chart.getAxisY().getScaling().setType(AxisScaleType.LOGARITHMIC);
        chart.getAxisY().getScaling().setLogBase(20.0);

        doc.save(getArtifactsDir() + "Charts.AxisScaling.docx");
        //ExEnd

        doc = new Document(getArtifactsDir() + "Charts.AxisScaling.docx");
        chart = ((Shape) doc.getChild(NodeType.SHAPE, 0, true)).getChart();

        Assert.assertEquals(AxisScaleType.LINEAR, chart.getAxisX().getScaling().getType());
        Assert.assertEquals(AxisScaleType.LOGARITHMIC, chart.getAxisY().getScaling().getType());
        Assert.assertEquals(20.0d, chart.getAxisY().getScaling().getLogBase());
    }

    @Test
    public void axisBound() throws Exception {
        //ExStart
        //ExFor:AxisBound.#ctor
        //ExFor:AxisBound.IsAuto
        //ExFor:AxisBound.Value
        //ExFor:AxisBound.ValueAsDate
        //ExSummary:Shows how to set custom axis bounds.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape chartShape = builder.insertChart(ChartType.SCATTER, 450.0, 300.0);
        Chart chart = chartShape.getChart();

        // Clear the chart's demo data series to start with a clean chart.
        chart.getSeries().clear();

        // Add a series with two decimal arrays. The first array contains the X-values,
        // and the second contains corresponding Y-values for points in the scatter chart.
        chart.getSeries().add("Series 1",
                              new double[]{1.1, 5.4, 7.9, 3.5, 2.1, 9.7},
                              new double[]{2.1, 0.3, 0.6, 3.3, 1.4, 1.9});

        // By default, default scaling is applied to the graph's X and Y-axes,
        // so that both their ranges are big enough to encompass every X and Y-value of every series.
        Assert.assertTrue(chart.getAxisX().getScaling().getMinimum().isAuto());

        // We can define our own axis bounds.
        // In this case, we will make both the X and Y-axis rulers show a range of 0 to 10.
        chart.getAxisX().getScaling().setMinimum(new AxisBound(0.0));
        chart.getAxisX().getScaling().setMaximum(new AxisBound(10.0));
        chart.getAxisY().getScaling().setMinimum(new AxisBound(0.0));
        chart.getAxisY().getScaling().setMaximum(new AxisBound(10.0));

        Assert.assertFalse(chart.getAxisX().getScaling().getMinimum().isAuto());
        Assert.assertFalse(chart.getAxisY().getScaling().getMinimum().isAuto());

        // Create a line chart with a series requiring a range of dates on the X-axis, and decimal values for the Y-axis.
        chartShape = builder.insertChart(ChartType.LINE, 450.0, 300.0);
        chart = chartShape.getChart();
        chart.getSeries().clear();

        Date[] dates = {DocumentHelper.createDate(1973, 5, 11),
                        DocumentHelper.createDate(1981, 2, 4),
                        DocumentHelper.createDate(1985, 9, 23),
                        DocumentHelper.createDate(1989, 6, 28),
                        DocumentHelper.createDate(1994, 12, 15)
        };

        chart.getSeries().add("Series 1", dates, new double[]{3.0, 4.7, 5.9, 7.1, 8.9});

        // We can set axis bounds in the form of dates as well, limiting the chart to a period.
        // Setting the range to 1980-1990 will omit the two of the series values
        // that are outside of the range from the graph.

        Date datetimeMin = DocumentHelper.createDate(1980, 1, 1);
        chart.getAxisX().getScaling().setMinimum(new AxisBound(datetimeMin));
        Date datetimeMax = DocumentHelper.createDate(1980, 1, 1);
        chart.getAxisX().getScaling().setMaximum(new AxisBound(datetimeMax));

        doc.save(getArtifactsDir() + "Charts.AxisBound.docx");
        //ExEnd

        doc = new Document(getArtifactsDir() + "Charts.AxisBound.docx");
        chart = ((Shape) doc.getChild(NodeType.SHAPE, 0, true)).getChart();

        Assert.assertFalse(chart.getAxisX().getScaling().getMinimum().isAuto());
        Assert.assertEquals(0.0d, chart.getAxisX().getScaling().getMinimum().getValue());
        Assert.assertEquals(10.0d, chart.getAxisX().getScaling().getMaximum().getValue());

        Assert.assertFalse(chart.getAxisY().getScaling().getMinimum().isAuto());
        Assert.assertEquals(0.0d, chart.getAxisY().getScaling().getMinimum().getValue());
        Assert.assertEquals(10.0d, chart.getAxisY().getScaling().getMaximum().getValue());

        chart = ((Shape) doc.getChild(NodeType.SHAPE, 1, true)).getChart();

        Assert.assertFalse(chart.getAxisX().getScaling().getMinimum().isAuto());
        Assert.assertEquals(datetimeMin, chart.getAxisX().getScaling().getMinimum().getValueAsDate());
        Assert.assertEquals(datetimeMax, chart.getAxisX().getScaling().getMaximum().getValueAsDate());

        Assert.assertTrue(chart.getAxisY().getScaling().getMinimum().isAuto());
    }

    @Test
    public void chartLegend() throws Exception {
        //ExStart
        //ExFor:Chart.Legend
        //ExFor:ChartLegend
        //ExFor:ChartLegend.Overlay
        //ExFor:ChartLegend.Position
        //ExFor:LegendPosition
        //ExSummary:Shows how to edit the appearance of a chart's legend.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.LINE, 450.0, 300.0);
        Chart chart = shape.getChart();

        Assert.assertEquals(3, chart.getSeries().getCount());
        Assert.assertEquals("Series 1", chart.getSeries().get(0).getName());
        Assert.assertEquals("Series 2", chart.getSeries().get(1).getName());
        Assert.assertEquals("Series 3", chart.getSeries().get(2).getName());

        // Move the chart's legend to the top right corner.
        ChartLegend legend = chart.getLegend();
        legend.setPosition(LegendPosition.TOP_RIGHT);

        // Give other chart elements, such as the graph, more room by allowing them to overlap the legend.
        legend.setOverlay(true);

        doc.save(getArtifactsDir() + "Charts.ChartLegend.docx");
        //ExEnd

        doc = new Document(getArtifactsDir() + "Charts.ChartLegend.docx");

        legend = ((Shape) doc.getChild(NodeType.SHAPE, 0, true)).getChart().getLegend();

        Assert.assertTrue(legend.getOverlay());
        Assert.assertEquals(LegendPosition.TOP_RIGHT, legend.getPosition());
    }

    @Test
    public void axisCross() throws Exception {
        //ExStart
        //ExFor:ChartAxis.AxisBetweenCategories
        //ExFor:ChartAxis.CrossesAt
        //ExSummary:Shows how to get a graph axis to cross at a custom location.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 450.0, 250.0);
        Chart chart = shape.getChart();

        Assert.assertEquals(3, chart.getSeries().getCount());
        Assert.assertEquals("Series 1", chart.getSeries().get(0).getName());
        Assert.assertEquals("Series 2", chart.getSeries().get(1).getName());
        Assert.assertEquals("Series 3", chart.getSeries().get(2).getName());

        // For column charts, the Y-axis crosses at zero by default,
        // which means that columns for all values below zero point down to represent negative values.
        // We can set a different value for the Y-axis crossing. In this case, we will set it to 3.
        ChartAxis axis = chart.getAxisX();
        axis.setCrosses(AxisCrosses.CUSTOM);
        axis.setCrossesAt(3.0);
        axis.setAxisBetweenCategories(true);

        doc.save(getArtifactsDir() + "Charts.AxisCross.docx");
        //ExEnd

        doc = new Document(getArtifactsDir() + "Charts.AxisCross.docx");
        axis = ((Shape) doc.getChild(NodeType.SHAPE, 0, true)).getChart().getAxisX();

        Assert.assertTrue(axis.getAxisBetweenCategories());
        Assert.assertEquals(AxisCrosses.CUSTOM, axis.getCrosses());
        Assert.assertEquals(3.0, axis.getCrossesAt());
    }

    @Test
    public void axisDisplayUnit() throws Exception {
        //ExStart
        //ExFor:AxisBuiltInUnit
        //ExFor:ChartAxis.DisplayUnit
        //ExFor:ChartAxis.MajorUnitIsAuto
        //ExFor:ChartAxis.MajorUnitScale
        //ExFor:ChartAxis.MinorUnitIsAuto
        //ExFor:ChartAxis.MinorUnitScale
        //ExFor:AxisDisplayUnit
        //ExFor:AxisDisplayUnit.CustomUnit
        //ExFor:AxisDisplayUnit.Unit
        //ExFor:AxisDisplayUnit.Document
        //ExSummary:Shows how to manipulate the tick marks and displayed values of a chart axis.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.SCATTER, 450.0, 250.0);
        Chart chart = shape.getChart();

        Assert.assertEquals(1, chart.getSeries().getCount());
        Assert.assertEquals("Y-Values", chart.getSeries().get(0).getName());

        // Set the minor tick marks of the Y-axis to point away from the plot area,
        // and the major tick marks to cross the axis.
        ChartAxis axis = chart.getAxisY();
        axis.setMajorTickMark(AxisTickMark.CROSS);
        axis.setMinorTickMark(AxisTickMark.OUTSIDE);

        // Set they Y-axis to show a major tick every 10 units, and a minor tick every 1 unit.
        axis.setMajorUnit(10.0);
        axis.setMinorUnit(1.0);

        // Set the Y-axis bounds to -10 and 20.
        // This Y-axis will now display 4 major tick marks and 27 minor tick marks.
        axis.getScaling().setMinimum(new AxisBound(-10));
        axis.getScaling().setMaximum(new AxisBound(20.0));

        // For the X-axis, set the major tick marks at every 10 units,
        // every minor tick mark at 2.5 units.
        axis = chart.getAxisX();
        axis.setMajorUnit(10.0);
        axis.setMinorUnit(2.5);

        // Configure both types of tick marks to appear inside the graph plot area.
        axis.setMajorTickMark(AxisTickMark.INSIDE);
        axis.setMinorTickMark(AxisTickMark.INSIDE);

        // Set the X-axis bounds so that the X-axis spans 5 major tick marks and 12 minor tick marks.
        axis.getScaling().setMinimum(new AxisBound(-10));
        axis.getScaling().setMaximum(new AxisBound(30.0));
        axis.getTickLabels().setAlignment(ParagraphAlignment.RIGHT);

        Assert.assertEquals(1, axis.getTickLabels().getSpacing());
        Assert.assertEquals(doc, axis.getDisplayUnit().getDocument());

        // Set the tick labels to display their value in millions.
        axis.getDisplayUnit().setUnit(AxisBuiltInUnit.MILLIONS);

        // We can set a more specific value by which tick labels will display their values.
        // This statement is equivalent to the one above.
        axis.getDisplayUnit().setCustomUnit(1000000.0);
        Assert.assertEquals(AxisBuiltInUnit.CUSTOM, axis.getDisplayUnit().getUnit()); //ExSkip

        doc.save(getArtifactsDir() + "Charts.AxisDisplayUnit.docx");
        //ExEnd

        doc = new Document(getArtifactsDir() + "Charts.AxisDisplayUnit.docx");
        shape = (Shape) doc.getChild(NodeType.SHAPE, 0, true);

        Assert.assertEquals(450.0d, shape.getWidth());
        Assert.assertEquals(250.0d, shape.getHeight());

        axis = shape.getChart().getAxisX();

        Assert.assertEquals(AxisTickMark.INSIDE, axis.getMajorTickMark());
        Assert.assertEquals(AxisTickMark.INSIDE, axis.getMinorTickMark());
        Assert.assertEquals(10.0d, axis.getMajorUnit());
        Assert.assertEquals(-10.0d, axis.getScaling().getMinimum().getValue());
        Assert.assertEquals(30.0d, axis.getScaling().getMaximum().getValue());
        Assert.assertEquals(1, axis.getTickLabels().getSpacing());
        Assert.assertEquals(ParagraphAlignment.RIGHT, axis.getTickLabels().getAlignment());
        Assert.assertEquals(AxisBuiltInUnit.CUSTOM, axis.getDisplayUnit().getUnit());
        Assert.assertEquals(1000000.0d, axis.getDisplayUnit().getCustomUnit());

        axis = shape.getChart().getAxisY();

        Assert.assertEquals(AxisTickMark.CROSS, axis.getMajorTickMark());
        Assert.assertEquals(AxisTickMark.OUTSIDE, axis.getMinorTickMark());
        Assert.assertEquals(10.0d, axis.getMajorUnit());
        Assert.assertEquals(1.0d, axis.getMinorUnit());
        Assert.assertEquals(-10.0d, axis.getScaling().getMinimum().getValue());
        Assert.assertEquals(20.0d, axis.getScaling().getMaximum().getValue());
    }

    @Test
    public void markerFormatting() throws Exception
    {
        //ExStart
        //ExFor:ChartDataPoint.Marker
        //ExFor:ChartMarker.Format
        //ExFor:ChartFormat.Fill
        //ExFor:ChartSeries.Marker
        //ExFor:ChartFormat.Stroke
        //ExFor:Stroke.ForeColor
        //ExFor:Stroke.BackColor
        //ExFor:Stroke.Visible
        //ExFor:Stroke.Transparency
        //ExFor:PresetTexture
        //ExFor:Fill.PresetTextured(PresetTexture)
        //ExSummary:Show how to set marker formatting.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.SCATTER, 432.0, 252.0);
        Chart chart = shape.getChart();

        // Delete default generated series.
        chart.getSeries().clear();
        ChartSeries series = chart.getSeries().add("AW Series 1", new double[] { 0.7, 1.8, 2.6, 3.9 },
                                                   new double[] { 2.7, 3.2, 0.8, 1.7 });

        // Set marker formatting.
        series.getMarker().setSize(40);
        series.getMarker().setSymbol(MarkerSymbol.SQUARE);
        ChartDataPointCollection dataPoints = series.getDataPoints();
        dataPoints.get(0).getMarker().getFormat().getFill().presetTextured(PresetTexture.DENIM);
        dataPoints.get(0).getMarker().getFormat().getStroke().setForeColor(Color.YELLOW);
        dataPoints.get(0).getMarker().getFormat().getStroke().setBackColor(Color.RED);
        dataPoints.get(1).getMarker().getFormat().getFill().presetTextured(PresetTexture.WATER_DROPLETS);
        dataPoints.get(1).getMarker().getFormat().getStroke().setForeColor(Color.YELLOW);
        dataPoints.get(1).getMarker().getFormat().getStroke().setVisible(false);
        dataPoints.get(2).getMarker().getFormat().getFill().presetTextured(PresetTexture.GREEN_MARBLE);
        dataPoints.get(2).getMarker().getFormat().getStroke().setForeColor(Color.YELLOW);
        dataPoints.get(3).getMarker().getFormat().getFill().presetTextured(PresetTexture.OAK);
        dataPoints.get(3).getMarker().getFormat().getStroke().setForeColor(Color.YELLOW);
        dataPoints.get(3).getMarker().getFormat().getStroke().setTransparency(0.5);

        doc.save(getArtifactsDir() + "Charts.MarkerFormatting.docx");
        //ExEnd
    }

    @Test
    public void seriesColor() throws Exception
    {
        //ExStart
        //ExFor:ChartSeries.Format
        //ExSummary:Sows how to set series color.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);

        Chart chart = shape.getChart();
        ChartSeriesCollection seriesColl = chart.getSeries();

        // Delete default generated series.
        seriesColl.clear();

        // Create category names array.
        String[] categories = new String[] { "Category 1", "Category 2" };

        // Adding new series. Value and category arrays must be the same size.
        ChartSeries series1 = seriesColl.add("Series 1", categories, new double[] { 1.0, 2.0 });
        ChartSeries series2 = seriesColl.add("Series 2", categories, new double[] { 3.0, 4.0 });
        ChartSeries series3 = seriesColl.add("Series 3", categories, new double[] { 5.0, 6.0 });

        // Set series color.
        series1.getFormat().getFill().setForeColor(Color.RED);
        series2.getFormat().getFill().setForeColor(Color.YELLOW);
        series3.getFormat().getFill().setForeColor(Color.BLUE);

        doc.save(getArtifactsDir() + "Charts.SeriesColor.docx");
        //ExEnd
    }

    @Test
    public void dataPointsFormatting() throws Exception
    {
        //ExStart
        //ExFor:ChartDataPoint.Format
        //ExSummary:Shows how to set individual formatting for categories of a column chart.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart = shape.getChart();

        // Delete default generated series.
        chart.getSeries().clear();

        // Adding new series.
        ChartSeries series = chart.getSeries().add("Series 1",
                                                   new String[] { "Category 1", "Category 2", "Category 3", "Category 4" },
                                                   new double[] { 1.0, 2.0, 3.0, 4.0 });

        // Set column formatting.
        ChartDataPointCollection dataPoints = series.getDataPoints();
        dataPoints.get(0).getFormat().getFill().presetTextured(PresetTexture.DENIM);
        dataPoints.get(1).getFormat().getFill().setForeColor(Color.RED);
        dataPoints.get(2).getFormat().getFill().setForeColor(Color.YELLOW);
        dataPoints.get(3).getFormat().getFill().setForeColor(Color.BLUE);

        doc.save(getArtifactsDir() + "Charts.DataPointsFormatting.docx");
        //ExEnd
    }

    @Test
    public void legendEntries() throws Exception
    {
        //ExStart
        //ExFor:ChartLegendEntryCollection
        //ExFor:ChartLegend.LegendEntries
        //ExFor:ChartLegendEntry.IsHidden
        //ExSummary:Shows how to work with a legend entry for chart series.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);

        Chart chart = shape.getChart();
        ChartSeriesCollection series = chart.getSeries();
        series.clear();

        String[] categories = new String[] { "AW Category 1", "AW Category 2" };

        ChartSeries series1 = series.add("Series 1", categories, new double[] { 1.0, 2.0 });
        series.add("Series 2", categories, new double[] { 3.0, 4.0 });
        series.add("Series 3", categories, new double[] { 5.0, 6.0 });
        series.add("Series 4", categories, new double[] { 0.0, 0.0 });

        ChartLegendEntryCollection legendEntries = chart.getLegend().getLegendEntries();
        legendEntries.get(3).isHidden(true);

        doc.save(getArtifactsDir() + "Charts.LegendEntries.docx");
        //ExEnd
    }

    @Test
    public void legendFont() throws Exception
    {
        //ExStart:LegendFont
        //GistId:66dd22f0854357e394a013b536e2181b
        //ExFor:ChartLegendEntry
        //ExFor:ChartLegendEntry.Font
        //ExFor:ChartLegend.Font
        //ExFor:ChartSeries.LegendEntry
        //ExSummary:Shows how to work with a legend font.
        Document doc = new Document(getMyDir() + "Reporting engine template - Chart series (Java).docx");
        Chart chart = ((Shape)doc.getChild(NodeType.SHAPE, 0, true)).getChart();

        ChartLegend chartLegend = chart.getLegend();
        // Set default font size all legend entries.
        chartLegend.getFont().setSize(14.0);
        // Change font for specific legend entry.
        chartLegend.getLegendEntries().get(1).getFont().setItalic(true);
        chartLegend.getLegendEntries().get(1).getFont().setSize(12.0);
        // Get legend entry for chart series.
        ChartLegendEntry legendEntry = chart.getSeries().get(0).getLegendEntry();

        doc.save(getArtifactsDir() + "Charts.LegendFont.docx");
        //ExEnd:LegendFont
    }

    @Test
    public void removeSpecificChartSeries() throws Exception
    {
        //ExStart
        //ExFor:ChartSeries.SeriesType
        //ExFor:ChartSeriesType
        //ExSummary:Shows how to remove specific chart serie.
        Document doc = new Document(getMyDir() + "Reporting engine template - Chart series (Java).docx");
        Chart chart = ((Shape)doc.getChild(NodeType.SHAPE, 0, true)).getChart();

        // Remove all series of the Column type.
        for (int i = chart.getSeries().getCount() - 1; i >= 0; i--)
        {
            if (chart.getSeries().get(i).getSeriesType() == ChartSeriesType.COLUMN)
                chart.getSeries().removeAt(i);
        }

        chart.getSeries().add(
                "Aspose Series",
                new String[] { "Category 1", "Category 2", "Category 3", "Category 4" },
                new double[] { 5.6, 7.1, 2.9, 8.9 });

        doc.save(getArtifactsDir() + "Charts.RemoveSpecificChartSeries.docx");
        //ExEnd
    }

    @Test
    public void populateChartWithData() throws Exception
    {
        //ExStart
        //ExFor:ChartXValue
        //ExFor:ChartXValue.FromDouble(Double)
        //ExFor:ChartYValue.FromDouble(Double)
        //ExFor:ChartSeries.Add(ChartXValue)
        //ExFor:ChartSeries.Add(ChartXValue, ChartYValue)
        //ExFor:ChartSeries.Add(ChartXValue, ChartYValue, double)
        //ExFor:ChartSeries.ClearValues
        //ExFor:ChartSeries.Clear
        //ExSummary:Shows how to populate chart series with data.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart = shape.getChart();
        ChartSeries series1 = chart.getSeries().get(0);

        // Clear X and Y values of the first series.
        series1.clearValues();

        // Populate the series with data.
        series1.add(ChartXValue.fromDouble(3.0), ChartYValue.fromDouble(10.0), 10.0);
        series1.add(ChartXValue.fromDouble(5.0), ChartYValue.fromDouble(5.0));
        series1.add(ChartXValue.fromDouble(7.0), ChartYValue.fromDouble(11.0));
        series1.add(ChartXValue.fromDouble(9.0));

        ChartSeries series2 = chart.getSeries().get(1);

        // Clear X and Y values of the second series.
        series2.clear();

        // Populate the series with data.
        series2.add(ChartXValue.fromDouble(2.0), ChartYValue.fromDouble(4.0));
        series2.add(ChartXValue.fromDouble(4.0), ChartYValue.fromDouble(7.0));
        series2.add(ChartXValue.fromDouble(6.0), ChartYValue.fromDouble(14.0));
        series2.add(ChartXValue.fromDouble(8.0), ChartYValue.fromDouble(7.0));

        doc.save(getArtifactsDir() + "Charts.PopulateChartWithData.docx");
        //ExEnd
    }

    @Test
    public void getChartSeriesData() throws Exception
    {
        //ExStart
        //ExFor:ChartXValueCollection
        //ExFor:ChartYValueCollection
        //ExSummary:Shows how to get chart series data.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder();

        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart = shape.getChart();
        ChartSeries series = chart.getSeries().get(0);

        double minValue = Double.MAX_VALUE;
        int minValueIndex = 0;
        double maxValue = -Double.MAX_VALUE;
        int maxValueIndex = 0;

        for (int i = 0; i < series.getYValues().getCount(); i++)
        {
            // Clear individual format of all data points.
            // Data points and data values are one-to-one in column charts.
            series.getDataPoints().get(i).clearFormat();

            // Get Y value.
            double yValue = series.getYValues().get(i).getDoubleValue();

            if (yValue < minValue)
            {
                minValue = yValue;
                minValueIndex = i;
            }

            if (yValue > maxValue)
            {
                maxValue = yValue;
                maxValueIndex = i;
            }
        }

        // Change colors of the max and min values.
        series.getDataPoints().get(minValueIndex).getFormat().getFill().setForeColor(Color.RED);
        series.getDataPoints().get(maxValueIndex).getFormat().getFill().setForeColor(Color.GREEN);

        doc.save(getArtifactsDir() + "Charts.GetChartSeriesData.docx");
        //ExEnd
    }

    @Test
    public void chartDataValues() throws Exception
    {
        //ExStart
        //ExFor:ChartXValue.FromString(String)
        //ExFor:ChartSeries.Remove(Int32)
        //ExFor:ChartSeries.Add(ChartXValue, ChartYValue)
        //ExSummary:Shows how to add/remove chart data values.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder();

        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart = shape.getChart();
        ChartSeries department1Series = chart.getSeries().get(0);
        ChartSeries department2Series = chart.getSeries().get(1);

        // Remove the first value in the both series.
        department1Series.remove(0);
        department2Series.remove(0);

        // Add new values to the both series.
        ChartXValue newXCategory = ChartXValue.fromString("Q1, 2023");
        department1Series.add(newXCategory, ChartYValue.fromDouble(10.3));
        department2Series.add(newXCategory, ChartYValue.fromDouble(5.7));

        doc.save(getArtifactsDir() + "Charts.ChartDataValues.docx");
        //ExEnd
    }

    @Test
    public void formatDataLables() throws Exception
    {
        //ExStart
        //ExFor:ChartDataLabelCollection.Format
        //ExFor:ChartFormat.ShapeType
        //ExFor:ChartShapeType
        //ExSummary:Shows how to set fill, stroke and callout formatting for chart data labels.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart = shape.getChart();

        // Delete default generated series.
        chart.getSeries().clear();

        // Add new series.
        ChartSeries series = chart.getSeries().add("AW Series 1",
                                                   new String[] { "AW Category 1", "AW Category 2", "AW Category 3", "AW Category 4" },
                                                   new double[] { 100.0, 200.0, 300.0, 400.0 });

        // Show data labels.
        series.hasDataLabels(true);
        series.getDataLabels().setShowValue(true);

        // Format data labels as callouts.
        ChartFormat format = series.getDataLabels().getFormat();
        format.setShapeType(ChartShapeType.WEDGE_RECT_CALLOUT);
        format.getStroke().setColor(Color.lightGray);
        format.getFill().solid(Color.GREEN);
        series.getDataLabels().getFont().setColor(Color.YELLOW);

        // Change fill and stroke of an individual data label.
        ChartFormat labelFormat = series.getDataLabels().get(0).getFormat();
        labelFormat.getStroke().setColor(Color.BLUE);
        labelFormat.getFill().solid(Color.BLUE);

        doc.save(getArtifactsDir() + "Charts.FormatDataLables.docx");
        //ExEnd
    }

    @Test
    public void chartAxisTitle() throws Exception
    {
        //ExStart:ChartAxisTitle
        //GistId:6d898be16b796fcf7448ad3bfe18e51c
        //ExFor:ChartAxis.Title
        //ExFor:ChartAxisTitle
        //ExFor:ChartAxisTitle.Text
        //ExFor:ChartAxisTitle.Show
        //ExFor:ChartAxisTitle.Overlay
        //ExFor:ChartAxisTitle.Font
        //ExSummary:Shows how to set chart axis title.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);

        Chart chart = shape.getChart();
        ChartSeriesCollection seriesColl = chart.getSeries();
        // Delete default generated series.
        seriesColl.clear();

        seriesColl.add("AW Series 1", new String[] { "AW Category 1", "AW Category 2" }, new double[] { 1.0, 2.0 });

        // Set axis title.
        ChartAxisTitle chartAxisXTitle = chart.getAxisX().getTitle();
        chartAxisXTitle.setText("Categories");
        chartAxisXTitle.setShow(true);
        ChartAxisTitle chartAxisYTitle = chart.getAxisY().getTitle();
        chartAxisYTitle.setText("Values");
        chartAxisYTitle.setShow(true);
        chartAxisYTitle.setOverlay(true);
        chartAxisYTitle.getFont().setSize(12.0);
        chartAxisYTitle.getFont().setColor(Color.BLUE);

        doc.save(getArtifactsDir() + "Charts.ChartAxisTitle.docx");
        //ExEnd:ChartAxisTitle
    }

    @Test (dataProvider = "dataArraysWrongSizeDataProvider")
    public void dataArraysWrongSize(double[] seriesValue, Class exception) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.LINE, 500.0, 300.0);
        ChartSeriesCollection seriesColl = shape.getChart().getSeries();
        seriesColl.clear();

        String[] categories = { "Word", null, "Excel", "GoogleDocs", "Note", null };
        if (exception == null)
            seriesColl.add("AW Series", categories, seriesValue);
        else
            Assert.assertThrows(exception, () -> seriesColl.add("AW Series", categories, seriesValue));
    }

    @DataProvider(name = "dataArraysWrongSizeDataProvider")
    public static Object[][] dataArraysWrongSizeDataProvider() throws Exception
    {
        return new Object[][]
                {
                        {new double[] { 1.0, 2.0, Double.NaN, 4.0, 5.0, 6.0 }, null},
                        {new double[] { Double.NaN, 4.0, 5.0, Double.NaN, 7.0, 8.0 }, null},
                        {new double[] { Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, 9.0 }, null},
                        {new double[] { Double.NaN, 4.0, 5.0, Double.NaN, Double.NaN },  IllegalArgumentException.class},
                        {new double[] { Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN },  IllegalArgumentException.class},
                        };
    }

    @Test
    public void copyDataPointFormat() throws Exception
    {
        //ExStart:CopyDataPointFormat
        //GistId:6d898be16b796fcf7448ad3bfe18e51c
        //ExFor:ChartSeries.CopyFormatFrom(int)
        //ExFor:ChartDataPointCollection.HasDefaultFormat(int)
        //ExFor:ChartDataPointCollection.CopyFormat(int, int)
        //ExSummary:Shows how to copy data point format.
        Document doc = new Document(getMyDir() + "DataPoint format.docx");

        // Get the chart and series to update format.
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        ChartSeries series = shape.getChart().getSeries().get(0);
        ChartDataPointCollection dataPoints = series.getDataPoints();

        Assert.assertTrue(dataPoints.hasDefaultFormat(0));
        Assert.assertFalse(dataPoints.hasDefaultFormat(1));

        // Copy format of the data point with index 1 to the data point with index 2
        // so that the data point 2 looks the same as the data point 1.
        dataPoints.copyFormat(0, 1);

        Assert.assertTrue(dataPoints.hasDefaultFormat(0));
        Assert.assertTrue(dataPoints.hasDefaultFormat(1));

        // Copy format of the data point with index 0 to the series defaults so that all data points
        // in the series that have the default format look the same as the data point 0.
        series.copyFormatFrom(1);

        Assert.assertTrue(dataPoints.hasDefaultFormat(0));
        Assert.assertTrue(dataPoints.hasDefaultFormat(1));

        doc.save(getArtifactsDir() + "Charts.CopyDataPointFormat.docx");
        //ExEnd:CopyDataPointFormat
    }

    @Test
    public void resetDataPointFill() throws Exception
    {
        //ExStart:ResetDataPointFill
        //GistId:6d898be16b796fcf7448ad3bfe18e51c
        //ExFor:ChartFormat.IsDefined
        //ExFor:ChartFormat.SetDefaultFill
        //ExSummary:Shows how to reset the fill to the default value defined in the series.
        Document doc = new Document(getMyDir() + "DataPoint format.docx");

        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        ChartSeries series = shape.getChart().getSeries().get(0);
        ChartDataPoint dataPoint = series.getDataPoints().get(1);

        Assert.assertTrue(dataPoint.getFormat().isDefined());

        dataPoint.getFormat().setDefaultFill();

        doc.save(getArtifactsDir() + "Charts.ResetDataPointFill.docx");
        //ExEnd:ResetDataPointFill
    }

    @Test
    public void dataTable() throws Exception
    {
        //ExStart:DataTable
        //GistId:9c17d666c47318436785490829a3984f
        //ExFor:Chart.DataTable
        //ExFor:ChartDataTable
        //ExFor:ChartDataTable.Show
        //ExFor:ChartDataTable.Format
        //ExFor:ChartDataTable.Font
        //ExFor:ChartDataTable.HasLegendKeys
        //ExFor:ChartDataTable.HasHorizontalBorder
        //ExFor:ChartDataTable.HasVerticalBorder
        //ExFor:ChartDataTable.HasOutlineBorder
        //ExSummary:Shows how to show data table with chart series data.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart = shape.getChart();

        ChartSeriesCollection series = chart.getSeries();
        series.clear();
        double[] xValues = new double[] { 2020.0, 2021.0, 2022.0, 2023.0 };
        series.add("Series1", xValues, new double[] { 5.0, 11.0, 2.0, 7.0 });
        series.add("Series2", xValues, new double[] { 6.0, 5.5, 7.0, 7.8 });
        series.add("Series3", xValues, new double[] { 10.0, 8.0, 7.0, 9.0 });

        ChartDataTable dataTable = chart.getDataTable();
        dataTable.setShow(true);

        dataTable.hasLegendKeys(false);
        dataTable.hasHorizontalBorder(false);
        dataTable.hasVerticalBorder(false);
        dataTable.hasOutlineBorder(false);

        dataTable.getFont().setItalic(true);
        dataTable.getFormat().getStroke().setWeight(1.0);
        dataTable.getFormat().getStroke().setDashStyle(DashStyle.SHORT_DOT);
        dataTable.getFormat().getStroke().setColor(Color.BLUE);

        doc.save(getArtifactsDir() + "Charts.DataTable.docx");
        //ExEnd:DataTable
    }

    @Test
    public void chartFormat() throws Exception
    {
        //ExStart:ChartFormat
        //GistId:31b7350f8d91d4b12eb43978940d566a
        //ExFor:ChartFormat
        //ExFor:Chart.Format
        //ExFor:ChartTitle.Format
        //ExFor:ChartAxisTitle.Format
        //ExFor:ChartLegend.Format
        //ExFor:Fill.Solid(Color)
        //ExSummary:Shows how to use chart formating.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart = shape.getChart();

        // Delete series generated by default.
        ChartSeriesCollection series = chart.getSeries();
        series.clear();

        String[] categories = new String[] { "Category 1", "Category 2" };
        series.add("Series 1", categories, new double[] { 1.0, 2.0 });
        series.add("Series 2", categories, new double[] { 3.0, 4.0 });

        // Format chart background.
        chart.getFormat().getFill().solid(Color.darkGray);

        // Hide axis tick labels.
        chart.getAxisX().getTickLabels().setPosition(AxisTickLabelPosition.NONE);
        chart.getAxisY().getTickLabels().setPosition(AxisTickLabelPosition.NONE);

        // Format chart title.
        chart.getTitle().getFormat().getFill().solid(Color.yellow);

        // Format axis title.
        chart.getAxisX().getTitle().setShow(true);
        chart.getAxisX().getTitle().getFormat().getFill().solid(Color.yellow);

        // Format legend.
        chart.getLegend().getFormat().getFill().solid(Color.yellow);

        doc.save(getArtifactsDir() + "Charts.ChartFormat.docx");
        //ExEnd:ChartFormat

        doc = new Document(getArtifactsDir() + "Charts.ChartFormat.docx");

        shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        chart = shape.getChart();

        Assert.assertEquals(Color.darkGray.getRGB(), chart.getFormat().getFill().getColor().getRGB());
        Assert.assertEquals(Color.yellow.getRGB(), chart.getTitle().getFormat().getFill().getColor().getRGB());
        Assert.assertEquals(Color.yellow.getRGB(), chart.getAxisX().getTitle().getFormat().getFill().getColor().getRGB());
        Assert.assertEquals(Color.yellow.getRGB(), chart.getLegend().getFormat().getFill().getColor().getRGB());
    }

    @Test
    public void secondaryAxis() throws Exception
    {
        //ExStart:SecondaryAxis
        //GistId:f99d87e10ab87a581c52206321d8b617
        //ExFor:ChartSeriesGroup
        //ExFor:ChartSeriesGroup.SeriesType
        //ExFor:ChartSeriesGroup.AxisGroup
        //ExFor:ChartSeriesGroup.AxisX
        //ExFor:ChartSeriesGroup.AxisY
        //ExFor:ChartSeriesGroup.Series
        //ExFor:ChartSeriesGroupCollection
        //ExFor:ChartSeriesGroupCollection.Add(ChartSeriesType)
        //ExFor:AxisGroup
        //ExSummary:Shows how to work with the secondary axis of chart.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.LINE, 450.0, 250.0);
        Chart chart = shape.getChart();
        ChartSeriesCollection series = chart.getSeries();

        // Delete default generated series.
        series.clear();

        String[] categories = new String[] { "Category 1", "Category 2", "Category 3" };
        series.add("Series 1 of primary series group", categories, new double[] { 2.0, 3.0, 4.0 });
        series.add("Series 2 of primary series group", categories, new double[] { 5.0, 2.0, 3.0 });

        // Create an additional series group, also of the line type.
        ChartSeriesGroup newSeriesGroup = chart.getSeriesGroups().add(ChartSeriesType.LINE);
        // Specify the use of secondary axes for the new series group.
        newSeriesGroup.setAxisGroup(AxisGroup.SECONDARY);
        // Hide the secondary X axis.
        newSeriesGroup.getAxisX().setHidden(true);
        // Define title of the secondary Y axis.
        newSeriesGroup.getAxisY().getTitle().setShow(true);
        newSeriesGroup.getAxisY().getTitle().setText("Secondary Y axis");

        Assert.assertEquals(ChartSeriesType.LINE, newSeriesGroup.getSeriesType());

        // Add a series to the new series group.
        ChartSeries series3 =
                newSeriesGroup.getSeries().add("Series of secondary series group", categories, new double[] { 13.0, 11.0, 16.0 });
        series3.getFormat().getStroke().setWeight(3.5);

        doc.save(getArtifactsDir() + "Charts.SecondaryAxis.docx");
        //ExEnd:SecondaryAxis
    }

    @Test
    public void configureGapOverlap() throws Exception
    {
        //ExStart:ConfigureGapOverlap
        //GistId:f99d87e10ab87a581c52206321d8b617
        //ExFor:Chart.SeriesGroups
        //ExFor:ChartSeriesGroup.GapWidth
        //ExFor:ChartSeriesGroup.Overlap
        //ExSummary:Show how to configure gap width and overlap.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 450.0, 250.0);
        ChartSeriesGroup seriesGroup = shape.getChart().getSeriesGroups().get(0);

        // Set column gap width and overlap.
        seriesGroup.setGapWidth(450);
        seriesGroup.setOverlap(-75);

        doc.save(getArtifactsDir() + "Charts.ConfigureGapOverlap.docx");
        //ExEnd:ConfigureGapOverlap
    }

    @Test
    public void bubbleScale() throws Exception
    {
        //ExStart:BubbleScale
        //GistId:f99d87e10ab87a581c52206321d8b617
        //ExFor:ChartSeriesGroup.BubbleScale
        //ExSummary:Show how to set size of the bubbles.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert a bubble 3D chart.
        Shape shape = builder.insertChart(ChartType.BUBBLE_3_D, 450.0, 250.0);
        ChartSeriesGroup seriesGroup = shape.getChart().getSeriesGroups().get(0);

        // Set bubble scale to 200%.
        seriesGroup.setBubbleScale(200);

        doc.save(getArtifactsDir() + "Charts.BubbleScale.docx");
        //ExEnd:BubbleScale
    }

    @Test
    public void removeSecondaryAxis() throws Exception
    {
        //ExStart:RemoveSecondaryAxis
        //GistId:f99d87e10ab87a581c52206321d8b617
        //ExFor:ChartSeriesGroupCollection.Count
        //ExFor:ChartSeriesGroupCollection.Item(Int32)
        //ExFor:ChartSeriesGroupCollection.RemoveAt(Int32)
        //ExSummary:Show how to remove secondary axis.
        Document doc = new Document(getMyDir() + "Combo chart.docx");

        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        Chart chart = shape.getChart();
        ChartSeriesGroupCollection seriesGroups = chart.getSeriesGroups();

        // Find secondary axis and remove from the collection.
        for (int i = 0; i < seriesGroups.getCount(); i++)
            if (seriesGroups.get(i).getAxisGroup() == AxisGroup.SECONDARY)
                seriesGroups.removeAt(i);
        //ExEnd:RemoveSecondaryAxis
    }

    @Test
    public void treemapChart() throws Exception
    {
        //ExStart:TreemapChart
        //GistId:a76df4b18bee76d169e55cdf6af8129c
        //ExFor:ChartSeriesCollection.Add(String, ChartMultilevelValue[], double[])
        //ExFor:ChartMultilevelValue
        //ExFor:ChartMultilevelValue.#ctor(String, String, String)
        //ExFor:ChartMultilevelValue.#ctor(String, String)
        //ExFor:ChartMultilevelValue.#ctor(String)
        //ExSummary:Shows how to create treemap chart.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert a Treemap chart.
        Shape shape = builder.insertChart(ChartType.TREEMAP, 450.0, 280.0);
        Chart chart = shape.getChart();
        chart.getTitle().setText("World Population");

        // Delete default generated series.
        chart.getSeries().clear();

        // Add a series.
        ChartSeries series = chart.getSeries().add(
                "Population by Region",
                new ChartMultilevelValue[]
                        {
                                new ChartMultilevelValue("Asia", "China"),
                                new ChartMultilevelValue("Asia", "India"),
                                new ChartMultilevelValue("Asia", "Indonesia"),
                                new ChartMultilevelValue("Asia", "Pakistan"),
                                new ChartMultilevelValue("Asia", "Bangladesh"),
                                new ChartMultilevelValue("Asia", "Japan"),
                                new ChartMultilevelValue("Asia", "Philippines"),
                                new ChartMultilevelValue("Asia", "Other"),
                                new ChartMultilevelValue("Africa", "Nigeria"),
                                new ChartMultilevelValue("Africa", "Ethiopia"),
                                new ChartMultilevelValue("Africa", "Egypt"),
                                new ChartMultilevelValue("Africa", "Other"),
                                new ChartMultilevelValue("Europe", "Russia"),
                                new ChartMultilevelValue("Europe", "Germany"),
                                new ChartMultilevelValue("Europe", "Other"),
                                new ChartMultilevelValue("Latin America", "Brazil"),
                                new ChartMultilevelValue("Latin America", "Mexico"),
                                new ChartMultilevelValue("Latin America", "Other"),
                                new ChartMultilevelValue("Northern America", "United States"),
                                new ChartMultilevelValue("Northern America", "Other"),
                                new ChartMultilevelValue("Oceania")
                        },
                new double[]
                        {
                                1409670000.0, 1400744000.0, 279118866.0, 241499431.0, 169828911.0, 123930000.0, 112892781.0, 764000000.0,
                                223800000.0, 107334000.0, 105914499.0, 903000000.0,
                                146150789.0, 84607016.0, 516000000.0,
                                203080756.0, 129713690.0, 310000000.0,
                                335893238.0, 35000000.0,
                                42000000.0
                        });

        // Show data labels.
        series.hasDataLabels(true);
        series.getDataLabels().setShowValue(true);
        series.getDataLabels().setShowCategoryName(true);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        String thousandSeparator = Character.toString(symbols.getGroupingSeparator());
        series.getDataLabels().getNumberFormat().setFormatCode(String.format("#{0}0", thousandSeparator));

        doc.save(getArtifactsDir() + "Charts.Treemap.docx");
        //ExEnd:TreemapChart
    }

    @Test
    public void sunburstChart() throws Exception
    {
        //ExStart:SunburstChart
        //GistId:a76df4b18bee76d169e55cdf6af8129c
        //ExFor:ChartSeriesCollection.Add(String, ChartMultilevelValue[], double[])
        //ExSummary:Shows how to create sunburst chart.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert a Sunburst chart.
        Shape shape = builder.insertChart(ChartType.SUNBURST, 450.0, 450.0);
        Chart chart = shape.getChart();
        chart.getTitle().setText("Sales");

        // Delete default generated series.
        chart.getSeries().clear();

        // Add a series.
        ChartSeries series = chart.getSeries().add(
                "Sales",
                new ChartMultilevelValue[]
                        {
                                new ChartMultilevelValue("Sales - Europe", "UK", "London Dep."),
                                new ChartMultilevelValue("Sales - Europe", "UK", "Liverpool Dep."),
                                new ChartMultilevelValue("Sales - Europe", "UK", "Manchester Dep."),
                                new ChartMultilevelValue("Sales - Europe", "France", "Paris Dep."),
                                new ChartMultilevelValue("Sales - Europe", "France", "Lyon Dep."),
                                new ChartMultilevelValue("Sales - NA", "USA", "Denver Dep."),
                                new ChartMultilevelValue("Sales - NA", "USA", "Seattle Dep."),
                                new ChartMultilevelValue("Sales - NA", "USA", "Detroit Dep."),
                                new ChartMultilevelValue("Sales - NA", "USA", "Houston Dep."),
                                new ChartMultilevelValue("Sales - NA", "Canada", "Toronto Dep."),
                                new ChartMultilevelValue("Sales - NA", "Canada", "Montreal Dep."),
                                new ChartMultilevelValue("Sales - Oceania", "Australia", "Sydney Dep."),
                                new ChartMultilevelValue("Sales - Oceania", "New Zealand", "Auckland Dep.")
                        },
                new double[] { 1236.0, 851.0, 536.0, 468.0, 179.0, 527.0, 799.0, 1148.0, 921.0, 457.0, 482.0, 761.0, 694.0 });

        // Show data labels.
        series.hasDataLabels(true);
        series.getDataLabels().setShowValue(false);
        series.getDataLabels().setShowCategoryName(true);

        doc.save(getArtifactsDir() + "Charts.Sunburst.docx");
        //ExEnd:SunburstChart
    }

    @Test
    public void histogramChart() throws Exception
    {
        //ExStart:HistogramChart
        //GistId:a76df4b18bee76d169e55cdf6af8129c
        //ExFor:ChartSeriesCollection.Add(String, double[])
        //ExSummary:Shows how to create histogram chart.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert a Histogram chart.
        Shape shape = builder.insertChart(ChartType.HISTOGRAM, 450.0, 450.0);
        Chart chart = shape.getChart();
        chart.getTitle().setText("Avg Temperature since 1991");

        // Delete default generated series.
        chart.getSeries().clear();

        // Add a series.
        chart.getSeries().add(
                "Avg Temperature",
                new double[]
                        {
                                51.8, 53.6, 50.3, 54.7, 53.9, 54.3, 53.4, 52.9, 53.3, 53.7, 53.8, 52.0, 55.0, 52.1, 53.4,
                                53.8, 53.8, 51.9, 52.1, 52.7, 51.8, 56.6, 53.3, 55.6, 56.3, 56.2, 56.1, 56.2, 53.6, 55.7,
                                56.3, 55.9, 55.6
                        });

        doc.save(getArtifactsDir() + "Charts.Histogram.docx");
        //ExEnd:HistogramChart
    }

    @Test
    public void paretoChart() throws Exception
    {
        //ExStart:ParetoChart
        //GistId:a76df4b18bee76d169e55cdf6af8129c
        //ExFor:ChartSeriesCollection.Add(String, String[], double[])
        //ExSummary:Shows how to create pareto chart.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert a Pareto chart.
        Shape shape = builder.insertChart(ChartType.PARETO, 450.0, 450.0);
        Chart chart = shape.getChart();
        chart.getTitle().setText("Best-Selling Car");

        // Delete default generated series.
        chart.getSeries().clear();

        // Add a series.
        chart.getSeries().add(
                "Best-Selling Car",
                new String[] { "Tesla Model Y", "Toyota Corolla", "Toyota RAV4", "Ford F-Series", "Honda CR-V" },
                new double[] { 1.43, 0.91, 1.17, 0.98, 0.85 });

        doc.save(getArtifactsDir() + "Charts.Pareto.docx");
        //ExEnd:ParetoChart
    }

    @Test
    public void boxAndWhiskerChart() throws Exception
    {
        //ExStart:BoxAndWhiskerChart
        //GistId:a76df4b18bee76d169e55cdf6af8129c
        //ExFor:ChartSeriesCollection.Add(String, String[], double[])
        //ExSummary:Shows how to create box and whisker chart.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert a Box & Whisker chart.
        Shape shape = builder.insertChart(ChartType.BOX_AND_WHISKER, 450.0, 450.0);
        Chart chart = shape.getChart();
        chart.getTitle().setText("Points by Years");

        // Delete default generated series.
        chart.getSeries().clear();

        // Add a series.
        ChartSeries series = chart.getSeries().add(
                "Points by Years",
                new String[]
                        {
                                "WC", "WC", "WC", "WC", "WC", "WC", "WC", "WC", "WC", "WC",
                                "NR", "NR", "NR", "NR", "NR", "NR", "NR", "NR", "NR", "NR",
                                "NA", "NA", "NA", "NA", "NA", "NA", "NA", "NA", "NA", "NA"
                        },
                new double[]
                        {
                                91.0, 80.0, 100.0, 77.0, 90.0, 104.0, 105.0, 118.0, 120.0, 101.0,
                                114.0, 107.0, 110.0, 60.0, 79.0, 78.0, 77.0, 102.0, 101.0, 113.0,
                                94.0, 93.0, 84.0, 71.0, 80.0, 103.0, 80.0, 94.0, 100.0, 101.0
                        });

        // Show data labels.
        series.hasDataLabels(true);

        doc.save(getArtifactsDir() + "Charts.BoxAndWhisker.docx");
        //ExEnd:BoxAndWhiskerChart
    }

    @Test
    public void waterfallChart() throws Exception
    {
        //ExStart:WaterfallChart
        //GistId:a76df4b18bee76d169e55cdf6af8129c
        //ExFor:ChartSeriesCollection.Add(String, String[], double[], bool[])
        //ExSummary:Shows how to create waterfall chart.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert a Waterfall chart.
        Shape shape = builder.insertChart(ChartType.WATERFALL, 450.0, 450.0);
        Chart chart = shape.getChart();
        chart.getTitle().setText("New Zealand GDP");

        // Delete default generated series.
        chart.getSeries().clear();

        // Add a series.
        ChartSeries series = chart.getSeries().add(
                "New Zealand GDP",
                new String[] { "2018", "2019 growth", "2020 growth", "2020", "2021 growth", "2022 growth", "2022" },
                new double[] { 100.0, 0.57, -0.25, 100.32, 20.22, -2.92, 117.62 },
                new boolean[] { true, false, false, true, false, false, true });

        // Show data labels.
        series.hasDataLabels(true);

        doc.save(getArtifactsDir() + "Charts.Waterfall.docx");
        //ExEnd:WaterfallChart
    }

    @Test
    public void funnelChart() throws Exception
    {
        //ExStart:FunnelChart
        //GistId:a76df4b18bee76d169e55cdf6af8129c
        //ExFor:ChartSeriesCollection.Add(String, String[], double[])
        //ExSummary:Shows how to create funnel chart.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert a Funnel chart.
        Shape shape = builder.insertChart(ChartType.FUNNEL, 450.0, 450.0);
        Chart chart = shape.getChart();
        chart.getTitle().setText("Population by Age Group");

        // Delete default generated series.
        chart.getSeries().clear();

        // Add a series.
        ChartSeries series = chart.getSeries().add(
                "Population by Age Group",
                new String[] { "0-9", "10-19", "20-29", "30-39", "40-49", "50-59", "60-69", "70-79", "80-89", "90-" },
                new double[] { 0.121, 0.128, 0.132, 0.146, 0.124, 0.124, 0.111, 0.075, 0.032, 0.007 });

        // Show data labels.
        series.hasDataLabels(true);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        String decimalSeparator = Character.toString(symbols.getGroupingSeparator());
        series.getDataLabels().getNumberFormat().setFormatCode("0" + decimalSeparator + "0%");

        doc.save(getArtifactsDir() + "Charts.Funnel.docx");
        //ExEnd:FunnelChart
    }

    @Test
    public void labelOrientationRotation() throws Exception
    {
        //ExStart:LabelOrientationRotation
        //GistId:67585b023474b7f73b0066dd022cf938
        //ExFor:ChartDataLabelCollection.Orientation
        //ExFor:ChartDataLabelCollection.Rotation
        //ExFor:ChartDataLabel.Rotation
        //ExFor:ChartDataLabel.Orientation
        //ExFor:ShapeTextOrientation
        //ExSummary:Shows how to change orientation and rotation for data labels.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        ChartSeries series = shape.getChart().getSeries().get(0);
        ChartDataLabelCollection dataLabels = series.getDataLabels();

        // Show data labels.
        series.hasDataLabels(true);
        dataLabels.setShowValue(true);
        dataLabels.setShowCategoryName(true);

        // Define data label shape.
        dataLabels.getFormat().setShapeType(ChartShapeType.UP_ARROW);
        dataLabels.getFormat().getStroke().getFill().solid(Color.blue);

        // Set data label orientation and rotation for the entire series.
        dataLabels.setOrientation(ShapeTextOrientation.VERTICAL_FAR_EAST);
        dataLabels.setRotation(-45);

        // Change orientation and rotation of the first data label.
        dataLabels.get(0).setOrientation(ShapeTextOrientation.HORIZONTAL);
        dataLabels.get(0).setRotation(45);

        doc.save(getArtifactsDir() + "Charts.LabelOrientationRotation.docx");
        //ExEnd:LabelOrientationRotation
    }

    @Test
    public void tickLabelsOrientationRotation() throws Exception
    {
        //ExStart:TickLabelsOrientationRotation
        //GistId:0ede368e82d1e97d02e615a76923846b
        //ExFor:AxisTickLabels.Rotation
        //ExFor:AxisTickLabels.Orientation
        //ExSummary:Shows how to change orientation and rotation for axis tick labels.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert a column chart.
        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        AxisTickLabels xTickLabels = shape.getChart().getAxisX().getTickLabels();
        AxisTickLabels yTickLabels = shape.getChart().getAxisY().getTickLabels();

        // Set axis tick label orientation and rotation.
        xTickLabels.setOrientation(ShapeTextOrientation.VERTICAL_FAR_EAST);
        xTickLabels.setRotation(-30);
        yTickLabels.setOrientation(ShapeTextOrientation.HORIZONTAL);
        yTickLabels.setRotation(45);

        doc.save(getArtifactsDir() + "Charts.TickLabelsOrientationRotation.docx");
        //ExEnd:TickLabelsOrientationRotation
    }

    @Test
    public void doughnutChart() throws Exception
    {
        //ExStart:DoughnutChart
        //GistId:3f058a176ba0e9f656c60c6d60d757a1
        //ExFor:ChartSeriesGroup.DoughnutHoleSize
        //ExFor:ChartSeriesGroup.FirstSliceAngle
        //ExSummary:Shows how to create and format Doughnut chart.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.DOUGHNUT, 400.0, 400.0);
        Chart chart = shape.getChart();
        // Delete the default generated series.
        chart.getSeries().clear();

        String[] categories = new String[] { "Category 1", "Category 2", "Category 3" };
        chart.getSeries().add("Series 1", categories, new double[] { 4.0, 2.0, 5.0 });

        // Format the Doughnut chart.
        ChartSeriesGroup seriesGroup = chart.getSeriesGroups().get(0);
        seriesGroup.setDoughnutHoleSize(10);
        seriesGroup.setFirstSliceAngle(270);

        doc.save(getArtifactsDir() + "Charts.DoughnutChart.docx");
        //ExEnd:DoughnutChart
    }

    @Test
    public void pieOfPieChart() throws Exception
    {
        //ExStart:PieOfPieChart
        //GistId:3f058a176ba0e9f656c60c6d60d757a1
        //ExFor:ChartSeriesGroup.SecondSectionSize
        //ExSummary:Shows how to create and format pie of Pie chart.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.PIE_OF_PIE, 440.0, 300.0);
        Chart chart = shape.getChart();
        // Delete the default generated series.
        chart.getSeries().clear();

        String[] categories = new String[] { "Category 1", "Category 2", "Category 3", "Category 4" };
        chart.getSeries().add("Series 1", categories, new double[] { 11.0, 8.0, 4.0, 3.0 });

        // Format the Pie of Pie chart.
        ChartSeriesGroup seriesGroup = chart.getSeriesGroups().get(0);
        seriesGroup.setGapWidth(10);
        seriesGroup.setSecondSectionSize(77);

        doc.save(getArtifactsDir() + "Charts.PieOfPieChart.docx");
        //ExEnd:PieOfPieChart
    }

    @Test
    public void formatCode() throws Exception
    {
        //ExStart:FormatCode
        //GistId:72d57eeddb7fb342fd51b26e5fcf9642
        //ExFor:ChartXValueCollection.FormatCode
        //ExFor:ChartYValueCollection.FormatCode
        //ExFor:BubbleSizeCollection.FormatCode
        //ExFor:ChartSeries.BubbleSizes
        //ExFor:ChartSeries.XValues
        //ExFor:ChartSeries.YValues
        //ExSummary:Shows how to work with the format code of the chart data.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert a Bubble chart.
        Shape shape = builder.insertChart(ChartType.BUBBLE, 432.0, 252.0);
        Chart chart = shape.getChart();

        // Delete default generated series.
        chart.getSeries().clear();

        ChartSeries series = chart.getSeries().add(
                "Series1",
                new double[] { 1.0, 1.9, 2.45, 3.0 },
                new double[] { 1.0, -0.9, 1.82, 0.0 },
                new double[] { 2.0, 1.1, 2.95, 2.0 });

        // Show data labels.
        series.hasDataLabels(true);
        series.getDataLabels().setShowCategoryName(true);
        series.getDataLabels().setShowValue(true);
        series.getDataLabels().setShowBubbleSize(true);

        // Set data format codes.
        series.getXValues().setFormatCode("#,##0.0#");
        series.getYValues().setFormatCode("#,##0.0#;[Red]\\-#,##0.0#");
        series.getBubbleSizes().setFormatCode("#,##0.0#");

        doc.save(getArtifactsDir() + "Charts.FormatCode.docx");
        //ExEnd:FormatCode

        doc = new Document(getArtifactsDir() + "Charts.FormatCode.docx");
        shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        chart = shape.getChart();

        ChartSeriesCollection seriesCollection = chart.getSeries();
        for (ChartSeries seriesProperties : seriesCollection)
        {
            Assert.assertEquals("#,##0.0#", seriesProperties.getXValues().getFormatCode());
            Assert.assertEquals("#,##0.0#;[Red]\\-#,##0.0#", seriesProperties.getYValues().getFormatCode());
            Assert.assertEquals("#,##0.0#", seriesProperties.getBubbleSizes().getFormatCode());
        }
    }

    @Test
    public void dataLablePosition() throws Exception
    {
        //ExStart:DataLablePosition
        //GistId:93fefe5344a8337b931d0fed5c028225
        //ExFor:ChartDataLabelCollection.Position
        //ExFor:ChartDataLabel.Position
        //ExFor:ChartDataLabelPosition
        //ExSummary:Shows how to set the position of the data label.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert column chart.
        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart = shape.getChart();
        ChartSeriesCollection seriesColl = chart.getSeries();

        // Delete default generated series.
        seriesColl.clear();

        // Add series.
        ChartSeries series = seriesColl.add(
                "Series 1",
                new String[] { "Category 1", "Category 2", "Category 3" },
                new double[] { 4.0, 5.0, 6.0 });

        // Show data labels and set font color.
        series.hasDataLabels(true);
        ChartDataLabelCollection dataLabels = series.getDataLabels();
        dataLabels.setShowValue(true);
        dataLabels.getFont().setColor(Color.WHITE);

        // Set data label position.
        dataLabels.setPosition(ChartDataLabelPosition.INSIDE_BASE);
        dataLabels.get(0).setPosition(ChartDataLabelPosition.OUTSIDE_END);
        dataLabels.get(0).getFont().setColor(Color.RED);

        doc.save(getArtifactsDir() + "Charts.LabelPosition.docx");
        //ExEnd:DataLablePosition
    }

    @Test
    public void doughnutChartLabelPosition() throws Exception
    {
        //ExStart:DoughnutChartLabelPosition
        //GistId:93fefe5344a8337b931d0fed5c028225
        //ExFor:ChartDataLabel.Left
        //ExFor:ChartDataLabel.LeftMode
        //ExFor:ChartDataLabel.Top
        //ExFor:ChartDataLabel.TopMode
        //ExFor:ChartDataLabelLocationMode
        //ExSummary:Shows how to place data labels of doughnut chart outside doughnut.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        final int CHART_WIDTH = 432;
        final int CHART_HEIGHT = 252;
        Shape shape = builder.insertChart(ChartType.DOUGHNUT, CHART_WIDTH, CHART_HEIGHT);
        Chart chart = shape.getChart();
        ChartSeriesCollection seriesColl = chart.getSeries();
        // Delete default generated series.
        seriesColl.clear();

        // Hide the legend.
        chart.getLegend().setPosition(LegendPosition.NONE);

        // Generate data.
        final int DATA_LENGTH = 20;
        double totalValue = 0.0;
        String[] categories = new String[DATA_LENGTH];
        double[] values = new double[DATA_LENGTH];
        for (int i = 0; i < DATA_LENGTH; i++)
        {
            categories[i] = MessageFormat.format("Category {0}", i);
            values[i] = DATA_LENGTH - i;
            totalValue += values[i];
        }

        ChartSeries series = seriesColl.add("Series 1", categories, values);
        series.hasDataLabels(true);

        ChartDataLabelCollection dataLabels = series.getDataLabels();
        dataLabels.setShowValue(true);
        dataLabels.setShowLeaderLines(true);

        // The Position property cannot be used for doughnut charts. Let's place data labels using the Left and Top
        // properties around a circle outside of the chart doughnut.
        // The origin is in the upper left corner of the chart.

        final double TITLE_AREA_HEIGHT = 25.5; // This can be calculated using title text and font.
        final double DOUGHNUT_CENTER_Y = TITLE_AREA_HEIGHT + (CHART_HEIGHT - TITLE_AREA_HEIGHT) / 2.0;
        final double DOUGHNUT_CENTER_X = CHART_WIDTH / 2d;
        final double LABEL_HEIGHT = 16.5; // This can be calculated using label font.
        final double ONE_CHAR_LABEL_WIDTH = 12.75; // This can be calculated for each label using its text and font.
        final double TWO_CHAR_LABEL_WIDTH = 17.25; // This can be calculated for each label using its text and font.
        final double Y_MARGIN = 0.75;
        final double LABEL_MARGIN = 1.5;
        final double LABEL_CIRCLE_RADIUS = CHART_HEIGHT - DOUGHNUT_CENTER_Y - Y_MARGIN - LABEL_HEIGHT / 2.0;

        // Because the data points start at the top, the X coordinates used in the Left and Top properties of
        // the data labels point to the right and the Y coordinates point down, the starting angle is -PI/2.
        double totalAngle = -Math.PI / 2f;
        ChartDataLabel previousLabel = null;

        for (int i = 0; i < series.getYValues().getCount(); i++)
        {
            ChartDataLabel dataLabel = dataLabels.get(i);

            double value = series.getYValues().get(i).getDoubleValue();
            double labelWidth = (value < 10) ? ONE_CHAR_LABEL_WIDTH : TWO_CHAR_LABEL_WIDTH;
            double labelSegmentAngle = value / totalValue * 2.0 * Math.PI;
            double labelAngle = labelSegmentAngle / 2.0 + totalAngle;
            double labelCenterX = LABEL_CIRCLE_RADIUS * Math.cos(labelAngle) + DOUGHNUT_CENTER_X;
            double labelCenterY = LABEL_CIRCLE_RADIUS * Math.sin(labelAngle) + DOUGHNUT_CENTER_Y;
            double labelLeft = labelCenterX - labelWidth / 2.0;
            double labelTop = labelCenterY - LABEL_HEIGHT / 2.0;

            // If the current data label overlaps other labels, move it horizontally.
            if ((previousLabel != null) &&
                (Math.abs(previousLabel.getTop() - labelTop) < LABEL_HEIGHT) &&
                (Math.abs(previousLabel.getLeft() - labelLeft) < labelWidth))
            {
                // Move right on the top, left on the bottom.
                boolean isOnTop = (totalAngle < 0) || (totalAngle >= Math.PI);
                labelLeft = previousLabel.getLeft() + labelWidth * (isOnTop ? 1 : -1) + LABEL_MARGIN;
            }

            dataLabel.setLeft(labelLeft);
            dataLabel.setLeftMode(ChartDataLabelLocationMode.ABSOLUTE);
            dataLabel.setTop(labelTop);
            dataLabel.setTopMode(ChartDataLabelLocationMode.ABSOLUTE);

            totalAngle += labelSegmentAngle;
            previousLabel = dataLabel;
        }

        doc.save(getArtifactsDir() + "Charts.DoughnutChartLabelPosition.docx");
        //ExEnd:DoughnutChartLabelPosition
    }

    @Test
    public void insertChartSeries() throws Exception
    {
        //ExStart
        //ExFor:ChartSeries.Insert(Int32, ChartXValue)
        //ExFor:ChartSeries.Insert(Int32, ChartXValue, ChartYValue)
        //ExFor:ChartSeries.Insert(Int32, ChartXValue, ChartYValue, double)
        //ExSummary:Shows how to insert data into a chart series.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.LINE, 432.0, 252.0);
        Chart chart = shape.getChart();
        ChartSeries series1 = chart.getSeries().get(0);

        // Clear X and Y values of the first series.
        series1.clearValues();
        // Populate the series with data.
        series1.insert(0, ChartXValue.fromDouble(3.0));
        series1.insert(1, ChartXValue.fromDouble(3.0), ChartYValue.fromDouble(10.0));
        series1.insert(2, ChartXValue.fromDouble(3.0), ChartYValue.fromDouble(10.0));
        series1.insert(3, ChartXValue.fromDouble(3.0), ChartYValue.fromDouble(10.0), 10.0);

        doc.save(getArtifactsDir() + "Charts.PopulateChartWithData.docx");
        //ExEnd
    }
}*/
