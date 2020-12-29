package com.bankBackend
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.auth.*
import config.DatabaseFactory
import io.ktor.auth.jwt.jwt
import io.ktor.features.*
import io.ktor.serialization.*
import io.ktor.util.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import repo.*
import rest.restReader
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)


@KtorExperimentalAPI
@Suppress("unused")
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(ContentNegotiation) {
        json()
    }

    DatabaseFactory.init()

    install(AutoHeadResponse)

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }

    restReader(
        RepoDSL(readerTable),
        Reader.serializer(),
        RepoDSL(myBookTable),
        MyBook.serializer(),
        RepoDSL(myDebtTable),
        MyDebt.serializer()
    )

    transaction {
        SchemaUtils.create(readerTable)
        SchemaUtils.create(myBookTable)
        SchemaUtils.create(myDebtTable)
    }

    routing {
        get("/api") {
            call.respondText("Version: 0.0.1", contentType = ContentType.Text.Plain)
        }
    }
}

