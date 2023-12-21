package recmanager;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import javafx.scene.input.KeyCode;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.collections.ObservableList;

import java.sql.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AddRecipe {
    
    /**Create a private data to build the pane */
    private TextField tfRTitle=new TextField();
    private TextField tfRIngredients =new TextField();
    private TextField tfRInstructions=new TextField();
    private TextField tfRauthor=new TextField();
    private TextField tfRcalories=new TextField();
    
    private ArrayList<Label> labels= new ArrayList<Label>(Arrays.asList(
     new Label("Title: "), new Label("Ingredients: "), new Label("Instructions: "),
     new Label("Author: "), new Label("Calories: ")));
    
    private Button add=new Button("Add");
    private Button clear=new Button("Clear");
    private Button cancel =new Button ("Cancel");
    
    private GridPane gridPane =new GridPane();
    private VBox vbox =new VBox();
    private HBox hbox =new HBox();
    
    private Label title =new Label("Add Recipe: ");
    
    /**Create a default constructor */
    public AddRecipe(Stage primaryStage, Statement stm, ObservableList<Recipe> data, int [] startingPoint,ArrayList<Recipe> recipes )
    {
        
        title.setStyle("-fx-font-family:calibri;"+"-fx-font-size:17;");
        
        
        /**Set the text fields properties  */
        tfRTitle.setStyle("-fx-background:yellow;");
        tfRTitle.setPrefWidth(200);
        tfRIngredients.setStyle("-fx-background:yellow;");
        tfRInstructions.setStyle("-fx-background:yellow;");
        tfRauthor.setStyle("-fx-background:yellow;");
        tfRcalories.setStyle("-fx-background:yellow;");
        
        /**Set the label properties */
        for (int i=0; i<labels.size();i++)
        {
            labels.get(i).setStyle("-fx-text-fill:black;"+"-fx-font-family:calibri;"+"-fx-font-size:15;");
        }
        
        /**Set the button properties*/
        add.setPrefWidth(100);
        clear.setPrefWidth(100);
        cancel.setPrefWidth(100);
        
        /**Set the grid pane properties */
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        
        gridPane.add(labels.get(0),0,0);
        gridPane.add(tfRTitle,1,0);
        gridPane.add(labels.get(1),0,1);
        gridPane.add(tfRIngredients,1,1);
        gridPane.add(labels.get(2),0,2);
        gridPane.add(tfRInstructions,1,2);
        gridPane.add(labels.get(3),0,3);
        gridPane.add(tfRauthor,1,3);
        gridPane.add(labels.get(4), 0, 4);
        gridPane.add(tfRcalories,1,4);
        gridPane.add(hbox,0,5);
        
        
        
        /**Set the HBox properties */
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20);
        hbox.getChildren().addAll(add,clear,cancel);
        
        
        /**Set the VBox properties */
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10,15,0,0));
        vbox.getChildren().addAll(title,gridPane,hbox);
        
        /** Create a Scene */
        Scene scene=new Scene(vbox,400,300);
        final Stage mainStage=new Stage();
        mainStage.initModality(Modality.APPLICATION_MODAL);
        mainStage.initOwner(primaryStage);
        
        mainStage.setScene(scene);
        mainStage.setTitle("Add Recipe");
        mainStage.show();
        
        
        
        /**Register and handle the event fired by the clear button */
        clear.setOnAction(e->{clearTextFields();});
        
        
        /**Register and handle the event fires by the exit button */
        cancel.setOnAction(e->{mainStage.close();});
        
        /**Register and handle */
        
            add.setOnAction(e->{
              try{
              addNewRecipe(primaryStage,stm,data, startingPoint,recipes);
              }catch(Exception exception)
              {
                  popErrorMessage(mainStage,exception.getMessage());
              }
              
            });
            
       /**Enter Key */
       vbox.setOnKeyPressed(e->{
          if(e.getCode()==KeyCode.ENTER)
          {
              try{
                 addNewRecipe(primaryStage,stm,data, startingPoint,recipes);
              }catch(Exception exception)
              {
                  popErrorMessage(mainStage,exception.getMessage());
              }
          }
       });
        
    }
    
    
    /**Clear text fields */
    public void clearTextFields ()
    {
        tfRTitle.clear();
        tfRIngredients.clear();
        tfRInstructions.clear();
        tfRauthor.clear();
        tfRcalories.clear();
    }
    
    
    /**Add new recipe */
    public void addNewRecipe(Stage stage,Statement stm, ObservableList<Recipe> data, int [] startingPoint, ArrayList<Recipe> recipes) throws Exception
    {
        if (tfRTitle.getText().equals(""))
        {
            tfRTitle.requestFocus();
        }
        else if (tfRIngredients.getText().equals(""))
        {
            tfRIngredients.requestFocus();
        }
        else if (tfRInstructions.getText().equals(""))
        {
            tfRInstructions.requestFocus();
        }
        else if(tfRauthor.getText().equals(""))
        {
            tfRauthor.requestFocus();
        }
        else if (tfRcalories.getText().equals(""))
        {
            tfRcalories.requestFocus();
        }
        else {           
             
        String query ="insert into recipe (rtitle, ringredients, rinstructions, rauthor, rcalories) values "
                + "('"+tfRTitle.getText()+"','"+tfRIngredients.getText()+"','"+tfRInstructions.getText()+"','"+tfRauthor.getText()+"','"+tfRcalories.getText()+"')";
        
        stm.executeUpdate(query);
        clearTextFields();
        
        infoDialog(stage,"Recipe Was Added Successfully!!!");
        
         query ="select * from recipe where id>'"+startingPoint[0]+"'";
               ResultSet resultSet=stm.executeQuery(query);
               
               while(resultSet.next())
               {
                   data.add(new Recipe(
                    Integer.parseInt(resultSet.getString(1)),
                    resultSet.getString(2),
                    resultSet.getString(4),
                    resultSet.getString(3),
                    resultSet.getString(5),
                    resultSet.getDouble(6)
                   ));
                   
                    recipes.add(new Recipe(
                    Integer.parseInt(resultSet.getString(1)),
                    resultSet.getString(2),
                    resultSet.getString(4),
                    resultSet.getString(3),
                    resultSet.getString(5),
                    resultSet.getDouble(6)
                   ));
                   
                   startingPoint[0]=Integer.parseInt(resultSet.getString(1));
                   
               }
        }       
    }
    
    
   
    /**Create Information Dialog method */
       public void infoDialog (Stage primaryStage, String text1)
       {
           /**Create a Stage  */
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
    
}
