package com.back

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import software.amazon.awssdk.services.s3.S3Client
import java.io.File

@RestController
class HomeController(
    private val s3Client: S3Client,
) {
    @GetMapping("/")
    fun main() = s3Client.listBuckets().buckets().map { it.name() }

    @GetMapping("/upload1")
    fun upload1(): String {
        val filePath = """
            C:\Users\jhs512\Pictures\Screenshots\스크린샷 2024-01-29 092348.png
        """.trimIndent()

        val bucketName = "bucket-jhs512-01"
        val key = "screenshots/스크린샷 2024-01-29 092348.png"

        val file = File(filePath)
        val requestBody = software.amazon.awssdk.core.sync.RequestBody.fromFile(file)

        val putObjectRequest = software.amazon.awssdk.services.s3.model.PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build()

        s3Client.putObject(putObjectRequest, requestBody)

        return "완료"
    }

    @GetMapping("/delete1")
    fun delete1(): String {
        val bucketName = "bucket-jhs512-01"
        val key = "screenshots/스크린샷 2024-01-29 092348.png"

        val deleteObjectRequest = software.amazon.awssdk.services.s3.model.DeleteObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build()

        s3Client.deleteObject(deleteObjectRequest)

        return "완료"
    }

    @GetMapping("/modify1")
    fun modify1(): String {
        val filePath = """
            C:\Users\jhs512\Pictures\Screenshots\스크린샷 2024-03-19 205456.png
        """.trimIndent()

        val bucketName = "bucket-jhs512-01"
        val key = "screenshots/스크린샷 2024-01-29 092348.png"

        val file = File(filePath)
        val requestBody = software.amazon.awssdk.core.sync.RequestBody.fromFile(file)

        val putObjectRequest = software.amazon.awssdk.services.s3.model.PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build()

        s3Client.putObject(putObjectRequest, requestBody)

        return "완료"
    }
}