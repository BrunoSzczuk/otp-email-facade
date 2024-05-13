package br.com.brunoszczuk.otpemailfacade.controller

import br.com.brunoszczuk.otpemailfacade.cache.CacheService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import java.util.UUID

@Controller
@RequestMapping("/v1/validate")
class ValidateController(private val cacheService: CacheService) {

    @PostMapping("/confirm-validate")
    fun confirmValidate(@ModelAttribute("clientIdSession") clientIdSession : UUID, @ModelAttribute("code") code: String) : ModelAndView{
        val cache = cacheService.get(clientIdSession)
        if (cache?.randomCode == code) {
            cacheService.save(clientIdSession, cache.copy(valid = true))
            return ModelAndView("sucesso")
        }
        return ModelAndView("error")
        //return "redirect:/v1/otp/validate/$clientIdSession?error=invalid-code"
    }
}