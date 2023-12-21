package recmanager;


import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.sql.*;
import javafx.scene.input.KeyCode;
import javafx.scene.Scene;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import recmanager.SearchPane;
import recmanager.AddRecipe;
import recmanager.deleteRecipe;
import recmanager.UpdateRecipe;
import recmanager.Recipe;


public class RecipeManager extends Application {
    
    private SearchPane searchPane =new SearchPane();
    private Connection conn;
    private Statement stm;
    private int [] startingPoint=new int []{0};
    private ArrayList<Recipe> recipes =new ArrayList<Recipe>();
    
    
    
    @Override
    public void start(Stage primaryStage) {
        
        /**Connect to a database  */
        connectToDataBase();
        
        listAllRecipe();
     
        recipes.addAll(searchPane.data);
        
        /**List all Recipe in the database */
         searchPane.listAllButton.setOnAction(new EventHandler<ActionEvent>() {

             public void handle(ActionEvent event) {
                 
                 searchPane.data.clear();
                 searchPane.data.addAll(recipes);
                  
             }
         });
         
         
         /**Add Recipe  */
         searchPane.addRecipe.setOnAction(new EventHandler<ActionEvent>(){
                 public void handle (ActionEvent event)
                 {
                     new AddRecipe(primaryStage,stm, searchPane.data, startingPoint,recipes);
                 }
         });
         
         
         
         /**Delete recipes */
         searchPane.deleteRecipe.setOnAction(e->{
             new deleteRecipe(primaryStage,stm,searchPane.data,recipes);
           
         });
           
         
          
         
         /**Update Recipe */
         searchPane.updateRecipe.setOnAction(e->{new UpdateRecipe(primaryStage, stm, searchPane.data, startingPoint,recipes);});
         
         
         
         /**Search */
         searchPane.setOnKeyPressed(e->{
         
            if(e.getCode()==KeyCode.ENTER)
            {
                search(primaryStage);
            }
             
         });
        
         
         /**Exit the application*/
        searchPane.exit.setOnAction(new EventHandler<ActionEvent>(){
           public void handle(ActionEvent event)
           {
               System.exit(0);
           }
        
        });
         
         
        /**Create a scene  */
        Scene scene =new Scene(searchPane,610,500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Recipe Manager");
        primaryStage.show();
        
    }
    
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    
   
    public void connectToDataBase()
    {
        try {
            
          //  Class.forName("com.mysql.jdbc.Driver");
            
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/RecManager","root","");
            
            stm=conn.createStatement();
            
        }catch(Exception exception)
        {
            System.out.println(exception.getMessage());
        }
    }
    
    
    
    
    
    
    public void listAllRecipe()
    {
        
        try{
            
               String query ="select * from recipe where id>'"+startingPoint[0]+"'";
               ResultSet resultSet=stm.executeQuery(query);
               
               while(resultSet.next())
               {
                    searchPane.data.add(new Recipe(
                    Integer.parseInt(resultSet.getString(1)),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDouble(6)
                   ));
                   
                  startingPoint[0]=Integer.parseInt(resultSet.getString(1));
               }
               
            
        }catch(Exception exception)
        {
            System.out.println(exception.getMessage());
        }
       
    }
    
    
    
    
    /**Search method */
    public void search(Stage primaryStage)
    {
        int index=-1;
        int exist=0;
        ArrayList<Integer> indexes=new ArrayList<>();
        
        if (searchPane.searchField.getText().equals(""))
            {
                searchPane.searchField.requestFocus();
            }
        else
        {
        
        if (searchPane.comboBoxSearch.getValue().equals("Id"))
        {
                
                for(int i=0; i<recipes.size();i++)
                {
                    
                    if (recipes.get(i).getId()==Integer.parseInt(searchPane.searchField.getText()))
                    {
                       exist=1;
                       index=i;
                    }
                }
                if (exist==1)
                {
                   searchPane.data.clear();
                   searchPane.data.add(recipes.get(index));
                   startingPoint[0]=0;
                }
                else
                {
                    popErrorMessage(primaryStage,"Recipe Does Not Exist !!!");
                }
          }
        else if (searchPane.comboBoxSearch.getValue().equals("Title"))
        {
                
                for(int i=0; i<recipes.size();i++)
                {
                    
                    if (recipes.get(i).getRTitle().equals(searchPane.searchField.getText()))
                    {
                       exist=1;
                       indexes.add(i);
                    }
                }
                if (exist==1)
                {
                   searchPane.data.clear();
                   for(int i=0; i<indexes.size();i++) 
                   {
                      searchPane.data.add(recipes.get(indexes.get(i)));
                   }
                   
                   startingPoint[0]=0;
                }
                else
                {
                    popErrorMessage(primaryStage,"Recipe Does Not Exist!!!");
                }
        }
         else if (searchPane.comboBoxSearch.getValue().equals("Ingredients"))
        {
                
                for(int i=0; i<recipes.size();i++)
                {
                    
                    if (recipes.get(i).getRIngredients().equals(searchPane.searchField.getText()))
                    {
                       exist=1;
                       indexes.add(i);
                    }
                }
                if (exist==1)
                {
                   searchPane.data.clear();
                   for(int i=0; i<indexes.size();i++) 
                   {
                      searchPane.data.add(recipes.get(indexes.get(i)));
                   }
                   
                   startingPoint[0]=0;
                }
                else
                {
                    popErrorMessage(primaryStage,"Recipe Does Not Exist!!!");
                }
          }
        
         else if (searchPane.comboBoxSearch.getValue().equals("Instructions"))
        {
                
                for(int i=0; i<recipes.size();i++)
                {
                    
                    if (recipes.get(i).getRInstructions().equals(searchPane.searchField.getText()))
                    {
                       exist=1;
                       indexes.add(i);
                    }
                }
                if (exist==1)
                {
                   searchPane.data.clear();
                   for(int i=0; i<indexes.size();i++) 
                   {
                      searchPane.data.add(recipes.get(indexes.get(i)));
                   }
                   
                   startingPoint[0]=0;
                }
                else
                {
                    popErrorMessage(primaryStage,"Recipe Does Not Exist!!!");
                }
          }
         else if (searchPane.comboBoxSearch.getValue().equals("Author"))
        {
                
                for(int i=0; i<recipes.size();i++)
                {
                    
                    if (recipes.get(i).getRauthor().equals(searchPane.searchField.getText()))
                    {
                       exist=1;
                       indexes.add(i);
                    }
                }
                if (exist==1)
                {
                   searchPane.data.clear();
                   for(int i=0; i<indexes.size();i++) 
                   {
                      searchPane.data.add(recipes.get(indexes.get(i)));
                   }
                   
                   startingPoint[0]=0;
                }
                else
                {
                    popErrorMessage(primaryStage,"Recipe Does Not Exist!!!");
                }
        }
         else if (searchPane.comboBoxSearch.getValue().equals("Calories"))
        {
                
                for(int i=0; i<recipes.size();i++)
                {
                    
                    if (recipes.get(i).getRcalories()==Double.parseDouble(searchPane.searchField.getText()))
                    {
                       exist=1;
                       indexes.add(i);
                    }
                }
                if (exist==1)
                {
                   searchPane.data.clear();
                   for(int i=0; i<indexes.size();i++) 
                   {
                      searchPane.data.add(recipes.get(indexes.get(i)));
                   }
                   
                   startingPoint[0]=0;
                }
                else
                {
                    
                    popErrorMessage(primaryStage,"Recipe Does Not Exist!!!");
                }
          }
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
    
}
