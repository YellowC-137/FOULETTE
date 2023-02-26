# Foulette

Summary
현재 위치를 중심으로 음식점을 하나 추천하는 어플리케이션

구글지도를 사용해서 근처의 맛집들을 검색후 그중 하나를 선택하여
경로를 보여주고 이를 히스토리에 저장해줍니다.

https://play.google.com/store/apps/details?id=yellowc.example.foulette

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

![KakaoTalk_20230220_145209640_01](https://user-images.githubusercontent.com/55780312/221406366-1c0b98f1-3b95-4f94-b88b-6e122076f7d8.jpg)

- 현재 내 위치에서 1Km 근방의 음식점 검색

![KakaoTalk_20230220_145209640_02](https://user-images.githubusercontent.com/55780312/221406373-4edddb84-e6c6-4c2b-b914-b79aeff3cff1.jpg)

- 해당 음식점 까지 최단 도보 길찾기 기능 제공

![KakaoTalk_20230220_145209640_03](https://user-images.githubusercontent.com/55780312/221406382-c7b50aed-bc9f-4918-a643-fbbc3d91e0a1.jpg)

- 찾은 음식점의 정보 저장



![image](https://user-images.githubusercontent.com/55780312/211495515-205502c2-4f94-4263-80fd-228afe89666c.png)

