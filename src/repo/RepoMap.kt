package repo

class RepoMap<T : Item> : Repo<T> {

    private var maxId = 0
    private val _repo = HashMap<Int, T>()

    override fun add(element: T) =
            if (_repo.containsValue(element))
                false
            else {
                val newId = maxId++
                element.id = newId
                _repo[newId] = element
                true
            }

    override fun get(id: Int) =
            _repo[id]

    override fun all() =
            _repo.values.toList()

    override fun update(id: Int, element: T) =
            if (_repo.containsKey(id)) {
                _repo[id] = element
                true
            } else
                false

    override fun delete(id: Int) =
            if (_repo.containsKey(id)) {
                _repo.remove(id)
                true
            } else
                false
}