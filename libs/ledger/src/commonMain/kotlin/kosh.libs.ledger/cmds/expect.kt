package kosh.libs.ledger.cmds

import kosh.libs.ledger.StatusWord

fun StatusWord.expectToBe(vararg expected: StatusWord) {
    require(expected.any { it == this }) { "Unexpected Status Word: expected=$expected, actual=$this" }
}
