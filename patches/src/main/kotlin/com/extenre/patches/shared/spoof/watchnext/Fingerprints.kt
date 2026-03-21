/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.shared.spoof.watchnext

import com.extenre.util.fingerprint.legacyFingerprint
import com.extenre.util.getReference
import com.extenre.util.indexOfFirstInstruction
import com.extenre.util.or
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.Method
import com.android.tools.smali.dexlib2.iface.reference.MethodReference

internal val watchNextResponseModelClassResolverFingerprint = legacyFingerprint(
    name = "watchNextResponseModelClassResolverFingerprint",
    returnType = "V",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    parameters = emptyList(),
    strings = listOf("Request being made from non-critical thread"),
    customFingerprint = { method, classDef ->
        method.name == "run" &&
                indexOfListenableFutureReference(method) >= 0
    }
)

internal fun indexOfListenableFutureReference(method: Method) =
    method.indexOfFirstInstruction {
        opcode == Opcode.INVOKE_INTERFACE &&
                getReference<MethodReference>()?.toString() == "Lcom/google/common/util/concurrent/ListenableFuture;->get()Ljava/lang/Object;"
    }

internal val watchNextConstructorFingerprint = legacyFingerprint(
    name = "watchNextConstructorFingerprint",
    returnType = "V",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.CONSTRUCTOR,
    opcodes = listOf(
        Opcode.INVOKE_DIRECT_RANGE,
        Opcode.RETURN_VOID,
    ),
)
