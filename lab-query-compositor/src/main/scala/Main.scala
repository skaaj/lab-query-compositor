import fr.denom._
import fr.denom.Column.ColumnExt

import scala.collection.SortedSet

object Main extends App {
  val simpleTable = SimpleTable(
    schema = SortedSet(col"id", col"value"),
    name = "simple_table"
  )

  val simpleQuery = ProjectionQuery(
    source = simpleTable,
    add = SortedSet(ComputedColumn("double", "2 * value")),
  )

  val parentQuery = ProjectionQuery(
    source = simpleQuery,
    drop = SortedSet(col"id"),
    add = SortedSet(col"padding"),
    replace = Map(
      col"double" -> ComputedColumn("quadruple", "2 * double")
    )
  )

  println(parentQuery)
}

