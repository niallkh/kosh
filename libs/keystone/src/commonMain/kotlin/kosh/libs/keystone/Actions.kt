package kosh.libs.keystone

enum class Actions(val value: Int) {
    CMD_ECHO_TEST(1),
    CMD_RESOLVE_UR(2),
    CMD_CHECK_LOCK_STATUS(3),
    CMD_EXPORT_ADDRESS(4),
    CMD_GET_DEVICE_VERSION(5),
}
