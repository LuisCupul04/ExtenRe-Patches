/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.music.utils.extension.hooks

import app.morphe.patches.shared.extension.extensionHook
import app.morphe.util.getReference
import app.morphe.util.indexOfFirstInstruction
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.reference.MethodReference

internal val applicationInitHook = extensionHook {
    returns("V")
    parameters()
    strings("activity")
    custom { method, _ ->
        method.name == "onCreate" &&
                method.indexOfFirstInstruction {
                    opcode == Opcode.INVOKE_VIRTUAL
                            && getReference<MethodReference>()?.name == "getRunningAppProcesses"
                } >= 0
    }
}
