# Imports de módulos externos necessários para manipular tempo,capturar dados e conectar com o BD MySQL
import time
import psutil as ps
from mysql.connector import connect, Error

def main(): # Função principal, fiz assim por questão de organização
    imprimeMenu("Olá, Bem vindo(a)! Aqui está a captura de dados em tempo real de: Uso de CPU, Uso de RAM, Uso de Disco, Conexões Abertas em Rede, e Tempo de atividade!", 2.5)
    print("\n------------------------------------------------------ USO DE HARDWARE ------------------------------------------------------\n")
    print(formataLinha('CPU (%)','Uso RAM', 'RAM Livre', 'Uso Disco', 'Disco Livre', 'Conexões Rede', 'Tempo Atividade'))
    print('-' * 150)
    while True:
        #Coleta e exibe os dados do sistema
        dados = obterDadosSistema()
        # Imprime os dados na tabela
        print(formataLinha(*dados)) # Utilizando o "*" como forma de "expandir" a tupla em dados separados, pois se passasse sem isso, ficaria apenas 1 tupla inteira como parâmetro.
        # Aguarda 2 segundos antes de atualizar
        time.sleep(2)

def executarQuery(script): # Função responsável por inserir os dados no banco, recebe uma query SQL qualquer como parãmetro e a executa, usando as credenciais específicas

    # Função fornecida pelo professor, estudei a função e só fiz 1 alteração.
    # Agora esta função retorna as linhas obtidas pela query

    config = {
      'user': 'root',
      'password': '2205',
      'host': 'localhost',
      'database': 'LogGuard'
    }

    try:
      db = connect(**config)
      if db.is_connected():
        db_info = db.get_server_info()
        print('Connected to MySQL server version -', db_info)

        with db.cursor() as cursor:
          result = cursor.execute(script)
          rows = cursor.fetchall()
          for rows in rows:
            print(rows)

        cursor.close()
        db.close()
        return rows

    except Error as e:
      print('Error to connect with MySQL -', e)

def imprimeMenu(texto, tempo): # Função responsável por imprimir o menu inicial, contém o nosso nome em ASCII ART, recebe os parâmetros de: texto a ser impresso e tempo a esperar após imprimir
    print(""" 
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
""")
    print(texto)
    time.sleep(tempo)

def formataLinha(*args): # Função de formatação utilizada nas tabelas, espera n parâmetros, ou seja, um número variável de parâmetros, denotado pelo uso de "*" antes do nome do argumento esperado
    # a parte de ' | '.join(...) é responsável por juntar os elementos da sequência fornecida com o separador "|"
    # "f'{item:^10}'" é uma string formatada, onde item representa cada elemento dentro dos argumentos fornecidos
    # "^" diz que item será centralizado em um campo de 10 caracteres, onde o resto será preenchido com espaços
    return ' | '.join(f'{item:^10}' for item in args)

def obterDadosSistema(): # Aqui, colho os dados por componente separadamente:
    # Uso de CPU
    porcentagemCpu = ps.cpu_percent(interval=1)

    # Uso de RAM
    memoria = ps.virtual_memory()
    totalMemoria = memoria.total / (1024 ** 3)  # Convertendo de bytes para GB
    memoriaUsada = memoria.used / (1024 ** 3)  # Convertendo de bytes para GB
    memoriaLivre = memoria.free / (1024 ** 3)  # Convertendo de bytes para GB

    # Uso de Disco
    usoDisco = ps.disk_usage('/')
    totalDisco = usoDisco.total / (1024 ** 3)  # Convertendo de bytes para GB
    usadoDisco = usoDisco.used / (1024 ** 3)  # Convertendo de bytes para GB
    livreDisco = usoDisco.free / (1024 ** 3)  # Convertendo de bytes para GB

    # Conexões Abertas em Rede
    conexoesRede = len(ps.net_connections()) # Length (Largura) da lista de conexões com internet

    # Tempo de Atividade
    uptime = ps.boot_time() # ps.boot_time() -> Retorna o timestamp desde a época, que é 01/01/1970, 00:00:00 em segundos
    tempoAtividade = time.time() - uptime # time.time() -> Retorna o tempo atual em segundos desde a época
    tempoAtividadeStr = time.strftime("%H:%M:%S", time.gmtime(tempoAtividade)) # Formata o tempo, no formato HH:MM:SS

    return (f'{porcentagemCpu}%', f'{memoriaUsada:.2f} GB', f'{memoriaLivre:.2f} GB',
            f'{usadoDisco:.2f} GB', f'{livreDisco:.2f} GB', conexoesRede, tempoAtividadeStr) # Retorno da função, a tupla com todos os dados em ordem.

# Como quis rodar o arquivo pela função "main()", preciso fazer isso:
# A variável "__name__" é uma variável que tem seu valor atribuído automaticamente pelo Python.
# Seu valor é de "__main__" caso o programa seja iniciado por conta própria, exemplo:
# Você digita no console "python Captura-Dados-LogGuard.py".
# Assim, o valor é esse. Caso esse arquivo fosse acessado de outra forma, como módulo, ela não teria esse valor.
# O valor seria "Captura-Dados-LogGuard"
# Após verificar, caso positivo, chama a função "main()", dando início ao programa.
if __name__ == "__main__":
    main()
