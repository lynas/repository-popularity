# Github repository popularity

![Build](https://github.com/lynas/repository-popularity/actions/workflows/build.yml/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=sazzad-islam-eu_repository-popularity&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=sazzad-islam-eu_repository-popularity)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=sazzad-islam-eu_repository-popularity&metric=coverage)](https://sonarcloud.io/summary/new_code?id=sazzad-islam-eu_repository-popularity)

- This project returns popularity of repository in github
- Input repository `language` and `lastUpdatedat`
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
- Redis used to cache user request response so that remote server ( Github Api ) does not need to hit for same user request
- Cache `TTL` set 10 minutes, it can be change from `application.yml`
- Keeping the polymorphism in mind service interface `ScoringService` is used. Currently `GithubRepositoryScoringService` is the implementation it can be implemented by other repository hosting
- Calculation is done from `ScoreCalculator` taking weight of start and from `application.yml` so that algorithm can be easily updated
- Used virtual thread it should be less resource hungry

## Future improvements
- Authentication and authorization can be added
- More granular exception handling can be implemented
- Application can be converted as non blocking api