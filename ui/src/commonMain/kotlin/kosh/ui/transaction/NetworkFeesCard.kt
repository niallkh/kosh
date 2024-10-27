package kosh.ui.transaction

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.core.raise.nullable
import com.ionspin.kotlin.bignum.integer.BigInteger.Companion.ZERO
import kosh.domain.entities.TransactionEntity
import kosh.domain.models.web3.Transaction
import kosh.domain.models.web3.gasPrice
import kosh.presentation.token.rememberNativeToken
import kosh.presentation.transaction.EstimateGasState
import kosh.presentation.transaction.GasPricesState
import kosh.presentation.transaction.GasSpeedState
import kosh.presentation.transaction.get
import kosh.presentation.transaction.rememberEstimateGas
import kosh.presentation.transaction.rememberGasPrices
import kosh.presentation.transaction.rememberGasSpeed
import kosh.ui.component.placeholder.placeholder
import kosh.ui.component.text.KeyValueRow
import kosh.ui.component.text.TextAmount
import kosh.ui.component.text.TextHeader
import kosh.ui.component.text.TextNumber
import kosh.ui.failure.AppFailureItem
import kosh.ui.transaction.calls.TokenAmountItem

@Composable
fun NetworkFeesCard(
    transaction: Transaction?,
    modifier: Modifier = Modifier,
    gasPrices: GasPricesState? = transaction?.chainId?.let { rememberGasPrices(it) },
    gasSpeed: GasSpeedState = rememberGasSpeed(),
    gasEstimation: EstimateGasState? = transaction?.let { rememberEstimateGas(transaction) },
) {
    val gasPrice = gasPrices?.prices?.get(gasSpeed.speed)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextHeader("Network Fees")

        var expanded by rememberSaveable { mutableStateOf(false) }

        val token = transaction?.chainId?.let { rememberNativeToken(it) }

        OutlinedCard(
            onClick = { expanded = !expanded }
        ) {
            gasPrices?.failure?.let {
                AppFailureItem(it) { gasPrices.retry() }
            } ?: gasEstimation?.failure?.let {
                AppFailureItem(it) { gasEstimation.retry() }
            } ?: run {
                TokenAmountItem(
                    token?.entity,
                    nullable {
                        ensureNotNull(gasPrice).gasPrice *
                                ensureNotNull(gasEstimation?.estimation).gas.toLong()
                    }
                ) {
                    Text("Max Amount")
                }
            }

            AnimatedVisibility(expanded) {
                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {

                    KeyValueRow(
                        modifier = Modifier.placeholder(transaction == null),
                        key = { Text("Estimated Gas Used") },
                        value = { TextNumber(gasEstimation?.estimation?.estimated ?: 0u) }
                    )

                    KeyValueRow(
                        modifier = Modifier.placeholder(transaction == null),
                        key = { Text("Gas Limit") },
                        value = { TextNumber(gasEstimation?.estimation?.gas ?: 0u) }
                    )

                    KeyValueRow(
                        modifier = Modifier.placeholder(gasPrice == null || token == null),
                        key = { Text("Max Base Gas Price") },
                        value = {
                            TextAmount(
                                token = token?.entity,
                                amount = gasPrice?.base,
                            )
                        }
                    )

                    KeyValueRow(
                        modifier = Modifier.placeholder(gasPrice == null || token == null),
                        key = { Text("Priority Gas Price") },
                        value = {
                            TextAmount(
                                token = token?.entity,
                                amount = gasPrice?.priority,
                            )
                        }
                    )

                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun NetworkFeesCard(
    transaction: TransactionEntity.Eip1559,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextHeader("Network Fees")

        var expanded by rememberSaveable { mutableStateOf(false) }

        val token = rememberNativeToken(transaction.networkId)

        OutlinedCard(
            onClick = { expanded = !expanded }
        ) {

            if (transaction.receipt != null) {
                val amount = (transaction.receipt?.gasPrice?.gasPrice ?: ZERO) *
                        (transaction.receipt?.gasUsed?.toLong() ?: 0L)

                TokenAmountItem(token.entity, amount) {
                    Text("Amount")
                }
            } else {
                val amount = transaction.gasPrice.gasPrice * transaction.gasLimit.toLong()
                TokenAmountItem(token.entity, amount) {
                    Text("Max Amount")
                }
            }

            AnimatedVisibility(expanded) {
                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    if (transaction.receipt != null) {
                        KeyValueRow(
                            key = { Text("Gas Used") },
                            value = { TextNumber(transaction.receipt?.gasUsed ?: 0u) }
                        )
                    }

                    KeyValueRow(
                        key = { Text("Gas Limit") },
                        value = { TextNumber(transaction.gasLimit) }
                    )

                    if (transaction.receipt != null) {
                        KeyValueRow(
                            key = { Text("Base Gas Price") },
                            value = {
                                TextAmount(
                                    token = token.entity,
                                    amount = transaction.receipt?.gasPrice?.base,
                                )
                            }
                        )
                    }

                    KeyValueRow(
                        key = { Text("Max Base Gas Price") },
                        value = {
                            TextAmount(
                                amount = transaction.gasPrice.base,
                                token = token.entity,
                            )
                        }
                    )

                    KeyValueRow(
                        key = { Text("Priority Gas Price") },
                        value = {
                            TextAmount(
                                amount = transaction.gasPrice.priority,
                                token = token.entity,
                            )
                        }
                    )

                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}
