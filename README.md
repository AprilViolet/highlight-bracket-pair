# highlight-bracket-pair #

![Build](https://github.com/AprilViolet/highlight-bracket-pair/workflows/Build/badge.svg) [![Version](https://img.shields.io/jetbrains/plugin/v/17320.svg)](https://plugins.jetbrains.com/plugin/17320) [![Downloads](https://img.shields.io/jetbrains/plugin/d/17320.svg)](https://plugins.jetbrains.com/plugin/17320) 

## Plugin Description

<!-- Plugin description -->

The plugin can color highlight the Bracket Pair in editor for IntelliJ.<br/>

New feature: can render the bracket color in gutter.<br/>

highlight-bracket-pair maybe support Languages: Java, Groovy, Kotlin, Scala, Haskell, Python, JavaScript, TypeScript, Golang, Ruby, Erlang, Rust, Html, XML, Json, CSS....Of course, the support for certain languages is not perfect.<br/>

FROM <a href="https://github.com/qeesung/HighlightBracketPair">qeesung#HighlightBracketPair</a>.Fix bugs and continue to develop new features.<br/>

If you have any questions or get more information, you can go to <a href="https://github.com/AprilViolet/highlight-bracket-pair">Github</a>.Thanks.

<!-- Plugin description end -->

## Screenshots ##

+ Display of results

![HighlightBracketPair-001](./images/HighlightBracketPair-001.gif)

+ Render bracket in gutter.

![HighlightBracketPair-002](./images/HighlightBracketPair-002.png)

+ Settings

![HighlightBracketPair-003](./images/HighlightBracketPair-003.png)



![HighlightBracketPair-004](./images/HighlightBracketPair-004.png)

## Installation

<a href="https://plugins.jetbrains.com/embeddable/install/17320">
    <img src="https://user-images.githubusercontent.com/12044174/123105697-94066100-d46a-11eb-9832-338cdf4e0612.png" width="300"/>
</a>

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
+ **Build with gradle, the gradle version is the latest Gradle7.4.1**


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
