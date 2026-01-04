package com.zukxu;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2025/4/29 14:44:11
 */
public class AsposeJarCrack {

    public static void main(String[] args) {
        //System.out.println(new String(new byte[]{69, 86, 65, 76, 85, 65, 84, 73, 79, 78}));
        //System.out.println(new String(new byte[]{76, 73, 67, 69, 78, 83, 69, 68}));
        //System.out.println(new String(new byte[]{44, 57, 62, 69, 78, 83, 68, 65, 76}));
        System.out.println(zzY1g(System.currentTimeMillis()) | 4611686018427387904L);
    }

    private static long zzY1g(long var0) {
        System.out.println(var0);
        long var2;
        if ((var2 = var0 + 62135596800000L) < 0L) {
            var2 = 0L;
        }

        return var2 * 10000L;
    }

    private static void viewSetLicenseCode() throws Exception {
        FileInputStream fis = new FileInputStream("lib/license.xml");

        com.aspose.words.License wordsLicense = new com.aspose.words.License();
        //com.aspose.slides.License slidesLicense = new com.aspose.slides.License();
        //com.aspose.cells.License cellsLicense = new com.aspose.cells.License();
        //com.aspose.ocr.License ocrLicense = new com.aspose.ocr.License();
        //请点击这里的setLicense方法进入查看源码，不同版本的破解方法名不一样，但道理差不多
        wordsLicense.setLicense(fis);
        //slidesLicense.setLicense(fis);
        //cellsLicense.setLicense(fis);
        //ocrLicense.setLicense(fis);
    }

    /**
     * aspose-words:jdk17:24.6 版本
     */
    public static void registerWord253() {
        // 构造一个注册信息
        /*
        * 搜索项目中涉及到4096的地方，这个地方就是对文件进行验证的地方
        * 我们会发现在最后调用验证的时候会从这里获取到一开始添加的License信息对象，然后进行验证
        * 会对信息进行判断，return this.zzWni == 1 && !this.zzZBG() && !this.zzYH8();
        * 所以我们可以通过反射的方式来修改这个信息，将其修改为1，这样就可以破解了
        * 但是这样做有一个问题，就是每次启动项目都会重新加载这个信息，所以我们需要将这个信息保存起来
        * 这样就可以避免每次启动项目都需要重新加载这个信息
        * */
        try {
            // 通过类名获取 com.aspose.words.zzZ40 类的 Class 对象
            Class<?> zzXgCClass = Class.forName("com.aspose.words.zzZ40");
            // 获取 zzZ40 类的第一个构造函数
            Constructor<?> constructors = zzXgCClass.getDeclaredConstructors()[0];
            constructors.setAccessible(true);
            // 使用构造函数创建一个实例，传入两个 null 参数
            Object instance = constructors.newInstance(null, null);
            // 获取 zzZ40 类中名为 zzXMg 的字段
            Field zzXMg = zzXgCClass.getDeclaredField("zzXMg");
            zzXMg.setAccessible(true);
            // 将实例中 zzXAm 字段的值设置为 1
            zzXMg.set(instance, 1);
            // 获取 zzZ40 类中名为 zzWni 的字段
            Field zzWni = zzXgCClass.getDeclaredField("zzWni");
            zzWni.setAccessible(true);
            zzWni.set(instance, 1);

            // 通过类名获取 com.aspose.words.zzWix 类的 Class 对象
            Class<?> zzWXGClass = Class.forName("com.aspose.words.zzWix");
            // 获取 zzWix 类中名为 zzY7Z 的字段, 是保存验证信息的列表
            Field zzY7Z = zzWXGClass.getDeclaredField("zzY7Z");
            zzY7Z.setAccessible(true);
            ArrayList<Object> list = new ArrayList<>();
            // 将之前创建的 zzZ40 类实例添加到 ArrayList 中
            list.add(instance);
            zzY7Z.set(null, list);

            // 代码中搜索4096相关的内容，找到相关的方法 zzWBE.zzW6u() != 4096
            // 生成文档会调用这个类的方法来判断
            // 通过类名获取类的 Class 对象
            Class<?> zzWBEClass = Class.forName("com.aspose.words.zzWBE");
            // 获取 zzWBEClass 类中相关的判断来修改对应的字段
            // zzWBE.zzW6u() != 4096
            //static int zzW6u() {
            //        return zzaB == 128 && !zzYW5 ? 256 : 4096;
            //    }
            Field zzHA = zzWBEClass.getDeclaredField("zzaB");
            zzHA.setAccessible(true);
            zzHA.set(null, 128);
            Field zzYW5 = zzWBEClass.getDeclaredField("zzYW5");
            zzYW5.setAccessible(true);
            zzYW5.set(null, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
