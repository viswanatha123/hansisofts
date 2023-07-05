package framework.diagnostics;

import framework.diagnostics.Status.State;

public abstract class MonitoredComponent implements Monitorable {

    protected final String label;
    protected final boolean vital;

    protected Status status;

    public MonitoredComponent(String label, boolean vital) {
        this.label = label;
        this.vital = vital;

        this.status = new Status(State.UNINITIALIZED);
    }

    @Override
    public synchronized void initialize() { // Override if there is something to be done besides changing the status.
        status = new Status(State.OPERATIONAL);
    }

    @Override
    public void shutdown() {    // Override if there is something to be done besides changing the status.
        status = new Status(State.UNINITIALIZED);
    }

    @Override
    public void permanentShutdown() {   // Override if there are some special ContextDestroyed procedures to be done.
        shutdown();
    }

    @Override
    public synchronized void reload() {
        shutdown();
        initialize();
    }

    @Override
    public synchronized void declareMalfunction(Exception ex) {
        this.status = new Status(State.MALFUNCTION, ex);
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public boolean isVital() {
        return vital;
    }

    @Override
    public Status getStatus() {
        return status;
    }

}
