package repo

interface Repo<T: Item> {

    fun add(element: T): Boolean // null if element was in repo

    fun get(id: Int): T? // null if id is absent

    fun all(): List<T> // read all

    fun update(id: Int, element: T): Boolean // false if id is absent

    fun delete(id: Int): Boolean // false if id is absent

}