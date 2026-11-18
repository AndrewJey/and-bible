package net.bible.android

import androidx.core.content.FileProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class FileProviderSharingTest {
    @Test
    fun backupAndExportFilesCanBeReadThroughTheirContentUris() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val payload = byteArrayOf(1, 7, 42)
        for (directory in listOf("backup", "export")) {
            val parent = File(context.filesDir, directory).apply { mkdirs() }
            val file = File.createTempFile("sharing-test-", ".txt", parent)
            try {
                file.writeBytes(payload)
                val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
                assertEquals("content", uri.scheme)
                context.contentResolver.openInputStream(uri).use { input ->
                    requireNotNull(input)
                    assertArrayEquals(payload, input.readBytes())
                }
            } finally {
                file.delete()
            }
        }
    }
}
