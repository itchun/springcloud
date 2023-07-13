//package com.itchun.word;
//
//import cn.hutool.core.collection.CollectionUtil;
//import cn.hutool.core.util.StrUtil;
//import org.apache.poi.POIXMLDocument;
//import org.apache.poi.xwpf.usermodel.*;
//
//import java.io.FileOutputStream;
//import java.util.*;
//
//public class WordTest {
//
//    public static void main1(String[] args) throws Exception {
//        Map<String, String> map = new HashMap<>();
//        map.put("$time", "2023-03-22");
//        XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage("D:\\test.docx"));
//        Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
//        while (itPara.hasNext()) {
//            XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
//            List<XWPFRun> runs = paragraph.getRuns();
//            for (int i = 0; i < runs.size(); i++) {
//                XWPFRun index = runs.get(i);
//                String oneparaString = index.getText(index.getTextPosition());
//                oneparaString = StrUtil.isBlank(oneparaString) ? "" : oneparaString.trim();
//                for (Map.Entry<String, String> entry : map.entrySet()) {
//                    if (oneparaString.equals(entry.getKey())) {
//                        oneparaString = oneparaString.replace(entry.getKey(), entry.getValue());
//                    }
//
//                }
//                runs.get(i).setText(oneparaString, 0);
//            }
//        }
//        FileOutputStream outStream = null;
//        outStream = new FileOutputStream("D:\\test1.docx");
//        document.write(outStream);
//        outStream.close();
//
//    }
//
//    public static void main(String[] args) throws Exception {
//        Map<String, String> map = new HashMap<>();
//        map.put("$time", "2023-03-22");
//        XWPFDocument doc = new XWPFDocument(POIXMLDocument.openPackage("D:\\test.docx"));
//
//        //模版table
//        Iterator<XWPFTable> iterator = doc.getTablesIterator();
//        XWPFTable table;
//        List<XWPFTableRow> rows;
//        List<XWPFTableCell> cells;
//        List<XWPFParagraph> paras;
//        //循环所有的文本进行添加定位
//        while (iterator.hasNext()) {
//            List<Map<String, Object>> mapList = new ArrayList<>(16);
//            //要删除的row
//            List<Integer> removeStringList = new ArrayList<>(16);
//            int rowIndex = 0;
//            table = iterator.next();
//            rows = table.getRows();
//            Iterator iteratorRow = rows.iterator();
//            if (CollectionUtil.isNotEmpty(dataSource)) {
//                while (iteratorRow.hasNext()) {
//                    //是否添加
//                    Boolean isInsert = false;
//
//                    XWPFTableRow row = (XWPFTableRow) iteratorRow.next();
//                    cells = row.getTableCells();
//                    for (XWPFTableCell cell : cells) {
//                        paras = cell.getParagraphs();
//                        for (XWPFParagraph para : paras) {
//                            List<XWPFRun> runs = para.getRuns();
//                            for (int i = 0; i < runs.size(); i++) {
//                                XWPFRun run = runs.get(i);
//                                String runText = run.toString().replaceAll("\\{", "").replaceAll("}", "");
//                                //此表格为下拉框时
//                                if (runText.contains("LIST")) {
//                                    List<String> codeList = substringData(runText);
//                                    //数据源编码
//                                    String funcCode = codeList.get(0);
//                                    //此为表格下拉框时
//                                    //数据源
//                                    List<Object> objects = (List<Object>) dataSource.get(funcCode);
//                                    if (CollectionUtil.isNotEmpty(objects) && !isInsert) {
//                                        for (int j = 0; j < objects.size(); j++) {
//                                            Map<String, Object> mapData = new HashMap<>(16);
//                                            mapData.put("data", objects.get(j));
//                                            mapData.put("table", table);
//                                            mapData.put("row", row);
//                                            mapData.put("rowIndex", rowIndex);
//                                            mapList.add(mapData);
//                                            rowIndex++;
//                                        }
//                                        isInsert = true;
//                                        removeStringList.add(rowIndex);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    rowIndex++;
//                }
//            }
//
//            //添加row 并替换内容
//            if (CollectionUtil.isNotEmpty(mapList)) {
//                for (Map map : mapList) {
//                    Map<String, Object> data = (Map<String, Object>) map.get("data");
//                    XWPFTable xwpfTable = (XWPFTable) map.get("table");
//                    XWPFTableRow row = (XWPFTableRow) map.get("row");
//                    int rowNum = (int) map.get("rowIndex");
//                    copyRowOverWrite(xwpfTable, row, rowNum, data);
//                }
//            }
//
//            //删除row--删除用来标记的row
//            if (CollectionUtil.isNotEmpty(removeStringList)) {
//                int afterRemoveNumber = 0;
//                for (Integer removeIndex : removeStringList) {
//                    table.removeRow(removeIndex - afterRemoveNumber);
//                    afterRemoveNumber++;
//                }
//            }
//
//            //循环所有的文本进行替换
//            List<XWPFTableRow> newrows = table.getRows();
//            //遍历表格，替换模版
//            for (XWPFTableRow row : newrows) {
//                cells = row.getTableCells();
//                for (XWPFTableCell cell : cells) {
//                    paras = cell.getParagraphs();
//                    for (XWPFParagraph para : paras) {
//                        replaceInParaOverWrite(para, dynaBean, dataSource);
//                    }
//                }
//            }
//        }
//
//        FileOutputStream outStream = null;
//        outStream = new FileOutputStream("D:\\test1.docx");
//        document.write(outStream);
//        outStream.close();
//
//    }
//}
