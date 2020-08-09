import com.sun.istack.internal.NotNull;

import java.util.*;
import java.lang.Math;

public abstract class Comment implements Votable, Commentable, Reportable, TimeMaintainable {
    private String content;

    private enum VoteType {
        UPVOTE, DOWNVOTE
    }

    private Map<String, VoteType> userVoteMap;

    private Integer downvotes = 0;

    private Integer upvotes = 0;

    private List<Comment> replies;

    private Boolean deleted;

    private Boolean hidden;

    private Long createdOn;

    private Long updatedOn;

    private static final Integer reportThreshold = 1000;

    private Set<String> reportedBy;

    Comment(String content) {
        this.content = content;
        this.userVoteMap = new HashMap<>();
        this.replies = new ArrayList<>();
        this.reportedBy = new HashSet<>();
        this.hidden = this.deleted = false;
        this.createdOn = this.updatedOn = new Date().getTime();
    }

    @Override
    public void addUpvote(String userId) {
        this.addVote(userId, VoteType.UPVOTE);
    }

    @Override
    public void removeUpvote(String userId) {
        this.removeVote(userId, VoteType.UPVOTE);
    }

    @Override
    public void addDownvote(String userId) {
        this.addVote(userId, VoteType.DOWNVOTE);
    }

    @Override
    public void removeDownvote(String userId) {
        this.removeVote(userId, VoteType.DOWNVOTE);
    }

    @Override
    public Integer getTotalUpvotes() {
        return this.upvotes;
    }

    @Override
    public Integer getTotalDownvotes() {
        return this.downvotes;
    }

    @Override
    public void addComment(@NotNull Comment comment) {
        this.replies.add(comment);
    }

    @Override
    public Integer getTotalComments() {
        return this.replies.size();
    }

    @Override
    public void report(@NotNull String userId) {
        this.reportedBy.add(userId);
        if (this.shouldBeHidden()) {
            this.hidden = true;
        }
    }

    @Override
    public Integer getTotalReportedBy() {
        return this.reportedBy.size();
    }

    @Override
    public Long getCreatedOn() {
        return this.createdOn;
    }

    @Override
    public  Long getUpdatedOn() {
        return this.updatedOn;
    }

    private Boolean shouldBeHidden() {
        return this.getTotalReportedBy() >= reportThreshold;
    }

    Boolean isHidden() {
        return this.hidden || this.deleted;
    }

    public String getContent() {
        return this.content;
    }

    public List<Comment> getReplies() {
        return this.replies;
    }

    public void editComment(@NotNull String content) {
        this.content = content;
        this.updatedOn = new Date().getTime();
    }

    public void deleteComment() {
        this.deleted = true;
    }

    private void addVote(String userId, VoteType voteType) {
        VoteType existing = this.userVoteMap.getOrDefault(userId, null);

        if (null == existing) {
            this.userVoteMap.put(userId, voteType);
            if (voteType == VoteType.UPVOTE) {
                upvotes++;
            }else {
                downvotes++;
            }
        } else if (existing != voteType) {
            this.userVoteMap.replace(userId, voteType);
            if (voteType == VoteType.UPVOTE) {
                upvotes++;
                downvotes--;
            } else {
                downvotes++;
                upvotes--;
            }
        }
    }

    private void removeVote(String userId, VoteType voteType) {
        VoteType existing = this.userVoteMap.getOrDefault(userId, null);

        if (existing == voteType) {
            this.userVoteMap.remove(userId);
            if (voteType == VoteType.UPVOTE) {
                upvotes--;
            } else {
                downvotes--;
            }
        }
    }
}
