package framework.diagnostics;

public class Status {

    public enum State {
        UNINITIALIZED, OPERATIONAL, MALFUNCTION, BUSY
    }

    private final State state;
    private final Exception exception;

    public Status(State state) {
        this.state = state;
        this.exception = null;
    }

    public Status(State state, Exception exception) {
        this.state = state;
        this.exception = exception;
    }

    public State getState() {
        return state;
    }

    public Exception getException() {
        return exception;
    }

    public boolean isOperational() {
        return (state == State.OPERATIONAL || state == State.BUSY);
    }

    public boolean isBusy() {
        return (state == State.BUSY);
    }

    public boolean isMalfunction() {
        return (state == State.MALFUNCTION);
    }

    public boolean isUninitialized() {
        return (state == State.UNINITIALIZED);
    }

}
