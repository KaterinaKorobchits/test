package my.luckydog.data.signin

import io.reactivex.Single.error
import my.luckydog.boundaries.signin.errors.CredentialsNotExists
import my.luckydog.boundaries.signin.repositories.SignInRepository
import my.luckydog.data.bd.UserDao

class SignInRepositoryImpl(private val dao: UserDao) :
    SignInRepository {

    override fun signIn(email: String, password: String) = dao.isExistUser(email, password)
        .map { it == 1 }
        .flatMap { isExist ->
            if (isExist) dao.getUserId(email) else error(CredentialsNotExists())
        }
}