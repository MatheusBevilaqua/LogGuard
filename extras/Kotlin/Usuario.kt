class Usuario (
    var email: String = "",
    var senha: String = "",
    var isAdm: Boolean = false
) {

    fun visualizarLogs(logs: List<Log>): String {
        if (isAdm) {
            var retorno: String = ""
            for(e in logs) { retorno += "\n - $e" }
            return retorno
        } else {
          return "Acesso negado. Somente administradores podem visualizar os logs."
        }
    }

}