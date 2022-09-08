package il.ac.hit.expensemanagement.model;

import java.sql.Date;

public class Dates {
    private Date start;
    private Date end;

    public Dates(Date start, Date end) {
       setStart(start);
       setEnd(end);
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

}
