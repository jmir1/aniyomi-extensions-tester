# Aniyomi extensions tester

This is a fork of [tachiyomi-extensions-inspector](https://github.com/tachiyomiorg/tachiyomi-extensions-inspector)(a headless fork of [Tachidesk](https://github.com/Suwayomi/Tachidesk)) modified to be able to test [aniyomi extensions](https://github.com/jmir1/aniyomi-extensions/tree/repo/apk).

## Features
- Can help a LOT to debug extensions
- Easy to use
- Works on pure Linux and Android
- Has enough options to meet most of your needs.

## Compiling
First, run the `getAndroid` script, to generate the needed android.jar file (only once)
```bash
$ ./AndroidCompat/getAndroid.sh
```

Then compile the project:
```bash
$ ./gradlew :server:shadowJar
```
output file path: server/build/aniyomi-extensions-tester-\<version\>.jar

## Usage
```bash
$ java -jar server/build/aniyomi-extensions-tester-v0.0.1.jar -h
Usage: aniyomi-extension-tester options_list
Arguments:
    apksPath -> Apk file or directory with apks { String }
Options:
    --anime-url -> Target anime url { String }
    --debug, -d [false] -> Enable okHttp debug
    --print-json, -J -> Show JSON data instead of tables
    --increment-pages, -i [false] -> Try using pagination when possible
    --search, -s [world] -> Text to use when testing the search { String }
    --show-all, -A -> Show all items of lists, instead of the first ~2
    --episode-url -> Target episode url { String }
    --episode-number -> Target episode number { Int }
    --results-count, -c [2] -> Amount of items to print from result lists { Int }
    --stop-on-error, -X -> Stop the tests on the first error
    --tests, -t [popular,latest,search,anidetails,eplist,videolist] -> Tests to be made(in order), delimited by commas { String }
    --tmp-dir [/data/data/com.termux/files/usr/tmp/] -> Directory to put temporary data { String }
    --help, -h -> Usage info

```

## TODO
- [x] Implement all main functions from extensions
- [ ] Support search filters
- [ ] Test and check thumbnail URLs and video URLs
- [ ] Show time spent on every test completed
- [x] Honor all CLI options

## Credits

The `AndroidCompat` module was originally developed by [@null-dev](https://github.com/null-dev) for [TachiWeb-Server](https://github.com/Tachiweb/TachiWeb-server) and is licensed under `Apache License Version 2.0`.

Parts of [Tachiyomi](https://github.com/tachiyomiorg/tachiyomi) and [Aniyomi](https://github.com/jmir1/aniyomi) are adopted into this codebase, both licensed under `Apache License Version 2.0`.

You can obtain a copy of `Apache License Version 2.0` from  http://www.apache.org/licenses/LICENSE-2.0

Changes to these codebases is licensed under `MPL 2.0` as the rest of this project.

## License
```
Copyright (C) The Aniyomi Open Source Project

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at http://mozilla.org/MPL/2.0/.
```

```
Copyright (C) The Tachiyomi Open Source Project

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at http://mozilla.org/MPL/2.0/.
```

```
Copyright (C) Contributors to the Suwayomi project

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at http://mozilla.org/MPL/2.0/.
```
