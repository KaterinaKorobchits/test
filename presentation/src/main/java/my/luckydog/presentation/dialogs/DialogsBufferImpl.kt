package my.luckydog.presentation.dialogs

import javax.inject.Inject

class DialogsBufferImpl @Inject constructor() : DialogsBuffer {

    init {
        println("!!!init: DialogsBufferImpl - ${this::class.java.name} ${this.hashCode()}")
    }

    private val dialogsParams = LinkedHashMap<String, DialogParams>()

    override fun getFirst(): DialogParams = dialogsParams.entries.first().value

    override fun getFirstId(): String = dialogsParams.entries.first().key

    override fun getIds(): List<String> = ArrayList<String>(dialogsParams.keys)

    override fun add(id: String, params: DialogParams) {
        dialogsParams[id] = params
    }

    override fun remove(id: String) {
        dialogsParams.remove(id)
    }

    override fun isContain(id: String): Boolean = dialogsParams.containsKey(id)

    override fun isNotEmpty(): Boolean = dialogsParams.isNotEmpty()
}