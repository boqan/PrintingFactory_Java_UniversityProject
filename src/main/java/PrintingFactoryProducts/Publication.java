package PrintingFactoryProducts;

import java.io.Serializable;
import java.util.Objects;

public class Publication implements Comparable<Publication>, Serializable {
    private String title;

    private int numberOfPages;

    private PageSize pageSize;

    public Publication(String title, int numberOfPages, PageSize pageSize) {
        this.title = title;
        this.numberOfPages = numberOfPages;
        this.pageSize = pageSize;
    }

    public String getTitle() {
        return title;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public PageSize getPageSize() {
        return pageSize;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public void setPageSize(PageSize pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publication that = (Publication) o;
        return Objects.equals(getTitle(), that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }

    @Override
    public String toString() {
        return "Publication{" +
                "title='" + title + '\'' +
                ", numberOfPages=" + numberOfPages +
                ", pageSize=" + pageSize +
                '}';
    }

    @Override
    public int compareTo(Publication object) {
        return this.title.compareTo(object.title);
    }
}
