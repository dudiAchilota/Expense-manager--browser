package il.ac.hit.expensemanagement.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import il.ac.hit.expensemanagement.model.*;


public class ApiController extends AbstractController {

    public ApiController(IModelExpenseDAO dao) {
        super(dao);
    }
    
    public void registration(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");

        if (userName != null || password != null) {
            userName = userName.trim();
            password = password.trim();
            if (!userName.equals("") && !password.equals("")) {
                try {
                    User user = new User(userName, password);
                    if (dao.checkExistingUserName(userName) == true) {
                        request.setAttribute("data", "such a username already exists");
                    } else {
                        dao.addUser(user);
                        request.setAttribute("data", "add user successfully username = " + userName);
                    }
                } catch (ExpenseManagerException e) {
                    e.printStackTrace();
                    request.setAttribute("data", "Error -"+e.getMessage()); 
                }
            } else {
                request.setAttribute("data", "Error in registration Missing data");
            }
        }
    }

    public void login(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        
        if (userName != null || password != null) {
            try {
                User user = new User(userName, password);
                if (dao.login(user) == true) {
                    request.getSession().setAttribute("user", userName);
                    request.setAttribute("ok", 1);
                    request.setAttribute("message", "login did succeed");                     
                } else {
                	request.getSession().setAttribute("user", null);
                	request.setAttribute("ok", 0);                	
                    request.setAttribute("message", "login did not succeed");             
                }

            } catch (ExpenseManagerException e) {
                e.printStackTrace();
                request.setAttribute("message", "Error -"+e.getMessage());
            }
        }

    }

    public void session(HttpServletRequest request, HttpServletResponse response) {
   	
    }
    
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("user",null);
     }
    
    public void add(HttpServletRequest request, HttpServletResponse response) {
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        String sumStr = request.getParameter("sum");
        String dateStr = request.getParameter("date");
        String user = (String) request.getSession().getAttribute("user");
        double sum;
        Date date;

        request.setAttribute("ok", 0);
        request.setAttribute("message", "");
        
        if (description != null || category != null || sumStr != null || dateStr != null) {
            description = description.trim();
            dateStr = dateStr.trim();
            category = category.trim();
            sumStr = sumStr.trim();
            if (description != "" || category != "" || sumStr != "" || dateStr != "") {
                try {
                    sum = Double.parseDouble(sumStr);
                    date = Date.valueOf(dateStr);
                    dao.addExpenseItem(new ExpenseItem(sum, category, description, date, user));
                    request.setAttribute("ok", 1);

                } catch (NumberFormatException |ExpenseManagerException e) {
                    e.printStackTrace();
                    request.setAttribute("message", "Error -"+e.getMessage());
                }
            } else {
                request.setAttribute("ok", 0);
                request.setAttribute("message", "ERROR - In addition");
            }
        }
    }

    public void items(HttpServletRequest request, HttpServletResponse response) {
        try {
            String user = (String) request.getSession().getAttribute("user");
            if (user == null) {
                request.getRequestDispatcher(Utils.file + "login.jsp").forward(request, response);
            } else {
                List<?> data = dao.getAllExpenses(user).getListItem();
                request.setAttribute("data", data);
            }

        } catch (ExpenseManagerException |ServletException | IOException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error -"+e.getMessage());
        }
    }

    public void report(HttpServletRequest request, HttpServletResponse response) {
    	 try {    		
    		 Date start = Date.valueOf(request.getParameter("datefrom").trim()); 
    		 Date end = Date.valueOf(request.getParameter("dateto").trim());
    		
             String user = (String) request.getSession().getAttribute("user");
             if (user == null) {
                 request.getRequestDispatcher(Utils.file + "login.jsp").forward(request, response);
             } else {
            	 Report report = dao.getReport(start,end,user);
                 List<?> items = report.getListItem();
                 request.setAttribute("items", items);
                 request.setAttribute("sum", report.sumTotal());
             }

         } catch (ExpenseManagerException |ServletException | IOException e) {
             e.printStackTrace();
             request.setAttribute("message", "Error -"+e.getMessage());
         }   	
    }
    
    public void delete(HttpServletRequest request, HttpServletResponse response) {
        try {
        	String idStr = request.getParameter("id");
        	int id = Integer.parseInt(idStr);
            String user = (String) request.getSession().getAttribute("user");
            if (user == null) {
                request.getRequestDispatcher(Utils.file + "login.jsp").forward(request, response);
            } else {
                dao.deleteExpenseItem(id);              
            }
        }
          catch (NumberFormatException | ExpenseManagerException | ServletException  | IOException e) {
                e.printStackTrace();
                request.setAttribute("message", "Error -"+e.getMessage());
        }     	
    }
        
    public void home(HttpServletRequest request, HttpServletResponse response) {
       try {
    	   String user = (String) request.getSession().getAttribute("user");
    	   if (user == null) {
    		   request.getRequestDispatcher(Utils.file + "login.jsp").forward(request, response);
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
    	            report = dao.getReport(start,end,user);    			   
    	            total.add(report.sumTotal());   			   
    		   }
    	 
    		   // current month
    		   start = new Date(year - 1900, month -1, 1);
    		   end =   new Date(year - 1900, month - 1, daysInMonth[month]);
    		   double current = dao.getReport(start,end,user).sumTotal();
    		   
    		   
    		   String str = "{ \"items\" : ["  ;  
    		   for (int i = 0; i < 6; i++) {            	 
    			   str += "{ \"sum\" :"+ total.get(i) +" }";
    			   if(i != 5) { str += ",";}
    		   }
    		   str += "]";
    		   
    		   str +=  " ,\"current\" :" + current ;
    		   str += "}";    		          
    		   request.setAttribute("data", str);
    	   }   
       }
       catch (NumberFormatException | ExpenseManagerException | ServletException  | IOException e) {
    	   e.printStackTrace();
    	   request.setAttribute("message", "Error -"+e.getMessage());      
       	}
    } 
    
    
}





