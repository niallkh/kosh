package kosh.data.web3

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Metadata(
    val name: String? = null,
    val symbol: String? = null,
    val description: String? = null,
    val image: String? = null,
    val decimals: UByte? = null,
    @SerialName("external_url")
    val externalUrl: String? = null,
    val attributes: List<Attribute> = emptyList(),
    @SerialName("animation_url")
    val animationUrl: String? = null,
    @SerialName("youtube_url")
    val youtubeUrl: String? = null,
    @SerialName("background_color")
    val backgroundColor: String? = null,
) {

    @Serializable
    data class Attribute(
        @SerialName("trait_type")
        val traitType: String? = null,
        val value: String? = null,
        @SerialName("display_type")
        val displayType: String? = null,
    )
}
