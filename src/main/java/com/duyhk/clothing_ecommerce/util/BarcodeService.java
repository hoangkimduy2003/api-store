package com.duyhk.clothing_ecommerce.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class BarcodeService {
    public static void generateAndSaveBarcode(String data){
        int width = 300;
        int height = 100;

        String filePath = "D:/workspace/file/" + data + ".png";

        // Tạo đối tượng Code128Writer
        Code128Writer code128Writer = new Code128Writer();

        // Tạo một bảng bit (BitMatrix) từ dữ liệu và định dạng mã vạch
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = code128Writer.encode(data, BarcodeFormat.QR_CODE, width, height, hints);

        // Lưu bảng bit thành hình ảnh
        Path path = FileSystems.getDefault().getPath(filePath);
        try {
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
