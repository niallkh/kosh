package kosh.app.di

import io.ktor.client.HttpClient

interface NetworkComponent {

    val httpClient: HttpClient
}
