package fr.denom

import scala.collection.SortedSet

trait DataFrameView {
  def schema: SortedSet[Column]
  def toString: String
}

trait QueryView extends DataFrameView {
  def query: String
  override def toString: String = query
}

final case class SimpleQuery(schema: SortedSet[Column], query: String) extends QueryView {
  override def toString: String = query
}

final case class ProjectionQuery(source: DataFrameView,
                                 drop: SortedSet[Column] = SortedSet.empty,
                                 add: SortedSet[Column] = SortedSet.empty,
                                 replace: Map[Column, Column] = Map.empty) extends QueryView {
  val schema: SortedSet[Column] = Schema.from(source.schema.map(x => SimpleColumn(x.name)), drop, add, replace)
  val query: String =
    s"""SELECT
       | (${Schema.asColumnsExpression(schema)})
       |FROM
       | (
       |   ${source.toString}
       | )""".stripMargin
}

trait TableView extends DataFrameView {
  def name: String
}

final case class SimpleTable(schema: SortedSet[Column], name: String) extends TableView {
  override def toString: String = name
}

object Schema {
  def from(base: SortedSet[Column], drop: SortedSet[Column], add: SortedSet[Column], replace: Map[Column, Column]): SortedSet[Column] = {
    (base ++ add).collect {
      case col if replace.contains(col) => replace(col)
      case col if !drop.contains(col) => col
    }
  }

  def asColumnsExpression(schema: SortedSet[Column]): String = {
    schema.mkString(", ")
  }
}