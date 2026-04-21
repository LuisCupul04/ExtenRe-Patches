/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.shared.extension

import com.extenre.patcher.Fingerprint
import com.extenre.patcher.FingerprintBuilder
import com.extenre.patcher.extensions.InstructionExtensions.addInstruction
import com.extenre.patcher.extensions.InstructionExtensions.replaceInstruction
import com.extenre.patcher.fingerprint
import com.extenre.patcher.patch.BytecodePatchContext
import com.extenre.patcher.patch.PatchException
import com.extenre.patcher.patch.bytecodePatch
import com.extenre.patcher.util.proxy.mutableTypes.encodedValue.MutableLongEncodedValue
import com.extenre.patches.shared.extension.Constants.EXTENSION_PATCH_STATUS_CLASS_DESCRIPTOR
import com.extenre.patches.shared.extension.Constants.EXTENSION_UTILS_CLASS_DESCRIPTOR
import com.extenre.util.findMethodsOrThrow
import com.extenre.util.returnEarly
import com.android.tools.smali.dexlib2.iface.Method
import com.android.tools.smali.dexlib2.immutable.value.ImmutableLongEncodedValue
import java.util.jar.Manifest

fun sharedExtensionPatch(
    vararg hooks: ExtensionHook,
) = bytecodePatch(
    description = "sharedExtensionPatch"
) {
    extendWith("extensions/shared.re")

    execute {
        if (classes.none { EXTENSION_UTILS_CLASS_DESCRIPTOR == it.type }) {
            throw PatchException(
                "Shared extension has not been merged yet. This patch can not succeed without merging it.",
            )
        }
        hooks.forEach { hook -> hook(EXTENSION_UTILS_CLASS_DESCRIPTOR) }
    }

    finalize {
        findMethodsOrThrow(EXTENSION_PATCH_STATUS_CLASS_DESCRIPTOR).apply {
            find { method -> method.name == "PatchedTime" }
                ?.replaceInstruction(
                    0,
                    "const-wide v0, ${MutableLongEncodedValue(ImmutableLongEncodedValue(System.currentTimeMillis()))}L"
                )

            find { method -> method.name == "PatchVersion" }
                ?.apply {
                    val manifest = object {}
                        .javaClass
                        .classLoader
                        .getResources("META-INF/MANIFEST.MF")

                    while (manifest.hasMoreElements()) {
                        Manifest(manifest.nextElement().openStream())
                            .mainAttributes
                            .getValue("Version")
                            ?.let {
                                returnEarly(it)
                                return@finalize
                            }
                    }
                }
        }
    }
}

@Suppress("CONTEXT_RECEIVERS_DEPRECATED")
class ExtensionHook internal constructor(
    val fingerprint: Fingerprint,
    private val insertIndexResolver: ((Method) -> Int),
    private val contextRegisterResolver: (Method) -> String,
) {
    context(BytecodePatchContext)
    operator fun invoke(extensionClassDescriptor: String) {
        val insertIndex = insertIndexResolver(fingerprint.method)
        val contextRegister = contextRegisterResolver(fingerprint.method)

        fingerprint.method.addInstruction(
            insertIndex,
            "invoke-static/range { $contextRegister .. $contextRegister }, " +
                    "$extensionClassDescriptor->setContext(Landroid/content/Context;)V",
        )
    }
}

fun extensionHook(
    insertIndexResolver: ((Method) -> Int) = { 0 },
    contextRegisterResolver: (Method) -> String = { "p0" },
    fingerprintBuilderBlock: FingerprintBuilder.() -> Unit,
) = ExtensionHook(
    fingerprint(block = fingerprintBuilderBlock),
    insertIndexResolver,
    contextRegisterResolver
)
