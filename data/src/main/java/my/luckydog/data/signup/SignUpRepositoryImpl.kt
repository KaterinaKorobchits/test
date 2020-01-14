package my.luckydog.data.signup

import io.reactivex.Single
import my.luckydog.boundaries.signup.errors.EmailAlreadyExist
import my.luckydog.boundaries.signup.repositories.SignUpRepository
import my.luckydog.data.bd.User
import my.luckydog.data.bd.UserDao

class SignUpRepositoryImpl(private val dao: UserDao) : SignUpRepository {

    override fun signUp(email: String, password: String) = dao.isExistEmail(email)
        .map { it == 1 }
        .flatMap { isExists ->
            if (isExists) Single.error(EmailAlreadyExist()) else
                dao.insert(User(email = email, password = password))
        }
}