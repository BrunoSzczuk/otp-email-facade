package br.com.brunoszczuk.otpemailfacade

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class OtpEmailFacadeApplication

fun main(args: Array<String>) {
	runApplication<OtpEmailFacadeApplication>(*args)
}
