/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.all.misc.signature

import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.Utils.printWarn
import app.morphe.util.fingerprint.mutableMethodOrThrow
import app.morphe.util.fingerprint.mutableClassOrThrow
import app.morphe.util.indexOfFirstStringInstructionOrThrow
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction

class AppInfo(
    val packageName: String? = "",
    val certificateData: String? = "",
)

fun baseSpoofSignaturePatch(appInfoSupplier: () -> AppInfo) = bytecodePatch(
    name = "Base Spoof Signature",
    description = "Base patch for spoofing app signature.",
) {
    // Lazy, so that patch options above are initialized before they are accessed.
    val replacements by lazy {
        with(appInfoSupplier()) {
            buildMap {
                if (!packageName.isNullOrEmpty()) put(PACKAGE_NAME, "\"$packageName\"")
                if (!certificateData.isNullOrEmpty()) put(CERTIFICATE_BASE64, "\"$certificateData\"")
            }
        }
    }

    extendWith("extensions/all/misc/signature/spoof-signature.mpe")

    execute {
        if (replacements.size != 2) {
            printWarn("Invalid package name or certificate data, skipping patch")

            return@execute
        }

        spoofSignatureFingerprint.mutableMethodOrThrow().apply {
            replacements.forEach { (k, v) ->
                val index = indexOfFirstStringInstructionOrThrow(k)
                val register = getInstruction<OneRegisterInstruction>(index).registerA

                replaceInstruction(index, "const-string v$register, $v")
            }

            applicationFingerprint.mutableClassOrThrow().setSuperClass(definingClass)
        }
    }
}
