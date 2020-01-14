package my.luckydog.presentation.dialogs

class DialogsBufferImpl : DialogsBuffer {

    private val dialogsParams = LinkedHashMap<String, DialogParams>()

    override fun getFirst(): DialogParams = dialogsParams.entries.first().value

    override fun getFirstId(): String = dialogsParams.entries.first().key

    override fun add(id: String, params: DialogParams) {
        dialogsParams[id] = params
    }

    override fun remove(id: String) {
        dialogsParams.remove(id)
    }

    override fun isContain(id: String): Boolean = dialogsParams.containsKey(id)

    override fun isNotEmpty(): Boolean = dialogsParams.isNotEmpty()

    override fun clear() = dialogsParams.clear()
}