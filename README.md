# SimpleMovieInfoProvider

Project Name: SwingMovie

Environment: Oracle 6g, WindowBuilder, Swing

Language and design pattern used : Java 8, Strategy pattern

Project Type: Refactoring

Total elapsed time original: 3 weeks

Total elapsed time reform: 6 hour

<details><summary>한국어 CLICK ME</summary>
<p>
  
</p>
</details>

<details><summary>日本語 CLICK ME</summary>
<p>
  
## 開発の動機


始めてJavaを3週間学んで作ったSwingプロジェクトです。

簡単に映画の情報を提供して、
映画を見た人のコメントを入力するプロジェクトです。


私が最初作る時は、DesignPatternに対する知識もなかったし、

「コードの重複を避けるべき。」という意識もありませんでした。

単に機能の完成だけしていれば、それでよいプログラムだと思いました。


半年が過ぎた今はそうじゃないことを知っております。

よいプログラムを作るためには、

コードの重複は最大限避ける必要があるし、
読みやすいコードにするべきです。

そのため、昔作ったこのプログラムをRefactoringしようと思いました。

単純な機能の故、機能の文面の説明は省略致します。
機能は少しの間、下の短いYoutubeをご覧ください。
それだけで把握できるプログラムです。


## SwingMovieの問題点


このプログラムは大きく四つの問題があります。
一つ一つ説明させて頂きます。


&nbsp;

**１．構成が雑すぎる。**

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/01.png)

左が直す前の構成で、右が直した構成です。
SwingMovieは大きく三つの機能で分けられております。

* DBとのデータを処理する「Connect」
* 資料の処理をより簡単にするための「Domain」
* ユーザーに見せる「View」

それを右のように改善できます。

&nbsp;

**２．ViewでDBとのConnectが発生しております。**

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/02.png)

GUI、MyFrame_Loginはユーザーに見せるための処です。

なので、プログラムの外見を具現化しております。

そいうクラスでDBのConnectが直接行われております。

&nbsp;

Javaのクラスでは一クラスに一つの機能が理想的です。

不要な機能の追加はコードの読み取りを難しくしますし、

メンテナンスも困難にします。

ですので、「１」はConnectを担当するクラスに移す必要があります。

&nbsp;

**３．DAOで重複コードが多すぎます。**

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/03.png)
DBとのConnectを管理するクラスDAOにメソッドごとに
同じことが繰り返しています。

* 接続のConnection
* SQLコマンドのPreparedStatement
* データセットのResultSet（Select文を使う時のみ使います）

イメージの制約のやめ、イメージでは3行になっておりますけれども

このこどは各30行ほどのロジックです。

上の２のコードと合わせれば「6回」の同じコードが使われ

30ｘ６をして、似ているコードが「180行」使われております。


この共通の処は別の方法を考案して、重複を抑える必要があります。

&nbsp;

**４．Interfaceを文字列の利用に使っています。**

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/04.png)

Interfaceは「データを実装するために使う」という一つの約束です。

文字列を使う為の使用はよくありません。他の方法を探す必要があります。

&nbsp;
&nbsp;

## 問題点の解決方法

**１問題（構成の問題）の解決方法**

構成の問題点は上から説明しましたので、

クラスの説明をさせて頂きます。

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/05.png)

* JdbcContext Class

まず、以前にはなかったJdbcContextが出来ました。

そのクラスの役割はOracleDBからのConnectionを管理することです。

先に話してましたが、以前のコードは合わせて6回のConnectionを呼び出して、作っております。

その重複問題を解決するための策です。

* ProjectDAO Interface and implementation Class

ProjectDAO、ProjectDAOImpleは以前と同じく、DBからの交流のコードが組み込まれておりますが、

その中のコードの効率性は確実によくなっております。

後ほど説明させていただきます。

* StatementStrategy

StatementStrategyは戦略を練るためのInterfaceです。

後にも説明しますが、DBからの接近のコードは似ているように見えて

中身は違う処がありませて、一律的には処理できません。


その為にStrategyPatternを使いました。

このStrategyPatternはJdbcContextと、ProjectDAOImpleで使うことになります。

StrategyPatternをご存知ではないお方は、自分が整理しておいた投稿があります。

ぜひ、下のURLをご参考お願い致します。

