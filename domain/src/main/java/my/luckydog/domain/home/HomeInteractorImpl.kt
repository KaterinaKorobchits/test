package my.luckydog.domain.home

import my.luckydog.boundaries.auth.AuthStore
import my.luckydog.boundaries.session.SessionStore
import my.luckydog.interactors.HomeInteractor

class HomeInteractorImpl(
    private val sessionStore: SessionStore,
    private val authStore: AuthStore
) : HomeInteractor {

    override fun storeHasAuthorize() = authStore.storeHasAuthorize(true)

    override fun logout() = sessionStore.clear()
}