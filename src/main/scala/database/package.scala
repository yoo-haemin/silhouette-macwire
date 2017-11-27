import io.getquill.{ MysqlAsyncContext, Escape }

package object database {
  type DbContext = MysqlAsyncContext[Escape]
}
