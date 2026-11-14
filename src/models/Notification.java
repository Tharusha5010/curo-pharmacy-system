package models;



//Declare Class and Encapsulated Variables
public class Notification {
    
    private String type;
    private String title;
    private String message;


    
    // Object creation & data initialization
    public Notification() {}

    
    public Notification(String type, String title, String message) {
        this.type = type;
        this.title = title;
        this.message = message;
    }

    

    //Reading and Updating private fields
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type; 
    }
    

    public String getTitle() { 
        return title;
    }
    public void setTitle(String title) { 
        this.title = title; 
    }

    
    public String getMessage() {
        return message; 
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
