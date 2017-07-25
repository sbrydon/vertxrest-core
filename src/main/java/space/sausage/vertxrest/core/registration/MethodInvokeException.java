package space.sausage.vertxrest.core.registration;

/**
 * Indicates invocation caused a reflection exception
 */
public class MethodInvokeException extends Exception {
    public MethodInvokeException(String message, Throwable cause) {
        super(message, cause);
    }
}
