# SimpleMovieInfoProvider

ImageClick - Demonstration of project via youtube

[![SwingTest](http://img.youtube.com/vi/sqHn9i6pRfg/0.jpg)](http://www.youtube.com/watch?v=sqHn9i6pRfg "SwingTest")

Project Name: SwingMovie

Environment: Oracle 6g, WindowBuilder, Swing

Language and design pattern used : Java 8, Strategy pattern

Project Type: Refactoring - https://github.com/Saimoon13/OldSimpleMovieInfoProvider

Total elapsed time original: 3 weeks

Total elapsed time reform: 6 hour

<details><summary>한국어 CLICK ME</summary>
<p>
  
  자바를 3주간 배우고 만든 Swing 프로젝트입니다.

영화 정보를 제공하고, 평가 코멘트를 입력할 수 있습니다.

위의 URL에 리펙토링 전의 프로젝트가 업로드 되어 있습니다.

제가 처음 프로젝트를 만들 때는 DesignPattern에 대한 지식도 없었고

코드의 중복도 신경쓰지 않았습니다. 기능만 제대로 한다면 별 문제 없다고 생각했습니다.

이젠 그렇지 않다는 걸 압니다.

그래서 최대한 중복을 제거하고, 배운 바를 적용해 보기로 했습니다.

기능은 위의 youtube 영상을 확인해주세요.

&nbsp;

## SwingMovie의 문제점

이 프로그램은 크게 4가지 문제가 있습니다. 하나씩 설명해보겠습니다.

** 1. 구성이 난잡하다 **

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/01.png)

왼쪽이 리팩토링 전의 구성이고, 오른쪽이 리팩토링 이후의 구성입니다.
SwingMovie는 크게 3가지 기능으로 분류됩니다

* DB와 데이터를 처리하는 Connection 부분
* 자료형 클래스 Domain
* 사용자가 조작하는 화면 View

위의 세개 입니다.

&nbsp;

** 2. View에서 DB와 커넥션이 발생합니다. **

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/02.png)

GUI, MyFrame_Login은 유저에게 보여주는 View입니다.

그런데 DB와의 Connection이 직접 발생하고 있습니다.

&nbsp;

Java의 클래스에서는 한 클래스에 한 기능이 이상적입니다.

불필요한 기능을 추가하는 것은 유지보수도 어렵게 하고, 코드를 읽기 힘들게도 합니다.

그러므로 1을 커넥션을 담당하는 클래스로 이동시킬 필요가 있습니다.

&nbsp;

** 3. DAO에서 중복 코드가 너무 많습니다. **

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/03.png)

DB와의 연결을 관리하는 클래스 DAO의 메소드별로 같은 코드가 반복되고 있습니다.

* 접속의 Connection
* SQL문을 처리하는 PreparedStatement
* 데이터 셋의 ResultSet(Select문을 사용할 때만 이용)

이미지에서는 3행이지만, 30줄 정도의 코드입니다.

이러한 코드가 위의 2의 코드를 합해 6번의 코드가 사용됩니다.

30x6을 해서 같은 코드가 180행입니다.

이 중복을 해결해야합니다.

&nbsp;

** 4. Interface를 문자열의 이용에 사용하고 있습니다. **

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/04.png)

인터페이스는 자료형을 구현하기 위해 사용한다는 하나의 약속입니다.

문자열의 구현을 위해 사용한다면, 다른 더 좋은 방법이 있습니다.

&nbsp;
&nbsp;

# 문제점의 해결방법

**문제 1(구성의 문제)의 해결방법**

위에서 해결했기 때문에 클래스의 설명을 하겠습니다.

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/05.png)

* JdbcContext Class

먼저 이전에는 없던 JdbcContext가 생겼습니다.

이 클래스의 역할은 OracleDB와의 커넥션을 관리하는 부분입니다.

위에서 이야기했듯이 6번으로 총 180줄이 DB와의 연결을 위해 사용되고 있습니다.

그 중복문제를 해결하기 위한 클래스입니다.

* ProjectDAO Interface and implementation Class

ProjectDAO, ProjectDAOImple은 이전과 같이 DB의 접속을 관리하는 코드입니다.

하지만 그 코드의 효율이 좋지 못합니다.

밑에서 설명하겠습니다.

* StatementStrategy

StatementStrategy는 전략을 짜는 Interface입니다.

밑에서 설명하겠지만, DB 비슷하지만 살짝 다릅니다.
(Select의 문제)

때문에 사용하는 인터페이스입니다.


그 때문에 전략 패턴을 사용합니다.

이 전략패턴은 JdbcContext와 ProejctDAOImple에서 사용하게 됩니다


* Queries and queries.properties

Queries는 위에서 설명했듯이 문제 4를 해결하기 위한 클래스입니다.

