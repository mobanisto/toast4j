# Toast4j
Toast4j is a Java package for creating native Windows notifications. It is a Java extension built
around C++ and is based on the [WinToast](https://github.com/mohabouje/WinToast) library.

## Prerequisites

Install Visual Studio and configure hardcoded paths in `build.gradle`.

## Documentation

You may find some documentation on the [WinToast](https://github.com/mohabouje/WinToast) repo
and also on the [introductory article](https://learn.microsoft.com/en-us/windows/apps/design/shell/tiles-and-notifications/toast-notifications-overview)
on Microsoft Learn about toast notifications.

## History

This project is based on [ootime/jWinToast](https://github.com/ootime/jWinToast)
and [ootime/WinToastWrapper](https://github.com/ootime/WinToastWrapper).
WinToastWrapper is in turn based on [mohabouje/WinToast](https://github.com/mohabouje/WinToast)
from which it includes files `wintoastlib.h` and `wintoastlib.cpp`.
It seems to originally have taken those files from that repository at revision
c8f50eb220cab7aec5e4e426ce52e75de7d20964 (Mar 24, 2019) on top of which some modifications
have been added.

WinToastWrapper and jWinToast added the C++ bindings using JavaCPP and also tried to support
some of the more modern notification features with custom templates, i.e. buttons, progress
bars etc. These extension were not fully baked yet, so we chose to remove them and only keep
the JavaCPP wrappers.

While working with the original WinToast source code, some rough edges have been detected that
have been smoothened within this repository. There's still some cleaning up and documentation
left to be done though.

## Development

In order to build successfully in IntelliJ you need to have the MSVC compiler on you path.
Edit the run configuration of a class you want to run
and set the PATH in the "Environment variables" input field:

```
PATH=C:\Program Files\Microsoft Visual Studio\2022\Community\VC\Tools\MSVC\14.33.31629\bin\Hostx64\x64
```

## Publishing

To build and publish, you need to have the MSVC compiler on your path. On cygwin, you can
do something like this:

    export PATH=$PATH:"/c/Program Files\Microsoft Visual Studio\2022\Community\VC\Tools\MSVC\14.33.31629\bin\Hostx64\x64"

Afterwards, in order to publish to Maven local:

    ./gradlew publishToMavenLocal

Publishing to our Maven repository works like this:

    ./gradlew -Ptopboyte publish
