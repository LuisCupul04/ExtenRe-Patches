/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.liskovsoft.youtubeapi.app.nsigsolver.provider

internal open class InfoExtractorError(message: String, cause: Exception? = null) :
    Exception(message, cause)

internal open class ContentProviderError(message: String, cause: Exception? = null) :
    Exception(message, cause)

/**
 * Reject the JsChallengeRequest (cannot handle the request)
 */
internal class JsChallengeProviderRejectedRequest(message: String, cause: Exception? = null) :
    ContentProviderError(message, cause)

/**
 * An error occurred while solving the challenge
 */
internal class JsChallengeProviderError(message: String, cause: Exception? = null) :
    ContentProviderError(message, cause)