Queries가 SQL명령어를 만들어주는 공장의 역할, properties가 제품의 역할을 합니다.



* その他

Connection 이외에는 크게 바뀐점이 없습니다.

Domain이나 View는 당시 만들 때도 순서에 맞추어서 테스트해가며 만들었기에

지금 손댄다면 문제가 생길 수 있다고 생각했습니다.

특별히 문제가 없어보이는 부분을 제외하고는 DB와의 연결 부분 개선에 집중했습니다.

&nbsp;

**문제 2(View에서 커넥션) 해결방법**

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/06.png)

먼저 View의 커넥션 코드를 ProjectDAOImple로 이동합니다.

클래스는 하나의 목적을 위해 사용한다는 원칙을 지키기 위함입니다.


&nbsp;

**문제 3(중복 코드의 배제)의 해결방법**

문제 3이 가장 작업량이 많습니다.

먼저 DAOImple에 Connection의 코드를 배제합니다.

DAO(Data Access Object)는 그 이름대로 데이터 접근을 돕는 것이 가장 큰 역할을 합니다.

Connection자체의 생성은 그 목적에 맞지 않기에

다른 클래스에 맡기기로 합시다.

그 클래스가 JdbcContext입니다.

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/07.png)

JdbcContext에서 주목해야할 점은 4군데 입니다.

1은 생성자에서 OracleDriver를 등록합니다.

JdbcContext를 불러내는 클래스가 ProjectDAOImple 하나이고

그 클래스는 싱글톤으로 설계되어 있습니다.

때문에 생성자는 한번만 불리게 됩니다.

2는 Insert, Update 등의 SQL 명령어를 처리하는 부분입니다.

코드는 이러합니다.

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/08.png)

PreparedStatement를 포함하는 전략 클래스가 매개변수로 옵니다.

그 PReparedStatement를 실행하고 결과를 반환합니다.

3은 Select의 SQL명령어를 처리하는 메소드입니다.

Select문은 도중에 ResultSet을 사용하기에 별도의 매소드를 만들어줄 필요가 있습니다.

코드는 이러합니다.

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/09.png)

ResultSet 데이터를 직접 리턴할 수는 없습니다.

Connection과 마찬가지로 ResultSet은 닫아주어야하는 데이터 객체이기 때문에

중간에 리턴해버리면 닫을 기회를 놓치게 되고

이것이 꾸준히 누적되면 메모리 누출로 프로그램 자체가 뻗어버립니다.

때문에 CacheRowSetImpl 형태로 리턴해줍니다.


이 다음은 JdbcConext클래스를 사용하는 Imple메소드를 봐주세요

이하가 Inset(Create) 메소드입니다.

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/10.png)

왼쪽이 이전의 코드고 오른쪽이 Refactoring한 코드입니다.

코드의 간략화를 위해 Ramda식을 사용했습니다.

DB 접속에 관련된 역할은 JdbcConetext에 맡기기 때문에 이 메소드의 역할은 PreparedStatement를 포함한 전략을 만들고

전송하는 역할입니다. DAO의 목적에 충실하고 있습니다.


왼쪽과 비교한 오른쪽이 확실히 간략화되어있습니다.

&nbsp;

esultSet을 사용한 Select문도 전후를 비교해보겠습니다.

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/11.png)

기본은 위의 코드와 같지만, CacheRowSetImpl을 사용하여 데이터 작업을 하고 있는 점이 다릅니다.

&nbsp;

**문제 4(SQL명령의 처리)의 해결방법**

Queries와 Queries.properties는 SQL문을 Interface에 보관하는 문제를 해결하기 위한 클래스와 properties입니다.

Queries클래스를 봅시다.

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/12.png)

Queries.properties를 찾아서 그 경로를 보관합니다.

싱글톤으로 처리되어 있습니다.

리팩토링하기 이전 Interface와 클래스가 이용하는 properties의 내용입니다.

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/13.png)

이전보다 쉽게 되어있다고 생각합니다.

실제로 Query를 불러보겠습니다.

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/14.png)

조금 길어졌지만, 객체지향측면에선 훨씬 좋다고 생각합니다.

&nbsp;
&nbsp;

## Refactoring후의 감상

혼자서 공부한 내용을 정리해봤습니다.

책으로만 공부하는 것과는 또 다른 느낌이었습니다.

앞으로도 이런 기회를 계속 만들어가야겠다는 생각이 들었습니다.
  
</p>
</details>

<details><summary>日本語 CLICK ME</summary>
<p>
  
## 開発の動機


始めてJavaを3週間学んで作ったSwingプロジェクトです。

映画の情報を提供して、
映画を見た人のコメントを入力するプロジェクトです。

Refactoring前のプログラムは上のURLをアップロードしております。


