My Notes
==================

Fully functional Android app built entirely with Kotlin and Jetpack Compose.

## Features

| List | Editor | Detail |
| ----------- | ----------- | ----------- |
| ![Screenshot - List Screen](docs/images/screenshot-list.png "Screenshot - List Screen") | ![Screenshot - Editor Screen](docs/images/screenshot-editor.png "Screenshot - Editor Screen") | ![Screenshot - Detail Screen](docs/images/screenshot-detail.png "Screenshot - Detail Screen") |

## Tech stack
* [Jetpack Compose](https://developer.android.com/jetpack/compose) Android’s modern toolkit for building native UI
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) Dependency injection library
* [Sqldelight](https://cashapp.github.io/sqldelight/) For generating typesafe Kotlin APIs from SQL statements
* [Accompanist](https://google.github.io/accompanist/) libraries

# Architecture
MVI (TBA)

# Modularization
```mermaid
graph TD
    A[App] --> B(:features:list)
    A --> C(:features:editor)
    A --> D(:features:detail)
    B --> E(:core:notes)
    C --> E(:core:notes)
    D --> E(:core:notes)
    B --> F(:foundation:theme)
    C --> F(:foundation:theme)
    D --> F(:foundation:theme)
    B --> G(:foundation:arch)
    C --> G(:foundation:arch)
    D --> G(:foundation:arch)
```