package il.ac.hit.expensemanagement.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.lang.reflect.*;

@WebServlet(name = "RouterServlet", value = "/router/*")
public class RouterServlet extends HttpServlet {
    /*
    Each request's URL will include the controller name and the
    action name (in that specific controller) at which the request
    targets.
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = request.getRequestURI();
        String[] arr = text.split("/");
        String controller = "";
        String action = "";

        if (arr.length > 4) {
            controller = arr[3];
            if (controller.equals("*")) {
                controller = "api";//
            }
            action = arr[4].toLowerCase();
        } else {
            showErrorMessage(request, response, "Error");
        }


        try {
            // calling the right action on the right controller

            Class c = this.getClass();
            String s = String.valueOf(c.getPackage());
            String[] s1 = s.split("package ");
            String className = s1[1] + "." + controller.substring(0, 1).toUpperCase() +
                    controller.substring(1).toLowerCase() + "Controller";


            Class clazz = Class.forName(className);
            Constructor constructor = clazz.getConstructor(il.ac.hit.expensemanagement.model.IModelExpenseDAO.class);
            Object object = constructor.newInstance
                    (il.ac.hit.expensemanagement.model.ModelExpenseDAO.getInstance());


            Method method = clazz.getMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(object, request, response);

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("file" + action + ".jsp");
            dispatcher.forward(request, response);

        } catch (ClassNotFoundException e) {
            showErrorMessage(request, response, "The requested controler doesnot exist");
        } catch (NoSuchMethodException e) {
            showErrorMessage(request, response, "Problem with instantiating the Model class " + e.getMessage());
        } catch (InvocationTargetException e) {
            showErrorMessage(request, response, "Problem with instantiating the Model class or invoking the action");
        } catch (InstantiationException e) {
            showErrorMessage(request, response, "Problem with instantiating the Model class " + e.getMessage());
        } catch (IllegalAccessException e) {
            showErrorMessage(request, response, "Problem with instantiating the Model class " + e.getMessage());
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void showErrorMessage(HttpServletRequest request, HttpServletResponse response, String text) throws ServletException, IOException {
        request.setAttribute("errormessage", text);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error.jsp");
        dispatcher.forward(request, response);
    }
}
