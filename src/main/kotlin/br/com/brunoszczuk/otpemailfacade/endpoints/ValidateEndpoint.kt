package br.com.brunoszczuk.otpemailfacade.endpoints

import br.com.brunoszczuk.otpemailfacade.cache.CacheService
import br.com.brunoszczuk.otpemailfacade.service.ValidateEmailService
import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.ResponseEntity
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import java.util.UUID

@RestController
class ValidateEndpoint(private val cacheService: CacheService, private val validateService: ValidateEmailService) {


    @PostMapping("/v1/validate")
    fun validate(@RequestBody request: ValidateRequest) = ResponseEntity.ok().body(validateService.validate(request))

    @GetMapping("/v1/otp/validate/{client-id-session}")
    fun otpValidate(@PathVariable("client-id-session") clientId : UUID, modelMap: ModelMap) : ModelAndView{
        val cache = cacheService.get(clientId)
        cache?.let {
            modelMap.addAttribute("clientIdSession", clientId)
            return ModelAndView("waiting-for-validation", modelMap)
        }
        return ModelAndView("error")
    }

}


data class ValidateRequest(
    val email: String,
    val strategy: ValidationStrategy = ValidationStrategy.EMAIL,
    val secret: String,
    val callbackUrl: String
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ValidateResponse(
    val message: String?,
    val url : String
)
enum class ValidationStrategy {
    EMAIL
}