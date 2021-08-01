# highlight-bracket-pair #

![Build](https://github.com/AprilViolet/highlight-bracket-pair/workflows/Build/badge.svg)[![Version](https://img.shields.io/jetbrains/plugin/v/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)[![Downloads](https://img.shields.io/jetbrains/plugin/d/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)![Downloads](https://camo.githubusercontent.com/3786cf64bc973cbd34b2b4e7b6f9de0da69d01833d7ca54e393cd855e204639c/68747470733a2f2f696d672e736869656c64732e696f2f6769746875622f6c6963656e73652f71656573756e672f486967686c69676874427261636b6574506169722e737667)

## Plugin Description

<!-- Plugin description -->

The plugin can color highlight the Bracket Pair in editor for intellij.
	FROM [qeesung#HighlightBracketPair](https://github.com/qeesung/HighlightBracketPair).After the original author didn't maintain it for a long time, I pulled out a branch from the previous code, redeveloped it, fixed bugs, and was compatible with the latest version of idea.

highlight-bracket-pair maybe support Languages: Java, Groovy, Kotlin, Scala, Haskell, Python, JavaScript, TypeScript, Golang, Ruby, Erlang, Rust, Html, XML, Json, CSS...

In the future, I will try to make it support more languages.Coming soon.
<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "highlight-bracket-pair"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/AprilViolet/highlight-bracket-pair/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

## How to customize colors ##

+ Settings/Preferences > Editor > Color Scheme > HighlighterBracketPair

## How to contribute ##

+ Clone the code.
+ Open with idea

**Preconditions**

+ **Note that the project is built on the LTS version of the JDK11.**
+ **Build with gradle, the gradle version is the latest Gradle7.1.1**


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
