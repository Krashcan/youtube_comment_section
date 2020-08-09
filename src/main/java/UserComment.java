import com.sun.istack.internal.NotNull;

import java.util.HashSet;
import java.util.Set;

public class UserComment extends Comment {
    private User author;

    UserComment(@NotNull User author, @NotNull String content) {
        super(content);
        this.author = author;
    }

    public User getAuthor() {
        return this.author;
    }

}
