package PrintingFactoryProducts;

public class Poster extends Publication{

    private String categoryOfPoster;

    public Poster(String title, int numberOfPages, PageSize pageSize, String categoryOfPoster) {
        super(title, numberOfPages, pageSize);
        this.categoryOfPoster = categoryOfPoster;
    }

    public String getCategoryOfPoster() {
        return categoryOfPoster;
    }

    public void setCategoryOfPoster(String categoryOfPoster) {
        this.categoryOfPoster = categoryOfPoster;
    }

    @Override
    public String toString() {
        return "Poster{" +
                "categoryOfPoster='" + categoryOfPoster + '\'' +
                '}';
    }
}
