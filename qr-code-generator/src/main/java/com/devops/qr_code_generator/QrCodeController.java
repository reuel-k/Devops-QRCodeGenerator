package com.devops.qr_code_generator;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Counter;
import com.codahale.metrics.Timer;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import jakarta.annotation.PostConstruct;

@Controller
public class QrCodeController {

    private final MetricRegistry metricRegistry;
    private Counter testMetricCounter;
    private final Counter qrGenerationCounter;
    private final Timer qrGenerationTimer;

    public QrCodeController(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
        this.qrGenerationCounter = metricRegistry.counter("qrgen.qr.generated");
        this.qrGenerationTimer = metricRegistry.timer("qrgen.qr.generation.time");
    }

    @PostConstruct
    public void initMetrics() {
        this.testMetricCounter = metricRegistry.counter("qrgen.test.metric");
        System.out.println("✅ Dropwizard MetricRegistry initialized: " + metricRegistry.getClass().getName());
    }

    @GetMapping("/test-metric")
    @ResponseBody
    public String testMetric() {
        testMetricCounter.inc();
        return "✅ Dropwizard metric incremented!";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/generate")
    public String generateQR(@RequestParam("text") String text, Model model) {
        final Timer.Context context = qrGenerationTimer.time(); // Start timer
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, 300, 300);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);

            String base64Image = Base64Utils.encodeToString(baos.toByteArray());

            model.addAttribute("qrImage", base64Image);
            model.addAttribute("inputText", text);

            qrGenerationCounter.inc(); // ✅ Increment metric

        } catch (Exception e) {
            model.addAttribute("error", "Error generating QR Code: " + e.getMessage());
        } finally {
            context.stop(); // ✅ Stop timer
        }

        return "index";
    }

}

