package my.luckydog.domain.splash

import my.luckydog.boundaries.app.auth.AuthStore
import my.luckydog.boundaries.app.session.SessionStore
import my.luckydog.interactors.SplashInteractor
import javax.inject.Inject

class SplashInteractorImpl @Inject constructor(
    private val sessionStore: SessionStore,
    private val authStore: AuthStore
) : SplashInteractor {

    init {
        println("!!!init: SplashInteractorImpl - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun hasAuthorize() = authStore.hasAuthorize()

    override fun hasSession() = sessionStore.getUserId() != 0L
}