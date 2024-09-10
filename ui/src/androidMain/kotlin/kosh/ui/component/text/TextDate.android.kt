package kosh.ui.component.text

import android.text.format.DateUtils
import android.text.format.DateUtils.formatDateTime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import kotlinx.datetime.Instant

@Composable
actual fun formatDateTime(date: Instant): String {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    return remember(configuration, context, date) {
        formatDateTime(
            context,
            date.toEpochMilliseconds(),
            DateUtils.FORMAT_SHOW_DATE or
                    DateUtils.FORMAT_SHOW_TIME or
                    DateUtils.FORMAT_SHOW_YEAR,
        )
    }
}
