package kosh.ui.resources

import kosh.domain.serializers.Uri

public object Icons {
    public fun icon(name: String): Uri = Uri.parse("res://files/icons/$name.webp")
}

