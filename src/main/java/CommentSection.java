import com.sun.istack.internal.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class CommentSection implements Commentable {

    private List<Comment> comments;

    public enum SortField {
        NONE, UPVOTES, NEWEST_FIRST
    }

    private static Map<SortField, Comparator<Comment>> sortingMap;

    @Override
    public void addComment(@NotNull Comment comment) {
        if (comment.isHidden()) {
            return;
        }
        this.comments.add(comment);
    }

    @Override
    public Integer getTotalComments() {
        return this.comments.size();
    }

    public List<Comment> getCommentsSortedBy(SortField sortField) {
        if (sortField == SortField.NONE) {
            return new ArrayList<>(comments);
        }
        return comments.stream().sorted(sortingMap.get(sortField)).collect(Collectors.toList());
    }

    private static void initializeSortingMap() {
        sortingMap = new HashMap<>();
        sortingMap.put(SortField.UPVOTES,
                Comparator.comparing(Comment::getTotalUpvotes).reversed());
        sortingMap.put(SortField.NEWEST_FIRST,
                Comparator.comparing(Comment::getCreatedOn).reversed());
    }
}
