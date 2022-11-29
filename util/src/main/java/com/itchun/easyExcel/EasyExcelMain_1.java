//package com.itchun.easyExcel;
//
//import com.alibaba.excel.EasyExcel;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 模板导出 {.test_image}
// */
//public class EasyExcelMain_1 {
//
//    public static void main(String[] args) throws Exception {
//
//        // 图片数据准备
//        List<Map<String, Object>> list = new ArrayList<>();
//        Map<String, Object> map = new HashMap<>();
//        File file = new File("C:\\Users\\王春\\Desktop\\电脑背景图\\(1).jpg");
//        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
//        BufferedImage bufferImg = ImageIO.read(file);
//        String suffix = "jpg";
//        ImageIO.write(bufferImg, suffix, byteArrayOut);
//        bufferImg.flush();
//        map.put("test_image", byteArrayOut.toByteArray());
//        list.add(map);
//
//        // 保存本地
//        FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\王春\\Desktop\\1.xlsx"));
//        EasyExcel.write(fileOutputStream).withTemplate("C:\\Users\\王春\\Desktop\\test.xlsx").sheet().doFill(list);
//    }
//}
