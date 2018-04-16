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

左が直す前の構成で、右が直した構成です。
SwingMovieは大きく三つの機能で分けられております。

* DBとのデータを処理する「Connect」
* 資料の処理をより簡単にするための「Domain」
* ユーザーに見せる「View」

それを右のように改善できます。

&nbsp;

**２．ViewでDBとのConnectが発生しております。**

GUI、MyFrame_Loginはユーザーに見せるための処です。

なので、プログラムの外見を具現化しております。

そいうクラスでDBのConnectが直接行われております。

&nbsp;

Javaのクラスでは一クラスに一つの機能が理想的です。

不要な機能の追加はコードの読み取りを難しくしますし、

メンテナンスも困難にします。

ですので、「１」は

Connectを担当するクラスに移す必要があります。

&nbsp;

**３．DAOで重複コードが多すぎます。**

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

Interfaceは「データを実装するために使う」という一つの約束です。
文字列を使う為の使用はよくありません。
他の方法を探す必要があります。


</p>
</details>
