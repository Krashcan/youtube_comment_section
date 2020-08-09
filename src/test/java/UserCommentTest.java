import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

public class UserCommentTest{
    private static User user;

    @BeforeAll
    static void setUp() {
        user = new User("TestUser", "testing.jpg");
    }

    private List<UserComment> getUserCommentForTestingVotes() {
        UserComment uc1 = new UserComment(user, "Testing add upvote with no initial vote");

        UserComment uc2 = new UserComment(user, "Testing add upvote with initial upvote");
        uc2.addUpvote(user.getUserId());

        UserComment uc3 = new UserComment(user, "Testing add upvote with initial downvote");
        uc3.addDownvote(user.getUserId());

        return new ArrayList<>(Arrays.asList(uc1, uc2, uc3));
    }

    @Test
    void testAddUpvote() {
        List<UserComment> userComments = getUserCommentForTestingVotes();
        userComments.forEach((uc) -> {
            uc.addUpvote(user.getUserId());
            assertEquals(1, uc.getTotalUpvotes(), "asserting upvotes for: " + uc.getContent());
            assertEquals(0, uc.getTotalDownvotes(), "asserting downvotes for: " + uc.getContent());
        });
    }

    @Test
    void testRemoveUpvote() {
        List<UserComment> userComments = getUserCommentForTestingVotes();
        userComments.forEach((uc) -> {
            Integer downvotes = uc.getTotalDownvotes();
            uc.removeUpvote(user.getUserId());
            assertEquals(0, uc.getTotalUpvotes(), "asserting upvotes for: " + uc.getContent());
            assertEquals(downvotes, uc.getTotalDownvotes(), "asserting downvotes for: " + uc.getContent());
        });
    }

    @Test
    void testAddDownvote() {
        List<UserComment> userComments = getUserCommentForTestingVotes();
        userComments.forEach((uc) -> {
            uc.addDownvote(user.getUserId());
            assertEquals(0, uc.getTotalUpvotes(), "asserting upvotes for: " + uc.getContent());
            assertEquals(1, uc.getTotalDownvotes(), "asserting downvotes for: " + uc.getContent());
        });
    }

    @Test
    void testRemoveDownvote() {
        List<UserComment> userComments = getUserCommentForTestingVotes();
        userComments.forEach((uc) -> {
            Integer upvotes = uc.getTotalUpvotes();
            uc.removeDownvote(user.getUserId());
            assertEquals(upvotes, uc.getTotalUpvotes(), "asserting upvotes for: " + uc.getContent());
            assertEquals(0, uc.getTotalDownvotes(), "asserting downvotes for: " + uc.getContent());
        });
    }

    @Test
    void testReport() {
        UserComment userComment = new UserComment(user, "Test report");
        IntStream.range(1, 1200).forEach((i) -> userComment.report(UUID.randomUUID().toString()));

        assert userComment.isHidden();
    }
}
