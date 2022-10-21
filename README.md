# jWinToast
jWinToast is a Java package for creating native Windows notifications. It is a Java extension built around C++.
The C++ part comes from [WinToastWrapper](https://github.com/ootime/WinToastWrapper) library.

## Prerequisites

Install Visual Studio and configure current environment variables.

## Documentation

You may find some limited documentation on [WinToast Page](https://github.com/mohabouje/WinToast).

## History

This project is based on [ootime/jWinToast](https://github.com/ootime/jWinToast)
and [ootime/WinToastWrapper](https://github.com/ootime/WinToastWrapper).
WinToastWrapper is in turn based on [mohabouje/WinToast](https://github.com/mohabouje/WinToast)
from which it includes files `wintoastlib.h` and `wintoastlib.cpp`.
It seems to originally have taken those files from that repository at revision
c8f50eb220cab7aec5e4e426ce52e75de7d20964 (Mar 24, 2019) on top of which some modifications
have been added.

## Publishing

To build and publish, you need to have the MSVC compiler on your path. On cygwin, you can
do something like this:

    export PATH=$PATH:"/c/Program Files\Microsoft Visual Studio\2022\Community\VC\Tools\MSVC\14.33.31629\bin\Hostx64\x64"

Afterwards, in order to publish to Maven local:

    ./gradlew publishToMavenLocal
