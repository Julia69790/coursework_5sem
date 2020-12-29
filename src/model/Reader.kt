package repo

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder

@Serializable
class Reader (
    val name: String,
    override var id: Int = -1
) : Item

class ReaderTable : ItemTable<Reader>() {
    val name = varchar("name", 50)

    override fun fill(builder: UpdateBuilder<Int>, item: Reader) {
        builder[name] = item.name
    }

    override fun readResult(result: ResultRow) =
        Reader(
            result[name],
            result[id].value
        )
}

val readerTable = ReaderTable()
