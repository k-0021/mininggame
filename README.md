# Javaコースミニゲーム開発編　鉱石採掘ゲーム
## ■ゲーム概要
制限時間内に採掘した鉱石ごとのスコアの合計を出すゲーム
- ゲーム開始：```/mininggame```コマンドを入力することによって開始
- 制限時間：3分
- 鉱石ごとのスコア
    - 鉄-10点
    - ラピスラズリ-20点
    - レッドストーン-30点
    - ダイヤモンド-60点
  
## ■ゲーム詳細
- 1.```/mininggame```コマンドを入力することにより制限時間3分のゲームが始まる
- 2.スコアが設定されている鉱石を採掘するとスコアが加算される
- 3.制限時間3分でゲームが終了し合計スコアが画面表示され、DBにゲームを実行した回数、スコア、日時が保存される
  
## ■ゲームスコアの表示
```/mininggame list```コマンドを入力することによりDBに保存されているスコアの一覧が表示される

## ■DB設計
 | 属性 | 設定値 |
 | ------ | -------- |
 | ユーザ名 | ※ |
 | パスワード | ※ |
 | URL | ※ |
 | データベース名 | spigot_server |
 | テーブル名 | player_score3 |

 （※）は自身のローカル環境に合わせてご使用ください。(mybatis-config.xmlで設定します)

 ## ■データベース・テーブル作成
 MySqlに接続し、以下のコマンドを上から順に実行してください。
 ```
CREATE DATABASE spigot_server;

USE spigot_server;

CREATE TABLE  player_score3(id int auto_increment, score int, registered_at datetime, primary key(id)) DEFAULT CHARSET=utf8;
```

 ## ■対応バージョン
- Minecraft：1.20.1
- Spigot：1.20.1
  
※動作確認はWindowsのみ実施
