package global;
import java.io.Serializable;
import java.util.Date;

public class VoteInformation implements Serializable {
    private Date voteDate;



    private int studentId;



    private String voterID;

    public VoteInformation(Date voteDate, int voterId, String voterID) {
        this.voteDate = voteDate;
        this.studentId = voterId;
        this.voterID = voterID;
    }

    public Date getVoteDate() {
        return voteDate;
    }
    public String getVoterID() {
        return voterID;
    }

    public int getVoterId() {
        return studentId;
    }
}
