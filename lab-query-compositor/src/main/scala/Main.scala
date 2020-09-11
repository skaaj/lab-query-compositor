import fr.denom._
import fr.denom.Column.ColumnExt

object Main extends App {
  val simpleTable = SimpleTable(
    schema = Seq(col"id", col"value"),
    name = "simple_table"
  )

  val simpleQuery = SimpleQuery(
    schema = simpleTable.schema ++ Seq(col"double_value"),
    query =
      s"""SELECT st.*, 2 * value AS double_value
         |FROM ($simpleTable) st
         |WHERE value > 0""".stripMargin
  )

  println(simpleQuery)
}

