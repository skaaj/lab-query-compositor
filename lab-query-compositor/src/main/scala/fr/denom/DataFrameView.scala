package fr.denom

trait DataFrameView {
  def schema: Seq[Column]
  def toString: String
}

trait QueryView extends DataFrameView {
  def query: String
}

final case class SimpleQuery(schema: Seq[Column], query: String) {
  override def toString: String = query
}

trait TableView extends DataFrameView {
  def name: String
}

final case class SimpleTable(schema: Seq[Column], name: String) {
  override def toString: String = name
}




