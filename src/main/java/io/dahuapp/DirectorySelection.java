/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dahuapp;

import java.io.File;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Jean-Baptiste
 */
public class DirectorySelection {
    
    private File file;
    private final Label labelFile = new Label();
    private FileChooser fileChooser = new FileChooser();
    private Stage parent ;
    
    public DirectorySelection(Stage primaryStage) {
        
        parent = primaryStage ;
    }
    
    public void start() {
         
        parent.setTitle("Select Directory");
                //Open directory from existing directory
                if(file != null){
                    File existDirectory = file.getParentFile();
                    fileChooser.setInitialDirectory(existDirectory);
                }
 
                //Set extension filter
                //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
                //fileChooser.getExtensionFilters().add(extFilter);
                 
                //Show open file dialog
                file = fileChooser.showOpenDialog(null);
                labelFile.setText(file.getPath());
            
     
        VBox vBox = new VBox();
        vBox.getChildren().addAll(labelFile);
         
        StackPane root = new StackPane();
        root.getChildren().add(vBox);
        parent.setScene(new Scene(root, 300, 250));
        parent.show();
    }
    
    File getFile() {
        return file.getParentFile() ;
    }
}
