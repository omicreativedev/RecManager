package recmanager;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.HPos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.*;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import javax.swing.*;

public class deleteRecipe {
    
    /**Create a private data */
    private TextField tfId=new TextField();
    private Label idLabel=new Label("Id");
    private Label titleLabel=new Label("Delete recipe: ");
    
    private Button delete =new Button ("Delete");
    private Button cancel =new Button("Cancel");
    
    private VBox vbox=new VBox();
    private HBox hbox=new HBox();
    private GridPane gridPane =new GridPane();
    
    public deleteRecipe(Stage primaryStage, Statement stm, ObservableList<Recipe> data, ArrayList<Recipe> recipes)
    {
        tfId.setStyle("-fx-background:yellow;");
        tfId.setPrefWidth(200);
        
        idLabel.setStyle("-fx-text-fill: black;"+"-fx-font-family:calibri;"+"-fx-font-size:14;");
        titleLabel.setStyle("-fx-text-fill: black;"+"-fx-font-family:calibri;"+"-fx-font-size:16;");
        
        delete.setPrefWidth(100);
        cancel.setPrefWidth(100);
        
        hbox.setSpacing(10);
        
        hbox.getChildren().addAll(delete, cancel);
        
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(20);
        gridPane.setPadding (new Insets(10,20,0,0));
        gridPane.add(idLabel,0,0);
        gridPane.add(tfId,1,0);
        gridPane.add(hbox,1,1);
        GridPane.setHalignment(hbox,HPos.RIGHT);
        
        vbox.setSpacing(25);
        vbox.setPadding(new Insets(10,20,0,0));
        vbox.getChildren().addAll(titleLabel,gridPane);
        
        Scene scene =new Scene(vbox,300,150);
        Stage mainStage=new Stage();
        mainStage.initModality(Modality.APPLICATION_MODAL);
        mainStage.initOwner(primaryStage);
        
        mainStage.setScene(scene);
        mainStage.setTitle("Recipe Manager App (Delete recipe)");
        mainStage.show();
        
        
        /**Register and handle the event fired by cancel method */
        cancel.setOnAction(e->{mainStage.close();});
        
        
        /**Register and handle the event fired by the cancel method */
        delete.setOnAction(e->{
          try{
              
              deleteRecipe(mainStage,stm,data,recipes);
              
          }catch (Exception exception)
          {
              popErrorMessage(mainStage, exception.getMessage());
          }
        });
        
        
        /**Register and handle the event fired by the Enter key */
        /**Enter Key */
       vbox.setOnKeyPressed(e->{
          if(e.getCode()==KeyCode.ENTER)
          {
              try{
                 
                  deleteRecipe(primaryStage, stm, data,recipes);
              }catch(Exception exception)
              {
                  popErrorMessage(mainStage,exception.getMessage());
              }
          }
       });
       
    }
    
    
    /**Delete recipes */
    public void deleteRecipe(Stage stage, Statement stm, ObservableList<Recipe> data, ArrayList<Recipe> recipes)throws Exception
    {
        int exist=0;
        String query ="delete from recipe where id='"+Integer.parseInt(tfId.getText())+"'";
        
       int result = JOptionPane.showOptionDialog(null, "Are you sure you want to delete this record","Delete Record",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
       if (result == JOptionPane.YES_OPTION){
        for (int i=0;i<data.size();i++)
        {
            if (data.get(i).getId()==Integer.parseInt(tfId.getText()))
            {
                stm.execute(query);
                data.remove(data.get(i));
                                            recipes.remove(recipes.get(i));
                exist=1;
                break;
          
            }
        }
       }//if (result == JOptionPane.YES_OPTION)
        
        if (exist==1)
        {
            tfId.clear();
            infoDialog(stage,"Recipe Was Successfully Deleted!!!");
          
            
        }
        else
        {
            
            popErrorMessage(stage,"Recipe Doesn't Exist!!! ");
        }
          
    }
    
    
    /**Error Message */
    public void popErrorMessage(Stage primaryStage,String text2)
        {
            /**Create a Stage  */
            final Stage dialog=new Stage ();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(primaryStage);
            
            /**Set text properties */
            Text text=new Text();
            text.setText(text2);
            text.setFill(Color.RED);
            text.setFont(Font.font("Times new roman",20));
            
            /**Set image properties */
            ImageView image =new ImageView(new Image("file:warning.png"));
           
             /**Set the button properties */
            Button dialogOk=new Button("OK");
            dialogOk.setPrefWidth(100);
            
            /**Set dialog HBox properties */
            HBox dialogHbox=new HBox ();
            dialogHbox.setSpacing(10);
            dialogHbox.getChildren().addAll(image,text);
            HBox.setMargin(image,new Insets(0,0,0,10));
            /**Set VBox properties */
            VBox dialogVBox =new VBox();
            dialogVBox.setSpacing(10);
            dialogVBox.getChildren().addAll(dialogHbox,dialogOk);
            VBox.setMargin(dialogOk,new Insets(0,0,0,170));
            
           
            
            /** exit the pop up window*/
            dialogOk.setOnAction(e->{dialog.close();});
            
            /**Create a scene  */
            Scene scene=new Scene (dialogVBox,450,100);
            
            dialog.setScene(scene);
            dialog.setTitle("Error Message");
            dialog.show();
        }
    
    
    /**Create Information Dialog method */
       public void infoDialog (Stage primaryStage, String text1)
       {
           /**Create a Stage  zip Files*/
            final Stage dialog=new Stage ();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(primaryStage);
            
            /**Set text properties */
            Text localText=new Text();
            localText.setText(text1);
            localText.setFill(Color.BLUE);
            localText.setFont(Font.font("Times new roman",20));
            
            /**Set image properties */
            ImageView image =new ImageView(new Image("file:info.jpg"));
           
            /**Set dialog HBox properties */
            HBox dialogHbox=new HBox();
            dialogHbox.setSpacing(10);
            dialogHbox.getChildren().addAll(image,localText);
            HBox.setMargin(image,new Insets(0,0,0,10));
            /**Set VBox properties */
            VBox dialogVBox=new VBox();
            Button dialogOk=new Button("Ok");
            dialogVBox.setSpacing(10);
            dialogVBox.getChildren().addAll(dialogHbox,dialogOk);
            VBox.setMargin(dialogOk,new Insets(0,0,0,170));
            
            /**Set the button properties */
            dialogOk.setPrefWidth(100);
            
            /** exit the pop up window*/
            dialogOk.setOnAction(new EventHandler <ActionEvent>(){
            
               public void handle (ActionEvent event)
               {
                   dialog.close();
               }
                
            });
            
            /**Create a scene  */
            Scene scene=new Scene (dialogVBox,450,100);
            
            dialog.setScene(scene);
            dialog.setTitle("Info Message");
            dialog.show();
       }
       
       
       
}//DELETE
