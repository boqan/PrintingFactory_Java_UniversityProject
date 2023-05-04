package PrintingFactoryProducts;

import java.util.Comparator;

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
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public static Comparator<Poster> comparatorByCategoryOfPoster = new Comparator<Poster>() {
        @Override
        public int compare(Poster o1, Poster o2) {
            return o1.categoryOfPoster.compareTo(o2.categoryOfPoster);
        }
    };

    @Override
    public String toString() {
        return "Poster{" +
                "categoryOfPoster='" + categoryOfPoster + '\'' +
                '}';
    }
}
