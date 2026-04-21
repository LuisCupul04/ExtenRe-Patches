/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.all.misc.transformation

import app.morphe.patcher.patch.BytecodePatchContext
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.util.mutableTypes.MutableMethod
import com.android.tools.smali.dexlib2.iface.ClassDef
import com.android.tools.smali.dexlib2.iface.Method
import com.android.tools.smali.dexlib2.iface.instruction.Instruction

private const val EXTENSION_NAME_SPACE_PATH =
    "Lapp/morphe/extension/"

fun <T> transformInstructionsPatch(
    filterMap: (ClassDef, Method, Instruction, Int) -> T?,
    transform: (MutableMethod, T) -> Unit,
    executeBlock: BytecodePatchContext.() -> Unit = {},
    skipExtension: Boolean = true,
) = bytecodePatch(
    description = "transformInstructionsPatch"
) {
    // Función auxiliar para encontrar índices donde filterMap devuelve algo
    fun findPatchIndices(classDef: ClassDef, method: Method): Sequence<T>? =
        method.implementation?.instructions?.asSequence()?.withIndex()
            ?.mapNotNull { (index, instruction) ->
                filterMap(classDef, method, instruction, index)
            }

    execute {
        // ✅ Cambio 1: usar mutableClassDefs en lugar de classes
        mutableClassDefs.forEach { mutableClass ->
            if (skipExtension && mutableClass.type.startsWith(EXTENSION_NAME_SPACE_PATH)) {
                return@forEach
            }
            
            // ✅ Cambio 2: filtrar métodos mutables directamente
            val methodsToPatch = mutableClass.methods.filter { method ->
                findPatchIndices(mutableClass, method)?.any() == true
            }

            // ✅ Cambio 3: transformar cada método (ya es MutableMethod)
            methodsToPatch.forEach { mutableMethod ->
                val patchIndices = findPatchIndices(mutableClass, mutableMethod)
                    ?.toCollection(ArrayDeque()) ?: return@forEach
                while (patchIndices.isNotEmpty()) {
                    transform(mutableMethod, patchIndices.removeLast())
                }
            }
        }

        executeBlock()
    }
}