私が最初作る時は、DesignPatternに対する知識もなかったし、

「コードの重複を避けるべき。」という意識もありませんでした。

単に機能の完成だけしていれば、それでよいプログラムだと思いました。


半年が過ぎた今はそうじゃないことを知っております。

よいプログラムを作るためには、

コードの重複は最大限避ける必要があり、
読みやすいコードにするべきだという事実を知っております。

そのため、昔作ったこのプログラムをRefactoringしようと思いました。

プロジェクトの機能は、上のYoutubeを見てください。




## SwingMovieの問題点


このプログラムは大きく四つの問題があります。
一つ一つ説明させて頂きます。


&nbsp;

**１．構成が雑すぎます。**

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/01.png)

左が直す前の構成で、右が直した構成です。
SwingMovieは大きく三つの機能で分けられております。

* DBとのデータを処理する「Connect」
* 資料の処理をより簡単にするための「Domain」
* ユーザーに見せる「View」

それを右のように改善できます。

&nbsp;

**２．ViewでDBとのConnectが発生しています。**

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/02.png)

GUI、MyFrame_Loginはユーザーに見せるための処です。

なので、プログラムの外見を具現化しております。

そういうクラスでDBのConnectが直接行われております。

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

イメージでは3行のみですけれども、本来は各30行ほどのコードです。

上の２のコードと合わせれば「6回」の同じコードが使われ

30ｘ６をして、似ているコードが「180行」使われております。

別の方法を考案して、重複を抑える必要があります。

&nbsp;

**４．Interfaceを文字列の利用に使っています。**

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/04.png)

Interfaceは「データを実装するために使う」という一つの約束です。

文字列を使う為の使用はよくありません。他の方法を探す必要があります。

&nbsp;
&nbsp;

## 問題点の解決方法

**問題 1（構成の問題）の解決方法**

この問題点は上でもう解決しましたので、クラスの説明をさせて頂きます。

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/05.png)

* JdbcContext Class

まず、以前にはなかったJdbcContextが出来ました。

このクラスの役割はOracleDBからのConnectionを管理することです。

先に話してましたが、以前のコードは合わせて6回のConnectionを呼び出して、作っております。

その重複問題を解決するためのクラスです。

* ProjectDAO Interface and implementation Class

ProjectDAO、ProjectDAOImpleは以前と同じく、DB交流のためのクラスですが

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

Queriesは上で述べました問題 4を解決するためのクラスです。

QueriesがSQLコマンドを作ってくれる工場の役を、queries.propertiesが製品の役をします。

* その他

Connection以外に大きく変わったことはありません。

DomainやViewは最初作る時も大分手を込んで作っておりますし、

特に大きな問題点も見えないので、Connectionのコード効率化が優先だと判断致しました。

&nbsp;

**問題 2（ViewでのConnectionの発生）の解決方法**

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/06.png)

まず、ViewのConnectionコードをProjectDAOImpleに移しました。

「クラスは一つの目的の為に使う」という原則を守るためです。

&nbsp;

**問題 3（重複コードの排除）の解決方法**

問題 3は一番修正点が多いConnectionのクラス作りです。

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

ここではコードを簡略化するため、Ramda式を使いました。

DBConnectionに必要なコードはJdbcContextクラスに任せたので、このメソッドの役目は

ただ、PreparedStatementを含んだストラテジーを作り、転送することのみです。

DAOの目的を忠実に果たしています。

左に比べれば右のコードが確実に簡略化しております。


&nbsp;

ResultSetを使うSelect文も前後を比べてみます。

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/11.png)

基本は上のコードと同じくですが、CachedRowSetImplを使ってデータの作業をしていることが違います。

&nbsp;

**問題 4（SQLコマンドの管理）の解決方法**

Queriesとqueries.propertiesはInterfaceにSQL文を格納する問題点を解決するため作っております。

Queriesクラスを見てください。

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/12.png)

queries.propertiesを探し、その経路を保管しています。

そして、シングルトンの処理になっています。

Refactoring以前Interfaceと、このクラスが利用するpropertiesの内容です。

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/13.png)

以前より大分よくなってると思います。


実際にQueryを呼び出す時の変化です。

![alt text](https://github.com/Saimoon13/SimpleMovieInfoProvider/blob/master/libs/image%20for%20readme/14.png)

少し、長くなりましたが、効率的オブジェクト指向の為には以後の方が確実によい方法だと思います。

&nbsp;
&nbsp;

## Refactoring後の感想

教育を修了した後、一人で勉強したことを使ってRefactoringしてみました。

本で勉強することと、実際に適用することはかなり違いがあると感じました。

適用はすこし難しい処もあったけれども、実力があがった感じがします。

これからも、新しいものを学ぶことばかりではなく、着実に適用して行きたいと思っております。

</p>
</details>
