package il.ac.hit.expensemanagement.controller;

import il.ac.hit.expensemanagement.model.*;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;


@RestController
public class ExpenseManagementController {

    private static String user = null;
    private IModelExpenseDAO model = ModelExpenseDAO.getInstance();


    @PostMapping("/ExpenseManagement/session")
    public String session() {
        int ok;
        if (this.user != null) {
            ok = 1;
        } else {
            ok = 0;
        }

        String output = "{ \"user\" : " + "\"" + this.user + "\" ";
        output += ", \"ok\" : " + "\"" + ok + "\" ";
        output += "}";

        return output;
    }

    @PostMapping("/ExpenseManagement/logout")
    public String logout() {
        this.user = null;
        String output = "";
        return output;
    }

    @PostMapping("/ExpenseManagement/registration")
    public String registration(@RequestBody User user) {
        String message = "";
        try {
            if (model.checkExistingUserName(user.getUserName()) == true) {
                message = "such a username already exists";
            } else {
                model.addUser(user);
                message = "add user successfully username = " + user.getUserName();
            }
        } catch (ExpenseManagerException e) {
            //e.printStackTrace();
            message = "Error -" + e.getMessage();
        }

        String output = "{ \"ok\" :" + 1;
        output += ", \"message\" :" + " \" " + message + " \" ";
        output += "}";

        return output;
    }

    @PostMapping("/ExpenseManagement/login")
    public String login(@RequestBody User user) {
        String userName = "", message = "";
        int ok = 0;
        try {
            if (model.login(user) == true) {
                userName = user.getUserName();
                ok = 1;
                message = "login did succeed";
            } else {
                userName = null;
                ok = 0;
                message = "login did not succeed";
            }

        } catch (ExpenseManagerException e) {
            //e.printStackTrace();
            message = "Error -" + e.getMessage();
        }
        this.user = userName;
        String output = "{ \"ok\" :" + ok;
        output += ", \"message\" :" + " \" " + message + " \" ";
        output += "}";
        return output;
    }

    @PostMapping("/ExpenseManagement/add")
    public String add(@RequestBody ExpenseItem item) {
        int ok = 1;
        String message = "";
        String output = "";
        item.setNameUser(this.user);

        try {
            model.addExpenseItem(item);

        } catch (ExpenseManagerException e) {
            ok = 0;
        }
        output = "{ \"ok\" :" + ok;
        output += ", \"message\" :" + " \" " + message + " \" ";
        output += "}";

        return output;
    }

    @PostMapping("/ExpenseManagement/items")
    public String items() {
        String output = "";
        try {
            if (this.user == null) {

            } else {
                List<?> data = model.getAllExpenses(this.user).getListItem();
                output = "{ \"items\" :" + data + "}";
            }

        } catch (ExpenseManagerException e) {
            e.printStackTrace();
        }

        return output;
    }


    @PostMapping("/ExpenseManagement/report")
    public String report(@RequestBody Dates dates) {
        String output = "";

        try {
            String user = this.user;
            if (user == null) {

            } else {
                Report report = model.getReport(dates.getStart(),dates.getEnd(),user);
                List<?> items = report.getListItem();
                output = "{ \"items\" :" + items ;
                output += ", \"sum\" :" + report.sumTotal();
                output +=  "}";
            }

        } catch (ExpenseManagerException e) {
            //e.printStackTrace();

        }

        return output;
    }

    @PostMapping("/ExpenseManagement/home")
    public String home() {
        String output = "";

        try {
            String user = this.user;
            if (user == null) {

            } else {

                java.util.Date date =  new java.util.Date();
                int month = date.getMonth() + 1;
                int year = date.getYear() + 1900;

                int []daysInMonth = {0,31,28,31,30, 31,30,31,31, 30,31,30,31};
                // 28 or 29
                if( (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0) ) {
                    daysInMonth[2] = 29;
                }

                List<Integer> months = new LinkedList();
                List<Integer> years = new LinkedList();

                int y;
                for (int i = 0; i < 6; i++) {
                    y = month - i - 1;
                    if(y <= 0) {
                        months.add(y + 12);
                        years.add(year - 1);
                    }else {
                        months.add(y);
                        years.add(year);
                    }
                }

                // -------------------------------------
                List<Double> total = new LinkedList();
                Report report = null;
                Date start,end;

                for (int i = 0; i < 6; i++) {
                    start = new Date(years.get(i) - 1900, months.get(i) -1, 1);
                    end   = new Date(years.get(i) - 1900, months.get(i) - 1, daysInMonth[months.get(i)]);
                    report = model.getReport(start,end,user);
                    total.add(report.sumTotal());
                }

                // current month
                start = new Date(year - 1900, month -1, 1);
                end =   new Date(year - 1900, month - 1, daysInMonth[month]);
                double current = model.getReport(start,end,user).sumTotal();


                output = "{ \"items\" : ["  ;
                for (int i = 0; i < 6; i++) {
                    output += "{ \"sum\" :"+ total.get(i) +" }";
                    if(i != 5) { output += ",";}
                }
                output += "]";

                output +=  " ,\"current\" :" + current ;
                output += "}";
            }
        }
        catch (NumberFormatException | ExpenseManagerException e) {
            //e.printStackTrace();
        }


        return output;
    }

    @GetMapping("/ExpenseManagement/delete/{id}")
    public String delete(@PathVariable int id) {

        try {
            model.deleteExpenseItem(id);
        } catch (ExpenseManagerException e) {
            e.printStackTrace();
        }
        return "";
    }


}
