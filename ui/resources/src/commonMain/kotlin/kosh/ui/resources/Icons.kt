package kosh.ui.resources

import kosh.domain.models.Uri

public object Icons {
    public fun icon(name: String): Uri = Uri("res://files/icons/$name.webp")
}

