package br.com.brunoszczuk.otpemailfacade.service

import br.com.brunoszczuk.otpemailfacade.cache.CacheService
import br.com.brunoszczuk.otpemailfacade.cache.ValidateCache
import br.com.brunoszczuk.otpemailfacade.endpoints.ValidateRequest
import br.com.brunoszczuk.otpemailfacade.endpoints.ValidateResponse
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.net.URI
import java.util.*
import kotlin.random.Random

@Service
class ValidateEmailService(private val cacheService: CacheService) {
    val log = KotlinLogging.logger {}
    fun validate(validateRequest: ValidateRequest): ValidateResponse {
        log.info { "Validating email: ${validateRequest.email}" }
        val randomCode = Random.nextInt(100000, 999999).toString()
        log.info { "Generated code: $randomCode"}
        val validateCache = ValidateCache(validateRequest.email, randomCode, validateRequest.callbackUrl)
        val clientId = UUID.randomUUID()
        cacheService.save(clientId, validateCache)
        return ValidateResponse("Email validated", URI.create("/v1/otp/validate/").path +clientId )
    }


}