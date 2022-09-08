package il.ac.hit.expensemanagement.model;

import java.util.List;

/**
 * This class represents a detailed report of expenses between certain dates
 */
public class Report {

    private List<ExpenseItem> listItem;

    /**
     * @param listItem
     */
    public Report(List<ExpenseItem> listItem) {
        setListItem(listItem);
    }

    public List<ExpenseItem> getListItem() {
        return listItem;
    }

    public void setListItem(List<ExpenseItem> listItem) {
        this.listItem = listItem;
    }

    @Override
    public String toString() {
        return "Report{" +
                "listItem=" + listItem +
                '}';
    }

    /**
     *
     * @return
     */
    public double sumTotal() {
    	double sum = 0.0; 
    	for (int i = 0; i < listItem.size(); i++) {
    		sum += listItem.get(i).getSum();
    	}   	
        return sum;
    }
       
}
