package wang.hijack.mfe.gateway.model.dto;

/**
 * @author Jack
 */
public class StatusDto {
    private long id;
    private boolean enabled;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
