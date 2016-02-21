<span style="font-size: 15px;"><span style="font-family:Courier New;">#Database Info<br />
<br />
<br />
Username:root Passwd:(null)<br />
<br />
mysql&gt; use test01;<br />
Database changed<br />
<br />
mysql&gt; show tables;<br />
+------------------+<br />
| Tables_in_test01 |<br />
+------------------+<br />
| answer &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; |<br />
| user &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; |<br />
+------------------+<br />
2 rows in set (0.00 sec)<br />
<br />
mysql&gt; describe user;<br />
+----------+----------+------+-----+---------+-------+<br />
| Field &nbsp; &nbsp;| Type &nbsp; &nbsp; | Null | Key | Default | Extra |<br />
+----------+----------+------+-----+---------+-------+<br />
| username | char(20) | NO &nbsp; | PRI | &nbsp; &nbsp; &nbsp; &nbsp; | &nbsp; &nbsp; &nbsp; |<br />
| password | char(50) | YES &nbsp;| &nbsp; &nbsp; | NULL &nbsp; &nbsp;| &nbsp; &nbsp; &nbsp; |<br />
| score &nbsp; &nbsp;| int(11) &nbsp;| YES &nbsp;| &nbsp; &nbsp; | NULL &nbsp; &nbsp;| &nbsp; &nbsp; &nbsp; |<br />
+----------+----------+------+-----+---------+-------+<br />
3 rows in set (0.08 sec)<br />
<br />
mysql&gt; describe answer;<br />
+-------+----------+------+-----+---------+-------+<br />
| Field | Type &nbsp; &nbsp; | Null | Key | Default | Extra |<br />
+-------+----------+------+-----+---------+-------+<br />
| ans &nbsp; | char(20) | NO &nbsp; | PRI | &nbsp; &nbsp; &nbsp; &nbsp; | &nbsp; &nbsp; &nbsp; |<br />
+-------+----------+------+-----+---------+-------+<br />
1 row in set (0.02 sec)<br />
<br />
mysql&gt;</span></span>
