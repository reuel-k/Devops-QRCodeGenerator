package com.devops.qr_code_generator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import io.micrometer.core.instrument.MeterRegistry; // ✅ import this

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;

@Controller
public class QrCodeController {

    private final MeterRegistry registry; // ✅ declare registry

    // ✅ constructor injection of MeterRegistry
    public QrCodeController(MeterRegistry registry) {
        this.registry = registry;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // ✅ Test metric endpoint
    @GetMapping("/test-metric")
    @ResponseBody
    public String testMetric() {
        registry.counter("qrgen.test.metric").increment();
        return "✅ Metric incremented!";
    }

    @PostMapping("/generate")
    public String generateQR(@RequestParam("text") String text, Model model) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, 300, 300);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);

            String base64Image = Base64Utils.encodeToString(baos.toByteArray());

            model.addAttribute("qrImage", base64Image);
            model.addAttribute("inputText", text);
        } catch (Exception e) {
            model.addAttribute("error", "Error generating QR Code: " + e.getMessage());
        }

        return "index";
    }
}

