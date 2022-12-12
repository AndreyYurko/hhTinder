# Vacanter
The Vacanter is a mobile application for matching employers with potential employees. Moreover, it also has a Desktop version for Employers.
<img src="https://user-images.githubusercontent.com/55112338/197409494-8b04a651-0cd0-43fc-a703-a393672411d6.svg" width="300" height="300">
# Sprints
- 1 sprint: 16.09.22-7.10.2022 ✅
- 2 sprint: 7.10.2022-28.10.2022 ✅
- 3 sprint: 28.10-18.11.2022 ✅
- 4 sprint: 18.11-09.12.2022 ⏳
# Build and run the container only for FastAPI(app.py)
Build the docker image using:
```
docker build . -t main 
```
Check whether you have successfully build the image:
```
docker images  
```
Generate the docker container using:
```
docker run --name main -p 8000:8000 main 
```
Check that your image is running:
```
docker ps -a 
```
Some usefull commands:

- ```docker stop main``` - stop the docker container
- ```docker rm main``` - remove image (before rebuild image step in case of error)
# Run the containers for FastAPI(app.py), Prometheus, Grafana
Run all 3 containers
```
docker compose up
```
Now you have access to those three containers and their respective ports:
- FastAPI(app.py): http://localhost:8000
- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000

To acces Grafana username ```admin``` password ```pass@123```

On the FastAPI, you can access ```localhost:8000/metrics``` endpoint to see the data Prometheus is scraping from it.

Example of Grafana visualization metrics:
![image](https://user-images.githubusercontent.com/55112338/205328257-87fac286-296b-487c-a1f7-be4b014d8d16.png)

# Documentation

### get("/")
```
{
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### get("/items/{item_id}")
```
{
  item_id: int
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### get("/next_worker/")
```
{
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### get("/next_vacancy/")
```
{
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### get("/cv/{cv_id}")
```
{
  cv_id: int
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### get("/vacancy/{vac_id}")
```
{
  vac_id: int
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### get("/history/vacancy/{job_id}")
```
{
  vacancy_id: int
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### get("/history/worker/{worker_id}")
```
{
  worker_id: int
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### post("/worker_like/")
```
{
  like: {
    job_id: int,
    worker_id: int,
  }
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### post("/vacancy_like/")
```
{
  like: {
    job_id: int,
    worker_id: int,
  }
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### get("/vacancy/vacancy_categories")
```
{
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### post("/login/token/{login}/{token}")
```
{
  login: str,
  token: str
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### post("/login/password/{login}/{password}")
```
{
  login: str,
  password: str
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### get("/user_role/{email}")
```
{
  email: str
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### get("/vacancy_preview_info/{email}")
```
{
  email: str
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### get("/cv_preview_info/{email}")
```
{
  email: str
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### get("/get_language_name_by_id/{id}")
```
{
  id: int
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### get("/get_grade_name_by_id/{id}")
```
{
  id: int
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### get("/get_gender_name_by_id/{id}")
```
{
  id: int
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### get("/get_location_name_by_id/{id}")
```
{
  id: int
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### get("/get_job_category_name_by_id/{id}")
```
{
  id: int
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### post("/add_vacancy/")
```
{
  vacancy: {
    id: int,
    cr_user: int,
    vac_name: str,
    vac_text: str,
    cr_date: datetime.datetime,
    vac_category: int,
    img_id: int
  }
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### post("/edit_vacancy/")
```
{
  vacancy: {
    id: int,
    cr_user: int,
    vac_name: str,
    vac_text: str,
    cr_date: datetime.datetime,
    vac_category: int,
    img_id: int
  }
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### post("/add_cv/")
```
{
  cv: {
    id: int,
    cr_user: int,
    cv_name: str,
    cv_text: str,
    cr_date: datetime.datetime,
    img_id: int,
    cv_category: int,
    salary: int,
    experience_content: str,
    education_content: str
  }
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### post("/edit_cv/")
```
{
  cv: {
    id: int,
    cr_user: int,
    cv_name: str,
    cv_text: str,
    cr_date: datetime.datetime,
    img_id: int,
    cv_category: int,
    salary: int,
    experience_content: str,
    education_content: str
  }
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### post("/add_profile/")
```
{
  profile: {
    id: int,
    name: str,
    surname: str,
    age: str,
    gender_id: int,
    img_id: int,
    cr_user: int
  }
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### post("/edit_profile/")
```
{
  profile: {
    id: int,
    name: str,
    surname: str,
    age: str,
    gender_id: int,
    img_id: int,
    cr_user: int
  }
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### get("/get_matched_users_for_vacancy/{id}")
```
{
  id: int
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### get("/get_matched_vacancies_for_user/{id}")
```
{
  id: int
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### post("/add_match/{user_id}/{vacancy_id}")
```
{
  user_id: int,
  vacancy_id: int
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### post("/get_vacancy_ids_by_filters/")
```
{
  vacancy_filters: {
    salary: int | None,
    is_fulltime: bool | None,
    is_distant: bool | None,
    location_id: int | None,
    grade_id: int | None
  }
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### post("/set_vacancy_filters/{vac_id}")
```
{
  vac_id: int
  vacancy_filters: {
    salary: int | None,
    is_fulltime: bool | None,
    is_distant: bool | None,
    location_id: int | None,
    grade_id: int | None
  }
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### post("/get_user_ids_by_filters/")
```
{
  user_filters: {
    salary: int | None,
    grade_id: int | None,
    languages_ids: List[int] | None
  }
  request: {
    headers: {
      token: string(token)
    }
  }
}
```

### post("/set_users_filters/{user_id}")
```
{
  user_id: int
  user_filters: {
    salary: int | None,
    grade_id: int | None,
    languages_ids: List[int] | None
  }
  request: {
    headers: {
      token: string(token)
    }
  }
}
```
