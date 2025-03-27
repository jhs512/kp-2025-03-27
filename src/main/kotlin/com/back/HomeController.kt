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

    @GetMapping("/makePresignedUrl")
    fun makePresignedUrl(): String {
        val bucketName = "bucket-jhs512-01"
        val key = "a.png"

        val presignedRequest = software.amazon.awssdk.services.s3.presigner.S3Presigner.create()
            .presignPutObject { builder ->
                builder
                    .putObjectRequest { putObjectRequest ->
                        putObjectRequest
                            .bucket(bucketName)
                            .key(key)
                    }
                    .signatureDuration(java.time.Duration.ofMinutes(10))
            }

        return presignedRequest.url().toString()
    }

    @GetMapping("/usePresignedUrl")
    fun usePresignedUrl(): String {
        return """
            <html>
            <head>
                <title>파일 업로드</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        max-width: 600px;
                        margin: 0 auto;
                        padding: 20px;
                    }
                    .upload-form {
                        border: 1px solid #ddd;
                        padding: 20px;
                        border-radius: 5px;
                    }
                    .file-input {
                        margin: 10px 0;
                    }
                    .submit-btn {
                        background-color: #4CAF50;
                        color: white;
                        padding: 10px 20px;
                        border: none;
                        border-radius: 4px;
                        cursor: pointer;
                    }
                    .submit-btn:hover {
                        background-color: #45a049;
                    }
                </style>
            </head>
            <body>
                <div class="upload-form">
                    <h2>PNG 파일 업로드</h2>
                    <form id="uploadForm" enctype="multipart/form-data">
                        <div class="file-input">
                            <input type="file" id="fileInput" accept=".png" required>
                        </div>
                        <button type="submit" class="submit-btn">업로드</button>
                    </form>
                </div>
                <script>
                    const presignedUrl = 'https://bucket-jhs512-01.s3.ap-northeast-2.amazonaws.com/a.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20250327T062344Z&X-Amz-SignedHeaders=host&X-Amz-Credential=AKIA4NAXQ3UKTXHDRJ4M%2F20250327%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Expires=600&X-Amz-Signature=00befe574bdfda516d9950d7944cfa904145268cac75e98ad5c7ec0d3db8fc15';
                    
                    document.getElementById('uploadForm').addEventListener('submit', async (e) => {
                        e.preventDefault();
                        
                        const fileInput = document.getElementById('fileInput');
                        const file = fileInput.files[0];
                        
                        if (!file) {
                            alert('파일을 선택해주세요.');
                            return;
                        }
                        
                        if (!file.type.includes('png')) {
                            alert('PNG 파일만 업로드 가능합니다.');
                            return;
                        }
                        
                        try {
                            const response = await fetch(presignedUrl, {
                                method: 'PUT',
                                body: file,
                                headers: {
                                    'Content-Type': file.type
                                }
                            });
                            
                            if (response.ok) {
                                alert('파일이 성공적으로 업로드되었습니다.');
                                fileInput.value = '';
                            } else {
                                throw new Error('업로드 실패');
                            }
                        } catch (error) {
                            alert('업로드 중 오류가 발생했습니다.');
                            console.error('Error:', error);
                        }
                    });
                </script>
            </body>
            </html>
        """.trimIndent()
    }
}