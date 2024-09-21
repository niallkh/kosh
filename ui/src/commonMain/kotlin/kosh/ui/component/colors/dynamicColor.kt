package kosh.ui.component.colors

import androidx.compose.material3.ColorScheme
import arrow.core.memoize
import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.Sign
import com.materialkolor.dynamicColorScheme
import com.materialkolor.hct.Hct
import com.materialkolor.ktx.toColor
import kotlinx.io.bytestring.ByteString

val dynamicColorMemo = ::calculateDynamicColor.memoize()

fun dynamicColor(
    bytes: ByteString,
    isDark: Boolean,
): ColorScheme = calculateDynamicColor(bytes, isDark)

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
