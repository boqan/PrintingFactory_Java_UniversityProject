package PrintingFactoryProducts;

import Interfaces.Generatable;

import java.util.Comparator;
import java.util.Random;

public class Book extends Publication implements Generatable {

    private String author;
    private final String isbn;

    public Book(String title, int numberOfPages, PageSize pageSize, String author) {
        super(title, numberOfPages, pageSize);
        this.author = author;
        this.isbn = generateID();
    }

    public String generateID(){
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
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public static Comparator<Book> comparatorByISBN = new Comparator<Book>() {
        @Override
        public int compare(Book o1, Book o2) {
            return o1.isbn.compareTo(o2.isbn);
        }
    };

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                "} " + super.toString();
    }
}
