package repo

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder

@Serializable
class MyBook (
    override var id: Int = -1,
    val readerId: Int,
    val title: String,
    val author: String,
    val deadlineCount: Int
) : Item

class MyBookTable : ItemTable<MyBook>() {
    val readerId = integer("readerId").references(readerTable.id, onDelete = ReferenceOption.CASCADE)
    val title = varchar("title", 50)
    val author = varchar("author", 50)
    val deadlineCount = integer("deadlineCount")

    override fun fill(builder: UpdateBuilder<Int>, item: MyBook) {
        builder[readerId] = item.readerId
        builder[title] = item.title
        builder[author] = item.author
        builder[deadlineCount] = item.deadlineCount
    }

    override fun readResult(result: ResultRow) =
        MyBook(
            result[id].value,
            result[readerId],
            result[title],
            result[author],
            result[deadlineCount]
        )
}

val myBookTable = MyBookTable()
