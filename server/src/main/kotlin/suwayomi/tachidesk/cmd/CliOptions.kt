package suwayomi.tachidesk.cmd

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import suwayomi.tachidesk.anime.impl.extension.tester.TestsEnum
import suwayomi.tachidesk.cmd.dto.ConfigsDto
import suwayomi.tachidesk.cmd.dto.OptionsDto

object CliOptions {
    fun parseArgs(args: Array<String>): OptionsDto {
        val parser = ArgParser("aniyomi-extension-tester")

        val apksPath by parser.argument(
            ArgType.String,
            description = "Apk file or directory with apks"
        )

        val animeUrl by parser.option(
            ArgType.String, "anime-url",
            description = "Target anime url"
        )

        val debug by parser.option(
            ArgType.Boolean, "debug", "d",
            description = "Enable okHttp debug"
        ).default(false)

        val printJson by parser.option(
            ArgType.Boolean, "print-json", "J",
            description = "Show JSON data instead of tables"
        )

        val increment by parser.option(
            ArgType.Boolean, "increment-pages", "i",
            description = "Try using pagination when possible"
        ).default(false)

        val searchStr by parser.option(
            ArgType.String, "search", "s",
            description = "Text to use when testing the search"
        ).default("world")

        val showAll by parser.option(
            ArgType.Boolean, "show-all", "A",
            description = "Show all items of lists, instead of the first ~2"
        )

        val episodeUrl by parser.option(
            ArgType.String, "episode-url",
            description = "Target episode url"
        )

        val episodeNumber by parser.option(
            ArgType.Int, "episode-number",
            description = "Target episode number"
        )

        val resultsCount by parser.option(
            ArgType.Int, "results-count", "c",
            description = "Amount of items to print from result lists"
        ).default(2)

        val stopOnError by parser.option(
            ArgType.Boolean, "stop-on-error", "X",
            description = "Stop the tests on the first error"
        )

        val tests by parser.option(
            ArgType.String, "tests", "t",
            description = "Tests to be made(in order), delimited by commas"
        ).default(TestsEnum.getValues())

        val tmpDir by parser.option(
            ArgType.String,
            "tmp-dir",
            description = "Directory to put temporary data"
        ).default(System.getProperty("java.io.tmpdir"))

        parser.parse(args)

        val configs = ConfigsDto(
            animeUrl ?: "",
            episodeUrl ?: "",
            episodeNumber ?: -1,
            increment,
            printJson ?: false,
            resultsCount,
            searchStr,
            showAll ?: false,
            stopOnError ?: false,
            tests
        )

        return OptionsDto(apksPath, tmpDir, debug, configs)
    }
}
