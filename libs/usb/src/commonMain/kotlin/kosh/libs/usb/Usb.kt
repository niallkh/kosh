package kosh.libs.usb

import kosh.libs.transport.Transport

interface Usb : Transport<UsbConfig>

class PermissionNotGrantedException : RuntimeException("Usb permission not granted for device")
class UsbInterfaceNotClaimedException : RuntimeException("Usb interface not claimed")
