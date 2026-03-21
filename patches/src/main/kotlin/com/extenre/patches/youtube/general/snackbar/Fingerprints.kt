/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.youtube.general.snackbar

import com.extenre.patches.youtube.utils.resourceid.insetElementsWrapper
import com.extenre.util.fingerprint.legacyFingerprint
import com.extenre.util.getReference
import com.extenre.util.indexOfFirstInstructionReversed
import com.extenre.util.or
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.Method
import com.android.tools.smali.dexlib2.iface.reference.MethodReference

private const val BOTTOM_UI_CONTAINER_CLASS_DESCRIPTOR =
    "Lcom/google/android/apps/youtube/app/common/ui/bottomui/BottomUiContainer;"

internal val bottomUiContainerFingerprint = legacyFingerprint(
    name = "bottomUiContainerFingerprint",
    returnType = "V",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    parameters = listOf("L", "L"),
    customFingerprint = { _, classDef ->
        classDef.type == BOTTOM_UI_CONTAINER_CLASS_DESCRIPTOR
    }
)

internal val bottomUiContainerPreFingerprint = legacyFingerprint(
    name = "bottomUiContainerPreFingerprint",
    returnType = "V",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    parameters = listOf("L", "L", "L"),
    opcodes = listOf(
        Opcode.IF_NEZ,
        Opcode.INVOKE_VIRTUAL,
        Opcode.RETURN_VOID
    ),
    customFingerprint = { _, classDef ->
        classDef.type == BOTTOM_UI_CONTAINER_CLASS_DESCRIPTOR
    }
)

internal val bottomUiContainerThemeFingerprint = legacyFingerprint(
    name = "bottomUiContainerThemeFingerprint",
    returnType = "V",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    parameters = listOf(BOTTOM_UI_CONTAINER_CLASS_DESCRIPTOR),
    opcodes = listOf(
        Opcode.INVOKE_VIRTUAL,
        Opcode.MOVE_RESULT_OBJECT,
        Opcode.SGET_OBJECT,
        Opcode.IF_NE,
        Opcode.CONST,
    ),
)

internal val lithoSnackBarFingerprint = legacyFingerprint(
    name = "lithoSnackBarFingerprint",
    returnType = "Landroid/view/View;",
    literals = listOf(insetElementsWrapper),
    customFingerprint = { method, _ ->
        indexOfBackGroundColor(method) >= 0
    }
)

internal fun indexOfBackGroundColor(method: Method) =
    method.indexOfFirstInstructionReversed {
        opcode == Opcode.INVOKE_VIRTUAL &&
                getReference<MethodReference>()?.name == "setBackgroundColor"
    }