public class Review {
    public int id;
    public int bookId;
    public int userId;
    public String review;

    public Review() {
    }

    public Review(int bookId, int userId, String review) {
        this.bookId = bookId;
        this.userId = userId;
        this.review = review;
    }
}
