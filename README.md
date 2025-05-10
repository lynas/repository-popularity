# Github repository popularity

![Build](https://github.com/lynas/repository-popularity/actions/workflows/build-and-test.yml/badge.svg)

- This project returns popularity of repository in github
- Input repository language and lastUpdated at
- How to run
  - Run docker desktop
  - Open terminal and execute `./gradlew bootRun`
- curl command to make api request
``` 
curl --location 'http://localhost:8088/repository-scores?language=java&lastUpdatedAt=2024-04-01' 
```
- Sample response
``` 
{
    "data": [
        {
            "name": "GhidraMCP",
            "score": 5336.0,
            "url": "https://github.com/LaurieWired/GhidraMCP"
        },
        {
            "name": "fastexcel",
            "score": 5187.0,
            "url": "https://github.com/fast-excel/fastexcel"
        }
    ]
} 
```

## Design Decision 
- Project is created with spring boot web and redis
- Redis used to cache user request response so that remote server ( github api ) does not need to hit for same user request
- Cache TTL set 10 minutes, it can be change from `CacheConfig.java`
- Interface implementation 

## Future improvements