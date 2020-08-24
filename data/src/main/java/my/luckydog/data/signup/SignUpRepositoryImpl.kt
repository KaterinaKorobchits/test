package my.luckydog.data.signup

import io.reactivex.Single
import my.luckydog.boundaries.signup.errors.EmailAlreadyExist
import my.luckydog.boundaries.signup.repositories.SignUpRepository
import my.luckydog.data.app.db.user.User
import my.luckydog.data.app.db.user.UserDao
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(private val dao: UserDao) : SignUpRepository {

    init {
        println("!!!init: SignUpRepositoryImpl - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun signUp(email: String, password: String) = dao.isExistEmail(email)
        .map { it == 1 }
        .flatMap { isExists ->
            if (isExists) Single.error(EmailAlreadyExist()) else
                dao.insert(User(email = email, password = password))
        }
}