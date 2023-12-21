package recmanager;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;



public class Recipe {
    
    /**Create private properties */
    
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty rTitle;
    private final SimpleStringProperty rIngredients; 
    private final SimpleStringProperty rInstructions;
    private final SimpleStringProperty rauthor;
    private final SimpleDoubleProperty rcalories;
    
    
    /**Constructor with specific properties */
    
    public Recipe (int id, String rTitle, String rIngredients, String rInstructions, String rauthor, double rcalories)
    {
        this.id=new SimpleIntegerProperty(id);
        this.rTitle=new SimpleStringProperty(rTitle);
        this.rIngredients= new SimpleStringProperty(rIngredients);
        this.rInstructions=new SimpleStringProperty(rInstructions);
        this.rauthor=new SimpleStringProperty(rauthor);
        this.rcalories=new SimpleDoubleProperty(rcalories);
    }
    
    /**getter and setter */
    public int getId()
    {
        return id.get();
    }
    
    public String getRTitle()
    {
        return rTitle.get();
    }
    
    public String getRIngredients()
    {
        return rIngredients.get();
    }
    
    public String getRInstructions()
    {
        return rInstructions.get();
    }
    
    public String getRauthor()
    {
        return rauthor.get();
    }
    
    public Double getRcalories()
    {
        return rcalories.get();
    }
    
    
    public void setId(int id)
    {
        this.id.set(id);
    }
    
    public void setRTitle(String rTitle)
    {
        this.rTitle.set(rTitle);
    }
    
     
    public void setRIngredients(String rIngredients)
    {
        this.rIngredients.set(rIngredients);
    }
    
    
      public void setRInstructions(String rInstructions)
    {
        this.rInstructions.set(rInstructions);
    }
    
    public void setRauthor(String rauthor)
    {
        this.rauthor.set(rauthor);
    }
    
    public void setRcalories(double rcalories)
    {
        this.rcalories.set(rcalories);
    }
}
