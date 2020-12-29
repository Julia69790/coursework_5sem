package repo

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder

@Serializable
class MyDebt (
    override var id: Int = -1,
    val myBookId: Int,
    val readerId: Int,
    var fine: Int = 0
) : Item

class MyDebtTable : ItemTable<MyDebt>() {
    val myBookId = integer("myBookId").references(myBookTable.id)
    val readerId = integer("readerId").references(readerTable.id)
    var fine = integer ("fine")
    override fun fill(builder: UpdateBuilder<Int>, item: MyDebt) {
        builder[myBookId] = item.myBookId
        builder[readerId] = item.readerId
        builder[fine] = item.fine
    }

    override fun readResult(result: ResultRow) =
        MyDebt(
            result[id].value,
            result[myBookId],
            result[readerId],
            result[fine]
        )
}

val myDebtTable = MyDebtTable()
