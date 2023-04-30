package PrintingFactoryProducts;

public class Publication {
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
    public String toString() {
        return "Publication{" +
                "title='" + title + '\'' +
                ", numberOfPages=" + numberOfPages +
                ", pageSize=" + pageSize +
                '}';
    }
}
