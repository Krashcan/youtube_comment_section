import com.sun.istack.internal.NotNull;

import java.util.*;
import java.lang.Math;

public abstract class Comment implements Votable, Commentable, Reportable, TimeMaintainable {
    private String content;

    private enum VoteType {
        UPVOTE, DOWNVOTE
    }

    private Map<String, VoteType> userVoteMap;

    private Integer downvotes;

    private Integer upvotes;

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
        this.deleted = false;
        this.createdOn = this.updatedOn = new Date().getTime();
    }

    @Override
    public void addUpvote(String userId) {
        changeVote(userId, VoteType.UPVOTE, 1);
    }

    @Override
    public void removeUpvote(String userId) {
        changeVote(userId, VoteType.UPVOTE, -1);
    }

    @Override
    public void addDownvote(String userId) {
        changeVote(userId, VoteType.DOWNVOTE, 1);
    }

    @Override
    public void removeDownvote(String userId) {
        changeVote(userId, VoteType.DOWNVOTE, -1);
    }

    @Override
    public Integer getTotalUpvotes() {
        return upvotes;
    }

    @Override
    public Integer getTotalDownvotes() {
        return downvotes;
    }

    @Override
    public void addComment(@NotNull Comment comment) {
        replies.add(comment);
    }

    @Override
    public Integer getTotalComments() {
        return replies.size();
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
        return content;
    }

    public void editComment(@NotNull String content) {
        this.content = content;
        this.updatedOn = new Date().getTime();
    }

    public void deleteComment() {
        deleted = true;
    }

    private void changeVote(String userId, VoteType voteType, Integer changeBy) {
        if (Math.abs(changeBy) != 1) {
            throw new IllegalArgumentException("Trying to change vote by more than 1 not allowed");
        }

        VoteType existing = userVoteMap.getOrDefault(userId, null);
        if (null == existing) {
            userVoteMap.put(userId, voteType);
            if (voteType == VoteType.UPVOTE) {
                upvotes += changeBy;
            }else {
                downvotes += changeBy;
            }
        } else if (existing != voteType) {
            userVoteMap.replace(userId, voteType);
            if (voteType == VoteType.UPVOTE) {
                upvotes += changeBy;
                downvotes -= changeBy;
            } else {
                downvotes += changeBy;
                upvotes -= changeBy;
            }
        }
    }
}
