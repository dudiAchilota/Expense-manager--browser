package il.ac.hit.expensemanagement.model;

/**
 * This class represents a category for an expense
 */
public class Category {

    private int id;
    private String category;
    private User user;

    public Category() {}

    public Category(String category) {
        setCategory(category);
    }
    
    public Category(String category,User user) {
        setCategory(category);
        setUser(user);
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
   
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    @Override
    public String toString() {
        return "id=" + id +
               ", category='" + category + '\'' ;
    }
}
