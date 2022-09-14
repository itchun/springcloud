package com.itchun.easyExcel;

import com.alibaba.excel.EasyExcel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 无模板导出
 */
public class EasyExcelMain_2 {

    public static void main(String[] args) throws Exception {

        // 图片数据准备
        List<DemoData> list = new ArrayList<>();
        DemoData demoData = new DemoData();
        File file = new File("C:\\Users\\xx\\Desktop\\电脑背景图\\(1).jpg");
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        BufferedImage bufferImg = ImageIO.read(file);
        String suffix = "jpg";
        ImageIO.write(bufferImg, suffix, byteArrayOut);
        bufferImg.flush();
        demoData.setImage(byteArrayOut.toByteArray());
        list.add(demoData);

        // 保存本地
        EasyExcel.write("C:\\Users\\xx\\Desktop\\1.xlsx", DemoData.class).sheet("sheet1").doWrite(list);
    }
}
