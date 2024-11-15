package kosh.app.utils

import kosh.eth.abi.keccak256
import kotlinx.io.bytestring.toByteString
import kotlinx.io.bytestring.toNSData
import platform.Foundation.NSData

public fun keccak256(data: NSData): NSData = data.toByteString().keccak256().toNSData()
