public interface Votable {
    void addUpvote(String userId);
    void removeUpvote(String userId);
    void addDownvote(String userId);
    void removeDownvote(String userId);
    Integer getTotalUpvotes();
    Integer getTotalDownvotes();
}
