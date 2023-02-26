# Foulette

Summary
현재 위치를 중심으로 음식점을 하나 추천하는 어플리케이션

구글지도를 사용해서 근처의 맛집들을 검색후 그중 하나를 선택하여
경로를 보여주고 이를 히스토리에 저장해줍니다.

기술 스택
- Android-Kotlin
- MVVM & AAC
- DataBinding
- Flow
- Room
- LuckyWheel (https://github.com/mmoamenn/LuckyWheel_Android)
- Hilt
- Retrofit2 , OkHttp3
- Google Maps API
- T Map API


Structure
```
├── util
├── data
│   ├── local
│   │   ├── dao
│   │   ├── database
│   │   ├── datasource
│   │   ├── datasourceimpl
│   │   ├── entity
│   │   ├── paging
│   │   └── repositoryimpl
│   ├── remote
│   │   ├── api
│   │   ├── datasource
│   │   ├── datasourceimpl
│   │   ├── repositoryimpl
│   │   └── response
├── domain
│   ├── mapper
│   ├── model
│   ├── repository
│   └── usecase
├── ui
│   ├── base
│   ├── history
│   ├── main
│   ├── map
│   ├── roulette
│   ├── search
│   └── historymap
└── di
```

기능🔧

![KakaoTalk_20230220_145209640_01](https://user-images.githubusercontent.com/55780312/221406045-c4ec1fa6-8d96-4d4c-9d74-154373ebeef4.jpg)

- 현재 내 위치에서 1Km 근방의 음식점 검색

![KakaoTalk_20230220_145209640_02](https://user-images.githubusercontent.com/55780312/221406053-496291c3-9aae-48d8-8709-9197ba7d992f.jpg)
- 해당 음식점 까지 최단 도보 길찾기 기능 제공

![KakaoTalk_20230220_145209640_03](https://user-images.githubusercontent.com/55780312/221406057-d8d0e642-a828-43e6-851a-62ed36ac8e01.jpg)

- 찾은 음식점의 정보 저장


![KakaoTalk_20230220_145209640_02](https://user-images.githubusercontent.com/55780312/221406053-496291c3-9aae-48d8-8709-9197ba7d992f.jpg)



![image](https://user-images.githubusercontent.com/55780312/211495515-205502c2-4f94-4263-80fd-228afe89666c.png)

