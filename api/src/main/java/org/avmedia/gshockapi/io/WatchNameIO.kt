package org.avmedia.gshockapi.io

import kotlinx.coroutines.CompletableDeferred
import org.avmedia.gshockapi.WatchInfo
import org.avmedia.gshockapi.utils.Utils
import org.json.JSONObject

object WatchNameIO {

    private object DeferredValueHolder {
        lateinit var deferredResult: CompletableDeferred<String>
    }

    suspend fun request(): String {
        return WatchInfo.name
    }

    private suspend fun getWatchName(key: String): String {
        DeferredValueHolder.deferredResult = CompletableDeferred()
        CasioIO.request(key)
        return DeferredValueHolder.deferredResult.await()
    }

    fun toJson(data: String): JSONObject {
        DeferredValueHolder.deferredResult.complete(Utils.trimNonAsciiCharacters(Utils.toAsciiString(data, 1)))
        return JSONObject()
    }
}