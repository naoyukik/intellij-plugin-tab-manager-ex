# Product Guidelines

## 適用範囲

本ガイドラインは Tab Manager Ex の表記、トーン、命名規則を定める。
コードレビュー、Issue、コミットメッセージの判定基準として使う。

## 言語

- 設計書、Issue、コミットメッセージ、レビューコメントは日本語で記述する。
- ソースコードの識別子、コメント、UI 文字列は英語で記述する。
- UI 文字列とコードは JetBrains Marketplace と公開リポジトリに露出するため、世界中で読める英語に統一する。

## トーン

- 簡潔さと事実の正確さを優先する。
- 宣伝的な表現を使わない。機能の効果を断定しない。
- 推測で書かない。未検証のビルド番号、API の存在、互換性の可否を断定しない。
- 一次情報を参照したときは、その URL を示す。

## 命名規則

### コミットメッセージ

Conventional Commits を採用する。形式は次のとおり。

    <type>: <日本語での説明（50文字以内）>

- type は `feat` `fix` `refactor` `style` `docs` `test` `chore` のみを使う。
- タイトルだけでは内容が分からない場合、本文に何を達成するための実装かを箇条書きで書く。
- 対応する Issue があれば `ref: <Issue 番号>` を付ける。

### コード

- クラス名は PascalCase とする。
- 関数名と変数名は camelCase とする。
- IntelliJ Platform の規約に従い、`AnAction` の派生クラスには `Action` の接尾辞を付ける。
- 層のディレクトリ名は `application` と `userInterface` とする。略称を使わない。

## UI 文字列

- アクションの text は英語の命令形とする。例: `Open Changed Files`、`Pin All Open Files`、`Remove All Pins`。
- description は text が示す内容を補足する英語とする。
- 句末にピリオドを付けない。既存 3 アクションの表記に合わせる。

## CHANGELOG

- Keep a Changelog の形式に従う。
- 対応 IntelliJ バージョンの変更は `### Changed` 節に記載する。
- ユーザーが体感できる機能追加は `### Added` 節に記載する。

## ユーザー検証

- 機能変更では `runIde` で起動した実 IDE 環境での動作確認を必須とする。
- 結果を Issue のコメントに記録し、ユーザーの明示的な承認を得る。
