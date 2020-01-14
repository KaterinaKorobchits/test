package my.luckydog.domain.splash

import my.luckydog.boundaries.auth.AuthStore
import my.luckydog.boundaries.session.SessionStore
import my.luckydog.interactors.SplashInteractor

class SplashInteractorImpl(
    private val sessionStore: SessionStore,
    private val authStore: AuthStore
) : SplashInteractor {

    override fun hasAuthorize() = authStore.hasAuthorize()

    override fun hasSession() = sessionStore.getUserId() != 0L
}