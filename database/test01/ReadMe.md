#Database Info


Username:root Passwd:(null)

mysql> use test01;
Database changed

mysql> show tables;
+------------------+
| Tables_in_test01 |
+------------------+
| answer           |
| user             |
+------------------+
2 rows in set (0.00 sec)

mysql> describe user;
+----------+----------+------+-----+---------+-------+
| Field    | Type     | Null | Key | Default | Extra |
+----------+----------+------+-----+---------+-------+
| username | char(20) | NO   | PRI |         |       |
| password | char(50) | YES  |     | NULL    |       |
| score    | int(11)  | YES  |     | NULL    |       |
+----------+----------+------+-----+---------+-------+
3 rows in set (0.08 sec)

mysql> describe answer;
+-------+----------+------+-----+---------+-------+
| Field | Type     | Null | Key | Default | Extra |
+-------+----------+------+-----+---------+-------+
| ans   | char(20) | NO   | PRI |         |       |
+-------+----------+------+-----+---------+-------+
1 row in set (0.02 sec)

mysql>