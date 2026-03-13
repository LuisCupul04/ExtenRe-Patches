/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.shared.webview

import com.extenre.patcher.Fingerprint
import com.extenre.patcher.extensions.InstructionExtensions.addInstructionsWithLabels
import com.extenre.patcher.patch.BytecodePatchBuilder
import com.extenre.patcher.patch.BytecodePatchContext
import com.extenre.patcher.patch.bytecodePatch
import com.extenre.patcher.patch.resourcePatch
import com.extenre.patches.shared.extension.Constants.EXTENSION_PATCH_STATUS_CLASS_DESCRIPTOR
import com.extenre.util.ResourceGroup
import com.extenre.util.className
import com.extenre.util.copyResources
import com.extenre.util.findFreeRegister
import com.extenre.util.findMethodOrThrow
import com.extenre.util.fingerprint.mutableClassOrThrow
import com.extenre.util.hookClassHierarchy
import com.extenre.util.indexOfFirstInstruction
import com.extenre.util.returnEarly
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.util.MethodUtil

private val webViewResourcePatch = resourcePatch(
    description = "webViewResourcePatch"
) {
    execute {
        arrayOf(
            ResourceGroup(
                "layout",
                "extenre_webview.xml",
            ),
            ResourceGroup(
                "menu",
                "extenre_webview_menu.xml",
            )
        ).forEach { resourceGroup ->
            copyResources("shared/webview", resourceGroup)
        }
    }
}

fun webViewPatch(
    block: BytecodePatchBuilder.() -> Unit = {},
    targetActivityFingerprint: Pair<String, Fingerprint>,
    executeBlock: BytecodePatchContext.() -> Unit = {},
) = bytecodePatch(
    description = "webViewPatch",
) {
    block()

    dependsOn(webViewResourcePatch)

    execute {
        val hostActivityClass = webViewHostActivityOnCreateFingerprint.mutableClassOrThrow()
        val targetActivityClass = targetActivityFingerprint.mutableClassOrThrow()

        hookClassHierarchy(
            hostActivityClass,
            targetActivityClass
        )

        targetActivityClass.methods.forEach { method ->
            method.apply {
                if (!MethodUtil.isConstructor(method) && returnType == "V") {
                    val insertIndex =
                        indexOfFirstInstruction(Opcode.INVOKE_SUPER) + 1
                    if (insertIndex > 0) {
                        val freeRegister = findFreeRegister(insertIndex)

                        addInstructionsWithLabels(
                            insertIndex, """
                                invoke-virtual {p0}, ${hostActivityClass.type}->isInitialized()Z
                                move-result v$freeRegister
                                if-eqz v$freeRegister, :ignore
                                return-void
                                :ignore
                                nop
                                """
                        )
                    }
                }
            }
        }

        val targetActivityClassName = targetActivityClass.type.className
        findMethodOrThrow(EXTENSION_PATCH_STATUS_CLASS_DESCRIPTOR) {
            name == "WebViewActivityClass"
        }.returnEarly(targetActivityClassName)

        executeBlock()
    }
}