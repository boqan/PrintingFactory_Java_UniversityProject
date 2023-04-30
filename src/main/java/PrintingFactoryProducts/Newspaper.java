package PrintingFactoryProducts;

import java.time.LocalDate;

public class Newspaper extends Publication{

    private String redactor;

    private LocalDate dateOfIssue;

    public Newspaper(String title, int numberOfPages, PageSize pageSize, String redactor, LocalDate dateOfIssue) {
        super(title, numberOfPages, pageSize);
        this.redactor = redactor;
        this.dateOfIssue = dateOfIssue;
    }

    public String getRedactor() {
        return redactor;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setRedactor(String redactor) {
        this.redactor = redactor;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    @Override
    public String toString() {
        return "Newspaper{" +
                "redactor='" + redactor + '\'' +
                ", dateOfIssue=" + dateOfIssue +
                "} " + super.toString();
    }
}
