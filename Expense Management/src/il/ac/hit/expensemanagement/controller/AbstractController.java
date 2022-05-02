package il.ac.hit.expensemanagement.controller;

import il.ac.hit.expensemanagement.model.IModelExpenseDAO;

public abstract class AbstractController {

    protected IModelExpenseDAO dao;

    AbstractController(IModelExpenseDAO dao) {
        this.dao = dao;
    }
}
