import com.eygraber.uri.Uri
import kotlin.test.Test

private val wcPairUri =
    "wc:5fff39b8c7cf3927a7f716ef3560499ca05837d75e124906bd639f44487190bb@2?expiryTimestamp=1717348374&relay-protocol=irn&symKey=02b6277f5abfb20fae3ae6a58cf13f364517f96499e5d3ed9eac675a79287b8e"
private val wcRequestUri =
    "wc:cd95c2af4d0d6adbac6c687e95ee7cfbc5a50673d2b929b37129c814bd1c8c80@2/wc?requestId=1717347851592492&sessionTopic=9ecb4a637f3d5983175384c213390df1f9d4d3c0586d88332785d525d748ffce"

class DeeplinkTest {

    @Test
    fun test() {
        val uri = Uri.parse(wcRequestUri)
        uri
    }
}
