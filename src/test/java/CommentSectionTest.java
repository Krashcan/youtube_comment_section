import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class CommentSectionTest {
    private static User user;

    @BeforeAll
    static void setUp() {
        user = new User("TestUser", "testing.jpg");
    }


    List<UserComment> getCommentWithRandomUpvotes() {
        List<UserComment> comments = new ArrayList<>();
        IntStream.range(1, 3).forEach(
            (i) -> {
                UserComment uc = new UserComment(user, "test" + i);
                IntStream.range(1, ThreadLocalRandom.current().nextInt(1, 11)).forEach(
                        (j) -> uc.addUpvote(UUID.randomUUID().toString()));
                comments.add(uc);
            }
        );
        Collections.shuffle(comments);
        return comments;
    }

    @Test
    void testCommentsSortOrderNone() {
        CommentSection cs = new CommentSection();
        List<UserComment> comments = getCommentWithRandomUpvotes();

        comments.forEach(cs::addComment);

        Integer totalComments = cs.getTotalComments();
        cs.getCommentsSortedBy(CommentSection.SortField.NONE).add(new UserComment(user, ""));

        assertEquals(totalComments, cs.getTotalComments(), "adding to comments returned should not affect " +
                "internal comments");
    }

    @Test
    void testCommentsSortOrderNewest() {
        CommentSection cs = new CommentSection();
        List<UserComment> comments = getCommentWithRandomUpvotes();

        comments.forEach(cs::addComment);

        List<Comment> sortedComments = cs.getCommentsSortedBy(CommentSection.SortField.NEWEST);

        IntStream.range(1, sortedComments.size()-1).forEach(
                (i) -> {
                    assert sortedComments.get(i).getCreatedOn() < sortedComments.get(i-1).getCreatedOn();
                }
        );
    }

    @Test
    void testCommentsSortOrderUpvotes() {
        CommentSection cs = new CommentSection();
        List<UserComment> comments = getCommentWithRandomUpvotes();

        comments.forEach(cs::addComment);

        List<Comment> sortedComments = cs.getCommentsSortedBy(CommentSection.SortField.UPVOTES);

        IntStream.range(1, sortedComments.size()-1).forEach(
                (i) -> {
                    assert sortedComments.get(i).getTotalUpvotes() < sortedComments.get(i-1).getTotalUpvotes();
                }
        );
    }
}
