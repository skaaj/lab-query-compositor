package fr.denom

final case class Column(name: String)


object Column {
  implicit class ColumnExt(val sc: StringContext) extends AnyVal {
    def col(args: Any*): Column = {
      val static = sc.parts.iterator
      val exprs = args.iterator

      val sb = new StringBuilder(static.next())

      while (exprs.hasNext) {
        sb.append(exprs.next().toString)
        sb.append(static.next())
      }

      Column(sb.toString)
    }
  }
}