package com.tc.website.common.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class QrcodeUtil {

    /**
     * 创建二维码
     * @param content
     * @param filepath
     */
    public static void encode(String content, String filepath){
        try {
            int width = 100;
            int height = 100;
            Map<EncodeHintType, Object> encodeHints = new HashMap<EncodeHintType, Object>();
            encodeHints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, encodeHints);
            Path path = FileSystems.getDefault().getPath(filepath);
            MatrixToImageWriter.writeToPath(bitMatrix, "png", path);
        }catch (Exception e){
            System.out.println("创建二维码失败：" + e.getMessage());
        }
    }

    /**
     * 解析二维码
     * @param filepath
     * @return
     */
    public static String decode(String filepath) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(filepath));
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            HashMap<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            MultiFormatReader formatReader = new MultiFormatReader();
            Result result = formatReader.decode(binaryBitmap, hints);
            return result.getText();
        }catch (Exception e){
            System.out.println("解析二维码失败：" + e.getMessage());
        }

        return null;
    }

    public static void main(String[] args) {
        System.out.println(QrcodeUtil.decode("F:\\22.jpg"));
    }
}
