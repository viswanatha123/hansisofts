package framework.diagnostics;

public interface Monitorable {

    public void initialize();   // Initialization of a component (Monitorable object)

    public void shutdown(); // Regular shutdown

    public void reload();

    public Status getStatus();

    public String getLabel();

    public boolean isVital();   // Should the Dashboard stop the initialization until the issue is resolved?

    public void permanentShutdown();    // Context-destroyed event, mainly for deregistering JDBC drivers

    public void declareMalfunction(Exception ex);   // Used by the Dashboard to handle poorly written Monitorable objects
}
