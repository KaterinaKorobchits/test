package my.luckydog.presentation.core.permissions

enum class State {
    GRANTED,

    /** Permission has been denied. */
    DENIED,

    /**
     * Permission is denied.
     * Previously the requested permission was denied and never ask again was selected.
     * This means that the user hasn't seen the permission dialog.
     * The only way to let the user grant the permission is via the settings now.
     */
    DENIED_NOT_SHOWN,

    /** Permission has been revoked by a policy. */
    REVOKED_BY_POLICY
}