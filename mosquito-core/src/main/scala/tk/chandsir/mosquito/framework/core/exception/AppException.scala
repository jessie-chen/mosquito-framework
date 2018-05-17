package tk.chandsir.mosquito.framework.core.exception

class AppException(message: String, cause: Throwable) extends RuntimeException(message, cause) {
  def this(message: String) = this(message, null)
}
