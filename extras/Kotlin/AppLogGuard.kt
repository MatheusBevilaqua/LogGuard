import kotlin.concurrent.thread
import kotlin.reflect.typeOf

val servidor = Servidor()
val servidores = mutableListOf<Servidor>()
val logs = mutableListOf<Log>()
val usuarios = mutableListOf<Usuario>()
var usuarioLogado = Usuario()

fun main() {

    println(""" 
 █████                           █████████                                     █████
░░███                           ███░░░░░███                                   ░░███ 
 ░███         ██████   ███████ ███     ░░░  █████ ████  ██████   ████████   ███████ 
 ░███        ███░░███ ███░░███░███         ░░███ ░███  ░░░░░███ ░░███░░███ ███░░███ 
 ░███       ░███ ░███░███ ░███░███    █████ ░███ ░███   ███████  ░███ ░░░ ░███ ░███ 
 ░███      █░███ ░███░███ ░███░░███  ░░███  ░███ ░███  ███░░███  ░███     ░███ ░███ 
 ███████████░░██████ ░░███████ ░░█████████  ░░████████░░████████ █████    ░░████████
░░░░░░░░░░░  ░░░░░░   ░░░░░███  ░░░░░░░░░    ░░░░░░░░  ░░░░░░░░ ░░░░░      ░░░░░░░░ 
                      ███ ░███                                                      
                     ░░██████                                                       
                      ░░░░░░                                                        
     """.trimIndent())
    Thread.sleep(800)

    while(true) {

        repeat(20) { print("\n") }

        val escolha: Int = perguntar("""
                        ---/ MENU - LogGuard v1 /---
            (Digite algum dos números a seguir para escolher a opção)
            1 .......................... Logar (Usuário)
            2 .......................... Cadastrar Usuário
            3 .......................... Cadastrar Servidor
            4 .......................... Adicionar Log a um Servidor
            5 .......................... Visualizar Logs do Servidor
            6 .......................... Sair
            Escolha uma opção: 
        """.trimIndent()).toInt()

        when(escolha) {
            1 -> logarUsuario()
            2 -> cadastrarUsuario()
            3 -> cadastrarServidor()
            4 -> print(adicionarLog())
            5 -> visualizarLogsServidor()
            6 -> {
                println("\nAté logo!")
                break
            }
            else -> {
                println("\n Opção Inválida, tente novamente!")
                Thread.sleep(650)
            }
        }

    }

}

fun perguntar(pergunta: String): String {
    print(pergunta)
    return readln()
}

fun logarUsuario(){
    val usuario = Usuario()
    usuario.email = perguntar("Insira o e-mail do usuário: ")
    usuario.senha = perguntar("Insira a senha do usuário: ")

    if (autenticarUsuario(usuario)) {
        println("Usuário autenticado com sucesso!")
        usuarioLogado = usuario
        Thread.sleep(650)
    } else {
        println("Usuário inválido, tente novamente")
        Thread.sleep(650)
    }

}

fun autenticarUsuario(usuario: Usuario): Boolean {
    var valido: Boolean = false
    for(e in usuarios) {
        if (usuario.email == e.email && usuario.senha == e.senha) {
            valido = true
        }
    }
    return valido
}

fun cadastrarUsuario(): String {

    val novoUsuario: Usuario = Usuario()
    novoUsuario.email = perguntar("Insira o e-mail do usuário a ser cadastrado: ")
    novoUsuario.senha = perguntar("Insira a senha do usuário a ser cadastrado: ")

    while (true) {
        val isAdm: Int = perguntar("Digite 1 para usuário Administrador e 2 para usuário Analista: ").toInt()

        when(isAdm) {
            1 -> {
                novoUsuario.isAdm = true
                break
            }
            2 -> {
                novoUsuario.isAdm = false
                break
            }
            else -> {
                println("\n Número inválido, digite novamente")
                Thread.sleep(650)
            }
        }
    }

    usuarios.add(novoUsuario)
    return "\nUsuário${novoUsuario.email} Cadastrado com Sucesso!"

}

fun cadastrarServidor(): String {

    val novoServidor: Servidor = Servidor()
    novoServidor.nome = perguntar("Insira o nome do servidor a ser cadastrado: ")
    novoServidor.modeloCpu = perguntar("Insira o modelo da CPU do servidor a ser cadastrado: ")
    novoServidor.numeroNucleos = perguntar("Insira a quantidade de núcleos da CPU deste servidor: ").toInt()
    novoServidor.qtdRam = perguntar("Insira a quantidade de memória RAM em GB (Ex.: 64.0): ").toDouble()

    servidores.add(novoServidor)
    println("Servidor cadastrado com sucesso!")
    Thread.sleep(650)
    return "\nServidor ${novoServidor.nome} Cadastrado com Sucesso!"

}

fun selecionarServidor(): Int {

    var indexServidor: Int = 0
    var indexServidorEscolhido: Int = 0

    while (true) {

        println("Lista de Servidores: ")
        var contador: Int = 0
        for (s in servidores) {
            contador++
            println("\n $contador - ${s.nome} \n")
        }

        val posicaoServidor: Int = perguntar("Insira o número do servidor que deseja selecionar: ").toInt()

        if(posicaoServidor <= servidores.size) {
            break
        }

    }

    while (true) {
        var i: Int = 0
        var existe: Boolean = false

        while (i <= servidores.size) {
            if (servidores[indexServidor].nome == servidores[i].nome) {
                existe = true
                indexServidorEscolhido = i
                break
            }
            i++
        }

        if (existe) {
            return indexServidorEscolhido
        }
    }


}

fun adicionarLog(): String {

    val indexServidor: Int = selecionarServidor()

    val novoLog = Log()
    novoLog.servidor = servidores[indexServidor].nome
    novoLog.texto = perguntar("Insira o Conteúdo do log: ")
    novoLog.data = perguntar("Insira a data do ocorrido (Ex.: 10/09/24): ")
    logs.add(novoLog)
    return "\nLog Adicionado com sucesso ao servidor ${servidores[indexServidor].nome}!"
}

fun visualizarLogsServidor() {

    val indexServidor: Int = selecionarServidor()
    var exibicaoLogs: String = ""
    val nomeServidor = servidores[indexServidor].nome

    var contador: Int = 0
    for(l in logs) {
        contador++
        if (nomeServidor == l.servidor) {
        exibicaoLogs += "\n ${contador} -> Texto: ${l.texto} - Data: ${l.data} \n"
        }
    }

    println("Lista de Logs do Servidor ${nomeServidor}:")
    print(exibicaoLogs)

    while (true) {

        val sair: String = perguntar("\nDigite qualquer caracter para parar a visualização: ")

        if (sair != "") {
        break
        }

    }

    }
