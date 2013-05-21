/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dahuapp;

import com.sun.glass.ui.Pixels;
import com.sun.glass.ui.Screen;
import java.awt.AWTException;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.SwingUtilities;

/**
 *
 * @author barraq
 */
public class Dummy extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        final StackPane root = new StackPane();
        
        final Button btn = new Button();
        final ImageView iv = new ImageView();
        iv.fitWidthProperty().bind(root.widthProperty());
        iv.fitHeightProperty().bind(root.heightProperty());
        
        btn.setText("Take a screenshot");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        takeScreenshotWithAWT(iv);
                    }
                });
            }
        });
        
        Scene scene = new Scene(root, 300, 250);
        root.getChildren().add(btn);
        root.getChildren().add(iv);
        
        primaryStage.setTitle("JavaFXGradle dummy screenshot");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void takeScreenshotWithAWT(final ImageView iv) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();

        java.awt.Robot robot = null;
        try {
            robot = new java.awt.Robot(gs[gs.length-1]);
        } catch (AWTException e) {
            System.out.println("Failed to create screenshot: "+e.getMessage());
            System.exit(-1);
        }
        DisplayMode mode = gs[0].getDisplayMode();
        Rectangle bounds = new Rectangle(0, 0, mode.getWidth(), mode.getHeight());
        final BufferedImage bi = robot.createScreenCapture(bounds);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Image im = SwingFXUtils.toFXImage(bi, null);
                iv.setImage(im);
            }
        });
    }
    
    public static void takeScreenshotWithGlass(final ImageView iv) {
        com.sun.glass.ui.Robot robot = com.sun.glass.ui.Application.GetApplication().createRobot();
        Screen mainScreen = Screen.getMainScreen();
        Pixels screenshot = robot.getScreenCapture(0, 0, mainScreen.getWidth(), mainScreen.getHeight());
        WritableImage image = new WritableImage(screenshot.getWidth(), screenshot.getHeight());
        
        // to continue
        
        System.out.println(screenshot.asByteBuffer());
        System.out.println("screenshot is "+screenshot.getWidth()+" "+screenshot.getHeight());
    }
    
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* fix for osx */
        System.setProperty("javafx.macosx.embedded", "true");
        java.awt.Toolkit.getDefaultToolkit();
        
        /* start the app */
        launch(args);
    }
}

