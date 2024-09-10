package kosh.data

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper
import com.benasher44.uuid.uuid4
import kosh.domain.repositories.ShareRepo
import okio.IOException
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AndroidShareRepo(
    private val contentResolver: ContentResolver,
    private val activityResultRegistry: ActivityResultRegistry,
) : ShareRepo {

    override suspend fun shareJson(
        name: String,
        json: String,
    ) {
        val uri = createJsonFile(name)
            ?: throw CancellationException("User cancelled file creation")

        contentResolver.openOutputStream(uri)?.bufferedWriter()?.use {
            it.write(json)
            it.flush()
        } ?: error("Couldn't open file for writing")
    }

    override suspend fun importJson(): String {
        val uri = openJsonFile()
            ?: throw CancellationException("User cancelled file opening")

        return contentResolver.openInputStream(uri)?.bufferedReader()?.use {
            it.readText()
        } ?: error("Couldn't open file for reading")
    }

    private suspend fun createJsonFile(name: String): Uri? = suspendCoroutine { cont ->
        activityResultRegistry.register(
            uuid4().toString(),
            CreateJsonDocument()
        ) { uri ->
            if (uri != null) {
                cont.resume(uri)
            } else {
                cont.resumeWithException(IOException("Failed to create file $name during exporting"))
            }
        }.launch(name)
    }

    private suspend fun openJsonFile(): Uri? = suspendCoroutine { cont ->
        activityResultRegistry.register(
            uuid4().toString(),
            OpenJsonDocument()
        ) { uri ->
            if (uri != null) {
                cont.resume(uri)
            } else {
                cont.resume(null)
            }
        }.launch(Unit)
    }
}

private class CreateJsonDocument : ActivityResultContract<String, Uri?>() {
    @CallSuper
    override fun createIntent(context: Context, input: String): Intent {
        return Intent(Intent.ACTION_CREATE_DOCUMENT)
            .addCategory(Intent.CATEGORY_OPENABLE)
            .putExtra(Intent.EXTRA_TITLE, input)
            .setType("text/plain")
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return intent.takeIf { resultCode == Activity.RESULT_OK }?.data
    }
}

private class OpenJsonDocument : ActivityResultContract<Unit, Uri?>() {
    @CallSuper
    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(Intent.ACTION_OPEN_DOCUMENT)
            .addCategory(Intent.CATEGORY_OPENABLE)
            .setType("text/plain")
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return intent.takeIf { resultCode == Activity.RESULT_OK }?.data
    }
}
