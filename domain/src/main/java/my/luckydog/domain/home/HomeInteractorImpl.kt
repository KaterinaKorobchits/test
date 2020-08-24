package my.luckydog.domain.home

import io.reactivex.schedulers.Schedulers
import my.luckydog.boundaries.app.auth.AuthStore
import my.luckydog.boundaries.app.session.SessionStore
import my.luckydog.interactors.HomeInteractor
import my.luckydog.interactors.core.image.GetImagesCase
import javax.inject.Inject

class HomeInteractorImpl @Inject constructor(
    private val sessionStore: SessionStore,
    private val authStore: AuthStore,
    private val case: GetImagesCase
) : HomeInteractor {

    init {
        println("!!!init: HomeInteractorImpl - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun storeHasAuthorize() = authStore.storeHasAuthorize(true)

    override fun logout() = sessionStore.clear()

    override fun images(text: String) = case.nextImages(text).subscribeOn(Schedulers.io())
}