/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.generator

import com.extenre.patcher.patch.loadPatchesFromJar
import java.io.File

internal fun main() = loadPatchesFromJar(
    setOf(File("build/libs/").listFiles { file ->
        val fileName = file.name
        !fileName.contains("javadoc") &&
                !fileName.contains("sources") &&
                fileName.endsWith(".EXRE")
    }!!.first()),
).also { loader ->
    if (loader.isEmpty()) throw IllegalStateException("No patches found")
}.let { bundle ->
    arrayOf(
        JsonPatchesFileGenerator(),
        ReadMeFileGenerator()
    ).forEach { generator -> generator.generate(bundle) }
}
