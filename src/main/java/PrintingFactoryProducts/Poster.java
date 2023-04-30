package PrintingFactoryProducts;

public class Poster {

    private String categoryOfPoster;

    public Poster(String categoryOfPoster) {
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
