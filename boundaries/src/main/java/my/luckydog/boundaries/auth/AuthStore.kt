package my.luckydog.boundaries.auth

interface AuthStore {

    fun hasAuthorize(): Boolean

    fun storeHasAuthorize(hasAuthorize: Boolean)
}