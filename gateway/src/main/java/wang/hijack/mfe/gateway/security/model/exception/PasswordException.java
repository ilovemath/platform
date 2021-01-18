package wang.hijack.mfe.gateway.security.model.exception;

/**
 * @author Jack
 */
public class PasswordException extends Exception {
    private static final long serialVersionUID = 1402169297504359168L;

    public PasswordException() {
    }

    public PasswordException(String message) {
        super(message);
    }

    public static PasswordException invalid() {
        return new PasswordException("密码不正确");
    }

    public static PasswordException illegal() {
        return new PasswordException("密码太弱");
    }

    public static PasswordException duplicate() {
        return new PasswordException("与历史密码重复");
    }

    public static PasswordException misMatch() {
        return new PasswordException("两次输入不一致");
    }
}
