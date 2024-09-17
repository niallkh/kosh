package kosh.ui.component.icon

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.unit.dp
import kosh.domain.models.Address
import kosh.ui.component.colors.addressColor
import kosh.ui.component.theme.LocalIsDark
import okio.ByteString

@Composable
fun AccountIcon(
    address: Address,
    modifier: Modifier = Modifier,
) {
    val colorScheme = addressColor(address, LocalIsDark.current)

    Surface(
        modifier = modifier
            .size(40.dp),
        color = colorScheme.secondaryContainer,
        contentColor = colorScheme.primary,
        shape = CircleShape,
    ) {
        Icon(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize(), // TODO check
            imageVector = walletVector(address.bytes()),
            contentDescription = "WalletIcon",
        )
    }
}

private fun walletVector(
    address: ByteString,
    spriteSize: Int = 5,
) = ImageVector.Builder(
    name = "WalletVector",
    defaultWidth = 40.dp,
    defaultHeight = 40.dp,
    viewportWidth = spriteSize.toFloat(),
    viewportHeight = spriteSize.toFloat(),
    autoMirror = false,
).apply {
    materialPath {
        val sequence = address.sequence.iterator()
        for (x in 0..(spriteSize / 2)) {
            for (y in 0 until spriteSize) {
                val byte = sequence.next()
                if (byte % 2 == 1) {
                    drawRect(
                        x = x,
                        y = y
                    )

                    val mx = spriteSize - x - 1
                    if (x != mx) {
                        drawRect(
                            x = mx,
                            y = y
                        )
                    }
                }
            }
        }
    }
}.build()

private val ByteString.sequence: Sequence<Byte>
    inline get() = sequence { repeat(size) { yield(get(it)) } }

private fun PathBuilder.drawRect(
    x: Int,
    y: Int,
) {
    moveTo(x.toFloat(), y.toFloat())
    horizontalLineToRelative(1f)
    verticalLineToRelative(1f)
    horizontalLineToRelative(-1f)
    verticalLineToRelative(-1f)
    close()
}
