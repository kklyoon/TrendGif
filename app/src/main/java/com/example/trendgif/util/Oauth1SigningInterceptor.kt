package com.example.trendgif.util

import okhttp3.*
import okio.Buffer
import okio.ByteString
import java.io.IOException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


class Oauth1SigningInterceptor private constructor(
    private val consumerKey: String,
    private val consumerSecret: String,
    private val accessToken: String,
    private val accessSecret: String,
    private val random: Random,
    private val clock: Clock
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(signRequest(chain.request()))
    }

    @Throws(IOException::class)
    fun signRequest(request: Request): Request {
        val nonce = ByteArray(32)
        random.nextBytes(nonce)
        val oauthNonce = ByteString.of(*nonce).base64().replace("\\W".toRegex(), "")
        val oauthTimestamp = clock.millis()
        val consumerKeyValue: String = UrlEscapeUtils.escape(consumerKey)
        val accessTokenValue: String = UrlEscapeUtils.escape(accessToken)
        val parameters: SortedMap<String, String> =
            TreeMap()
        parameters[OAUTH_CONSUMER_KEY] = consumerKeyValue
        parameters[OAUTH_ACCESS_TOKEN] = accessTokenValue
        parameters[OAUTH_NONCE] = oauthNonce
        parameters[OAUTH_TIMESTAMP] = oauthTimestamp
        parameters[OAUTH_SIGNATURE_METHOD] = OAUTH_SIGNATURE_METHOD_VALUE
        parameters[OAUTH_VERSION] = OAUTH_VERSION_VALUE
        val url: HttpUrl = request.url()
        for (i in 0 until url.querySize()) {
            parameters[UrlEscapeUtils.escape(url.queryParameterName(i))] =
                UrlEscapeUtils.escape(url.queryParameterValue(i))
        }
        val body = Buffer()
        val requestBody: RequestBody? = request.body()
        requestBody?.writeTo(body)
        while (!body.exhausted()) {
            val keyEnd = body.indexOf('='.toByte())
            check(keyEnd != -1L) { "Key with no value: " + body.readUtf8() }
            val key = body.readUtf8(keyEnd)
            body.skip(1) // Equals.
            val valueEnd = body.indexOf('&'.toByte())
            val value =
                if (valueEnd == -1L) body.readUtf8() else body.readUtf8(valueEnd)
            if (valueEnd != -1L) body.skip(1) // Ampersand.
            parameters[key] = value
        }
        val base = Buffer()
        val method: String = request.method()
        base.writeUtf8(method)
        base.writeByte('&'.toInt())
        base.writeUtf8(
            UrlEscapeUtils.escape(request.url().newBuilder().query(null).build().toString())
        )
        base.writeByte('&'.toInt())
        var first = true
        for ((key, value) in parameters) {
            if (!first) base.writeUtf8(UrlEscapeUtils.escape("&"))
            first = false
            base.writeUtf8(UrlEscapeUtils.escape(key))
            base.writeUtf8(UrlEscapeUtils.escape("="))
            base.writeUtf8(UrlEscapeUtils.escape(value))
        }
        val signingKey: String =
            UrlEscapeUtils.escape(consumerSecret).toString() + "&" + UrlEscapeUtils.escape(
                accessSecret
            )
        val keySpec =
            SecretKeySpec(signingKey.toByteArray(), "HmacSHA1")
        val mac: Mac
        try {
            mac = Mac.getInstance("HmacSHA1")
            mac.init(keySpec)
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalStateException(e)
        } catch (e: InvalidKeyException) {
            throw IllegalStateException(e)
        }
        val result = mac.doFinal(base.readByteArray())
        val signature = ByteString.of(*result).base64()
        val authorization =
            ("OAuth " + OAUTH_CONSUMER_KEY + "=\"" + consumerKeyValue + "\", " + OAUTH_NONCE + "=\""
                    + oauthNonce + "\", " + OAUTH_SIGNATURE + "=\"" + UrlEscapeUtils.escape(
                signature
            )
                    + "\", " + OAUTH_SIGNATURE_METHOD + "=\"" + OAUTH_SIGNATURE_METHOD_VALUE + "\", "
                    + OAUTH_TIMESTAMP + "=\"" + oauthTimestamp + "\", " + OAUTH_ACCESS_TOKEN + "=\""
                    + accessTokenValue + "\", " + OAUTH_VERSION + "=\"" + OAUTH_VERSION_VALUE + "\"")
        return request.newBuilder().addHeader("Authorization", authorization).build()
    }

    class Builder {
        private var consumerKey: String? = null
        private var consumerSecret: String? = null
        private var accessToken: String? = null
        private var accessSecret: String? = null
        private var random: Random = SecureRandom()
        private var clock = Clock()
        fun consumerKey(consumerKey: String?): Builder {
            if (consumerKey == null) throw NullPointerException("consumerKey = null")
            this.consumerKey = consumerKey
            return this
        }

        fun consumerSecret(consumerSecret: String?): Builder {
            if (consumerSecret == null) throw NullPointerException("consumerSecret = null")
            this.consumerSecret = consumerSecret
            return this
        }

        fun accessToken(accessToken: String?): Builder {
            if (accessToken == null) throw NullPointerException("accessToken == null")
            this.accessToken = accessToken
            return this
        }

        fun accessSecret(accessSecret: String?): Builder {
            if (accessSecret == null) throw NullPointerException("accessSecret == null")
            this.accessSecret = accessSecret
            return this
        }

        fun random(random: Random?): Builder {
            if (random == null) throw NullPointerException("random == null")
            this.random = random
            return this
        }

        private fun clock(clock: Clock?): Builder {
            if (clock == null) throw NullPointerException("clock == null")
            this.clock = clock
            return this
        }

        fun build(): Oauth1SigningInterceptor {
            checkNotNull(consumerKey) { "consumerKey not set" }
            checkNotNull(consumerSecret) { "consumerSecret not set" }
            checkNotNull(accessToken) { "accessToken not set" }
            checkNotNull(accessSecret) { "accessSecret not set" }
            return Oauth1SigningInterceptor(
                consumerKey!!, consumerSecret!!, accessToken!!, accessSecret!!,
                random, clock
            )
        }
    }

    /** Simple clock like class, to allow time mocking.  */
    internal class Clock {
        /** Returns the current time in milliseconds divided by 1K.  */
        fun millis(): String {
            return java.lang.Long.toString(System.currentTimeMillis() / 1000L)
        }
    }

    companion object {
        private const val OAUTH_CONSUMER_KEY = "oauth_consumer_key"
        private const val OAUTH_NONCE = "oauth_nonce"
        private const val OAUTH_SIGNATURE = "oauth_signature"
        private const val OAUTH_SIGNATURE_METHOD = "oauth_signature_method"
        private const val OAUTH_SIGNATURE_METHOD_VALUE = "HMAC-SHA1"
        private const val OAUTH_TIMESTAMP = "oauth_timestamp"
        private const val OAUTH_ACCESS_TOKEN = "oauth_token"
        private const val OAUTH_VERSION = "oauth_version"
        private const val OAUTH_VERSION_VALUE = "1.0"
    }

}