package kosh.ui.component.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import arrow.core.right
import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.Sign
import com.materialkolor.dynamicColorScheme
import com.materialkolor.hct.Hct
import com.materialkolor.ktx.toColor
import kosh.domain.utils.md5
import kosh.presentation.rememberLoad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.bytestring.ByteString

private val dynamicColorMemo = ::calculateDynamicColor

@Composable
fun dynamicColor(
    bytes: ByteString,
    isDark: Boolean,
): () -> ColorScheme {
    val scheme = rememberLoad(bytes, isDark) {
        withContext(Dispatchers.Default) {
            dynamicColorMemo(bytes.md5(), isDark).right().bind()
        }
    }

    val default by rememberUpdatedState(MaterialTheme.colorScheme)

    return remember {
        { scheme.result ?: default }
    }
}

private fun calculateDynamicColor(
    bytes: ByteString,
    isDark: Boolean,
): ColorScheme {
    val hue = BigInteger.fromByteArray(bytes.toByteArray(), Sign.POSITIVE)
        .mod(BigInteger(360))
        .intValue()

    return dynamicColorScheme(
        seedColor = Hct.from(hue.toDouble(), 36f.toDouble(), 50f.toDouble()).toColor(),
        isDark = isDark,
        isAmoled = false,
    )
}
