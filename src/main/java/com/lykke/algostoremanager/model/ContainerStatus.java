package com.lykke.algostoremanager.model;

/**
 * Created by niau on 12/19/17.
 */
public enum ContainerStatus {

    //created, restarting, running, removing, paused, exited, dead

    status_created("CREATED"),
    status_restarted("RESTARTING"),
    status_running("RUNNING"),
    status_up("UP"),
    status_removing("REMOVING"),
    status_paused("PAUSED"),
    status_exited("EXITED"),
    status_dead("DEAD");

    private final String status;

    /**
     * @param status
     */
    private ContainerStatus(final String status) {
        this.status = status;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return status;
    }



}
