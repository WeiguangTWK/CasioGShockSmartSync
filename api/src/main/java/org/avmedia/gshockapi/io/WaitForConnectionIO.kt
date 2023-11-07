package org.avmedia.gshockapi.io

import android.bluetooth.BluetoothDevice
import android.content.Context
import kotlinx.coroutines.CompletableDeferred
import org.avmedia.gshockapi.EventAction
import org.avmedia.gshockapi.ProgressEvents
import org.avmedia.gshockapi.ble.BleScannerLocal
import org.avmedia.gshockapi.ble.Connection
import org.avmedia.gshockapi.ble.DeviceCharacteristics
import org.avmedia.gshockapi.utils.WatchDataListener
import timber.log.Timber

object WaitForConnectionIO {

    private object DeferredValueHolder {
        lateinit var deferredResult: CompletableDeferred<String>
    }

    suspend fun request(
        context: Context,
        bleScannerLocal: BleScannerLocal,
        deviceId: String? = "",
        deviceName: String? = ""
    ): String {
        return waitForConnection(context, bleScannerLocal, deviceId, deviceName)
    }

    private suspend fun waitForConnection(
        context: Context,
        bleScannerLocal: BleScannerLocal,
        deviceId: String? = "",
        deviceName: String? = ""
    ): String {

        if (Connection.isConnected() || Connection.isConnecting()) {
            return "Connecting"
        }

        DeferredValueHolder.deferredResult = CompletableDeferred()

        Connection.init(context)
        WatchDataListener.init()

        // TODO: remove  bleScannerLocal = BleScannerLocal(context)
        bleScannerLocal.startConnection(deviceId, deviceName)

        fun waitForConnectionSetupComplete() {
            val eventActions = arrayOf(
                EventAction("ConnectionSetupComplete") {
                    val device =
                        ProgressEvents.getPayload("ConnectionSetupComplete") as BluetoothDevice
                    DeviceCharacteristics.init(device)

                    CachedIO.clearCache()
                    DeferredValueHolder.deferredResult.complete("OK")
                },
            )

            ProgressEvents.subscriber.runEventActions(this.javaClass.canonicalName, eventActions)
        }

        waitForConnectionSetupComplete()
        return DeferredValueHolder.deferredResult.await()
    }
}