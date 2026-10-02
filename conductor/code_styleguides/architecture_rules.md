# Architecture Rules

## 適用範囲

本ファイルは Tab Manager Ex のアーキテクチャ規則を定める。
コードレビューと Track 作成の判断基準として使う。

## 現在の構成

実装は Kotlin ファイル 4 本、計 133 行である。

| ファイル | 行数 | 層 |
| --- | --- | --- |
| `application/TabPinningService.kt` | 29 | アプリケーション層 |
| `userInterface/PinCurrentFilesAction.kt` | 19 | ユーザーインターフェース層 |
| `userInterface/UnpinAllAction.kt` | 19 | ユーザーインターフェース層 |
| `userInterface/OpenFilesFromSelectedCommitsAction.kt` | 66 | ユーザーインターフェース層 |

## 依存の方向

依存は `userInterface/` から `application/` の一方向である。

- `application/` は `userInterface/` を参照してはならない。
- `userInterface/` は `application/` を参照してよい。
- 循環参照を作ってはならない。

この規則は現状、次の import で担保されている。

- `PinCurrentFilesAction.kt` は `TabPinningService` を import する。
- `UnpinAllAction.kt` は `TabPinningService` を import する。

## 層の責務

### application/

IDE の状態を操作するサービスを置く層である。
プロジェクトスコープの状態を持たない純粋なロジックはここに置かない。

- IntelliJ Platform のサービス機構を使う。`@Service(Service.Level.PROJECT)` を付与する。
- プロジェクトはコンストラクタ注入で受け取る。サービスから `ProjectManager` で取得してはならない。
- EDT で実行しなければならないメソッドには `@RequiresEdt` を付与する。

### userInterface/

`AnAction` の実装を置く層である。

- `getActionUpdateThread()` は `ActionUpdateThread.BGT` を返す。
- `actionPerformed` は EDT 上で実行される。EDT 専用サービスを呼び出してよい。
- ドメインロジックをアクションクラス内に書かない。`application/` のサービスへ委譲する。
- `presentation.isEnabled` の更新は `update` で行う。

### resources/META-INF/plugin.xml

アクションの登録のみを書く。
- アクションの `id` は PascalCase とする。
- グループの登録先は `Git.Log.ContextMenu`、`EditorTabPopupMenu`、`EditorPopupMenu` を使用する。

## IntelliJ Platform 固有の制約

- プラグインは `com.intellij.modules.platform` と `Git4Idea` に依存する。この依存関係を追加してはならない。
- Git ログの取得は `VcsLogDataKeys.VCS_LOG_COMMIT_SELECTION` を通す。Git4Idea の内部クラスへ直接アクセスしてはならない。
- ファイルを開く操作は `FileEditorManager` を通す。
- ピン留めは `FileEditorManagerEx` を通す。`FileEditorManager` にはピン留めの API がない。

## アクションの構造に関する既知の例外

`OpenFilesFromSelectedCommitsAction` は 66 行と他のアクションの 3 倍であり、ドメインロジックである `getChangedFilePathsFromCommits` と `openFile` をアクションクラス内に直接保持している。ファイルの検索とエディタを開く処理を含むためである。
`PinCurrentFilesAction` と `UnpinAllAction` はいずれも `TabPinningService` へ委譲しており、この構成と一致していない。

新しいアクションを追加する場合はサービスへ委譲する形を既定とする。既存アクションの分割は本ファイルの規定外であり、別 Track で扱う。

## スタイルの実行力

本ガイドは上位原則である。実際には以下の設定が違反を検出する。

- `config/detekt/detekt.yml` : 1053 行。`build.maxIssues: 0` のため違反は 0 件でなければならない。detekt-formatting により ktlint 系の整形規則も強制する。
- `.editorconfig` : インデント 4 スペース、改行 LF、文字コード UTF-8、最大行長 120。

新しいスタイル規則を追加する場合は `config/detekt/detekt.yml` に書く。`conductor/code_styleguides/` には書かない。

## AGENTS.md との乖離

`AGENTS.md` は依存性逆転の法則を核としたクリーンアーキテクチャを本プロジェクトの方針として記述している。実装にはリポジトリ層をまたぐ依存逆転は存在しない。現状の構造は IntelliJ Platform が提供するサービス機構に素直に従った形であり、抽象化の層を意図的に持たない設計である。

この乖離は記録するための記載である。`AGENTS.md` の記述を変更するかは、メンテナの判断に委ねる。
