package wang.hijack.mfe.gateway.model.exception;

/**
 * @author Jack
 */
public class EntityNotExistException extends Exception {
    private static final long serialVersionUID = -844697420081729286L;

    public EntityNotExistException(String message) {
        super(message);
    }

    public EntityNotExistException(String type, Long id) {
        super(String.format("%s:%d不存在", type, id));
    }
}
