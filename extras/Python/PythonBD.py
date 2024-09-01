from mysql.connector import connect, Error

config = {
  'user': 'root',
  'password': '2205',
  'host': 'localhost',
  'database': 'BancoTeste'
}

try:
  db = connect(**config)
  if db.is_connected():
    db_info = db.get_server_info()
    print('Connected to MySQL server version -', db_info)

    with db.cursor() as cursor:
      result = cursor.execute("SELECT * FROM tabela;")
      rows = cursor.fetchall()
      for rows in rows:
        print(rows)

    cursor.close()
    db.close()

except Error as e:
  print('Error to connect with MySQL -', e)