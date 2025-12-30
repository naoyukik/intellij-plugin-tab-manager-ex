# intellij-plugin-tab-manager-ex

![Build](https://github.com/naoyukik/intellij-plugin-tab-manager-ex/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/29355-tab-manager-ex.svg)](https://plugins.jetbrains.com/plugin/29355-tab-manager-ex)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/29355-tab-manager-ex.svg)](https://plugins.jetbrains.com/plugin/29355-tab-manager-ex)

<!-- Plugin description -->
This plugin provides convenient tab management features for IntelliJ-based IDEs, including opening files from Git commits and bulk pin/unpin operations.

This is useful when you want to review the changes in a set of commits, or quickly open all relevant files for a feature or bug fix.

### Features
**Open Changed Files from Git Log**
- Right-click on one or more commits in the Git log view.
- Select "Open Changed Files" from the context menu.
- All files that were created, modified, or moved in the selected commits will be opened in the editor.
- Deleted files are ignored.

**Pin All Open Files**
- Right-click on an editor tab or within the editor.
- Select "Pin All Open Files".
- All currently open files will be pinned.

**Remove All Pins**
- Right-click on an editor tab or within the editor.
- Select "Remove All Pins".
- All currently open files will be unpinned.

<!-- Plugin description end -->

## Installation

- Using the IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "Tab Manager Ex"</kbd> >
  <kbd>Install</kbd>
  
- Using JetBrains Marketplace:

  Go to [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/29355-tab-manager-ex) and install it by clicking the <kbd>Install to ...</kbd> button in case your IDE is running.

  You can also download the [latest release](https://plugins.jetbrains.com/plugin/29355-tab-manager-ex/versions) from JetBrains Marketplace and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

- Manually:

  Download the [latest release](https://github.com/naoyukik/intellij-plugin-tab-manager-ex/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation
