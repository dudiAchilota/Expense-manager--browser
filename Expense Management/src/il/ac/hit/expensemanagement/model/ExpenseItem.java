package il.ac.hit.expensemanagement.model;

import java.sql.Date;

/**
 * This class represents a person's expense
 */
public class ExpenseItem {

    private int id;
    private double sum;
    private String category;
    private String description;
    private Date date;
    private String nameUser;

    public ExpenseItem(){        
    }

    /**
     * Constructs a new expense
     * The Id is extracted from the database while add item to DB
     *
     * @param sum         The sum of the expense
     * @param category    To which string the expense belongs to
     * @param description A string described by the user about the expense
     * @param date        The time of the expense
     * @param nameUser
     */
    public ExpenseItem(double sum, String category, String description, Date date,String nameUser) {
        setSum(sum);
        setCategory(category);
        setDescription(description);
        setDate(date);
        setNameUser(nameUser);
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSum() {
        return this.sum;
    }

    public void setSum(double sum) {
        if (sum > 0.0D)
            this.sum = sum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toStringJson() {
        return "{" +
                " \"id\" :" + id +
                ", \"sum\" :"  + sum +
                ", \"category\" :" + '\"' + category + '\"' +
                ", \"description\" :"+ '\"' + description + '\"' +
                ", \"date\" :" + '\"' + date +'\"' +
                ", \"username\" :" + '\"' + nameUser +'\"' +
                '}';
    }

    @Override
    public String toString() {
    	return toStringJson();
		/*
		 * return "ExpenseItem{" + "id=" + id + ", sum=" + sum + ", category=" +
		 * category + ", description='" + description + '\'' + ", date=" + date + '}';
		 */
    }

}