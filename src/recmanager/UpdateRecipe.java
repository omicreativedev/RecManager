package recmanager;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UpdateRecipe {
    
    int elementPosition=-1;
    /**Create text fields */
    private TextField tfId=new TextField();
    private TextField tfRTitle=new TextField ();
    private TextField tfRIngredients =new TextField();
    private TextField tfRInstructions  =new TextField();
    private TextField tfRauthor =new TextField();
    private TextField tfRcalories =new TextField ();
    
    /**Create Labels */
    private ArrayList<Label> labels= new ArrayList<Label>(Arrays.asList( new Label("Id"),
     new Label("Title: "), new Label("Ingredients: "), new Label("Instructions: "),
     new Label("Author: "), new Label("Calories: ")));
    
    private Button update=new Button("Update");
    private Button clear=new Button("Clear");
    private Button cancel =new Button ("Cancel");
    
    private GridPane gridPane =new GridPane();
    private VBox vbox =new VBox();
    private HBox hbox =new HBox();
    
    private Label title =new Label("Update recipe: ");
    
   /**Create a default constructor */
    public UpdateRecipe (Stage primaryStage, Statement stm, ObservableList<Recipe> data, int [] startingPoint, ArrayList<Recipe> recipes )
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
        update.setPrefWidth(100);
        clear.setPrefWidth(100);
        cancel.setPrefWidth(100);
        
        /**Set the grid pane properties */
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        
        gridPane.add(labels.get(0),0,0);
        gridPane.add(tfId, 1, 0);
        gridPane.add(labels.get(1),0,1);
        gridPane.add(tfRTitle,1,1);
        gridPane.add(labels.get(2),0,2);
        gridPane.add(tfRIngredients,1,2);
        gridPane.add(labels.get(3),0,3);
        gridPane.add(tfRInstructions,1,3);
        gridPane.add(labels.get(4),0,4);
        gridPane.add(tfRauthor,1,4);
        gridPane.add(labels.get(5), 0,5);
        gridPane.add(tfRcalories,1,5);
        gridPane.add(hbox,0,6);
        
        
        
        /**Set the HBox properties */
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20);
        hbox.getChildren().addAll(update,clear,cancel);
        
        
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
        
            update.setOnAction(e->{
              try{
                    
                  updateRecipe(mainStage,data,stm,recipes);
                  
              }catch(Exception exception)
              {
                  popErrorMessage(mainStage,exception.getMessage());
              }
              
            });
            
            
       /**Enter Key 
       vbox.setOnKeyPressed(e->{
          if(e.getCode()==KeyCode.ENTER)
          {
              try{
                 
                  updateRecipe(mainStage,data,stm,recipes);
                  
              }catch(Exception exception)
              {
                  popErrorMessage(mainStage,exception.getMessage());
              }
          }
       });*/
       
       
       /** Recipe information */
       tfId.setOnKeyPressed(e->{
       
          if(e.getCode()==KeyCode.ENTER) 
          {
              try{
                  recipeInfo(data,mainStage);
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
        tfId.clear ();
        tfRTitle.clear();
        tfRIngredients.clear();
        tfRInstructions.clear();
        tfRauthor.clear();
        tfRcalories.clear();
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
    
    
    /**Update method  */
    public void recipeInfo(ObservableList<Recipe> data, Stage stage) throws Exception
    {
       
        int exist=0;
        
        
        if(tfId.getText().equals(""))
        {
            tfId.requestFocus();        
        }
        else
        {
           for(int i=0;i<data.size();i++)
           {
               if(data.get(i).getId()==Integer.parseInt(tfId.getText()))
               {
                   exist=1;
                   elementPosition=i;
                   
                   break;
                   
               }
           }
           if(exist==1)
           {
              tfRTitle.setText(data.get(elementPosition).getRTitle());
              tfRIngredients.setText(data.get(elementPosition).getRIngredients());
              tfRInstructions.setText(data.get(elementPosition).getRInstructions());
              tfRauthor.setText(data.get(elementPosition).getRauthor());
              tfRcalories.setText(String.valueOf(data.get(elementPosition).getRcalories()));
              
           }
           else
           {
               popErrorMessage(stage,"Recipe does not exist.\nPlease Enter Another Id Number!!!");
           }
        }
    }
    
    
    /**Update Recipe */
    
    public void updateRecipe(Stage stage, ObservableList<Recipe> data, Statement stm, ArrayList<Recipe> recipes) throws Exception
    {
        int id=Integer.parseInt(tfId.getText());
        String rTitle=tfRTitle.getText();
        String rIngredients=tfRIngredients.getText();
        String rInstructions=tfRInstructions.getText();
        String rauthor =tfRauthor.getText();
        double rcalories=Double.parseDouble(tfRcalories.getText());
        
        String query="update recipe set rtitle='"+rTitle+"', ringredients='"+rIngredients+"', rinstructions='"+rInstructions+"', rauthor='"+rauthor+"', rcalories='"+rcalories+"' where id='"+id+"'";
        
        
        if(tfRcalories.getText().equals(""))
        {
            tfId.requestFocus();
        }
        else
        {
            stm.executeUpdate(query);
            
            data.set(elementPosition, new Recipe(id,rTitle, rIngredients, rInstructions, rauthor, rcalories));
            recipes.set(elementPosition, new Recipe(id,rTitle, rIngredients, rInstructions, rauthor, rcalories));
            
            clearTextFields();
                                                                   
            infoDialog(stage,"Recipe was successfully updated.");
        }
    }
    
    
}
