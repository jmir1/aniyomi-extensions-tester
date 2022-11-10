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
            ArgType.String, "anime-url", "a",
            description = "Target anime url"
        )

        val checkThumbnails by parser.option(
            ArgType.Boolean, "check-thumbnails",
            description = "Check if thumbnails are loading"
        ).default(false)

        val dateFormat by parser.option(
            ArgType.String, "date-format", "f",
            description = "Format to use when printing episode date"
        ).default("dd/MM/yyyy")

        val debug by parser.option(
            ArgType.Boolean, "debug", "d",
            description = "Enable okHttp debug"
        ).default(false)

        val episodeNumber by parser.option(
            ArgType.Int, "episode-number", "n",
            description = "Target episode number"
        )

        val episodeUrl by parser.option(
            ArgType.String, "episode-url", "e",
            description = "Target episode url"
        )

        val increment by parser.option(
            ArgType.Boolean, "increment-pages", "i",
            description = "Try using pagination when possible"
        ).default(false)

        val printJson by parser.option(
            ArgType.Boolean, "json", "j",
            description = "Show JSON data instead of tables"
        )

        val resultsCount by parser.option(
            ArgType.Int, "results-count", "c",
            description = "Amount of items to print from result lists"
        ).default(2)

        val searchStr by parser.option(
            ArgType.String, "search", "s",
            description = "Text to use when testing the search"
        ).default("world")

        val showAll by parser.option(
            ArgType.Boolean, "show-all", "A",
            description = "Show all items of lists, instead of the first ~2"
        )

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

        val userAgent by parser.option(
            ArgType.String,
            "user-agent", "U",
            description = "Set and use a specific user agent"
        )

        parser.parse(args)

        val configs = ConfigsDto(
            animeUrl ?: "",
            checkThumbnails,
            dateFormat,
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

        return OptionsDto(apksPath, configs, debug, tmpDir, userAgent)
    }
}
