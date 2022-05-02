package il.ac.hit.expensemanagement.model;

import java.sql.Date;
import java.util.List;

/**
 * IModelExpenseDAO handles all the data in the database
 *
 */
public interface IModelExpenseDAO {

    /**
     * Adds a new expense to the database
     *
     * @param item ExpenseItem object holds a reference to a new expense
     * @throws ExpenseManagerException
     */
    public void addExpenseItem(ExpenseItem item) throws ExpenseManagerException;


    /**
     * Deletes an expense by id
     *
     * @param 
     * @throws ExpenseManagerException
     */
    public void deleteExpenseItem(int id) throws ExpenseManagerException;


    /**
     * Extracts all the data from the database on expense item
     *
     * @return DataAllExpenses object which holds all the expense item data
     * @throws ExpenseManagerException
     */
    public Report getAllExpenses(String user) throws ExpenseManagerException;


    /**
     * Builds a Report object that holds all the expense items in the database between start and end dates
     *
     * @param start Holds reference to an sql.date object for the starting date
     * @param end   Holds reference to an sql.date object for the ending date
     * @return Report object that holds all the data between the dates start and end
     * @throws ExpenseManagerException
     */
    public Report getReport(Date start, Date end,String user) throws ExpenseManagerException;


    /**
     * Adds a new category to the table categories in the database
     *
     * @param category Represents the name of the new category
     * @throws ExpenseManagerException
     */
    public void addCategory(Category category) throws ExpenseManagerException;

    /**
     * Extracts all the categories from the database table: categories
     *
     * @return list of categories
     * @throws ExpenseManagerException
     */
    public List<Category> getCategories() throws ExpenseManagerException;


    /**
     * add user new
     * @param user
     * @throws ExpenseManagerException
     */
    public void addUser(User user) throws ExpenseManagerException;

    /**
     *
     * @param userName
     * @return true if a similar user exists else false
     * @throws ExpenseManagerException
     */
    public Boolean checkExistingUserName(String userName) throws ExpenseManagerException;

    /**
     *
     * @param user
     * @return true if successful login else false
     * @throws ExpenseManagerException
     */
    public Boolean login(User user) throws ExpenseManagerException;

}