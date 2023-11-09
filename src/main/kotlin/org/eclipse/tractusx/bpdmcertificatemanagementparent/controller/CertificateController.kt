package org.eclipse.tractusx.bpdmcertificatemanagementparent.controller

import org.eclipse.tractusx.bpdmcertificatemanagementparent.dto.CertificateMatchVerboseDto
import org.eclipse.tractusx.bpdmcertificatemanagementparent.dto.PageDtoCertificateMatchVerboseDto
import org.eclipse.tractusx.bpdmcertificatemanagementparent.service.CertificateService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CertificateController(
        val certificateService: CertificateService
) {

    @GetMapping("/api/catena/certificate/{bpn}")
    fun getCertificatesPaginated(
        @PathVariable bpn: String,
        @RequestParam(name = "page", defaultValue = "0") page: Int,
        @RequestParam(name = "size", defaultValue = "10") size: Int
    ): ResponseEntity<PageDtoCertificateMatchVerboseDto> {
        return ResponseEntity.ok().body(certificateService.getCertificatesPaginated(bpn,page,size))
    }

    @PostMapping("/api/catena/certificate")
    fun createCertificate(@RequestBody certificate: CertificateMatchVerboseDto): ResponseEntity<CertificateMatchVerboseDto> {
        // TODO: Implement this method to create a new certificate in PostgreSQL

        return ResponseEntity.ok().build()
    }
}