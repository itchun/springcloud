package com.itchun.word.freemarker;

import java.io.*;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class ZipUtil {

    /**
     * 功能描述: 压缩成Zip格式
     *
     * @param: srcFilePath 要压缩的源文件路径
     * @param: destFilePath 压缩后文件存放路径
     * @param: KeepFileStructure 是否保留原来的目录结构,true:保留目录结构;
     * false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     */
    public static void toZip(String srcFilePath, String destFilePath, boolean KeepFileStructure) {
        // 判断要压缩的源文件是否存在
        File sourceFile = new File(srcFilePath);
        if (!sourceFile.exists()) {
            throw new RuntimeException(sourceFile + "不存在...");
        }

        long start = System.currentTimeMillis();

        // 如果压缩文件已经存在，增加序号
        String zipName = destFilePath + sourceFile.getName();

        // 创建存放压缩文件的文件对象
        File zipFile = new File(zipName + ".zip");
        ZipOutputStream zos = null;
        try {
            // 生成目标文件对象的输出流
            FileOutputStream fos = new FileOutputStream(zipFile);
            CheckedOutputStream cos = new CheckedOutputStream(fos, new CRC32());
            // 生成ZipOutputStream，用于写入要压缩的文件
            zos = new ZipOutputStream(cos);
            compressbyType(sourceFile, zos, sourceFile.getName(), KeepFileStructure);
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时====" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void compressbyType(File sourceFile, ZipOutputStream zos, String zipName, boolean KeepDirStructure) throws Exception {
        if (!sourceFile.exists())
            return;
        System.out.println("压缩" + sourceFile.getName());

        if (sourceFile.isFile()) {
            // if(!"myDir3.txt".equals(sourceFile.getName())) {
            // 文件
            compressFile(sourceFile, zos, zipName, KeepDirStructure);
            // }

        } else {
            // 文件夹
            compressDir(sourceFile, zos, zipName, KeepDirStructure);
        }
    }

    public static void compressFile(File file, ZipOutputStream zos, String zipName, boolean keepDirStructure)
            throws IOException {

        // 1、向zip输出流中添加一个zip实体(压缩文件的目录)，构造器中name为zip实体的文件的名字
        ZipEntry entry = new ZipEntry(zipName);
        zos.putNextEntry(entry);
        FileInputStream fis = null;
        BufferedInputStream bis = null;

        // 2、 copy文件到zip输出流中
        int len;
        byte[] buf = new byte[1024];
        try {
            // 要压缩的文件对象写入文件流中
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            while ((len = bis.read(buf)) != -1) {
                zos.write(buf, 0, len);
                zos.flush();
            }
        } catch (Exception e) {

        } finally {
// Complete the entry
            if (fis != null) {
                fis.close();
            }
//            zos.closeEntry();
            if (bis != null) {
                bis.close();
            }
        }


    }

    public static void compressDir(File dir, ZipOutputStream zos, String zipName, boolean KeepDirStructure)
            throws IOException, Exception {

        if (!dir.exists())
            return;

        File[] files = dir.listFiles();
        if (files.length == 0) { // 空文件夹
            // 需要保留原来的文件结构时,需要对空文件夹进行处理
            if (KeepDirStructure) {
                // 空文件夹的处理
                zos.putNextEntry(new ZipEntry(zipName + File.separator));
                // 没有文件，不需要文件的copy
                zos.closeEntry();
            }
        } else {
            for (File file : files) {
                // 判断是否需要保留原来的文件结构
                if (KeepDirStructure) {
                    // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                    // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                    compressbyType(file, zos, zipName + File.separator + file.getName(), KeepDirStructure);
                } else {
                    compressbyType(file, zos, file.getName(), KeepDirStructure);
                }
            }
        }
    }

    /**
     * 功能描述: outputStream转inputStream
     *
     * @author: hongzm
     * @param: out 输出流
     * @return: byte[]
     */
    public static ByteArrayInputStream outPareIn(OutputStream out) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos = (ByteArrayOutputStream) out;
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        return bais;
    }

    /**
     * 功能描述: inputStream转byte[]
     * @author: hongzm
     * @param: in 输入流
     * @return: byte[]
     */
    public static byte[] outPareIn(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int n = 0;
        while ((n = in.read(buff)) != -1) {
            baos.write(buff, 0, n);
        }

        byte[] buff2 = baos.toByteArray();
        return buff2;
    }

    /**
     * 删除文件或者目录
     *
     * @param file
     * @return
     */
    public static boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
        }
        return file.delete();
    }
}