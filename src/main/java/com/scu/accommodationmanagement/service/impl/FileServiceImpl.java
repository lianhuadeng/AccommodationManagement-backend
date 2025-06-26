package com.scu.accommodationmanagement.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.PageReadListener;
import com.scu.accommodationmanagement.model.po.User;
import com.scu.accommodationmanagement.service.FileService;
import com.scu.accommodationmanagement.utils.UserExcelData;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FileServiceImpl implements FileService {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMM");

    @Value("${file-upload-path}")
    private String fileUploadPath;

    @Override
    public Map upload(MultipartFile file) throws IOException {
        return storeFile(file, Paths.get(fileUploadPath, "image").toString());
    }

    @Override
    public List<User> parseUserExcel(MultipartFile file) throws IOException {
        List<User> users = new ArrayList<>();

        // 读取标题行获取列名映射
        Map<String, Integer> headerMap = new HashMap<>();
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            headerRow.forEach(cell -> headerMap.put(cell.getStringCellValue(), cell.getColumnIndex()));
        }

        // 检查必要列是否存在
        String[] requiredColumns = {"学号", "姓名"};
        for (String col : requiredColumns) {
            if (!headerMap.containsKey(col)) {
                throw new IOException("缺少必要列: " + col);
            }
        }

        // 使用列名映射解析数据
        EasyExcel.read(file.getInputStream())
                .head(UserExcelData.class)
                .registerReadListener(new AnalysisEventListener<UserExcelData>() {
                    @Override
                    public void invoke(UserExcelData data, AnalysisContext context) {
                        User user = new User();
                        user.setUserId(data.getUserId());
                        user.setName(data.getName());
                        user.setCollege(data.getCollege());
                        user.setMajor(data.getMajor());
                        user.setGrade(data.getGrade());
                        user.setClazz(data.getClazz());
                        user.setSex(data.getSex());
                        user.setContact(data.getContact());
                        users.add(user);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {}
                })
                .sheet()
                .headRowNumber(1) // 跳过标题行
                .doRead();

        return users;
    }

    private static Map<String, String> storeFile(MultipartFile file, String fileUploadPath) throws IOException {

        String yearMonth = SDF.format(new Date());//当前年月
        //String random = "" + (int) (Math.random() * 1000);//随机4位数,没补0
        String fileName = file.getOriginalFilename();//文件全名
        String suffix = suffix(fileName);//文件后缀
        String relPath = "/" + yearMonth + "/" + "-" + UUID.randomUUID().toString().replaceAll("-","") + suffix;
        String toPath = fileUploadPath + relPath;
        FileOutputStream out = null;

        File toFile = new File(toPath).getParentFile();
        if (!toFile.exists()) {
            toFile.mkdirs(); //自动创建目录
        }
        try {
            out = new FileOutputStream(toPath);
            out.write(file.getBytes());
            out.flush();
            Map<String, String> map = new HashMap();
            map.put("url", "/image" + relPath);

            return map;
        } catch (FileNotFoundException fnfe) {
            throw fnfe;
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 后缀名或empty："a.png" => ".png"
     */
    private static String suffix(String fileName) {
        int i = fileName.lastIndexOf('.');
        return i == -1 ? "" : fileName.substring(i);
    }
}
