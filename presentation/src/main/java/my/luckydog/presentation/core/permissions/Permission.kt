package my.luckydog.presentation.core.permissions

sealed class Permission(name: String, state: State) {

    data class Granted(val name: String) : Permission(name, State.GRANTED)

    data class Denied(val name: String) : Permission(name, State.DENIED)

    data class DeniedNotShown(val name: String) : Permission(name, State.DENIED_NOT_SHOWN)

    data class RevokedByPolicy(val name: String) : Permission(name, State.REVOKED_BY_POLICY)
}