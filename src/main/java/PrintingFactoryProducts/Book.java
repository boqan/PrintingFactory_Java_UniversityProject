package PrintingFactoryProducts;

import java.util.Random;

public class Book extends Publication{

    private String author;
    private final String isbn;

    public Book(String title, int numberOfPages, PageSize pageSize, String author, String isbn) {
        super(title, numberOfPages, pageSize);
        this.author = author;
        this.isbn = GenerateIsbn();
    }

    private String GenerateIsbn(){
        Random random = new Random();
        long randomNumber = (long) (random.nextDouble() * 1_000_000_000_000L);
        return "ISBN" + String.format("%013d", randomNumber);
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                "} " + super.toString();
    }
}