https://meaownworld.blogspot.kr/2018/03/strategy-pattern.html



* Queries and queries.properties

Queriesは上で述べました４の問題を解決するためのクラスです。

QueriesがSQLコマンドを作ってくれる工場の役を、queries.propertiesが製品の役をします。

* その他

Connection以外に大きく変わったことはありません。

DomainやViewは最初作る時も大分手を込んで作っておりますし、

特に大きな問題点も見えないので、Connectionのコード効率化が優先だと判断致しました。

&nbsp;

**２問題（ViewでのConnectionの発生）の解決方法**

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/06.png)

まず、ViewのConnectionコードをProjectDAOImpleに移しました。

「クラスは一つの目的の為に使う」という原則を守るためです。

&nbsp;

**３問題（重複コードの排除）の解決方法**

３の問題は一番修正点が多いConnectionのクラス作りです。

まず、DAOImpleにConnectionのコードを排除します。

DAO(Data Access Object)はその名の通りデータのアクセスを手伝うオブゼダートを作ることが一番の使命です。

Connection自体を生成してはその二つの目的を持つことになりますので

別のクラスに任せた方がいいです。

そのクラスがこのJdbcContextです。

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/07.png)

JdbcContextのポイントは四つあります。

１はコンストラクタにOracleDriverを呼び出しています。

JdbcContextを呼び出すクラスはProjectDAOImpleクラスのみで

そのProjectDAOImpleはシングルトンになっています。

(シングルトンを知らないお方は、こっちらに整理しておきました

https://meaownworld.blogspot.kr/2018/02/effective-java-3.html)

なので、コンストラクタは一回だけ呼ばれることになり。PCのリソースを節約してくれます。

２はInsert, update, deleteなどのSQLコマンドを処理するためのメソッドです。

中身はこうなっております。

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/08.png)

ProjectDAOImpleからPreparedStatementを含んだ戦略がパラメータに入ってくると

そのPreparedStatementを実行し、結果を返します。

３はSelectのSQLコマンドを処理するためのメソッドです。

Select文は途中ResultSetを使うため、別のメソッドを作れざる負えないです。

中身はこんな風になっております。

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/09.png)

ResultSetは直接リターンすることはできません。

ResultSetを開けて、そのオブゼダートをリターンすればResultSetをを閉じる機会を失います。

ResultSetを閉じない場合、メモリのリークが起こります。そのうえ

CacheRowSetImplの形でリターンするように仕組まれております。


それではこのJdbcContextクラスを使うImpleのメソッドを見てみます。

以下はInsert(Create)のメソッドです。

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/10.png)

左が以前のコードで、右がRefactoringしたコードです。

ここではコードを短くするため、Ramda式を使いました。

接続とその中身はJdbcContextクラスに預けておりますので、このメソッドの役目は

ただ、PreparedStatementを用意したストラテジーを作り、転送することのみです。

DAOの目的を忠実に果たしています。

左に比べれば右のコードが学実に簡略化しております。


&nbsp;

ResultSetを使うSelect文も一つ前後を比べてみます。

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/11.png)

基本上と同じくですが、CachedRowSetImplを使いデータの作業をしていることが違います。

同じくコードが短くなり、見やすくなっております。

&nbsp;

**４問題（SQLコマンドの管理）の解決方法**

Queriesとqueries.propertiesは以前InterfaceにSQL文を保管する問題点を解決するため作っております。

Queriesクラスを見てください。

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/12.png)

queries.propertiesを探し、その経路を保管しています。

そして、シングルトンの処理になっています。

以前とこのクラスが利用するpropertiesの内容です。

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/13.png)

左が以前のもので、右が変わったものです。

以前より随分よくなってると思います。


実際にQueryを呼び出す時の変化です。

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/14.png)

少し、長くなりましたが、効率かオブジェクト指向の為には以後の方が学実によい形だと思います。

&nbsp;
&nbsp;

## Refactoring後の感想

教育を修了した後、一人で勉強したことを使ってRefactoringしてみました。

本で勉強することと、実際に適用することはかなり違いがあると感じました。

適用はすこし難しい処もあったけれども、実力があがった感じがします。

これからも、新しいものを学ぶことばかりではなく、着実に適用して行きたいと思っております。

</p>
</details>
