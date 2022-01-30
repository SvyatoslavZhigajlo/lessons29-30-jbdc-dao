public class Book {
    public int id;
    public String title;
    public int authorId;

    public Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    public Book(String title, int authorId) {
        this.title = title;
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                '}';
    }
}
