package kosh.ui.component.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.decimal.DecimalMode
import com.ionspin.kotlin.bignum.decimal.RoundingMode.ROUND_HALF_AWAY_FROM_ZERO
import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.domain.entities.TokenEntity
import kosh.domain.models.token.TokenMetadata
import kosh.eth.abi.Value
import kosh.ui.component.placeholder.placeholder

@Composable
fun TextAmount(
    token: TokenEntity?,
    amount: BigInteger?,
    modifier: Modifier = Modifier,
) {
    TextAmount(
        amount = amount ?: BigInteger(100000),
        symbol = token?.symbol,
        decimals = token?.decimals ?: 0u,
        placeholder = amount == null || token == null,
        modifier = modifier,
    )
}

@Composable
fun TextAmount(
    token: TokenMetadata?,
    amount: BigInteger?,
    modifier: Modifier = Modifier,
) {
    TextAmount(
        amount = amount ?: BigInteger(100000),
        symbol = token?.symbol ?: "Lorem",
        decimals = token?.decimals ?: 0u,
        placeholder = amount == null || token == null,
        modifier = modifier,
    )
}

@Composable
private fun TextAmount(
    symbol: String?,
    amount: BigInteger,
    decimals: UByte,
    placeholder: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val text = remember(amount, symbol, decimals) {
        formatAmount(amount, decimals, symbol)
    }

    Text(
        modifier = modifier
            .placeholder(placeholder),
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

private fun formatAmount(
    amount: BigInteger,
    decimals: UByte,
    symbol: String?,
): String {
    val symbol1 = symbol?.let { " $it" } ?: ""
    if (amount == BigInteger.ZERO) return "0 $symbol1"
    if (amount == Value.BigNumber.UINT256_MAX) return "UNLIMITED $symbol1"
    if (amount > Value.BigNumber.UINT256_MAX) return "INVALID $symbol1"

    val decimalMode = DecimalMode(decimals.toLong(), ROUND_HALF_AWAY_FROM_ZERO, 5)

    return Scale.entries.asSequence()
        .filter { it.correction + decimals.toInt() >= 0 }
        .mapNotNull { scale ->
            val decimal = BigInteger.TEN.pow((decimals.toInt() + scale.correction))
            val value = BigDecimal.fromBigInteger(amount)
                .divide(BigDecimal.fromBigInteger(decimal))
                .roundSignificand(decimalMode)

            val txt = value.toStringExpanded()
            if (txt == "0") return@mapNotNull null
            "$txt ${scale.prefix}$symbol1"
        }
        .firstOrNull()
        ?: "0 $symbol1"
}

private enum class Scale(val correction: Byte, val prefix: String) {
    Default(0, ""),
    Nano(-9, "n"),
}
