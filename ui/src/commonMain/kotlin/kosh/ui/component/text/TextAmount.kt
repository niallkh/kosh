package kosh.ui.component.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.decimal.RoundingMode
import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.eth.abi.Value

@Composable
fun TextAmount(
    amount: BigInteger,
    symbol: String,
    decimals: UByte = 18u,
    modifier: Modifier = Modifier,
) {
    val text = remember(amount, symbol, decimals) { // TODO improve this
        if (amount == Value.BigNumber.UINT256_MAX) {
            "ALL $symbol"
        } else {
            var resultScale: Scale = Scale.Default
            var text = amount.toString()

            for (scale in Scale.entries.filter { decimals >= it.correction }) {
                val decimal =
                    BigInteger.ONE * BigInteger.TEN.pow((decimals - scale.correction).toInt())

                text = BigDecimal.fromBigInteger(amount)
                    .divide(BigDecimal.fromBigInteger(decimal))
                    .roundToDigitPositionAfterDecimalPoint(6, RoundingMode.TOWARDS_ZERO)
                    .toStringExpanded()

                if (text != "0") {
                    resultScale = scale
                    break
                }
            }

            "$text ${resultScale.prefix}$symbol"
        }
    }

    Text(
        modifier = modifier,
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

private enum class Scale(val correction: UByte, val prefix: String) {
    Default(0u, ""),
    Nano(9u, "n"),
}
