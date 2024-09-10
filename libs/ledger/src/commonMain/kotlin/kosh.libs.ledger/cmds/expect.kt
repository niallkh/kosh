package kosh.libs.ledger.cmds

import kosh.libs.ledger.StatusWord

fun StatusWord.expectToBe(expected: StatusWord) {
    require(this == expected) { "Unexpected Status Word: expected=$expected, actual=$this" }
}
