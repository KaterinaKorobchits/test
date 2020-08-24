package my.luckydog.data.signin

import io.reactivex.Single.error
import my.luckydog.boundaries.signin.errors.CredentialsNotExists
import my.luckydog.boundaries.signin.repositories.SignInRepository
import my.luckydog.data.app.db.user.UserDao
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(private val dao: UserDao) :
    SignInRepository {

    init {
        println("!!!init: SignInRepositoryImpl - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun signIn(email: String, password: String) = dao.isExistUser(email, password)
        .map { it == 1 }
        .flatMap { isExist ->
            if (isExist) dao.getUserId(email) else error(CredentialsNotExists())
        }
}