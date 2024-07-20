package com.lsk.packagefetch.helper;

import com.lsk.packagefetch.util.RedisKeys;
import com.lsk.packagefetch.util.StatusCode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

@Component
public class CaptchaHelper {

    private static final int HEIGHT = 50;
    private static final int WIDTH = 150;

    @Resource
    private RedisHelper redisHelper;

    private String generateRandomText() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private void drawCaptcha(String text, Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        // Generate random text
        String captchaText = text;
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial", Font.BOLD, 24));
        graphics.drawString(captchaText, 20, 30);

        // Add noise to the image
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            graphics.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            graphics.drawLine(x, y, x + 1, y + 1);
        }

        graphics.dispose();
    }

    public String prepareCaptcha() {
        String codeID = UUID.randomUUID().toString();
        String codeContent = generateRandomText();
        redisHelper.newCaptcha(codeID, codeContent);
        return codeID;
    }

    public String getCaptcha(String codeID) {
        try {
            String codeContent = redisHelper.getCaptchaContent(codeID);
            BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
            drawCaptcha(codeContent, image.createGraphics());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", bos);
            return Base64.getEncoder().encodeToString(bos.toByteArray());
        } catch (StatusCode e) {
            throw e;
        } catch (Exception e) {
            throw new StatusCode(500, e);
        }
    }

    public boolean checkCaptcha(String codeID, String userAnswer) {
        String realAnswer = redisHelper.getCaptchaContent(codeID);
        redisHelper.delete(RedisKeys.CODE_CONTENT.of(codeID));
        return realAnswer.equals(userAnswer);
    }
}
