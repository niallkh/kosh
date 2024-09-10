import io.ktor.client.*
import io.ktor.client.plugins.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement

public fun DefaultBundler(
    httpClient: HttpClient,
    bundler: String,
    web3Provider: Web3Provider,
): Bundler = DefaultBundler(
    jsonRpcClient = DefaultJsonRpcClient(
        httpClient = httpClient.config {
            defaultRequest { url(bundler) }
        },
        json = web3Provider.json
    ),
    web3Provider = web3Provider,
)

public class DefaultBundler internal constructor(
    private val jsonRpcClient: JsonRpcClient,
    private val web3Provider: Web3Provider,
) : Bundler,
    Web3Provider by web3Provider,
    JsonRpcClient by jsonRpcClient {

    override val json: Json
        get() = jsonRpcClient.json

    override suspend fun call(
        method: String,
        vararg params: JsonElement,
    ): JsonElement = jsonRpcClient.call(method, *params)

    public override suspend fun sendUserOperation(
        userOperation: UserOperation,
        entryPoint: Address,
    ): Hash = withContext(Dispatchers.Default) {
        require(userOperation.signature != Value.Bytes.EMPTY)
        val result = call(
            method = "eth_sendUserOperation",
            json.encodeToJsonElement(userOperation),
            json.encodeToJsonElement(Address.serializer(), entryPoint),
        )
        json.decodeFromJsonElement(Hash.serializer(), result)
    }

    public override suspend fun estimateUserOperationGas(
        userOperation: UserOperation,
        entryPoint: Address,
    ): UserOperationGas = withContext(Dispatchers.Default) {
        val result = call(
            method = "eth_estimateUserOperationGas",
            json.encodeToJsonElement(userOperation),
            json.encodeToJsonElement(Address.serializer(), entryPoint),
        )
        json.decodeFromJsonElement(result)
    }

    public override suspend fun getUserOperationReceipt(
        userOpHash: Hash,
    ): UserOperationReceipt = withContext(Dispatchers.Default) {
        val result = call(
            method = "eth_getUserOperationReceipt",
            json.encodeToJsonElement(Hash.serializer(), userOpHash),
        )
        json.decodeFromJsonElement(result)
    }

    public override suspend fun supportedEntryPoints(): List<Address> =
        withContext(Dispatchers.Default) {
            val result = call(
                method = "eth_supportedEntryPoints",
            )
            json.decodeFromJsonElement(ListSerializer(Address.serializer()), result)
        }
}
