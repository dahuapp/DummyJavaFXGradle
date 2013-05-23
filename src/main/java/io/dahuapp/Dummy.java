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
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 *
 * @author barraq
 */
public class Dummy extends Application {

    static int numscreen = 1;
   DirectorySelection dir ;
   static String NOM_FILE ;
    static final String NOM_IMAGE = "image"; 			// A CHANGER
    static final String EXT = "png";
    
    @Override
    public void start(final Stage primaryStage) {
        final StackPane root = new StackPane();
        dir = new DirectorySelection(primaryStage) ;
        final Button btn = new Button();
        final ImageView iv = new ImageView();
        iv.fitWidthProperty().bind(root.widthProperty());
        iv.fitHeightProperty().bind(root.heightProperty());
        btn.setText("Select a directory'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dir.start() ;
                NOM_FILE = dir.getDir();
            }
        });

        Scene scene = new Scene(root, 300, 250);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                try {
                    Screenshot.takeScreenshotWithAWT();
                } catch (IOException ex) {
                    Logger.getLogger(Dummy.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
       });

        root.getChildren().add(btn);
        root.getChildren().add(iv);

        Stage widget = new Stage();
        widget.setTitle("JavaFXGradle dummy screenshot Widget");
        widget.initStyle(StageStyle.UTILITY);
        widget.initModality(Modality.APPLICATION_MODAL);
        widget.setScene(scene);
        widget.show();
    }

    public static void takeScreenshotWithAWT(final ImageView iv) throws IOException {
        // Get all the screens available (like if dual screens)
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();

        //@warning get by default the first screen!
        int screenId = gs.length - 1;

        Robot robot = null;
        try {
            robot = new Robot(gs[screenId]);
        } catch (AWTException e) {
            System.out.println("Failed to create screenshot: " + e.getMessage());
        }

        DisplayMode mode = gs[screenId].getDisplayMode();
        Rectangle bounds = new Rectangle(0, 0, mode.getWidth(), mode.getHeight());

        final BufferedImage createScreenCapture = robot.createScreenCapture(bounds);

        // update the javafx panel
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                iv.setImage(SwingFXUtils.toFXImage(createScreenCapture, null));
            }
        });

        // store screenshot as file
        ImageIO.write(createScreenCapture, EXT, new File(NOM_FILE + NOM_IMAGE + numscreen + "." + EXT));
        numscreen++;
    }

    public static void takeScreenshotWithGlass(final ImageView iv) {
        com.sun.glass.ui.Robot robot = com.sun.glass.ui.Application.GetApplication().createRobot();
        Screen mainScreen = Screen.getMainScreen();
        Pixels screenshot = robot.getScreenCapture(0, 0, mainScreen.getWidth(), mainScreen.getHeight());
        WritableImage image = new WritableImage(screenshot.getWidth(), screenshot.getHeight());

        // to continue

        System.out.println(screenshot.asByteBuffer());
        System.out.println("screenshot is " + screenshot.getWidth() + " " + screenshot.getHeight());
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
