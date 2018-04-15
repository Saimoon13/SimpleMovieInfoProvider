# SimpleMovieInfoProvider

Project Name: SwingMovie

Environment: Oracle 6g, WindowBuilder, Swing

Language and design pattern used : Java 8, Strategy pattern

Project Type: Refactoring

Total elapsed time: 6 hour

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

私が最初作る時は、DesignPatternに対する知識もなかったです。
「コードの重複を避けるべき。」という意識もありませんでした。
単に機能の完成だけしていれば、それでよいプログラムだと思いました。

半年が過ぎた今はそうじゃないことを知っております。

プログラムはコードの重複は最大限避ける必要があるし、
読みやすいコードにするべきです。

そのため、昔作ったこのプログラムをRefactoringしようと思いました。

単純な機能の故、機能の文面の説明は省略致します。
機能は少しの間、下の短いYoutubeをご覧ください。
それだけで把握できるプログラムです。


## SwingMovieの問題点


このプログラムは大きく四つの問題があります。
一つ一つ説明させて頂きます。



１．構成が雑すぎる。


左が直す前の構成で、右が直した構成です。
SwingMovieは大きく三つの機能で分けられております。

* DBとのデータを処理する「Connect」
* 資料の処理をより簡単にするための「Domain」
* ユーザーに見せる「View」

それを右のように改善できます。



２．ViewでDBとのConnectが発生しております。

GUI、MyFrame_Loginはユーザーに見せるための処です。
なので、プログラムの外見を具現化しております。
そいうクラスでDBのConnectが直接行われております。

Javaのクラスでは一クラスに一つの機能が理想的です。
不要な機能の追加はコードの読み取りを難しくしますし、
メンテナンスも困難にします。

ですので、「１」は
Connectを担当するクラスに移す必要があります。




</p>
</details>
