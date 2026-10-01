# Tech Stack

## 実行環境

| 項目 | 値 |
| --- | --- |
| 言語 | Kotlin 2.3.21 |
| JVM ツールチェーン | 21 |
| ビルド | Gradle 9.5.1（Kotlin DSL） |
| ツールチェーン解決 | foojay-resolver-convention 1.0.0 |

## IntelliJ Platform

| 項目 | 値 |
| --- | --- |
| Gradle プラグイン | org.jetbrains.intellij.platform 2.16.0 |
| ビルド対象 | IntelliJ IDEA Ultimate |
| プラットフォームバージョン | 262.6653.22 |
| 対応開始ビルド | 243 |
| 対応終了ビルド | 262.* |
| バンドル済みプラグイン依存 | Git4Idea |
| マーケットプレイスプラグイン ID | 29355 |

## テストと品質

| 項目 | 値 | 備考 |
| --- | --- | --- |
| テストフレームワーク | JUnit 4.13.2 | テストは未実装。`src/test` が存在しない |
| テスト基盤 | TestFrameworkType.Platform | 基盤のみ設定済み |
| UI テスト | robotServerPlugin + `runIdeForUiTests` | 基盤のみ設定済み |
| カバレッジ | Kover 0.9.4 | `onCheck = true` |
| 静的解析 | Detekt 1.23.8 | detekt-formatting 1.23.8 を併用 |
| 静的解析 | Qodana 2025.3.2 | CI 上で実行 |

## 配布

| 項目 | 値 |
| --- | --- |
| 署名 | IntelliJ Platform SDK の zipSigner |
| 証明書 | 環境変数 `CERTIFICATE_CHAIN` から読む |
| 秘密鍵 | 環境変数 `PRIVATE_KEY` と `PRIVATE_KEY_PASSWORD` から読む |
| 発行 | 環境変数 `PUBLISH_TOKEN` を使う |
| バージョン | `gradle.properties` の `pluginVersion`。SemVer 形式 |
| CHANGELOG | org.jetbrains.changelog 2.5.0。Keep a Changelog 形式 |
| 変更履歴の抽出 | README.md の `<!-- Plugin description -->` 区間を説明文に使う |

## 継続的インテグレーション

| ファイル | 役割 | Java バージョン |
| --- | --- | --- |
| `.github/workflows/build.yml` | buildPlugin、check、Qodana、verifyPlugin、リリース下書き作成 | 21 |
| `.github/workflows/release.yml` | 公開 | 21 |
| `.github/workflows/run-ui-tests.yml` | UI テストの手動実行 | 17 |

- カバレッジは Kover の `report.xml` を Codecov へ送信する。
- 依存更新は Renovate が担当する。`gradle.properties` の `gradleVersion` を regex マネージャで追跡する。
- `build.yml` は main への push でリリースの下書きを作る。人が下書きを公開すると `release` イベントが発生し、`release.yml` が起動する。
- `release.yml` は `patchChangelog` を実行し、`CHANGELOG.md` を更新する PR を `changelog-update-<version>` ブランチで自動作成する。人間がその PR をマージする運用である。

## 既知の不整合

- `.github/workflows/run-ui-tests.yml` の `java-version` が 17 である。他のワークフローは 21 であり、プラグインの `jvmToolchain(21)` とも一致しない。
- `CHANGELOG.md` がリリース内容とずれている。0.2.2 は 2026-06-01 に公開済みだが、「Support for IntelliJ versions 2026.2」がまだ `[Unreleased]` に残っており、`[0.2.2]` 節が存在しない。`release.yml` が作る `changelog-update-0.2.2` 型の PR が未作成、または未マージである。
- IntelliJ Platform Gradle Plugin は 2.16.0 のまま。PR #123 で 2.19.0 への更新が提案されているが未マージである。
- テスト基盤（JUnit、Kover、UI テスト、Plugin Verifier）は設定済みだが、テストコードは 1 件も存在しない。
- `kover` の `onCheck = true` はカバレッジ計測だけを行う。テストが無いため有意義なゲートにならない。
