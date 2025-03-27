# 커밋
## 커밋 1 : 프로젝트 생성
## 커밋 2 : 공개 버킷 생성, S3 라이브러리 추가, 버킷 리스트 출력
## 커밋 3 : 버킷의 파일을 URL로 접근 가능하도록 수정
```
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "",
            "Effect": "Allow",
            "Principal": "*",
            "Action": "s3:GetObject",
            "Resource": "arn:aws:s3:::bucket-name/*"
        }
    ]
}
```
## 커밋 4 : upload1 구현
## 커밋 5 : delete1 구현
## 커밋 6 : modify1 구현
## 커밋 7 : presigned 발급, 사용
```angular2html
[
    {
        "AllowedHeaders": [
            "*"
        ],
        "AllowedMethods": [
            "GET",
            "PUT",
            "POST",
            "DELETE",
            "HEAD"
        ],
        "AllowedOrigins": [
            "http://localhost:8080"
        ],
        "ExposeHeaders": [
            "x-amz-server-side-encryption",
            "x-amz-request-id",
            "x-amz-id-2"
        ],
        "MaxAgeSeconds": 3000
    }
]
```
## 커밋 8 : 최종