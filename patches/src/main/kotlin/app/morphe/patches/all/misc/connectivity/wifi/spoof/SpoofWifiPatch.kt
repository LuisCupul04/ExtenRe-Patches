/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.all.misc.connectivity.wifi.spoof

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.all.misc.transformation.IMethodCall
import app.morphe.patches.all.misc.transformation.filterMapInstruction35c
import app.morphe.patches.all.misc.transformation.transformInstructionsPatch

internal const val EXTENSION_CLASS_DESCRIPTOR_PREFIX =
    "Lapp/morphe/extension/all/connectivity/wifi/spoof/SpoofWifiPatch"

internal const val EXTENSION_CLASS_DESCRIPTOR = "$EXTENSION_CLASS_DESCRIPTOR_PREFIX;"

@Suppress("unused")
val spoofWifiPatch = bytecodePatch(
    name = "Spoof Wi-Fi connection",
    description = "Spoofs an existing Wi-Fi connection.",
    defaultEnable = false,   // ← Cambiado de 'use = false'
) {
    extendWith("extensions/all/connectivity/wifi/spoof/spoof-wifi.mpe")

    dependsOn(
        transformInstructionsPatch(
            filterMap = { classDef, _, instruction, instructionIndex ->
                filterMapInstruction35c<MethodCall>(
                    EXTENSION_CLASS_DESCRIPTOR_PREFIX,
                    classDef,
                    instruction,
                    instructionIndex,
                )
            },
            transform = { method, entry ->
                val (methodType, instruction, instructionIndex) = entry
                methodType.replaceInvokeVirtualWithExtension(
                    EXTENSION_CLASS_DESCRIPTOR,
                    method,
                    instruction,
                    instructionIndex,
                )
            },
        ),
    )
}

// El resto del código (enum MethodCall) permanece igual...
