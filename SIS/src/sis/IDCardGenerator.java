package sis;

import java.awt.Color;
import java.io.File;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.BasicStroke;

public class IDCardGenerator {

    public static void generateIDCard(String studentName, String studentId, String course_id, String photoPath) {
        int width = 600;
        int height = 350;

        BufferedImage idImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = idImage.createGraphics();

        // Enable High Quality
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Modern Gradient Background
        GradientPaint gp = new GradientPaint(0, 0, new Color(30, 75, 160), width, height, new Color(20, 140, 220));
        g.setPaint(gp);
        g.fillRect(0, 0, width, height);

        // White Rounded Rectangle Card
        g.setColor(Color.WHITE);
        g.fillRoundRect(20, 20, width - 40, height - 40, 30, 30);

        // Header Bar
        g.setColor(new Color(40, 90, 200));
        g.fillRoundRect(20, 20, width - 40, 60, 30, 30);

        // Title
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 26));
        g.drawString("STUDENT ID CARD", 180, 60);

        // Modern Divider Line
        g.setColor(new Color(40, 90, 200));
        g.fillRect(40, 100, width - 80, 3);

        // Student Information Section
        g.setFont(new Font("SansSerif", Font.BOLD, 20));
        g.setColor(new Color(30, 30, 30));
        g.drawString("Name", 40, 150);
        g.drawString("Student ID", 40, 190);
        g.drawString("Course ID", 40, 230);

        g.setFont(new Font("SansSerif", Font.PLAIN, 19));
        g.setColor(new Color(80, 80, 80));

        g.drawString(": " + studentName, 180, 150);
        g.drawString(": " + studentId, 180, 190);
        g.drawString(": " + course_id, 180, 230);

        // Photo Frame Shadow
        g.setColor(new Color(0, 0, 0, 30));
        g.fillRoundRect(430, 95, 140, 165, 20, 20);

        // Photo Frame
        g.setColor(Color.WHITE);
        g.fillRoundRect(425, 90, 140, 165, 20, 20);

        // Student Photo
        try {
            Image photo = ImageIO.read(new File(photoPath)).getScaledInstance(140, 165, Image.SCALE_SMOOTH);
            g.drawImage(photo, 425, 90, null);
        } catch (Exception e) {
            System.out.println("Photo load error: " + e.getMessage());
        }

        // Border for Card
        g.setColor(new Color(40, 90, 200));
        g.setStroke(new BasicStroke(4));
        g.drawRoundRect(20, 20, width - 40, height - 40, 30, 30);

        g.dispose();

        // Export as PNG
        try {
            File output = new File("Student_ID_" + studentId + ".png");
            ImageIO.write(idImage, "png", output);
            System.out.println("ID Card saved: " + output.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
