package my.luckydog.boundaries.app.auth

interface AuthStore {

    fun hasAuthorize(): Boolean

    fun storeHasAuthorize(hasAuthorize: Boolean)
}