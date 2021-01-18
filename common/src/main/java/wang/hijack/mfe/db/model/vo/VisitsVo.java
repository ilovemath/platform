package wang.hijack.mfe.db.model.vo;

/**
 * @author Jack
 */
public class VisitsVo {
    private long newIp;
    private long newVisits;
    private long recentIp;
    private long recentVisits;

    public VisitsVo(long newIp, long newVisits, long recentIp, long recentVisits) {
        this.newIp = newIp;
        this.newVisits = newVisits;
        this.recentIp = recentIp;
        this.recentVisits = recentVisits;
    }

    public long getNewIp() {
        return newIp;
    }

    public void setNewIp(long newIp) {
        this.newIp = newIp;
    }

    public long getNewVisits() {
        return newVisits;
    }

    public void setNewVisits(long newVisits) {
        this.newVisits = newVisits;
    }

    public long getRecentIp() {
        return recentIp;
    }

    public void setRecentIp(long recentIp) {
        this.recentIp = recentIp;
    }

    public long getRecentVisits() {
        return recentVisits;
    }

    public void setRecentVisits(long recentVisits) {
        this.recentVisits = recentVisits;
    }
}
