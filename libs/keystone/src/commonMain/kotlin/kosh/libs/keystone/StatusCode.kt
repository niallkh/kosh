package kosh.libs.keystone

enum class StatusCode(val code: Long) {
    RSP_SUCCESS_CODE(0x00000000), // Response success code
    RSP_FAILURE_CODE(0x00000001), // Response failure code
    PRS_INVALID_TOTAL_PACKETS(0x00000002), // Invalid total packets
    PRS_INVALID_INDEX(0x00000003), // Invalid index
    PRS_PARSING_REJECTED(0x00000004), // Parsing rejected
    PRS_PARSING_ERROR(0x00000005), // Parsing error
    PRS_PARSING_DISALLOWED(0x00000006), // Parsing disallowed
    PRS_PARSING_UNMATCHED(0x00000007), // Parsing unmatched
    PRS_PARSING_MISMATCHED_WALLET(0x00000008), // Parsing mismatched wallet
    PRS_PARSING_VERIFY_PASSWORD_ERROR(0x00000009), // Verify password error
    PRS_EXPORT_ADDRESS_UNSUPPORTED_CHAIN(0x0000000A), // Export address unsupported chain
    PRS_EXPORT_ADDRESS_INVALID_PARAMS(0x0000000B), // Export address invalid params
    PRS_EXPORT_ADDRESS_ERROR(0x0000000C), // Export address error
    PRS_EXPORT_ADDRESS_DISALLOWED(0x0000000D), // Export address disallowed
    PRS_EXPORT_ADDRESS_REJECTED(0x0000000E), // Export address rejected
    PRS_EXPORT_ADDRESS_BUSY(0x0000000F), // Export address busy
    ERR_DEVICE_NOT_OPENED(0xA0000001), // The USB device cannot be connected
    ERR_DEVICE_NOT_FOUND(0xA0000002), // The USB device cannot be found
    ERR_RESPONSE_STATUS_NOT_OK(0xA0000003), // The response status is not ok
    ERR_TIMEOUT(0xA0000004), // The request timed out
    ERR_DATA_TOO_LARGE(0xA0000005), // The data is too large
    ERR_NOT_SUPPORTED(0xA0000006), // The USB device is not supported
    ERR_BUFFER_MISMATCH(0xA0000007), // The buffer mismatched
    RSP_MAX_VALUE(0xFFFFFFFF), // Max value
}

val StatusCode.isSuccess: Boolean
    inline get() = this == StatusCode.RSP_SUCCESS_CODE

fun StatusCode.expectSuccess(payload: String = "") {
    require(isSuccess) { "Expected RSP_SUCCESS_CODE, but got $this: $payload" }
}
