package kosh.ui.component.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.datetime.Instant
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.dateWithTimeIntervalSince1970

@Composable
actual fun formatDateTime(date: Instant): String {
    val dateFormatter = remember {
        NSDateFormatter().apply {
            dateStyle = 2u
            timeStyle = 1u
        }
    }

    return remember(date) {
        val nsDate = NSDate.dateWithTimeIntervalSince1970(date.epochSeconds.toDouble())
        dateFormatter.stringFromDate(nsDate)
    }
}
