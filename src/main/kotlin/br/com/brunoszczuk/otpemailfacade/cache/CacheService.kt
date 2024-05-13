package br.com.brunoszczuk.otpemailfacade.cache

import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CacheService {
    private val cache = mutableMapOf<UUID, ValidateCache>()

    fun save(key: UUID, validateCache: ValidateCache) {
        cache[key] = validateCache
    }

    fun get(key: UUID): ValidateCache? {
        return cache[key]
    }

    fun remove(key: UUID) {
        cache.remove(key)
    }
}

data class ValidateCache(
    val email: String,
    val randomCode: String,
    val callbackUrl : String,
    val valid: Boolean = false
)