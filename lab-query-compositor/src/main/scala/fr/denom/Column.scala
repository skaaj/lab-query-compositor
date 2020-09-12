package fr.denom

sealed trait Column {
  def name: String
}

final case class SimpleColumn(override val name: String) extends Column {
  override def toString: String = name
}

final case class ComputedColumn(override val name: String, expression: String) extends Column {
  override def toString: String = s"$expression AS $name"
}

object Column {
  implicit val columnOrdering: Ordering[Column] = Ordering.by(_.name)

  implicit class ColumnExt(val sc: StringContext) extends AnyVal {
    def col(args: Any*): Column = {
      val statics = sc.parts.iterator
      val expressions = args.iterator

      val sb = new StringBuilder(statics.next())

      while (expressions.hasNext) {
        sb.append(expressions.next().toString)
        sb.append(statics.next())
      }

      SimpleColumn(sb.toString)
    }
  }
}