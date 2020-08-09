import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class User implements Reportable{
    private String userId;

    private String name;

    private String pic;

    private Set<String> reportedBy;

    User(String name, String pic) {
        this.userId = UUID.randomUUID().toString();
        this.name = name;
        this.pic = pic;
        this.reportedBy = new HashSet<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public void report(String userId) {
        this.reportedBy.add(userId);
    }

    @Override
    public Integer getTotalReportedBy() {
        return this.reportedBy.size();
    }
}
