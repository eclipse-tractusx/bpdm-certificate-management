package org.eclipse.tractusx.bpdmcertificatemanagementparent.dto

import io.swagger.v3.oas.annotations.media.Schema

data class PageDto<T>(

    @get:Schema(description = "Total number of all results in all pages")
    val totalElements: Long,

    @get:Schema(description = "Total number pages")
    val totalPages: Int,

    @get:Schema(description = "Current page number")
    val page: Int,

    @get:Schema(description = "Number of results in the page")
    val contentSize: Int,

    @get:Schema(description = "Collection of results in the page")
    val content: Collection<T>
)
