package my.luckydog.common

import sun.reflect.generics.reflectiveObjects.NotImplementedException

interface Mapper<E, R> {

    fun transform(entity: E): R {
        throw NotImplementedException()
    }

    fun transform(entity: E, param: Any): R = transform(entity)

    fun transform(entity: E, param1: Any, param2: Any): R = transform(entity, param1)

    fun transform(entity: E, param1: Any, param2: Any, param3: Any): R {
        return transform(entity, param1, param2)
    }

    fun transform(entities: List<E>): List<R> = entities.map { transform(it) }

    fun transform(entities: List<E>, param: Any): List<R> = entities.map { transform(it, param) }

    fun transform(entities: List<E>, param1: Any, param2: Any): List<R> {
        return entities.map { transform(it, param1, param2) }
    }

    fun transform(entities: List<E>, param1: Any, param2: Any, param3: Any): List<R> {
        return entities.map { transform(it, param1, param2, param3) }
    }

    fun transform(entities: Array<E>): List<R> = entities.map { transform(it) }

    fun transform(entities: Array<E>, param: Any): List<R> = entities.map { transform(it, param) }

    fun transform(entities: Array<E>, param1: Any, param2: Any): List<R> {
        return entities.map { transform(it, param1, param2) }
    }

    fun transform(entities: Array<E>, param1: Any, param2: Any, param3: Any): List<R> {
        return entities.map { transform(it, param1, param2, param3) }
    }
}