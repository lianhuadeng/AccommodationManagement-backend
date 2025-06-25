package com.scu.accommodationmanagement.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageCompressUtil {

    private static final int TARGET_FILE_SIZE = 1024 * 1024; // 目标文件大小：1MB

    /**
     * 压缩图片，直到文件大小小于1MB
     *
     * @param file 需要压缩的MultipartFile
     * @return 压缩后的MultipartFile
     * @throws IOException 如果发生IO异常
     */
    public static MultipartFile compressImage(MultipartFile file) throws IOException {
        // 读取原始图片
        BufferedImage originalImage = ImageIO.read(file.getInputStream());

        // 初始文件大小
        byte[] compressedImageBytes = compressToByteArray(originalImage, 1.0f);

        // 如果原始图片已经小于1MB，直接返回
        if (compressedImageBytes.length <= TARGET_FILE_SIZE) {
            return createMultipartFile(file, compressedImageBytes);
        }

        // 按比例缩小图片，直到文件大小小于1MB
        float quality = 0.9f;
        while (compressedImageBytes.length > TARGET_FILE_SIZE && quality > 0.1f) {
            quality -= 0.1f;
            compressedImageBytes = compressToByteArray(originalImage, quality);
        }

        return createMultipartFile(file, compressedImageBytes);
    }

    /**
     * 将图片压缩为字节数组
     *
     * @param image   图片
     * @param quality 压缩质量，范围：0.0 - 1.0
     * @return 压缩后的字节数组
     * @throws IOException 如果发生IO异常
     */
    private static byte[] compressToByteArray(BufferedImage image, float quality) throws IOException {
        // 创建输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // 获取压缩后的图片
        javax.imageio.plugins.jpeg.JPEGImageWriteParam jpegParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(null);
        jpegParams.setCompressionMode(javax.imageio.plugins.jpeg.JPEGImageWriteParam.MODE_EXPLICIT);
        jpegParams.setCompressionQuality(quality);

        // 写入图片
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 创建MultipartFile
     *
     * @param file 原始文件
     * @param bytes 压缩后的字节数据
     * @return 压缩后的MultipartFile
     */
    private static MultipartFile createMultipartFile(MultipartFile file, byte[] bytes) {
        return new MultipartFile() {
            @Override
            public String getName() {
                return file.getName();
            }

            @Override
            public String getOriginalFilename() {
                return file.getOriginalFilename();
            }

            @Override
            public String getContentType() {
                return file.getContentType();
            }

            @Override
            public boolean isEmpty() {
                return bytes.length == 0;
            }

            @Override
            public long getSize() {
                return bytes.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return bytes;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(bytes);
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                throw new UnsupportedOperationException("Transfer to File not supported");
            }
        };
    }
}
