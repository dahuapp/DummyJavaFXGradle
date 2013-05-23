package io.dahuapp;


import static io.dahuapp.Screenshot.idScreen;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mathieu
 */
public class Screenshot {

    String date;
    static double mouseX;
    static double mouseY;
    static int idScreen = 1;

    public Screenshot() {
    
    };


    public static void takeScreenshotWithAWT() throws IOException {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Point pmouse = MouseInfo.getPointerInfo().getLocation();
        Dimension screenSize = toolkit.getScreenSize();
        Rectangle screenRect = new Rectangle(screenSize);
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            System.out.println(ex);
        }

        // recuperation des coordonnees de la souris
        mouseX = pmouse.getX();
        mouseY = pmouse.getY();
        System.out.println(mouseX + " et " + mouseY);
        
        // prise du screenshot
        BufferedImage createScreenCapture = robot.createScreenCapture(screenRect);
        final String NOM_FILE = "screenshot/";
        final String NOM_IMAGE = "image"; 			// A CHANGER
        final String EXT = "png";

        // ecriture de l'image sur le disque
        ImageIO.write(createScreenCapture, EXT, new File(NOM_FILE + NOM_IMAGE + idScreen + "." + EXT));
        idScreen++;
    }
}
