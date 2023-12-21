package recmanager;

/**Classes needed to build the Menu */
import javafx.collections.FXCollections;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import java.sql.*;


/**Classes needed to build the com box */
import javafx.scene.control.ComboBox;

/**Classes needed to build the table view */
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**Others */

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
 

public  class SearchPane extends BorderPane{
    
   protected final ObservableList<Recipe> data =FXCollections.observableArrayList();
            
    /**Create a menu bar*/
    private MenuBar menuBar=new MenuBar();
    protected Menu mainMenu=new Menu("Menu");
    protected MenuItem addRecipe=new MenuItem("Add Recipe");
    protected MenuItem deleteRecipe=new MenuItem("Delete Recipe");
    protected MenuItem updateRecipe=new MenuItem("Edit Recipe");
    protected MenuItem help=new MenuItem("Help");
    protected MenuItem exit =new MenuItem("Exit");
    
    /**List All button */
    
    protected Button listAllButton =new Button("List All Recipes");
    
    /** Create a combo box*/
    protected ComboBox<String> comboBoxSearch=new ComboBox<>();
    
    
    /**Create a a search field */
    protected TextField searchField =new TextField();
    
    
    /**Create table view and table columns*/
    protected TableView <Recipe>table =new TableView();
    protected TableColumn idCol=new TableColumn("Id");
    protected TableColumn rTitleCol =new TableColumn("Title");
    protected TableColumn rIngredientsCol=new TableColumn("Ingredients");
    protected TableColumn rInstructionsCol=new TableColumn("Instructions");
    protected TableColumn rauthorCol=new TableColumn("Author");
    protected TableColumn rcaloriesCol=new TableColumn("Calories");
    
    /**Create title label */
    private Label searchLabel =new Label("Search");
    
    /**Create a VBox */
    private HBox searchBox=new HBox();
    private VBox vbox=new VBox();
    
    
    /**Default constructor  */
    public SearchPane()
    {
        
        /**Set the menu properties  */
        menuBar.setStyle("-fx-background-color:rgb(143,188,143);");
        mainMenu.setStyle("-fx-font-size:16;"+"-fx-font-family:calibri;");
        
        menuBar.getMenus().add(mainMenu);
        mainMenu.getItems().addAll(addRecipe,deleteRecipe,updateRecipe,help,exit);
        
        searchLabel.setStyle("-fx-text-fill:black;"+"-fx-font-family:Arial;"+"-fx-font-size:13;");
        
        
        /**Set the  table view properties */
        table.getColumns().addAll(idCol,rTitleCol,rIngredientsCol,rInstructionsCol,rauthorCol,rcaloriesCol);
       
        idCol.setMinWidth(50);
        rTitleCol.setMinWidth(100);
        rIngredientsCol.setMinWidth(100);
        rInstructionsCol.setMinWidth(100);
        rauthorCol.setMinWidth(100);
        rcaloriesCol.setMinWidth(150);
        
        
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        rTitleCol.setCellValueFactory(new PropertyValueFactory<>("rTitle"));
        rIngredientsCol.setCellValueFactory(new PropertyValueFactory<>("rIngredients"));
        rInstructionsCol.setCellValueFactory(new PropertyValueFactory<>("rInstructions"));
        rauthorCol.setCellValueFactory(new PropertyValueFactory<>("rauthor"));
        rcaloriesCol.setCellValueFactory(new PropertyValueFactory<>("rcalories"));
        
        table.setItems(data);
       
        
        /**Set the search field properties */
        searchField.setStyle("-fx-background:yellow;");
        searchField.setPrefWidth(200);
        
        
        /**HBox properties */
        searchBox.setSpacing(10);
        searchBox.setPadding(new Insets(10,100,0,0));
        
        searchBox.getChildren().addAll(comboBoxSearch,searchField,listAllButton);
        
        
        /**Set combo box properties */
        comboBoxSearch.getItems().addAll("Id","Title","Ingredients","Instructions","Author","Calories");
        comboBoxSearch.setPrefWidth(150);
        comboBoxSearch.setValue("Id");
        
        
        /**HBox properties */
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10,10,10,10));
        vbox.getChildren().addAll(searchLabel,searchBox,table);
        
       
        
        /**Border pane*/
        setTop(menuBar);
        setCenter(vbox);
        
        
       
   }
    
    
    
}
